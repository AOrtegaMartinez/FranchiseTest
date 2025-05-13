package franchise.test.mongo.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MapperUtils {
    private final ObjectMapper mapper;

    @SneakyThrows
    public <T, U> U mapToEntity(T source, Class<U> targetClass) {
        return mapper.convertValue(source, targetClass);
    }
}
