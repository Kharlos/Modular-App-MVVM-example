package ve.com.cgblanco.everispoc1.base.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ve.com.cgblanco.everispoc1.view.fragment.HomeFragment

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {


    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment
}