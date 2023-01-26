package com.yoosangyeop.imagepicker.feature.search.util

import com.yoosangyeop.imagepicker.model.search.FavoriteDate
import com.yoosangyeop.imagepicker.model.search.SearchItem
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class ExtensionTest {

    @Test
    fun sortedByFavoriteDateTest() {
        val search1 = object : SearchItem, FavoriteDate {
            override val thumbnail_url = ""
            override val datetime = ""
            override var favoriteDate: Long? = 1L
        }
        val search2 = object : SearchItem, FavoriteDate {
            override val thumbnail_url = ""
            override val datetime = ""
            override var favoriteDate: Long? = 2L
        }
        val search3 = object : SearchItem, FavoriteDate {
            override val thumbnail_url = ""
            override val datetime = ""
            override var favoriteDate: Long? = 3L
        }

        val list = listOf(search2, search1 ,search3).sortedByFavoriteDate() as List<FavoriteDate>

        assertThat(list[0].favoriteDate, `is`(search1.favoriteDate))
        assertThat(list[1].favoriteDate, `is`(search2.favoriteDate))
        assertThat(list[2].favoriteDate, `is`(search3.favoriteDate))
    }
}