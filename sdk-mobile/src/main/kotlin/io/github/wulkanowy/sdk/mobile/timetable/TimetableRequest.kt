package io.github.wulkanowy.sdk.mobile.timetable

import com.google.gson.annotations.SerializedName
import io.github.wulkanowy.sdk.mobile.ApiRequest

data class TimetableRequest(

    @SerializedName("DataPoczatkowa")
    val startDate: String,

    @SerializedName("DataKoncowa")
    val endDate: String,

    @SerializedName("IdOddzial")
    val classId: Int,

    @SerializedName("IdOkresKlasyfikacyjny")
    val classificationPeriodId: Int,

    @SerializedName("IdUczen")
    val studentId: Int
) : ApiRequest()
