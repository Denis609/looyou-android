package ru.looyou.looyou_android.ui.login.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import ru.looyou.looyou_android.R
import ru.looyou.looyou_android.databinding.RegistrationFragmentBinding
import ru.looyou.looyou_android.extension.onUnAuthorize
import ru.looyou.looyou_android.util.Timer

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private lateinit var binding: RegistrationFragmentBinding
    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RegistrationFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.registrationState.collect {
                it?.let {
                    if (!it.isVerified && !it.isComplete) {
                        binding.emailStateGroup.isVisible = false
                        binding.codeStateGroup.isVisible = true
                        startTimer()
                    } else if (it.isVerified && !it.isComplete) {
                        binding.codeStateGroup.isVisible = false
                        binding.passwordStateGroup.isVisible = true
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.loading.collect {
                binding.progressBar.isVisible = it
                binding.nextButton.isEnabled = !it
                binding.nextButton.text = if (it) "" else getString(R.string.next)

                binding.progressBarPassword.isVisible = it
                binding.registrationButton.isEnabled = !it
                binding.registrationButton.text = if (it) "" else getString(R.string.sign_in)

                binding.progressBarVerify.isVisible = it
                binding.verificationCode.isEnabled = !it
            }
        }

        binding.registrationButton.setOnClickListener {
            checkPassword()
        }

        binding.verificationCode.setOnCodeChangedListener { (verificationCode, completed) ->
            if (completed) {
                viewModel.confirmRegistration(verificationCode)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.account.collect {
                it?.let {
                    viewModel.singIn(it.email)
                }
            }
        }

        binding.sendCodeButton.setOnClickListener {
            viewModel.sendVerifyCode()
            startTimer()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.success.collect {
                if (it) onUnAuthorize()
            }
        }

        binding.nextButton.setOnClickListener {
            viewModel.createRegistration(binding.email.editText?.text.toString())
        }
        binding.backButton.setOnClickListener {
            viewModel.viewModelScope.coroutineContext.cancelChildren()
            job?.cancel()
            binding.verificationCode.text = ""
            binding.emailStateGroup.isVisible = true
            binding.codeStateGroup.isVisible = false
        }
    }

    private fun checkPassword() {
        binding.apply {
            password.editText?.text?.let {
                if (it.length >= 6) {
                    if (password.editText?.text.toString() == passwordConfirm.editText?.text.toString()) {
                        viewModel.createAccount(password.editText?.text.toString())
                    } else {
                        passwordConfirm.editText?.error = getString(R.string.password_error)
                    }
                } else {
                    passwordConfirm.editText?.error = getString(R.string.password_error)
                }
            }
        }
    }

    private var job: Job? = null

    private fun startTimer() {
        job = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            Timer.timer()
                .onStart {
                    binding.sendCodeButton.isEnabled = false
                }.onCompletion {
                    binding.sendCodeButton.isEnabled = true
                    binding.sendCodeButton.text = getString(
                        R.string.send_code
                    )
                }.collect {
                    binding.sendCodeButton.text = getString(
                        R.string.resent_the_code_after,
                        it.toString()
                    )
                }
        }
    }
}