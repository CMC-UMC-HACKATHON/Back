package umc.dofarming.domain.file.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import umc.dofarming.api_response.ApiResponse;
import umc.dofarming.domain.file.dto.FileCreateResponse;
import umc.dofarming.domain.file.service.FileService;

@Tag(name = "File API", description = "파일 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "파일 생성 API")
    public ApiResponse<FileCreateResponse> createFile
        (
            @Valid @Parameter(
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE) )
            @RequestPart("file") MultipartFile file
        )
    {
        return ApiResponse.onSuccess(fileService.createFile("", file));
    }

    @DeleteMapping
    @Operation(summary = "파일 삭제 API")
    public ApiResponse<String> deleteFile(@RequestParam("fileUrl") String fileUrl) {
        fileService.deleteFile(fileUrl);
        return ApiResponse.onSuccess("파일 삭제 성공");
    }
}

