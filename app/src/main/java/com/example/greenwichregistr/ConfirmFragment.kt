package com.example.greenwichregistr

import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.greenwichregistr.databinding.FragmentConfirmBinding
import com.example.greenwichregistr.databinding.FragmentSuccessBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class ConfirmFragment : Fragment() {

    private lateinit var binding: FragmentConfirmBinding
    private val args by navArgs<ConfirmFragmentArgs>()
    private lateinit var auth: FirebaseAuth
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var OTP: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = Firebase.auth

        resendToken = args.resendToken
        OTP = args.verificationID!!

        binding = FragmentConfirmBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userPhoneTxt.text = args.userPhone

        resendCodeVisibility()

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
                    openDialog()

                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(requireContext(), "Invalid code", Toast.LENGTH_LONG).show()
                    }
                }
            }
    }

    private fun openDialog() {
        val dialog = Dialog(requireContext())

        dialog.setContentView(R.layout.fragment_success)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setCancelable(true)
        dialog.window?.attributes?.windowAnimations = R.style.animation

        val button = dialog.findViewById<Button>(R.id.button)

        button.setOnClickListener {
            dialog.dismiss()
            findNavController().navigate(R.id.action_confirmFragment_to_loginFragment)
        }

        dialog.show()
    }

    private fun resendCodeVisibility(){
        binding.resendTxtBtn.setTextColor(resources.getColor(R.color.black))
        binding.resendTxtBtn.text = "Отправить код повторно через 60"

        val timer = object: CountDownTimer(60000, 1000){
            override fun onTick(p0: Long) {
                binding.resendTxtBtn.text = "Отправить код повторно через ${p0 / 1000}"
            }

            override fun onFinish() {
                binding.resendTxtBtn.text = "Отправить код повторно"
                binding.resendTxtBtn.setTextColor(resources.getColor(R.color.main_green))
                binding.resendTxtBtn.setOnClickListener {
                    resendCode()
                    resendCodeVisibility()
                }
            }
        }

        timer.start()

    }

    private fun resendCode(){
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(args.userPhone)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this.requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

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
            resendToken = token
            OTP = verificationId

        }
    }
}