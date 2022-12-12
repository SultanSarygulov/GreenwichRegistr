package com.example.greenwichregistr

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.greenwichregistr.databinding.FragmentConfirmBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.Executor

class ConfirmFragment : Fragment() {

    private lateinit var binding: FragmentConfirmBinding
    private val args by navArgs<ConfirmFragmentArgs>()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = Firebase.auth

        binding = FragmentConfirmBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.confirmRegisterButton.setOnClickListener {
            if (binding.codeBoxes.text?.isEmpty() == true ||
                binding.codeBoxes.text?.length!! < 6){
                binding.codeBoxes.setItemBackground(resources.getDrawable(R.drawable.invalid_code_box))
                binding.codeBoxes.setTextColor(resources.getColor(R.color.wrong_red))
                binding.invalidCodeAlert.isVisible = true
                Toast.makeText(requireContext(), "Invalid code", Toast.LENGTH_LONG).show()
            }
            else {
                if (args.verificationID != null){

                    val code = binding.codeBoxes.text.toString()
                    binding.codeBoxes.setItemBackground(resources.getDrawable(R.drawable.code_box))
                    binding.codeBoxes.setTextColor(resources.getColor(R.color.black))
                    binding.invalidCodeAlert.isVisible = false
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()

                    val credential = PhoneAuthProvider.getCredential(args.verificationID!!, code)
                    signInWithPhoneAuthCredential(credential)
                }
            }
        }

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                Log.d("Nigger", "${task.isSuccessful}")
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()

                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(requireContext(), "NIGGAAAAA WRONG CODE", Toast.LENGTH_LONG).show()
                    }
                }
            }
    }
}