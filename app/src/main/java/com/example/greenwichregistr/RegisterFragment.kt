package com.example.greenwichregistr

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.greenwichregistr.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginTxtButton.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }



        binding.registerButton.setOnClickListener {
            checkInput()

            colorButton()
        }
    }

    private fun colorButton() {
        if (binding.enterNameEt.text.isEmpty() || binding.enterPnoneEt.text.isEmpty()){
            binding.registerButton.backgroundTintList =
                getResources().getColorStateList(R.color.gray)
        } else {
            binding.registerButton.backgroundTintList =
                getResources().getColorStateList(R.color.main_green)

            findNavController().navigate(R.id.action_registerFragment_to_confirmFragment)
        }
    }

    private fun checkInput() {
        binding.emptyAlertTxt.isVisible =
            binding.enterNameEt.text.isEmpty() || binding.enterPnoneEt.text.isEmpty()

        binding.emptyNameAlert.isVisible = binding.enterNameEt.text.isEmpty()

        binding.emptyPhoneAlert.isVisible = binding.enterPnoneEt.text.isEmpty()
    }
}