package com.example.lifemap.data.remote

import com.example.lifemap.domain.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import javax.inject.Inject


class AuthRemoteDataSourceImpl @Inject constructor(
private val auth: FirebaseAuth,
private val firestore: FirebaseFirestore
) : AuthRemoteDataSource {


    override suspend fun signUp(
        fullName: String,
        email: String,
        password: String
    ): Result<User> {

        return try {

            auth.createUserWithEmailAndPassword(email, password).await()

            val firebaseUser = auth.currentUser!!

            firebaseUser.updateProfile(
                UserProfileChangeRequest.Builder()
                    .setDisplayName(fullName)
                    .build()
            ).await()

            val user = User(
                uid = firebaseUser.uid,
                fullName = fullName,
                email = email
            )

            try {
                withTimeout(5000) {
                    firestore
                        .collection("Users")
                        .document(firebaseUser.uid)
                        .set(user)
                        .await()
                }
            } catch (e: Exception) {
                if (e !is kotlinx.coroutines.TimeoutCancellationException) {
                    throw e
                }
            }

            Result.success(user)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun login(
        email: String,
        password: String
    ): Result<User> {

        return try {

            auth.signInWithEmailAndPassword(email, password).await()

            val firebaseUser = auth.currentUser!!

            val document = firestore
                .collection("Users")
                .document(firebaseUser.uid)
                .get()
                .await()

            val user = document.toObject(User::class.java)
                ?: throw Exception("User not found")

            Result.success(user)

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

    override suspend fun sendPasswordResetEmail(
        email: String
    ): Result<Unit> {

        return try {

            auth.sendPasswordResetEmail(email).await()

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

    override suspend fun logout() {
        auth.signOut()
    }


}