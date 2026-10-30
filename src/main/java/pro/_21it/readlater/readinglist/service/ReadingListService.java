package pro._21it.readlater.readinglist.service;

import java.util.List;
import pro._21it.readlater.readinglist.domain.ReadingListItem;
import pro._21it.readlater.readinglist.dto.ReadingItemResponse;

public interface ReadingListService {

    List<ReadingListItem> findAll();

    ReadingItemResponse findById(long id);
}
