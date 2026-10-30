package pro._21it.readlater.readinglist.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import pro._21it.readlater.readinglist.domain.ReadingListItem;
import pro._21it.readlater.readinglist.dto.ReadingItemResponse;
import pro._21it.readlater.readinglist.dto.ReadingListResponse;
import pro._21it.readlater.readinglist.repository.ReadingListRepository;
import pro._21it.readlater.readinglist.repository.InMemoryReadingListRepository;
import pro._21it.readlater.readinglist.service.ReadingListMapper;

public class ReadingListServiceInMemoryImpl implements ReadingListService {
    private final ReadingListRepository repository = new InMemoryReadingListRepository();
    private final Logger logger = LoggerFactory.getLogger(ReadingListServiceInMemoryImpl.class);
    private final ReadingListMapper mapper = new ReadingListMapper();

    @Override
    public ReadingListResponse findAll() {
        List<ReadingListItem> readingList = repository.findAll();
        List<ReadingItemResponse> readingItemResponse = readingList.stream()
            .map(mapper::toResponse)
            .toList();
        ReadingListResponse response = new ReadingListResponse(readingItemResponse, readingItemResponse.size());

        return response;
    }

    @Override
    public ReadingItemResponse findById(long id) {
        logger.debug("Trying to find an item with ID: {}", id);
        ReadingListItem readingListItem = repository.findById(id);
        if (readingListItem == null) return null;
        ReadingItemResponse readingItemResponse = mapper.toResponse(readingListItem);

        return readingItemResponse;
    }
}
