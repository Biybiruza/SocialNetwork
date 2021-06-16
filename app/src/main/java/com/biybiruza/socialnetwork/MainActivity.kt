package com.biybiruza.socialnetwork

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.biybiruza.socialnetwork.databinding.ActivityMainBinding
import com.biybiruza.socialnetwork.ui.profile.ProfileFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val mAuth = FirebaseAuth.getInstance()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db.collection("user").document(mAuth.currentUser?.uid!!.toString()).get()
            .addOnCompleteListener{
                Log.d("tekseriw",it.result.toString())
                if (it.isSuccessful && !it.result?.exists()!!){
                    var map = mutableMapOf<String,Any?>()
                    map["email"] = mAuth.currentUser?.email
                    db.collection("user").document(mAuth.currentUser?.uid!!).set(map)
                        .addOnSuccessListener {
                            Log.d("users", "User has been added successfully")
                        }
                        .addOnFailureListener { e ->
                            Log.d("users", e.localizedMessage!!.toString())
                        }
                }
            }

        binding.bnv.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.itemProfile -> {
                    changeFragment(ProfileFragment(),R.id.fragmentContainer)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.itemAllPost -> {

                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
    }

    private fun changeFragment(fragment: Fragment, container: Int){
        supportFragmentManager.beginTransaction().replace(container,fragment).commit()
    }

}