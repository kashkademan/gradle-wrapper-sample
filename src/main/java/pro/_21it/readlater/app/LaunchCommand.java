package pro._21it.readlater.app;

public sealed interface LaunchCommand permits ServerCommand, CatalogSearchCommand, CatalogDetailsCommand {
    String summary();
}
