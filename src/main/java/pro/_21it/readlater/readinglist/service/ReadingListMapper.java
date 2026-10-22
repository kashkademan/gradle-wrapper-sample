package pro._21it.readlater.readinglist.service;

import pro._21it.readlater.readinglist.domain.ReadingListItem;
import pro._21it.readlater.readinglist.dto.CreateReadingItemRequest;
import pro._21it.readlater.readinglist.dto.UpdateReadingItemRequest;
import pro._21it.readlater.readinglist.dto.ReadingItemResponse;
import pro._21it.readlater.readinglist.dto.ReadingListResponse;

import java.util.List;

public class ReadingListMapper {
    // Здесь соберём все преобразования фичи reading list:
    // toDomain(...), applyUpdate(...), toResponse(...), toListResponse(...).

    public ReadingListItem toDomain(long id, CreateReadingItemRequest req) {
        return new ReadingListItem(
                id,
                req.title(),
                req.author(),
                req.status(),
                req.externalId(),
                req.comment()
        );
    }

    public void applyUpdate(ReadingListItem item, UpdateReadingItemRequest req) {
        // Полное обновление делегируем каноническому domain API
        item.update(
                req.title(),
                req.author(),
                req.status(),
                req.externalId(),
                req.comment()
        );
    }

    public ReadingItemResponse toResponse(ReadingListItem item) {
        // Явно перечисляем поля: это проще читать и проще проверять на ревью
        return new ReadingItemResponse(
                item.getId(), 
                item.getTitle(), 
                item.getAuthor(),
                item.getStatus(), 
                item.getExternalId(), 
                item.getComment()
        );
    }

    public ReadingListResponse toListResponse(List<ReadingListItem> items) {
        var dtos = items.stream().map(this::toResponse).toList();
        
        return new ReadingListResponse(dtos, dtos.size());
    }
}
