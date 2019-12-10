package ve.com.cgblanco.everispoc1.view.fragment


import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment
import ve.com.cgblanco.datasource.data.model.user.User

import ve.com.cgblanco.everispoc1.R
import ve.com.cgblanco.everispoc1.databinding.FragmentHomeBinding
import ve.com.cgblanco.everispoc1.viewmodel.HomeViewModel
import javax.inject.Inject


class HomeFragment : DaggerFragment() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var binding:FragmentHomeBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )


        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel = ViewModelProviders.of(this, viewModelFactory)[HomeViewModel::class.java]

        binding.lifecycleOwner = this

        binding.homeViewModel = homeViewModel

        homeViewModel.getUser().observe(this, Observer {
            homeViewModel.user.postValue(it)
        })


        homeViewModel.user.observe(this, Observer {
            settingUserData(it)
        })

    }

    fun settingUserData(user:User){
        binding.tvUsername.text = "Nombre de usuario : ${user.name}"
        binding.tvEmail.text = "Email : ${user.email}"
    }
}
