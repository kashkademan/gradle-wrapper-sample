package pro._21it.readlater.readinglist.service;

import pro._21it.readlater.dto.CreateReadingItemRequest;
import pro._21it.readlater.readinglist.dto.ReadingItemResponse;
import pro._21it.readlater.readinglist.dto.ReadingListResponse;

public interface ReadingListService {

    ReadingListResponse findAll();

    ReadingItemResponse findById(long id);

    ReadingItemResponse createReadingListItem(CreateReadingItemRequest request);
}
