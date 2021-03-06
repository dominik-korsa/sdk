package io.github.wulkanowy.sdk.mobile.interceptor

import io.github.wulkanowy.sdk.mobile.BaseLocalTest
import io.github.wulkanowy.sdk.mobile.exception.InvalidSymbolException
import io.github.wulkanowy.sdk.mobile.register.Student
import io.github.wulkanowy.sdk.mobile.repository.RegisterRepository
import io.reactivex.observers.TestObserver
import org.junit.Test
import retrofit2.create
import java.io.IOException

class ErrorInterceptorTest : BaseLocalTest() {

    @Test
    fun unknownError() {
        server.enqueueAndStart("bad-request.txt")

        val repo = RegisterRepository(getRetrofitBuilder().baseUrl("http://localhost:3030/").build().create())
        val students = repo.getStudents()
        val studentsObserver = TestObserver<List<Student>>()
        students.subscribe(studentsObserver)
        studentsObserver.assertNotComplete()
        studentsObserver.assertError(IOException::class.java)
    }

    @Test
    fun invalidSymbol_diacritics() {
        server.enqueueAndStart("invalid-symbol.html")

        val repo = RegisterRepository(getRetrofitBuilder().baseUrl("http://localhost:3030/").build().create())
        val students = repo.getStudents()
        val studentsObserver = TestObserver<List<Student>>()
        students.subscribe(studentsObserver)
        studentsObserver.assertNotComplete()
        studentsObserver.assertError(InvalidSymbolException::class.java)
    }
}
