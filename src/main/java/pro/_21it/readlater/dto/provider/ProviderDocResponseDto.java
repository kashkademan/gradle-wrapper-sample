package pro._21it.readlater.dto.provider;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProviderDocResponseDto (
        String key,
        String title,
        List<String> author_name
) {};
