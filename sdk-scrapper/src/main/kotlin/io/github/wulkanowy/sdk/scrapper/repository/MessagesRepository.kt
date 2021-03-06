package io.github.wulkanowy.sdk.scrapper.repository

import com.google.gson.Gson
import io.github.wulkanowy.sdk.scrapper.ApiResponse
import io.github.wulkanowy.sdk.scrapper.ScrapperException
import io.github.wulkanowy.sdk.scrapper.getScriptParam
import io.github.wulkanowy.sdk.scrapper.interceptor.ErrorHandlerTransformer
import io.github.wulkanowy.sdk.scrapper.messages.Attachment
import io.github.wulkanowy.sdk.scrapper.messages.DeleteMessageRequest
import io.github.wulkanowy.sdk.scrapper.messages.Message
import io.github.wulkanowy.sdk.scrapper.messages.Recipient
import io.github.wulkanowy.sdk.scrapper.messages.ReportingUnit
import io.github.wulkanowy.sdk.scrapper.messages.SendMessageRequest
import io.github.wulkanowy.sdk.scrapper.messages.SentMessage
import io.github.wulkanowy.sdk.scrapper.service.MessagesService
import io.reactivex.Single
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class MessagesRepository(private val api: MessagesService) {

    fun getReportingUnits(): Single<List<ReportingUnit>> {
        return api.getUserReportingUnits().map { it.data.orEmpty() }
    }

    fun getRecipients(unitId: Int, role: Int = 2): Single<List<Recipient>> {
        return api.getRecipients(unitId, role)
            .compose(ErrorHandlerTransformer()).map { it.data.orEmpty() }
            .map { res ->
                res.map { it.copy(shortName = it.name.normalizeRecipient()) }
            }
    }

    fun getReceivedMessages(startDate: LocalDateTime?, endDate: LocalDateTime?): Single<List<Message>> {
        return api.getReceived(getDate(startDate), getDate(endDate))
            .compose(ErrorHandlerTransformer()).map { it.data.orEmpty() }
            .map { res ->
                res.asSequence()
                    .map { it.copy(folderId = 1) }
                    .sortedBy { it.date }
                    .toList()
            }
    }

    fun getSentMessages(startDate: LocalDateTime?, endDate: LocalDateTime?): Single<List<Message>> {
        return api.getSent(getDate(startDate), getDate(endDate))
            .compose(ErrorHandlerTransformer()).map { it.data.orEmpty() }
            .map { res ->
                res.asSequence()
                    .map { message ->
                        message.copy(
                            messageId = message.id,
                            folderId = 2,
                            recipient = message.recipient?.split(";")?.joinToString("; ") { it.normalizeRecipient() }
                        )
                    }
                    .sortedBy { it.date }
                    .toList()
            }
    }

    fun getDeletedMessages(startDate: LocalDateTime?, endDate: LocalDateTime?): Single<List<Message>> {
        return api.getDeleted(getDate(startDate), getDate(endDate))
            .compose(ErrorHandlerTransformer()).map { it.data.orEmpty() }
            .map { res ->
                res.asSequence()
                    .map { it.apply { removed = true } }
                    .sortedBy { it.date }
                    .toList()
            }
    }

    fun getMessageRecipients(messageId: Int, loginId: Int): Single<List<Recipient>> {
        return (if (0 == loginId) api.getMessageRecipients(messageId)
        else api.getMessageSender(loginId, messageId))
            .compose(ErrorHandlerTransformer()).map { it.data.orEmpty() }
            .map {
                it.map { recipient ->
                    recipient.copy(shortName = recipient.name.normalizeRecipient())
                }
            }
    }

    fun getMessageDetails(messageId: Int, folderId: Int, read: Boolean, id: Int?): Single<Message> {
        return api.getMessage(messageId, folderId, read, id)
            .compose(ErrorHandlerTransformer()).map { requireNotNull(it.data) }
    }

    fun getMessage(messageId: Int, folderId: Int, read: Boolean, id: Int?): Single<String> {
        return api.getMessage(messageId, folderId, read, id)
            .compose(ErrorHandlerTransformer()).map { requireNotNull(it.data) }
            .map { it.content.orEmpty() }
    }

    fun getMessageAttachments(messageId: Int, folderId: Int): Single<List<Attachment>> {
        return api.getMessage(messageId, folderId, false, null)
            .compose(ErrorHandlerTransformer()).map { requireNotNull(it.data) }
            .map { it.attachments.orEmpty() }
    }

    fun sendMessage(subject: String, content: String, recipients: List<Recipient>): Single<SentMessage> {
        return api.getStart().flatMap { res ->
            api.sendMessage(
                SendMessageRequest(
                    SendMessageRequest.Incoming(
                        recipients = recipients,
                        subject = subject,
                        content = content
                    )
                ),
                getScriptParam("antiForgeryToken", res).ifBlank { throw ScrapperException("Can't find antiForgeryToken property!") },
                getScriptParam("appGuid", res),
                getScriptParam("version", res)
            )
        }.compose(ErrorHandlerTransformer()).map { requireNotNull(it.data) }
    }

    fun deleteMessages(messages: List<Pair<Int, Int>>): Single<Boolean> {
        return api.getStart().flatMap { res ->
            api.deleteMessage(
                messages.map { (messageId, folderId) ->
                    DeleteMessageRequest(
                        messageId = messageId,
                        folderId = folderId
                    )
                },
                getScriptParam("antiForgeryToken", res),
                getScriptParam("appGuid", res),
                getScriptParam("version", res)
            )
        }.flatMap {
            if (it.isBlank()) Single.just(ApiResponse(true, null))
            else Single.just(Gson().fromJson(it, ApiResponse::class.java))
        }.compose(ErrorHandlerTransformer()).map { it.success }
    }

    private fun String.normalizeRecipient(): String {
        return this.substringBeforeLast("-").substringBefore(" [").substringBeforeLast(" (").trim()
    }

    private fun getDate(date: LocalDateTime?): String {
        if (date == null) return ""
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
    }
}
