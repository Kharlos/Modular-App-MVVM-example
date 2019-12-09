package ve.com.cgblanco.featuresignin.base

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ve.com.cgblanco.featuresignin.view.SignInFragment

@Module
abstract class SignInFragmentBuilder {

    @ContributesAndroidInjector
    internal abstract fun bindSignFragment(): SignInFragment

}