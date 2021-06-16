package com.biybiruza.socialnetwork.ui.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.biybiruza.socialnetwork.MainActivity
import com.biybiruza.socialnetwork.databinding.ActivityRegistrBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrBinding
    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.register.setOnClickListener {
            if (binding.username.text.isNotEmpty() && binding.password.text.isNotEmpty()) {
                binding.loading.visibility = View.VISIBLE
                mAuth.createUserWithEmailAndPassword(
                    binding.username.text.toString(),
                    binding.password.text.toString()
                )
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = mAuth.currentUser
                            binding.loading.visibility = View.GONE
                            updateUI(user)
                        } else {
                            Toast.makeText(this, task.exception?.localizedMessage!!, Toast.LENGTH_LONG).show()
                            binding.loading.visibility = View.GONE
                        }
                    }
            }else{
                Toast.makeText(this, "mánislerdi toliq kiritń", Toast.LENGTH_LONG)
            }
        }
    }

    private fun updateUI(user: FirebaseUser?){
        if (user != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}