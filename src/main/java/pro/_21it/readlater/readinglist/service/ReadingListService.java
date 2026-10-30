package pro._21it.readlater.readinglist.service;

import java.util.List;
import pro._21it.readlater.readinglist.domain.ReadingListItem;

public interface ReadingListService {

    List<ReadingListItem> findAll();

    ReadingListItem findById(long id);
}
