package pro._21it.readlater.readinglist.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import pro._21it.readlater.readinglist.domain.ReadingListItem;
import pro._21it.readlater.readinglist.repository.ReadingListRepository;
import pro._21it.readlater.readinglist.repository.InMemoryReadingListRepository;

public class ReadingListServiceInMemoryImpl implements ReadingListService {
    private final ReadingListRepository repository = new InMemoryReadingListRepository();
    private final Logger logger = LoggerFactory.getLogger(ReadingListServiceInMemoryImpl.class);

    @Override
    public List<ReadingListItem> findAll() {
        return repository.findAll();
    }

    @Override
    public ReadingListItem findById(long id) {
        logger.debug("Trying to find an item with ID: {}", id);
        return repository.findById(id);
    }
}
