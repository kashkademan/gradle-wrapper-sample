package pro._21it.readlater;

import pro._21it.readlater.dto.*;

import java.util.List;

public interface CatalogClient {
    List<CatalogSearchItemResponse> search(String query);
}
