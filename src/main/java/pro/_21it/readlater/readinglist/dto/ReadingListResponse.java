package pro._21it.readlater.readinglist.dto;

import java.util.List;

public record ReadingListResponse(
        // Список элементов в текущей выборке
        List<ReadingItemResponse> items,
        // Количество элементов (обычно совпадает с items.size(), но полезно как мета-поле)
        int count
) {
    // Обёртка позволяет позже добавлять метаданные без поломки контракта.
}
