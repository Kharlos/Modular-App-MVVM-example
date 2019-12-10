package ve.com.cgblanco.featuresignin.view

import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import dagger.android.support.DaggerFragment
import ve.com.cgblanco.featuresignin.R
import ve.com.cgblanco.featuresignin.databinding.SignInFragmentBinding
import ve.com.cgblanco.featuresignin.viewmodel.SignInViewModel
import java.util.*
import javax.inject.Inject


class SignInFragment : DaggerFragment() {

    companion object {
        fun newInstance() = SignInFragment()
    }

    private lateinit var signInViewModel: SignInViewModel

    private lateinit var binding: SignInFragmentBinding

    private lateinit var progressBar: ProgressDialog

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.sign_in_fragment, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        signInViewModel = ViewModelProviders.of(this, viewModelFactory)[SignInViewModel::class.java]

        progressBar = ProgressDialog(context)

        binding.lifecycleOwner = this

        binding.signInViewModel = signInViewModel

        setProgessBar()

        signInViewModel.validateUser().observe(this, Observer {
            if (TextUtils.isEmpty(it.strEmailAddress)) {
                binding.etEmail.error = "Enter an E-Mail Address"
                binding.etEmail.requestFocus()
            } else if (!it.isEmailValid) {
                binding.etEmail.error = "Enter a Valid E-mail Address"
                binding.etEmail.requestFocus()
            } else if (TextUtils.isEmpty(Objects.requireNonNull(it).strPassword)) {
                binding.etPassword.error = "Enter a Password"
                binding.etPassword.requestFocus()
            } else if (!it.isPasswordLengthGreaterThan5) {
                binding.etPassword.error = "Enter at least 6 Digit password"
                binding.etPassword.requestFocus()
            } else {
                binding.etEmail.setText(it.strEmailAddress)
                binding.etPassword.setText(it.strPassword)
                makeRequest()
            }
        })

        signInViewModel.getUser().observe(this, Observer {
            progressBar.dismiss()
            if(it != null){
                val moviesURI = "homeview://homeview".toUri()
                findNavController(binding.btnSignIn).navigate(moviesURI)
            }else{
                //Show an Error
            }
        })
    }

    fun setProgessBar(){
        progressBar.setMessage("Loading...")
        progressBar.setCancelable(false)
    }

    fun makeRequest(){
        progressBar.show()
       signInViewModel.makeSignIn()
    }

}
