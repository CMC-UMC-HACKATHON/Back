package umc.dofarming.api_response.exception;


import umc.dofarming.api_response.dto.ApiDto;

public interface BaseErrorCode {

    ApiDto getReasonHttpStatus();
}
