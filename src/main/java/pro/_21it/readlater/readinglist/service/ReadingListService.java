package pro._21it.readlater.readinglist.service;

import java.util.List;
import pro._21it.readlater.readinglist.domain.ReadingListItem;
import pro._21it.readlater.readinglist.dto.ReadingItemResponse;
import pro._21it.readlater.readinglist.dto.ReadingListResponse;

public interface ReadingListService {

    ReadingListResponse findAll();

    ReadingItemResponse findById(long id);
}
