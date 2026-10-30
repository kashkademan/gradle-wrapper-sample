package pro._21it.readlater.readinglist.repository;

import java.util.List;
import pro._21it.readlater.readinglist.domain.ReadingListItem;

public interface ReadingListRepository {

    List<ReadingListItem> findAll();

    ReadingListItem findById(long id);
}
