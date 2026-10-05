package pro._21it.readlater.dto;

import java.util.List;

public record CatalogSearchResponse (
    List<CatalogSearchItemResponse> items,
    int count
) {};
