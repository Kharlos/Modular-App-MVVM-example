package ve.com.cgblanco.everispoc1.base.builder

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ve.com.cgblanco.featuresignin.viewmodel.SignInViewModel

@Module
abstract class SignInViewModelBuilder {

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    internal abstract fun bindSignViewModel(viewModel: SignInViewModel): ViewModel



}