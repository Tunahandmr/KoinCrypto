package com.tunahan.koincrypto

import org.koin.core.qualifier.named
import org.koin.dsl.module

val anotherModule = module {

    scope<ListFragment> {
        scoped(qualifier = named("hello")) {
            "hello kotlin"
        }

        scoped(qualifier = named("hi")) {
            "hi kotlin"
        }
    }
}