package io.github.wulkanowy.sdk.messages

import com.google.gson.annotations.SerializedName
import io.github.wulkanowy.sdk.ApiRequest

data class MessageStatusChangeRequest(

    @SerializedName("WiadomoscId")
    val messageId: Int,

    @SerializedName("FolderWiadomosci")
    val folder: String,

    @SerializedName("Status")
    val status: String,

    @SerializedName("LoginId")
    val loginId: Int,

    @SerializedName("IdUczen")
    val studentId: Int
) : ApiRequest()
