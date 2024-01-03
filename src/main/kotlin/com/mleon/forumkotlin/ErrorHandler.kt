package com.mleon.forumkotlin

import com.mleon.forumkotlin.shared.domain.exceptions.BadRequestException
import com.mleon.forumkotlin.shared.domain.exceptions.NotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.Date

data class ErrorMessageFormat(
    var status: Int? = null,
    var timestamp: Date? = null,
    var message: String? = null,
    var details: List<String>? = null,
    var context: String? = null,
)

@ControllerAdvice
class ErrorHandler : ResponseEntityExceptionHandler() {
    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any>? {
        val fieldsErrors = ex.bindingResult.fieldErrors.map { "${it.field}: ${it.defaultMessage.orEmpty()}" }
        val message = "Validation errors"
        val messageModel =
            ErrorMessageFormat(
                status = status.value(),
                timestamp = Date(),
                message = message,
                details = fieldsErrors,
                context = "null",
            )
        return ResponseEntity(messageModel, status)
    }

    @ExceptionHandler(value = [NotFoundException::class, BadRequestException::class])
    fun handleNotFoundException(
        ex: Exception,
        request: WebRequest,
    ): ResponseEntity<ErrorMessageFormat> {
        var status = HttpStatus.INTERNAL_SERVER_ERROR
        if (ex is NotFoundException) {
            status = HttpStatus.NOT_FOUND
        }
        if (ex is BadRequestException) {
            status = HttpStatus.BAD_REQUEST
        }
        val messageModel =
            ErrorMessageFormat(
                status = status.value(),
                timestamp = Date(),
                message = ex.message.orEmpty(),
                context = ex::class.simpleName,
                details = listOf(),
            )
        return ResponseEntity(messageModel, status)
    }
}
