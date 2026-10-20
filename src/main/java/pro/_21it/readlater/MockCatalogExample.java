package pro._21it.readlater;

import pro._21it.readlater.dto.*;

import java.util.List;

public class MockCatalogExample implements CatalogClient {
    @Override
    public List<CatalogSearchItemResponse> search(String query) {
        return List.of();
    }
}
