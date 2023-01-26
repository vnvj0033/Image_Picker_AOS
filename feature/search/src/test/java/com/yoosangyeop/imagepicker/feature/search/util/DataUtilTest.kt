package com.yoosangyeop.imagepicker.feature.search.util

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test


class DataUtilTest {

    @Test
    fun changeDatePatternTest() {
        val date = "2001-07-04T12:08:56.235-07:00"
        val pattern1 = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
        val pattern2 = "yyyy-MM-dd HH:mm:ss"

        val success = DateUtil.changeDatePattern(
            date,
            pattern1,
            pattern2
        )

        val notChange = DateUtil.changeDatePattern(
            date,
            pattern2,
            pattern1
        )

        assertThat(success, `is`("2001-07-05 04:08:56"))
        assertThat(notChange, `is`(date))
    }
}