package umc.dofarming.domain.test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.dofarming.api_response.exception.GeneralException;
import umc.dofarming.api_response.status.ErrorStatus;

@Service
@RequiredArgsConstructor
public class TestService {

    public void CheckFlag(Integer flag) {
        if (flag == 1)
            throw new GeneralException(ErrorStatus.TEMP_EXCEPTION);
    }
}
