package android.kotlin.foodclub.repositories

import android.kotlin.foodclub.domain.models.auth.FirebaseUserModel
import android.kotlin.foodclub.network.retrofit.responses.general.DefaultErrorResponse
import android.kotlin.foodclub.utils.helpers.Resource
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

// data class for firebase - done
// user lari ekle firebase - done


// TODO mapper for firebase user
// TODO fcm al main acitivity'den user a ekle


class FirebaseUserRepository(
    private val firestore: FirebaseFirestore,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun saveUserToFirestore(firebaseUserModel: FirebaseUserModel) = withContext(ioDispatcher) {
        firestore.collection(USERS).document(firebaseUserModel.userID)
            .set(firebaseUserModel)
            .addOnSuccessListener {
                Log.w(TAG, "saveUserToFirestore: SUCCESS")
            }.addOnFailureListener {
                Log.e(TAG, "saveUserToFirestore: ERROR", it)
            }

    }

    suspend fun getUserFromFirestore(userId: String) = withContext(ioDispatcher) {
        val document = firestore.collection(USERS).document(userId)
            .get()
            .addOnSuccessListener {
                Log.w(TAG, "getUserFromFirestore: SUCCESS")
            }.addOnFailureListener {
                Log.e(TAG, "getUserFromFirestore: ERROR", it)
            }
            .await()
            .get(userId) as? FirebaseUserModel
    }


    private companion object {
        const val TAG = "firebaseUserRepository"
        const val USERS = "users"
        const val CONVERSATIONS = "conversations"
    }
}