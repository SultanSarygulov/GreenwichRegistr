package com.example.greenwichregistr

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.greenwichregistr.databinding.FragmentRegisterBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginTxtButton.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.enterNameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {

                if (binding.enterNameEt.text.isNotEmpty() && binding.enterPnoneEt.text.isNotEmpty()){
                    binding.registerButton.backgroundTintList =
                        getResources().getColorStateList(R.color.main_green)

                } else {
                    binding.registerButton.backgroundTintList =
                        getResources().getColorStateList(R.color.gray)
                }
            }
        })

        binding.enterPnoneEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {

                if (binding.enterNameEt.text.isNotEmpty() && binding.enterPnoneEt.text.isNotEmpty()){
                    binding.registerButton.backgroundTintList =
                        getResources().getColorStateList(R.color.main_green)

                } else {
                    binding.registerButton.backgroundTintList =
                        getResources().getColorStateList(R.color.gray)
                }
            }
        })

        binding.registerButton.setOnClickListener {

            checkInput()
        }
    }

    private fun checkInput() {
        if (binding.enterNameEt.text.isEmpty() || binding.enterPnoneEt.text.isEmpty()){
            binding.emptyAlertTxt.isVisible = true

            binding.emptyNameAlert.isVisible = binding.enterNameEt.text.isEmpty()

            binding.emptyPhoneAlert.isVisible = binding.enterPnoneEt.text.isEmpty()
        } else {
            sendCode()
        }

    }

    private fun sendCode() {

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(requireContext(), "Send a correct phone number", Toast.LENGTH_LONG).show()
                Log.d("Nigger", "${e}")
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                binding.emptyAlertTxt.isVisible = false
                findNavController().navigate(R.id.action_registerFragment_to_confirmFragment)
            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(binding.enterPnoneEt.text.toString().trim())       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this.requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}