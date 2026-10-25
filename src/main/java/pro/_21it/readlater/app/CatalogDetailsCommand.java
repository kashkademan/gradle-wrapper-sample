package pro._21it.readlater.app;

public record CatalogDetailsCommand(String externalId) implements LaunchCommand {
    @Override
    public String summary() {
        return "launch=catalog-details, externalId=" + externalId;
    }
}
