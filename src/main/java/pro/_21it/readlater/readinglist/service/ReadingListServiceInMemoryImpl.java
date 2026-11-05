package pro._21it.readlater.readinglist.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import pro._21it.readlater.readinglist.domain.ReadingListItem;
import pro._21it.readlater.readinglist.domain.ReadingStatus;
import pro._21it.readlater.readinglist.dto.ReadingItemResponse;
import pro._21it.readlater.readinglist.dto.ReadingListResponse;
import pro._21it.readlater.dto.CreateReadingItemRequest;
import pro._21it.readlater.readinglist.repository.ReadingListRepository;

public class ReadingListServiceInMemoryImpl implements ReadingListService {
    private final ReadingListRepository repository;
    private final Logger logger = LoggerFactory.getLogger(ReadingListServiceInMemoryImpl.class);
    private final ReadingListMapper mapper = new ReadingListMapper();

    public ReadingListServiceInMemoryImpl(ReadingListRepository repository) {
        this.repository = repository;
    }

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

    public ReadingItemResponse createReadingListItem(CreateReadingItemRequest request) {
        if (request.externalId() != null && repository.existsByExternalId(request.externalId())) {
            // В учебном проекте — простое исключение, позже можно заменить на доменное/HTTP-ошибку
            throw new IllegalStateException("externalId already exists: " + request.externalId());
        }

        long id = repository.nextId();

        ReadingListItem itemToSave = new ReadingListItem(
                id,
                request.title(),
                request.author(),
                ReadingStatus.PLANNED,
                request.externalId(),
                request.comment()
        );

        ReadingListItem createdItem = repository.save(itemToSave);
        ReadingItemResponse readingItemResponse = mapper.toResponse(createdItem);

        return readingItemResponse;
    }
}
