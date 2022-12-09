package com.example.greenwichregistr

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.greenwichregistr.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRegisterButton()

        binding.loginButton.setOnClickListener {

            checkInput()

            colorButton()
        }
    }

    private fun colorButton() {
        if (binding.enterNameEt.text.isEmpty() || binding.enterPnoneEt.text.isEmpty()){
            binding.loginButton.backgroundTintList =
                getResources().getColorStateList(R.color.gray)
        } else {
            binding.loginButton.backgroundTintList =
                getResources().getColorStateList(R.color.main_green)
        }
    }

    private fun checkInput() {
        binding.emptyAlertTxt.isVisible =
            binding.enterNameEt.text.isEmpty() || binding.enterPnoneEt.text.isEmpty()

        binding.emptyNameAlert.isVisible = binding.enterNameEt.text.isEmpty()

        binding.emptyPhoneAlert.isVisible = binding.enterPnoneEt.text.isEmpty()
    }

    private fun setRegisterButton() {
        binding.registerTxtButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }
}