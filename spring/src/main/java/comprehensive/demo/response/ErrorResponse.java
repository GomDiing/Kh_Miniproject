package comprehensive.demo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import comprehensive.demo.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 공통 오류 페이지
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private String message;
    private int statusCode;
    private List<FieldError> errors;

    private String code;

    /**
     * 오류 코드와 검증(Valid) 오류 포함한 오류 페이지 생성자
     */
    private ErrorResponse(final ErrorCode code, final List<FieldError> errors) {
        this.message = code.getMessage();
        this.statusCode = code.getStatus();
        this.code = code.getCode();
        this.errors = errors;
    }

    /**
     * 오류 코드를 포함한 오류 페이지 생성자
     */
    private ErrorResponse(final ErrorCode code) {
        this.message = code.getMessage();
        this.statusCode = code.getStatus();
        this.code = code.getCode();
    }

    /**
     * Binding 오류가 발생시 오류 페이지 생성 메서드
     */
    public static ErrorResponse of(final ErrorCode errorCode, final BindingResult bindingResult) {
        return new ErrorResponse(errorCode, FieldError.of(bindingResult));
    }

    /**
     * 에러 코드만 포함된 오류 페이지 생성 메서드
     */
    public static ErrorResponse of(final ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

    /**
     * 에러 코드와 필드 오류를 포함한 오류 페이지 생성 메서드
     */
    public static ErrorResponse of(final ErrorCode errorCode, final List<FieldError> errors) {
        return new ErrorResponse(errorCode, errors);
    }

    /**
     * 입력 타입이 맞지 않으면 발생하는 오류 페이지 생성 메서드
     */
    public static ErrorResponse of(MethodArgumentTypeMismatchException e) {
        final String value = e.getValue() == null ? "" : e.getValue().toString();
        final List<ErrorResponse.FieldError> errors = ErrorResponse.FieldError.of(e.getName(), value, e.getErrorCode());
        return new ErrorResponse(ErrorCode.INVALID_TYPE_VALUE, errors);
    }


    /**
     * 필드 오류 클래스
     */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        /**
         * 필드 오류 생성자
         */
        private FieldError(final String field, final String value, final String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        /**
         * 필드 오류를 생성해서 List 컬렉션에 넣고 반환
         */
        public static List<FieldError> of(final String field, final String value, final String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        /**
         * Binding 오류가 발생시 필드 오류에 맞게 생성해서 반환
         */
        private static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }
    }
}
