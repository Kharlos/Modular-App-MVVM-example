package ve.com.cgblanco.everispoc1.base


import android.app.Application
import ve.com.cgblanco.everispoc1.base.builder.ActivityBuilder
import ve.com.cgblanco.everispoc1.base.builder.viewmodel.ViewModelBuilder
import com.app.network.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AppModule::class,
        ViewModelBuilder::class,
        ActivityBuilder::class,
        NetworkModule::class
    ]
)

interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(app: MyApplication)

    override fun inject(instance: DaggerApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}
