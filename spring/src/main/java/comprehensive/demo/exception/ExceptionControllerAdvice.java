package comprehensive.demo.exception;

import comprehensive.demo.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;

/**
 * 예외 처리 컨트롤러
 */
@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    /**
     * @Valid 혹은 @Validated로 binding error 발생시
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("[handleMethodArgumentNotValidException] ex", e);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_TYPE_VALUE, e.getBindingResult());

//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

    /**
     * @ModelAttribute로 binding error 발생시
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        log.error("[handleBindException] ex", e);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_TYPE_VALUE, e.getBindingResult());

//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);

    }

    /**
     * enum type 일치하지 않아 binding 못할 경우 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("[handleMethodArgumentTypeMismatchException] ex", e);
        ErrorResponse errorResponse = ErrorResponse.of(e);

//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("[handleHttpRequestMethodNotSupportedException] ex", e);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.METHOD_NOT_ALLOWED);

//        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

    /**
     * Authentication 객체가 필요한 권한을 보유하지 않은 경우 발생함
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        log.error("[handleAccessDeniedException] ex", e);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.HANDLE_ACCESS_DENIED);

//        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ErrorCode.HANDLE_ACCESS_DENIED.getStatus()));
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

    /**
     * 사용자 정의 예외 처리
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(final CustomException e) {
        log.error("[handleCustomException] ex", e);
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);

//        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorCode.getStatus()));
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

    /**
     * 그 외 모든 에러 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("[handleException] ex", e);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INETRNAL_SERVER_ERROR);

//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }
}
