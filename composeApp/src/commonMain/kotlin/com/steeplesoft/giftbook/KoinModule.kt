package com.steeplesoft.giftbook

import com.arkivanov.decompose.router.stack.StackNavigation
import com.steeplesoft.giftbook.ui.drawer.NavigationConfig
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

// https://medium.com/@yeldar.nurpeissov/master-kotlin-multiplatform-navigation-with-decompose-add-di-with-kodein-and-koin-405462b2691b
// https://gist.github.com/aartikov/30e182fd58ed9697af498bb22ef4edfa
// https://medium.com/arconsis/dependency-injection-using-koin-in-kotlin-multiplatform-mobile-kmm-eb4cfe82ed6
// https://medium.com/@j.c.moreirapinto/simplifying-cross-platform-app-development-dependency-injection-with-koin-in-compose-multiplatform-f77595396fbc
// https://proandroiddev.com/how-to-use-koin-scopes-with-decompose-components-14d04ea18e1a


fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        modules(
            appModule
        )
        config?.invoke(this)
    }
}

val appModule = module {
    single<StackNavigation<NavigationConfig>> { StackNavigation<NavigationConfig>() }
}

