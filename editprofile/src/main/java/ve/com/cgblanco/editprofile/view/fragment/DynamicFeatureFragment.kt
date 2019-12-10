package ve.com.cgblanco.editprofile.view.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerFragment

import ve.com.cgblanco.editprofile.R
import ve.com.cgblanco.editprofile.databinding.FragmentDynamicFeatureBinding
import ve.com.cgblanco.editprofile.viewmodel.DynamicFeatureViewModel
import javax.inject.Inject


class DynamicFeatureFragment : DaggerFragment() {

    private lateinit var dynamicFeatureViewModel: DynamicFeatureViewModel

    private lateinit var binding: FragmentDynamicFeatureBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dynamic_feature, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dynamicFeatureViewModel = ViewModelProviders.of(this, viewModelFactory)[DynamicFeatureViewModel::class.java]

        binding.lifecycleOwner = this

        binding.dynamicFeatureViewModel = dynamicFeatureViewModel

    }
}
