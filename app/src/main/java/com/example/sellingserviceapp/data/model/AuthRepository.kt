package com.example.sellingserviceapp.data.model

/*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun registerUser(email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { verificationTask ->
                            if (verificationTask.isSuccessful) {
                                onSuccess()
                            } else {
                                onFailure("Ошибка отправки письма для подтверждения")
                            }
                        }
                } else {
                    onFailure("Ошибка регистрации: ${task.exception?.message}")
                }
            }
    }

    fun sendVerificationEmail(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val user: FirebaseUser? = auth.currentUser

        if (user != null && !user.isEmailVerified) {
            user.sendEmailVerification()
                .addOnCompleteListener {
                    task -> if (task.isSuccessful) {
                        onSuccess()
                    } else {
                        onFailure(task.exception?.message ?: "Ошибка отправки письма")
                    }
                }
        } else {
            onFailure("Пользователь не найден или уже подтвержден.")
        }
    }
}*/
