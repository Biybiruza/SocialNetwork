package com.biybiruza.socialnetwork.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.biybiruza.socialnetwork.R
import com.biybiruza.socialnetwork.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val db = FirebaseFirestore.getInstance()
    private val mAuth = FirebaseAuth.getInstance()
    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showData()
        binding.saveButton.setOnClickListener {
            val map = mutableMapOf<String, Any?>()
            map["username"] = binding.etUserName.text
            map["email"] = binding.etEmail.text
            map["phone"] = binding.etPhoneNumber.text
            map["info"] = binding.etInfo.text
            db.collection("user").document(mAuth.currentUser!!.uid).set(map)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(),"Your profile data has been changed successfully",Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {e ->
                    Toast.makeText(requireContext(), e.localizedMessage!!, Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun showData(){
        db.collection("user").document(mAuth.currentUser!!.uid).get()
            .addOnSuccessListener {
                binding.etUserName.setText(it.get("username").toString())
                binding.etEmail.setText(it.get("email").toString())
                binding.etPhoneNumber.setText(it.get("phone").toString())
                binding.etInfo.setText(it.get("info").toString())
            }
    }
}