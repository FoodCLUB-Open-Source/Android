package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.domain.models.auth.ConversationModel
import android.kotlin.foodclub.domain.models.auth.FirebaseUserModel
import android.kotlin.foodclub.domain.models.auth.MessageModel
import android.kotlin.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import android.kotlin.foodclub.utils.helpers.Resource
import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class FirebaseUserRepository(
    private val firestore: FirebaseFirestore,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
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
                val user = firestore.collection(USERS).document(userId.toString())
                    .get()
                    .addOnSuccessListener {
                        Log.w(TAG, "getUserFromFirestore: SUCCESS")
                    }.addOnFailureListener {
                        Log.e(TAG, "getUserFromFirestore: ERROR", it)
                    }
                    .await()
                    .toObject<FirebaseUserModel>()

                if (user != null) {
                    Resource.Success(user)
                } else {
                    Resource.Error("User not found")
                }

            } catch (e: Exception) {
                Resource.Error(message = e.message!!, data = null)
            }


        }

    suspend fun createConversation(conversation: ConversationModel) = withContext(ioDispatcher) {
        try {

            firestore.collection(CONVERSATIONS).document(conversation.conversationName)
                .set(conversation)
                .addOnSuccessListener {
                    Log.w(TAG, "createConversation:SUCCESS")
                }
                .addOnFailureListener {
                    Log.e(TAG, "createConversation:ERROR: ", it)
                }.await()
        } catch (e: Exception) {
            Log.e(TAG, "createConversation:ERROR", e)
        }
    }

    suspend fun getConversation(
        senderId: String,
        recipientId: String
    ): Resource<ConversationModel, DefaultErrorResponse> = withContext(ioDispatcher) {
        return@withContext try {
            val conversationName = "${senderId}_$recipientId"
            val conversation = firestore.collection(CONVERSATIONS).document(conversationName)
                .get()
                .addOnSuccessListener { }
                .addOnFailureListener { }
                .await()
                .toObject<ConversationModel>()

            if (conversation != null) {
                Log.w(TAG, "getConversation:SUCCESS")
                Resource.Success(conversation)
            } else {
                Log.e(TAG, "getConversation:ERROR: Conversation not found.")
                Resource.Error("Conversation not found", null)
            }
        } catch (e: Exception) {
            Log.e(TAG, "getConversation:ERROR: ", e)
            Resource.Error(e.message!!, data = null)
        }
    }

    suspend fun deleteConversation(conversationName: String) = withContext(ioDispatcher) {
        try {
            val conversationRef = firestore.collection(CONVERSATIONS).document(conversationName)

            conversationRef.delete()
                .addOnSuccessListener {
                    Log.w(TAG, "deleteConversation:SUCCESS")
                }
                .addOnFailureListener {
                    Log.e(TAG, "deleteConversation:ERROR: ", it)
                }.await()
            Log.w(TAG, "deleteConversation:Success")
        } catch (e: Exception) {
            Log.e(TAG, "deleteConversation:Error", e)
        }
    }

    suspend fun createMessage(message: MessageModel, conversationName: String) =
        withContext(ioDispatcher) {
            try {
                val conversationRef = firestore.collection(CONVERSATIONS).document(conversationName)

                val updatedValue = mapOf(
                    "messages" to FieldValue.arrayUnion(message),
                    "lastMessage" to message,
                )

                conversationRef.update(updatedValue)
                    .addOnSuccessListener {
                        Log.w(TAG, "createMessage:Success")
                    }.addOnFailureListener {
                        Log.e(TAG, "createMessage:Error", it)
                    }
            } catch (e: Exception) {
                Log.e(TAG, "createMessage:Error", e)
            }
        }


    suspend fun deleteMessage(messageId: String, conversationName: String) =
        withContext(ioDispatcher) {
            try {
                val conversationRef = firestore.collection(CONVERSATIONS).document(conversationName)
                val conversation = conversationRef.get().await().toObject<ConversationModel>()

                if (conversation != null) {
                    val messages = conversation.messages
                    val index = findMessageIndex(messages, messageId)
                    // Delete message
                    if (index != -1) {
                        // Update lastMessage if necessary
                        conversation.lastMessage = updateLastMessage(messages, messageId)

                        messages.removeAt(index)
                        conversationRef.set(conversation)
                            .addOnSuccessListener {
                                Log.w(TAG, "deleteMessage:Success")
                            }.addOnFailureListener {
                                Log.e(TAG, "deleteMessage:Error", it)
                            }

                    } else {
                        Log.e(TAG, "Message not found")
                    }
                } else {
                    Log.e(TAG, "Conversation not found")
                }
            } catch (e: Exception) {
                Log.e(TAG, "deleteMessage:Error", e)
            }
        }

    private fun findMessageIndex(messages: List<MessageModel>, messageId: String): Int {
        return messages.indexOfFirst { it.messageId == messageId }
    }

    private fun updateLastMessage(
        messages: MutableList<MessageModel>,
        messageId: String
    ): MessageModel? {
        return if (messages.isNotEmpty() && messages.last().messageId == messageId) {
            if (messages.size >= 2) {
                messages[messages.size - 2]
            } else if (messages.size == 1) {
                messages[0]
            } else {
                null
            }
        } else {
            Log.e(TAG, "Messages not found or deleted message is not the lastMessage")
            null
        }
    }

    private companion object {
        const val TAG = "firebaseUserRepository"
        const val USERS = "users"
        const val CONVERSATIONS = "conversations"
    }
}