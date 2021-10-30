package com.github.alekseygett.newsapp.feature.feed.domain.model

enum class Language(val value: String) { // ISO-639-1
    Arabic("ar"),
    German("de"),
    English("en"),
    Spanish("es"),
    French("fr"),
    Hebrew("he"),
    Italian("it"),
    Dutch("nl"),
    Norwegian("no"),
    Portuguese("pt"),
    Russian("ru"),
    NorthernSami("se"),
    // TODO: ???("ud"),
    Chinese("zh")
}

//Possible options: ar de en es fr he it nl no pt ru se ud zh