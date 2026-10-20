package pro._21it.reqdlater.dto;

import java.util.List;

public record OpenLibraryDocDto(
    String key,
    String title,
    List <String> author_names
) {};
