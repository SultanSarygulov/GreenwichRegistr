package com.example.greenwichregistr

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
            binding.emptyAlertTxt.isVisible = false
            findNavController().navigate(R.id.action_registerFragment_to_confirmFragment)
        }



    }
}