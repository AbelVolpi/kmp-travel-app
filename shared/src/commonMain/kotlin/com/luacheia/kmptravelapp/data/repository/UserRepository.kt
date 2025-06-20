package com.luacheia.kmptravelapp.data.repository

import dev.gitlive.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository(
    private val firebaseAuth: FirebaseAuth
) {

    fun login(email: String, password: String): Flow<Boolean> = flow {
        try {
            val user = firebaseAuth.signInWithEmailAndPassword(email, password)
            println(user)
            emit(true)
        } catch (error: Exception) {
            println(error)
            emit(false)
        }
    }

    fun register(email: String, password: String): Flow<Boolean> = flow {
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
            emit(true)
        } catch (error: Exception) {
            println(error)
            emit(false)
        }
    }

}