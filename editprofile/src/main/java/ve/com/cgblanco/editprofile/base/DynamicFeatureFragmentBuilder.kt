package ve.com.cgblanco.editprofile.base

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ve.com.cgblanco.editprofile.view.fragment.DynamicFeatureFragment

@Module
abstract class DynamicFeatureFragmentBuilder {

    @ContributesAndroidInjector
    internal abstract fun bindDynamicFeatureFragment(): DynamicFeatureFragment
}