package pro._21it.readlater.readinglist.repository;

import java.util.List;
import pro._21it.readlater.readinglist.domain.ReadingListItem;

public interface ReadingListRepository {

    List<ReadingListItem> findAll();

    ReadingListItem findById(long id);

    long nextId();

    boolean existsByExternalId(String externalId);

    ReadingListItem save(ReadingListItem itemToCreate); 

    ReadingListItem update(ReadingListItem itemToUpdate);

    boolean deleteById(long id);
}
