package pro._21it.readlater.readinglist.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import pro._21it.readlater.readinglist.domain.ReadingListItem;
import pro._21it.readlater.readinglist.domain.ReadingStatus;

public class InMemoryReadingListRepository implements ReadingListRepository {
    private final Logger logger = LoggerFactory.getLogger(InMemoryReadingListRepository.class);
    private Map<Long, ReadingListItem> storage = new HashMap<>();
    private AtomicLong nextId = new AtomicLong(0);

    public InMemoryReadingListRepository() {
    // Стартовые данные нужны только для демонстрации read-only эндпоинтов.
        putSample("Clean Code", "Robert C. Martin", ReadingStatus.PLANNED, "OL12345M", "comment 1");
        putSample("Effective Java", "Joshua Bloch", ReadingStatus.IN_PROGRESS, "OL67890M", "comment 2");
    }

    @Override
    public List<ReadingListItem> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public ReadingListItem findById(long id) {
        return storage.get(id);
    }

    @Override
    public long nextId() {
        return nextId.incrementAndGet();
    }

    @Override
    public boolean existsByExternalId(String externalId) {
        return false;
    }

    @Override
    public ReadingListItem save(ReadingListItem itemToCreate) {
        storage.put(itemToCreate.getId(), itemToCreate);
        return findById(itemToCreate.getId());
    }

    @Override
    public ReadingListItem update(ReadingListItem itemToUpdate) {
        storage.put(itemToUpdate.getId(), itemToUpdate);
        return findById(itemToUpdate.getId());
    }

    private void putSample(String title, String author, ReadingStatus status, String externalId, String comment) {
        long id = nextId();
        ReadingListItem item = new ReadingListItem(id, title, author, status, externalId, comment);
        storage.put(id, item);
    }
}
