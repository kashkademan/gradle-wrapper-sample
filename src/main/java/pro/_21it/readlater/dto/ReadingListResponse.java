package pro._21it.readlater.dto;

import java.util.List;

public record ReadingListResponse(
    List<ReadingItemResponse> items,
    int count
) {};
