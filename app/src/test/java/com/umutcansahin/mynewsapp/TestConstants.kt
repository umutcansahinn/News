package com.umutcansahin.mynewsapp

import androidx.annotation.VisibleForTesting

@VisibleForTesting
const val SERVER_PORT = 8000

@VisibleForTesting
const val COUNTRY_CODE = "us"

@VisibleForTesting
const val SEARCH_Q = "q"

@VisibleForTesting
const val TOP_HEADLINES_TEST = "/top-headlines?country=us"

@VisibleForTesting
const val EVERYTHING_TEST = "/everything?q=q&sortBy=us"

val newsResponse = """{
    "status": "ok",
    "totalResults": 35,
    "articles": [
    {
        "source": {
        "id": "usa-today",
        "name": "USA Today"
    },
        "author": "Laura Trujillo",
        "title": "Britney Spears shocked after husband files for divorce - USA TODAY",
        "description": "Britney Spears breaks silence on divorce filing, saying \"she couldn't take the pain anymore\" after six years with Sam Asghari",
        "url": "https://www.usatoday.com/story/entertainment/celebrities/2023/08/19/britney-spears-shocked-after-husband-files-for-divorce/70631250007/",
        "urlToImage": "https://www.usatoday.com/gcdn/authoring/authoring-images/2023/08/17/USAT/70607972007-ap-22160705498091.jpg?crop=2744,1551,x0,y285&width=2744&height=1551&format=pjpg&auto=webp",
        "publishedAt": "2023-08-19T15:49:32Z",
        "content": "Britney Spears said she is \"shocked\" after her split with actor Sam Asghari but that it's \"nobody's business\" in a video post on Instagram Saturday where the singer dances in a neon green bikini bott… [+2936 chars]"
    },
    {
        "source": {
        "id": null,
        "name": "YouTube"
    },
        "author": null,
        "title": "Zelenskyy in Sweden for talks, Putin meets with generals in Rostov-on-Don | Ukraine Update - DW News",
        "description": null,
        "url": "https://www.youtube.com/watch?v=Xwgiz-MD_18",
        "urlToImage": null,
        "publishedAt": "2023-08-19T15:30:15Z",
        "content": "Your browser isnt supported anymore. Update it to get the best YouTube experience and our latest features. Learn more\r\nRemind me later"
    }
    ]
}""".trimIndent()