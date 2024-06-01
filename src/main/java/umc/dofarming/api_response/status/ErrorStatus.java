package umc.dofarming.api_response.status;

import java.util.Optional;
import java.util.function.Predicate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import umc.dofarming.api_response.dto.ApiDto;
import umc.dofarming.api_response.exception.BaseErrorCode;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "유효성 검증 오류"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Not Found"),
    KEY_NOT_EXIST(HttpStatus.BAD_REQUEST, "존재하지 않는 식별자"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한 없음"),

    FALIED_READ_IMAGE(HttpStatus.BAD_REQUEST, "이미지 파일 읽기 실패"),
    FALIED_READ_FILE(HttpStatus.BAD_REQUEST, "파일 읽기 실패"),
    FAILED_UPLOAD_S3_IMAGE(HttpStatus.BAD_REQUEST, "S3 이미지 업로드 실패"),
    FAILED_UPLOAD_S3_FILE(HttpStatus.BAD_REQUEST, "S3 파일 업로드 실패"),

    TEMP_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "임시 에러");

    private final HttpStatus httpStatus;
    private final String code;
    private String message;


    public String getMessage(String message) {
        this.message = message;
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }

    @Override
    public ApiDto getReasonHttpStatus() {
        return ApiDto.builder()
            .message(message)
            .code(code)
            .isSuccess(false)
            .httpStatus(httpStatus)
            .build();
    }
}
