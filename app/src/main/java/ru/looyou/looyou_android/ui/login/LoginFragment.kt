package ru.looyou.looyou_android.ui.login

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import com.vk.api.sdk.ui.VKWebViewAuthActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.looyou.looyou_android.Const
import ru.looyou.looyou_android.R
import ru.looyou.looyou_android.api.extension.onUnAuthorize
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
//        val vkStartForResult = VK.login(requireActivity()) { result ->
//            when (result) {
//                is VKAuthenticationResult.Success -> {
//
//                }
//                is VKAuthenticationResult.Failed -> {
//                    // User didn't pass authorization
//                }
//            }
//        }
        binding.vk.setOnClickListener {
//            vkStartForResult.launch(arrayListOf())
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://oauth.vk.com/authorize?client_id=8193627&display=popup&redirect_uri=https://looyou-dev.com/&scope=friends&response_type=code&v=5.131")
            start.launch(intent)
        }

        viewModel.success.observe(viewLifecycleOwner) {
            onUnAuthorize()
        }

        binding.google.setOnClickListener {

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestServerAuthCode(Const.CLIENT_ID_GOOGLE)
                .build()

            val mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso);

            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            startForResult.launch(signInIntent)
        }
    }

    private val start = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        val a = 1
    }

    private val startForResult: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount = completedTask.getResult(ApiException::class.java)
            account.serverAuthCode?.let {
                viewModel.singInGoogle(it)
            }

        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        }
    }
}