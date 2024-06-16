package spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type.InvalidCredentialException
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.type.ModelNotFoundException
import spartacodingclub.nbcamp.kotlinspring.project.fco234.gameboard.global.exception.dto.ErrorResponse

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ModelNotFoundException::class)
    fun handleModelNotFoundException(
        e: ModelNotFoundException
    ): ResponseEntity<ErrorResponse> =

        ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse(e.message))


    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(
        e: IllegalStateException
    ): ResponseEntity<ErrorResponse> =

        ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ErrorResponse(e.message))


    @ExceptionHandler(InvalidCredentialException::class)
    fun handleInvalidCredentialException(
        e: InvalidCredentialException
    ): ResponseEntity<ErrorResponse> =

        ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ErrorResponse(e.message))

}