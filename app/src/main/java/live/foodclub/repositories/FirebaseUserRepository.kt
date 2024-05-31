package live.foodclub.repositories

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import live.foodclub.di.IoDispatcher
import live.foodclub.domain.models.auth.ConversationModel
import live.foodclub.domain.models.auth.FirebaseUserModel
import live.foodclub.domain.models.auth.MessageModel
import live.foodclub.domain.models.profile.SimpleUserModel
import live.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import live.foodclub.utils.helpers.Resource
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject


class FirebaseUserRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val firebaseMessaging: FirebaseMessaging,
) {
    suspend fun saveUserToFirestore(firebaseUserModel: FirebaseUserModel) =
        withContext(ioDispatcher) {
            firestore.collection(USERS).document(firebaseUserModel.userID.toString())
                .set(firebaseUserModel)
                .addOnSuccessListener {
                    Log.w(TAG, "saveUserToFirestore: SUCCESS")
                }.addOnFailureListener {
                    Log.e(TAG, "saveUserToFirestore: ERROR", it)
                }

        }

    suspend fun getUserFromFirestore(userId: Int): Resource<FirebaseUserModel, DefaultErrorResponse> =
        withContext(ioDispatcher) {
            return@withContext try {
                val fcmToken = firebaseMessaging.token.await()
                val updateToken = mapOf(
                    FCM_TOKEN to fcmToken
                )

                val userRef = firestore.collection(USERS).document(userId.toString())

                val userSnapshot = userRef.get().await()
                val user = userSnapshot.toObject<FirebaseUserModel>()
                user?.let { userModel ->
                    if (userModel.fcmToken != fcmToken) {
                        userRef.update(updateToken).await()
                    }
                }
                if (user != null) {
                    Resource.Success(user)
                } else {
                    Resource.Error("User not found")
                }
            } catch (e: Exception) {
                Resource.Error(message = e.message ?: "Unknown error", data = null)
            }
        }

    suspend fun getAllConversation(
        userId: Int,
    ): Resource<Query, DefaultErrorResponse> =
        withContext(ioDispatcher) {
            try {
                val conversationsQuery = firestore
                    .collection(CONVERSATIONS)
                    .whereArrayContains(PARTICIPANT_IDS, userId)
                    .orderBy(LAST_UPDATED, Query.Direction.DESCENDING)

                Log.w(TAG, "getAllConversation:Success")
                Resource.Success(conversationsQuery)
            } catch (e: Exception) {
                Log.e(TAG, "getAllConversation:Error", e)
                Resource.Error(e.message ?: "An error occurred.")
            }
        }


    suspend fun createConversation(
        senderId: Int,
        recipient: SimpleUserModel
    ): Resource<ConversationModel, DefaultErrorResponse> =
        withContext(ioDispatcher) {
            return@withContext try {
                if (senderId.toLong() == recipient.userId) {
                    Resource.Error<ConversationModel, DefaultErrorResponse>("You can't start chat with yourself.")
                }
                val recipientUserDocument =
                    firestore.collection(USERS).where(Filter.equalTo(USER_ID, recipient.userId))
                        .get()
                        .await()

                val senderUserDocument =
                    firestore.collection(USERS).where(Filter.equalTo(USER_ID, senderId)).get()
                        .await()

                // There is already have a conversation return that conversation
                val existingConversation = getExistingConversation(senderId, recipient.userId.toInt())
                if (existingConversation != null) {
                    Resource.Success(existingConversation)
                } else {
                    // Start a conversation if recipient data is found
                    if (recipientUserDocument.isEmpty.not() && senderUserDocument.isEmpty.not()) {
                        val recipientRef = recipientUserDocument.documents.first()
                        val senderRef = senderUserDocument.documents.first()
                        val conversationDocument = firestore.collection(CONVERSATIONS).document()
                        val recipientUser = recipientRef.toObject<FirebaseUserModel>()!!
                        val senderUser = senderRef.toObject<FirebaseUserModel>()!!

                        val conversation = ConversationModel(
                            conversationId = conversationDocument.id,
                            participants = listOf(recipientUser, senderUser),
                            participantIds = listOf(
                                recipientUser.userID,
                                senderUser.userID
                            ).sorted(),
                            lastMessage = null,
                            lastUpdated = LocalDateTime.now().toString(),
                            messages = listOf()
                        )


                        conversationDocument.set(conversation)
                            .addOnFailureListener { Log.e(TAG, "createConversation:ERROR", it) }
                            .addOnSuccessListener { Log.w(TAG, "createConversation:SUCCESS") }
                            .await()

                        Resource.Success(conversation)
                    } else {
                        val newFireBaseUser = recipient.mapToFirebaseUserModel()
                        saveUserToFirestore(newFireBaseUser)
                            .addOnFailureListener {
                                Log.e(TAG, "createConversation: User not created")
                                Resource.Error<ConversationModel, DefaultErrorResponse>("User can not created try again.")
                            }
                        createConversation(senderId, recipient)
                    }
                }


            } catch (e: Exception) {
                Log.e(TAG, "createConversation:ERROR", e)
                Resource.Error(e.message ?: "An error occurred.")
            }
        }


    suspend fun getConversation(
        documentId: String,
        eventListener: EventListener<DocumentSnapshot>
    ) = withContext(ioDispatcher) {
        try {
            val conversationDocRef = firestore
                .collection(CONVERSATIONS)
                .document(documentId)
            val messages = conversationDocRef.get().await().toObject<ConversationModel>()?.messages

            updateMessageRead(messages, conversationDocRef)

            conversationDocRef.addSnapshotListener(eventListener)
        } catch (e: Exception) {
            Log.e(TAG, "getConversation:ERROR: ", e)
        }
    }

    suspend fun deleteConversation(documentId: String) = withContext(ioDispatcher) {
        try {
            val conversationRef = firestore.collection(CONVERSATIONS).document(documentId)

            conversationRef.delete().await()
            Log.w(TAG, "deleteConversation:Success")
        } catch (e: Exception) {
            Log.e(TAG, "deleteConversation:Error", e)
        }
    }

    suspend fun sendMessage(
        currentUserId: Int,
        content: String,
        conversationId: String,
    ) = withContext(ioDispatcher)
    {
        try {
            val conversationRef = firestore.collection(CONVERSATIONS).document(conversationId)
            val recipientId = getRecipientId(conversationRef, currentUserId)

            val newMessage = MessageModel(
                messageId = UUID.randomUUID().toString(),
                senderId = currentUserId,
                content = content,
                timestamp = LocalDateTime.now().toString(),
                recipientId = recipientId
            )

            val updatedValue = mapOf(
                MESSAGES to FieldValue.arrayUnion(newMessage),
                LAST_MESSAGE to newMessage,
                LAST_UPDATED to LocalDateTime.now().toString(),
            )

            conversationRef.update(updatedValue)
                .addOnSuccessListener {
                    Log.w(TAG, "createMessage:Success")
                }.addOnFailureListener {
                    Log.e(TAG, "createMessage:Error", it)
                }
                .await()

        } catch (e: Exception) {
            Log.e(TAG, "createMessage:Error", e)
        }
    }


//    suspend fun updateMessage(message: MessageModel, documentId: String) =
//        withContext(ioDispatcher) {
//            try {
//                val conversationRef = firestore.collection(CONVERSATIONS).document(documentId)
//
//                val updatedValue = mapOf(
//                    "messages" to FieldValue.arrayUnion(message)
//                )
//
//                conversationRef.update(updatedValue)
//                    .addOnSuccessListener {
//                        Log.w(TAG, "updateMessage:Success")
//                    }
//                    .addOnFailureListener {
//                        Log.e(TAG, "updateMessage:Error", it)
//                    }
//            } catch (e: Exception) {
//                Log.e(TAG, "updateMessage:ERROR", e)
//            }
//        }

    private fun updateMessageRead(
        messages: List<MessageModel>?,
        conversationDocRef: DocumentReference
    ) {
        messages?.forEach { it.read = true }

        conversationDocRef.update(MESSAGES, messages)
    }

    private suspend fun getExistingConversation(
        senderId: Int,
        recipientId: Int
    ): ConversationModel? {
        val currentParticipantIds = listOf(senderId, recipientId).sorted()
        val existingConversations = firestore.collection(CONVERSATIONS)
            .whereIn(PARTICIPANT_IDS, listOf(currentParticipantIds)).get().await()
            .toObjects(ConversationModel::class.java)
        return existingConversations.firstOrNull { it.participantIds.sorted() == currentParticipantIds }
    }

    private suspend fun getRecipientId(
        conversationRef: DocumentReference,
        currentUserId: Int
    ): Int {
        return conversationRef.get().await()
            .toObject<ConversationModel>()!!.participantIds.first { it != currentUserId }
    }

    private companion object {
        const val TAG = "firebaseUserRepository"
        const val USERS = "users"
        const val USER_ID = "userID"
        const val MESSAGES = "messages"
        const val LAST_MESSAGE = "lastMessage"
        const val LAST_UPDATED = "lastUpdated"
        const val PARTICIPANT_IDS = "participantIds"
        const val CONVERSATIONS = "conversations"
        const val FCM_TOKEN = "fcmToken"
    }
}