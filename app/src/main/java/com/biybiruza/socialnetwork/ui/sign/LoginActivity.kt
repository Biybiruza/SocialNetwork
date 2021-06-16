package com.biybiruza.socialnetwork.ui.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.biybiruza.socialnetwork.MainActivity
import com.biybiruza.socialnetwork.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener {
            if(binding.username.text.isNotEmpty() &&
                    binding.password.text.isNotEmpty()){
                binding.loading.visibility = View.VISIBLE
                mAuth.signInWithEmailAndPassword(binding.username.text.toString(),binding.password.text.toString())
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            binding.loading.visibility = View.GONE
                            onStart()
                        } else {
                            Toast.makeText(this,"siz registratsiyadan otpegensiz",Toast.LENGTH_LONG).show()
                            binding.loading.visibility = View.GONE
                        }
                    }
            } else {
                Toast.makeText(this, "Polyalardi toldırıń", Toast.LENGTH_LONG).show()
            }
        }
        binding.register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        upDateUI(currentUser)
    }

    private fun upDateUI(user: FirebaseUser?){
        if(user != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}