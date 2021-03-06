package io.github.wulkanowy.sdk.mobile.repository

import io.github.wulkanowy.sdk.mobile.ApiRequest
import io.github.wulkanowy.sdk.mobile.register.CertificateRequest
import io.github.wulkanowy.sdk.mobile.register.CertificateResponse
import io.github.wulkanowy.sdk.mobile.register.Student
import io.github.wulkanowy.sdk.mobile.service.RegisterService
import io.reactivex.Single

class RegisterRepository(private val api: RegisterService) {

    fun getCertificate(token: String, pin: String, deviceName: String, android: String, firebaseToken: String): Single<CertificateResponse> {
        return api.getCertificate(CertificateRequest(
            tokenKey = token,
            pin = pin,
            deviceName = "$deviceName (Wulkanowy)",
            deviceSystemVersion = android,
            firebaseToken = firebaseToken
        ))
    }

    fun getStudents(): Single<List<Student>> = api.getPupils(object : ApiRequest() {}).map { requireNotNull(it.data) }
}
