package ru.looyou.looyou_android.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.looyou.looyou_android.databinding.LoginFragmentBinding

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInButton.setOnClickListener {
            viewModel.singIn(
                binding.email.editText?.text.toString(),
                binding.password.editText?.text.toString()
            )
        }

        viewModel.success.observe(viewLifecycleOwner) {
            findNavController().navigate(LoginFragmentDirections.actionLoginToHome())
        }

    }
}