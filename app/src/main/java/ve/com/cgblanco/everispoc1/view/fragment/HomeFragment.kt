package ve.com.cgblanco.everispoc1.view.fragment


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import dagger.android.support.DaggerFragment
import org.koin.android.ext.android.bind
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

    lateinit var splitInstallManager: SplitInstallManager
    lateinit var request: SplitInstallRequest
    val DYNAMIC_FEATURE = "editprofile"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDynamicModules()
    }

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

        binding.buttonClick.setOnClickListener{
            if (!isDynamicFeatureDownloaded(DYNAMIC_FEATURE)) {
                downloadFeature()
            } else {
                binding.buttonDeleteNewsModule.visibility = View.VISIBLE
                binding.buttonOpenNewsModule.visibility = View.VISIBLE
            }
        }

        binding.buttonOpenNewsModule.setOnClickListener{

            val moviesURI = "homeview://homeview".toUri()
            Navigation.findNavController(binding.buttonOpenNewsModule).navigate(moviesURI)
            //val intent = Intent().setClassName(context!!, "com.mindorks.news_feature.newsloader.NewsLoaderActivity")
            //startActivity(intent)
        }

        binding.buttonDeleteNewsModule.setOnClickListener{
            val list = ArrayList<String>()
            list.add(DYNAMIC_FEATURE)
            uninstallDynamicFeature(list)
        }

    }

    private fun initDynamicModules() {
        splitInstallManager = SplitInstallManagerFactory.create(context)
        request = SplitInstallRequest
            .newBuilder()
            .addModule(DYNAMIC_FEATURE)
            .build()

    }

    private fun isDynamicFeatureDownloaded(feature: String): Boolean =
        splitInstallManager.installedModules.contains(feature)

    private fun downloadFeature() {
        splitInstallManager.startInstall(request)
            .addOnFailureListener {
            }
            .addOnSuccessListener {
                binding.buttonOpenNewsModule.visibility = View.VISIBLE
                binding.buttonDeleteNewsModule.visibility = View.VISIBLE
            }
            .addOnCompleteListener {
            }
    }

    private fun uninstallDynamicFeature(list: List<String>) {
        splitInstallManager.deferredUninstall(list)
            .addOnSuccessListener {
                binding.buttonDeleteNewsModule.visibility = View.GONE
                binding.buttonOpenNewsModule.visibility = View.GONE
            }
    }

    fun settingUserData(user:User){
        binding.tvUsername.text = "Nombre de usuario : ${user.name}"
        binding.tvEmail.text = "Email : ${user.email}"
    }
}
