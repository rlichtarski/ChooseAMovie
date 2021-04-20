package com.rradzzio.chooseamovie.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    var pk: Int? = null,
    var title: String? = null,
    var year: String? = null,
    var poster: String? = null
) : Parcelable
