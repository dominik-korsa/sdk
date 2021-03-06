package io.github.wulkanowy.sdk.mobile.register

import com.google.gson.annotations.SerializedName

data class Student(

    @SerializedName("IdOkresKlasyfikacyjny")
    val classificationPeriodId: Int,

    @SerializedName("OkresPoziom")
    val periodLevel: Int,

    @SerializedName("OkresNumer")
    val periodNumber: Int,

    @SerializedName("OkresDataOd")
    val periodDateFrom: Long,

    @SerializedName("OkresDataDo")
    val periodDateTo: Long,

    @SerializedName("OkresDataOdTekst")
    val periodDateFromText: String,

    @SerializedName("OkresDataDoTekst")
    val periodDateToText: String,

    @SerializedName("IdJednostkaSprawozdawcza")
    val reportingUnitId: Int,

    @SerializedName("JednostkaSprawozdawczaSkrot")
    val reportingUnitShortcut: String,

    @SerializedName("JednostkaSprawozdawczaNazwa")
    val reportingUnitName: String,

    @SerializedName("JednostkaSprawozdawczaSymbol")
    val reportingUnitSymbol: String,

    @SerializedName("IdJednostka")
    val unitId: Int,

    @SerializedName("JednostkaNazwa")
    val unitName: String,

    @SerializedName("JednostkaSkrot")
    val unitShortcut: String,

    @SerializedName("OddzialSymbol")
    val classSymbol: String,

    @SerializedName("OddzialKod")
    val classCode: String?,

    @SerializedName("UzytkownikRola")
    val userRole: String,

    @SerializedName("UzytkownikLogin")
    val userLogin: String,

    @SerializedName("UzytkownikLoginId")
    val userLoginId: Int,

    @SerializedName("UzytkownikNazwa")
    val userName: String,

    @SerializedName("Id")
    val id: Int,

    @SerializedName("IdOddzial")
    val classId: Int,

    @SerializedName("Imie")
    val name: String,

    @SerializedName("Imie2")
    val nameSecond: String?,

    @SerializedName("Nazwisko")
    val surname: String,

    @SerializedName("Pseudonim")
    val nick: String?,

    @SerializedName("UczenPlec")
    val pupilGender: Int,

    @SerializedName("Pozycja")
    val position: Int,

    @SerializedName("LoginId")
    val loginId: Int?
) {

    var privateKey = ""

    var certificateKey = ""

    var mobileBaseUrl = ""
}
