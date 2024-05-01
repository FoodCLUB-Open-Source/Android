package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.domain.models.auth.FirebaseUserModel
import android.kotlin.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import android.kotlin.foodclub.utils.helpers.Resource
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


// TODO mapper for firebase user
// TODO take fcm token from main activity and add to the user field in firebase


class FirebaseUserRepository(
    private val firestore: FirebaseFirestore,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun saveUserToFirestore(firebaseUserModel: FirebaseUserModel) = withContext(ioDispatcher) {
        firestore.collection(USERS).document(firebaseUserModel.userID.toString())
            .set(firebaseUserModel)
            .addOnSuccessListener {
                Log.w(TAG, "saveUserToFirestore: SUCCESS")
            }.addOnFailureListener {
                Log.e(TAG, "saveUserToFirestore: ERROR", it)
            }

    }


    suspend fun getUserFromFirestore(userId: Int): Resource<FirebaseUserModel, DefaultErrorResponse> = withContext(ioDispatcher) {
        try {
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
                return@withContext Resource.Success(user)
            } else {
                return@withContext Resource.Error("User not found")
            }

        } catch (e: Exception) {
            return@withContext Resource.Error(message = e.message!!, data = null)
        }


    }

//    suspend fun createConversation(senderUser: FirebaseUserModel, recipientUser: FirebaseUserModel) = withContext(ioDispatcher) {
//        try {
//
//            firestore.collection(CONVERSATIONS).document(conversationName)
//        } catch (e: Exception) {
//
//        }
//    }


    private companion object {
        const val TAG = "firebaseUserRepository"
        const val USERS = "users"
        const val CONVERSATIONS = "conversations"
    }
}