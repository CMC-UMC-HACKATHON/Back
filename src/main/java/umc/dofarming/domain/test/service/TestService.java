package umc.dofarming.domain.test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.dofarming.global.common.exception.RestApiException;
import umc.dofarming.global.common.exception.code.GlobalErrorCode;

@Service
@RequiredArgsConstructor
public class TestService {

    public void CheckFlag(Integer flag) {
        if (flag == 1)
            throw new RestApiException(GlobalErrorCode.TEMP_EXCEPTION);
    }
}
