package umc.dofarming.domain.test.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import umc.dofarming.api_response.ApiResponse;
import umc.dofarming.domain.test.dto.TestRequest;
import umc.dofarming.domain.test.dto.TestResponse;
import umc.dofarming.domain.test.service.TestService;

@Tag(name = "예제 API", description = "테스트용 API")
@RestController
@AllArgsConstructor
@RequestMapping("/tests")
@Deprecated
public class TestController {

    private final TestService testService;

    @Operation(summary = "성공적인 응답 반환 API", description = "테스트 문자열을 반환하는 API입니다.")
    @GetMapping("/success")
    public ApiResponse<TestResponse.TempTestDTO> successResponseAPI() {
        return ApiResponse.onSuccess(TestResponse.TempTestDTO.builder().testString("성공했음!").build());
    }



    @Operation(summary = "예외처리 API", description = "flag 값에 따라 예외를 발생시키는 API입니다.")
    @Parameter(name = "flag", description = "1인 경우 예외처리", example = "1", required = true)
    @GetMapping("/exception")
    public ApiResponse<TestResponse.TempExceptionDTO> exceptionAPI(@RequestParam Integer flag) {
        testService.CheckFlag(flag);
        return ApiResponse.onSuccess(TestResponse.TempExceptionDTO.builder()
                .flag(flag)
                .build());
    }

    @Operation(summary = "유효성 검사 API", description = "요청 객체의 유효성을 검사하는 API입니다.")

    @PostMapping("/validation")
    public ApiResponse<TestResponse.TempTestDTO> testValidationAPI(
            @RequestBody @Valid TestRequest.TempTestRequest request) {
        return ApiResponse.onSuccess(TestResponse.TempTestDTO.builder()
                .testString(request.getTestString())
                .build());
    }

}
