package com.example.greenwichregistr

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.greenwichregistr.databinding.FragmentLoginBinding
import org.w3c.dom.Text

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

        binding.enterNameEt.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {

                if (binding.enterNameEt.text.isNotEmpty() && binding.enterPnoneEt.text.isNotEmpty()){
                    binding.loginButton.backgroundTintList =
                        getResources().getColorStateList(R.color.main_green)

                } else {
                    binding.loginButton.backgroundTintList =
                        getResources().getColorStateList(R.color.gray)
                }
            }
        })

        binding.enterPnoneEt.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {

                if (binding.enterNameEt.text.isNotEmpty() && binding.enterPnoneEt.text.isNotEmpty()){
                    binding.loginButton.backgroundTintList =
                        getResources().getColorStateList(R.color.main_green)

                } else {
                    binding.loginButton.backgroundTintList =
                        getResources().getColorStateList(R.color.gray)
                }
            }
        })

        binding.loginButton.setOnClickListener {

            checkInput()
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