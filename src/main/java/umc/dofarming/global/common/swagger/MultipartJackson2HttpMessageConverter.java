package umc.dofarming.global.common.swagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

//요청 및 응답을 JSON 형식으로 변환하는 데 사용
@Component
public class MultipartJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {

    // MultipartJackson2HttpMessageConverter 생성자
    public MultipartJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper, MediaType.APPLICATION_OCTET_STREAM);
    }

    // 클래스 및 미디어 유형을 쓸 수 있는지 여부를 반환
    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    // 타입 및 클래스, 미디어 유형을 쓸 수 있는지 여부를 반환
    @Override
    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
        return false;
    }

    // 미디어 유형을 쓸 수 있는지 여부를 반환
    @Override
    protected boolean canWrite(MediaType mediaType) {
        return false;
    }
}
