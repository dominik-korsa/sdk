package io.github.wulkanowy.sdk.pojo

import io.github.wulkanowy.sdk.Sdk

data class Student(
    val email: String,
    val symbol: String,
    val isParent: Boolean,
    val studentId: Int,
    val userLoginId: Int,
    val studentName: String,
    val schoolSymbol: String,
    val schoolShortName: String,
    val schoolName: String,
    val className: String,
    val classId: Int,
    val loginType: Sdk.ScrapperLoginType,
    val loginMode: Sdk.Mode,
    val scrapperBaseUrl: String,
    val mobileBaseUrl: String,
    val certificateKey: String,
    val privateKey: String
)
