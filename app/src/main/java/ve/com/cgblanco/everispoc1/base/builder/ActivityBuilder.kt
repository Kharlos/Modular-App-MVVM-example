package ve.com.cgblanco.everispoc1.base.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ve.com.cgblanco.everispoc1.view.activities.MainActivity
import ve.com.cgblanco.everispoc1.view.fragment.HomeFragment
import ve.com.cgblanco.everispoc1.viewmodel.HomeViewModel
import ve.com.cgblanco.featuresignin.base.SignInFragmentBuilder


@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(
        modules = [
            SignInFragmentBuilder::class,
            SignInViewModelBuilder::class,
            FragmentBuildersModule::class,
            HomeViewModelBuilder::class
        ]
    )

    internal abstract fun bindMainActivity(): MainActivity

}