package com.example.firebaselogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.example.firebaselogin.databinding.ActivitySigninBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.TextUtils
import com.google.firebase.ktx.Firebase

class SigninActivity : BaseActivity() {
    private var binding:ActivitySigninBinding?=null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        auth = Firebase.auth

        binding?.tvRegister?.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }
        binding?.tvForgotPassword?.setOnClickListener {
            startActivity(Intent(this,ForgetPasswordActivity::class.java))
        }
        binding?.btnSignIn?.setOnClickListener {
            userLogin()
        }

    }
    private fun userLogin()
    {
        val email = binding?.etSinInEmail?.text.toString()
        val password = binding?.etSinInPassword?.text.toString()
        if (validateForm(email, password))
        {
            showProgressBar()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {task->
                    if (task.isSuccessful)
                    {
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                        hideProgressBar()
                    }
                    else
                    {
                        showToast(this,"Can't Login currently. Try after sometime")
                        hideProgressBar()
                    }
                }
        }
    }
    private fun validateForm(email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()->{
                binding?.tilEmail?.error = "Enter valid email address"
                false
            }
            TextUtils.isEmpty(password)->{
                binding?.tilPassword?.error = "Enter password"
                false
            }
            else -> { true }
        }
    }

}