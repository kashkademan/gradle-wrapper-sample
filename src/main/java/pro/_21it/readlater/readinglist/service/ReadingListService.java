package pro._21it.readlater.readinglist.service;

import pro._21it.readlater.readinglist.dto.CreateReadingItemRequest;
import pro._21it.readlater.readinglist.dto.UpdateReadingItemRequest;
import pro._21it.readlater.readinglist.dto.UpdateStatusRequest;
import pro._21it.readlater.readinglist.dto.ReadingItemResponse;
import pro._21it.readlater.readinglist.dto.ReadingListResponse;

public interface ReadingListService {

    ReadingListResponse findAll();

    ReadingItemResponse findById(long id);

    ReadingItemResponse createReadingListItem(CreateReadingItemRequest request);

    ReadingItemResponse updateReadingListItem(long id, UpdateReadingItemRequest request);

    ReadingItemResponse patchReadingListItem(long it, UpdateStatusRequest request);

    boolean delete(long id);
}
