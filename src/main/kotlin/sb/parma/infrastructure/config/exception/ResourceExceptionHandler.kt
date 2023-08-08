package sb.parma.infrastructure.config.exception

import com.auth0.jwt.exceptions.JWTCreationException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import sb.parma.infrastructure.config.exception.model.StandardError
import java.time.LocalDateTime

private const val VALIDATION_ERROR = "Validation error"

@ControllerAdvice
class ResourceExceptionHandler {

    @ExceptionHandler(NotFoundException::class)
    fun notFoundException(
        e: NotFoundException,
        request: HttpServletRequest
    ): ResponseEntity<StandardError<String>> {
        val exception = StandardError(
            status = HttpStatus.NOT_FOUND.value(),
            error = HttpStatus.NOT_FOUND.name,
            path = request.servletPath,
            message = e.message!!,
            timeStamp = LocalDateTime.now().toString()
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception)
    }

    @ExceptionHandler(JWTCreationException::class)
    fun notFoundException(
        e: JWTCreationException,
        request: HttpServletRequest
    ): ResponseEntity<StandardError<String>> {
        val exception = StandardError(
            status = HttpStatus.NOT_FOUND.value(),
            error = HttpStatus.NOT_FOUND.name,
            path = request.servletPath,
            message = e.message!! +" "+ e.cause,
            timeStamp = LocalDateTime.now().toString()
        )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun accessDeniedException(
        e: AccessDeniedException,
        request: HttpServletRequest
    ): ResponseEntity<StandardError<String>> {
        val exception = StandardError(
            status = HttpStatus.FORBIDDEN.value(),
            error = HttpStatus.FORBIDDEN.name,
            path = request.servletPath,
            message = e.message!!,
            timeStamp = LocalDateTime.now().toString()
        )
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception)
    }
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun methodArgumentNotValidException(
        e: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<StandardError<Map<String, List<String?>>>> {

        val errors = e.fieldErrors.groupBy(FieldError::getField, FieldError::getDefaultMessage)

        return ResponseEntity.badRequest().body(
            StandardError(
                status = HttpStatus.BAD_REQUEST.value(),
                message = VALIDATION_ERROR,
                error = errors,
                path = request.requestURI,
                timeStamp = LocalDateTime.now().toString()
            )
        )
    }

    @ExceptionHandler(BadRequestException::class)
    fun badRequestException(
        e: BadRequestException,
        request: HttpServletRequest
    ): ResponseEntity<StandardError<String>> {
        val exception = StandardError(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.name,
            path = request.servletPath,
            message = e.message!!,
            timeStamp = LocalDateTime.now().toString()
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception)
    }

}