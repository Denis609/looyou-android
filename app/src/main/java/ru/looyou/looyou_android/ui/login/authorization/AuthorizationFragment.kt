package ru.looyou.looyou_android.ui.login.authorization

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import ru.looyou.looyou_android.Const
import ru.looyou.looyou_android.R
import ru.looyou.looyou_android.api.extension.onUnAuthorize
import ru.looyou.looyou_android.databinding.AuthorizationFragmentBinding

@AndroidEntryPoint
class AuthorizationFragment : Fragment() {

    private lateinit var binding: AuthorizationFragmentBinding
    private val viewModel: AuthorizationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AuthorizationFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.success.collect {
                if (it) onUnAuthorize()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.loading.collect {
                binding.progressBar.isVisible = it
                binding.signInButton.isEnabled = !it
                binding.signInButton.text = if (it) "" else getString(R.string.sign_in)
            }
        }

        binding.signInButton.setOnClickListener {
            viewModel.singIn(
                binding.email.editText?.text.toString(),
                binding.password.editText?.text.toString()
            )
        }

        binding.vk.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data =
                Uri.parse("https://oauth.vk.com/authorize?client_id=8193627&display=popup&redirect_uri=https://looyou-dev.com/&scope=friends&response_type=code&v=5.131")
            start.launch(intent)
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

    private val start =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val a = 1
        }

    private val startForResult: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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
            Log.w(ContentValues.TAG, "signInResult:failed code=" + e.statusCode)
        }
    }

}