package ru.itmo.task3.controller.advice

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.persistence.EntityNotFoundException

data class MessageResponse(val message: String)

class RaffleException(msg: String) : RuntimeException(msg)

@ControllerAdvice
class RaffleControllerAdvice : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [EntityNotFoundException::class])
    private fun handleNotFound(
        ex: Exception, request: WebRequest
    ): ResponseEntity<Any> {
        return handleExceptionInternal(ex, MessageResponse(ex.message!!),
            HttpHeaders(), HttpStatus.NOT_FOUND, request)
    }

    @ExceptionHandler(value = [RaffleException::class])
    private fun handleRaffleException(
        ex: Exception, request: WebRequest
    ): ResponseEntity<Any> {
        return handleExceptionInternal(ex, MessageResponse(ex.message!!),
            HttpHeaders(), HttpStatus.CONFLICT, request)
    }
}