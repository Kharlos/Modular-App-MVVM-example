package ve.com.cgblanco.featuresignin.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.sign_in_fragment.*

import android.text.TextUtils
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import ve.com.cgblanco.featuresignin.model.LoginUser
import ve.com.cgblanco.featuresignin.R
import ve.com.cgblanco.featuresignin.databinding.SignInFragmentBinding
import java.util.*


class SignInFragment : Fragment() {

    companion object {
        fun newInstance() = SignInFragment()
    }

    private lateinit var signInViewModel: SignInViewModel

    private lateinit var binding: SignInFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        signInViewModel = ViewModelProviders.of(this).get(SignInViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.sign_in_fragment, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.lifecycleOwner = this

        binding.signInViewModel = signInViewModel

        signInViewModel.getUser().observe(this, Observer {
            if (TextUtils.isEmpty(it.strEmailAddress)) {
                binding.etEmail.setError("Enter an E-Mail Address")
                binding.etEmail.requestFocus()
            } else if (!it.isEmailValid) {
                binding.etEmail.setError("Enter a Valid E-mail Address")
                binding.etEmail.requestFocus()
            } else if (TextUtils.isEmpty(Objects.requireNonNull(it).strPassword)) {
                binding.etPassword.setError("Enter a Password")
                binding.etPassword.requestFocus()
            } else if (!it.isPasswordLengthGreaterThan5) {
                binding.etPassword.setError("Enter at least 6 Digit password")
                binding.etPassword.requestFocus()
            } else {
                binding.etEmail.setText(it.strEmailAddress)
                binding.etPassword.setText(it.strPassword)
            }
        })
    }

}
