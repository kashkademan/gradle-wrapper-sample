package pro._21it.readlater.dto;

import java.util.List;

public record OpenLibraryDocDto(
        String key,
        String title,
        List<String> author_names) {
};
