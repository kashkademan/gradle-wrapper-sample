package pro._21it.readlater.catalog.client;

import pro._21it.readlater.catalog.dto.provider.ProviderSearchResponseDto;

public interface CatalogClient {
    public ProviderSearchResponseDto search(String query);
}
