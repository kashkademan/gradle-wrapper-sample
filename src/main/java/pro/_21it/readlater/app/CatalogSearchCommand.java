package pro._21it.readlater.app;

public record CatalogSearchCommand(String query) implements LaunchCommand {
    @Override
    public String summary() {
        return "launch=catalog-search, query=" + query;
    }
}
