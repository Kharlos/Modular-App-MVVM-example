package ve.com.cgblanco.everispoc1.view.fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.welcome_fragment.*

import ve.com.cgblanco.everispoc1.R
import ve.com.cgblanco.everispoc1.viewmodel.WelcomeViewModel

class WelcomeFragment : Fragment() {

    companion object {
        fun newInstance() = WelcomeFragment()
    }

    private lateinit var viewModel: WelcomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.welcome_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WelcomeViewModel::class.java)
        // TODO: Use the ViewModel


        welcomeBtn.setOnClickListener{
            val action = WelcomeFragmentDirections.actionWelcomeFragmentToSignInFragment()
            welcomeBtn.findNavController().navigate(action)
        }
    }

}
