package ve.com.cgblanco.everispoc1.base


import dagger.Subcomponent
import dagger.android.AndroidInjector
import ve.com.cgblanco.everispoc1.view.activities.MainActivity



@Subcomponent
interface MainActivityComponent : AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()
}