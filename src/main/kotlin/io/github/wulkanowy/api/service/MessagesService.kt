package io.github.wulkanowy.api.service

import io.github.wulkanowy.api.messages.Message
import io.github.wulkanowy.api.messages.MessagesResponse
import io.github.wulkanowy.api.messages.Recipient
import io.github.wulkanowy.api.messages.ReportingUnit
import io.reactivex.Single
import retrofit2.http.*

interface MessagesService {

    @GET("NowaWiadomosc.mvc/GetJednostkiUzytkownika")
    fun getUserReportingUnits(): Single<MessagesResponse<List<ReportingUnit>>>

    @GET("NowaWiadomosc.mvc/GetAdresaci")
    fun getRecipients(@Query("IdJednostkaSprawozdawcza") reportingUnitId: Int, @Query("Rola") role: Int): Single<MessagesResponse<List<Recipient>>>

    @GET("Wiadomosc.mvc/GetWiadomosciOdebrane")
    fun getReceived(@Query("dataOd") dateStart: String, @Query("dataDo") dateEnd: String): Single<MessagesResponse<List<Message>>>

    @GET("Wiadomosc.mvc/GetWiadomosciWyslane")
    fun getSent(@Query("dataOd") dateStart: String, @Query("dataDo") dateEnd: String): Single<MessagesResponse<List<Message>>>

    @GET("Wiadomosc.mvc/GetWiadomosciUsuniete")
    fun getDeleted(@Query("dataOd") dateStart: String, @Query("dataDo") dateEnd: String): Single<MessagesResponse<List<Message>>>

    @POST("Wiadomosc.mvc/GetTrescWiadomosci")
    @FormUrlEncoded
    fun getMessage(@Field("idWiadomosc") id: Int, @Field("Folder") folderId: Int): Single<MessagesResponse<Message>>
}