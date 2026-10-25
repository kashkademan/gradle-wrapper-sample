package pro._21it.readlater.app;

import java.util.Arrays;

public final class LaunchCommandParser {
    public static LaunchCommand parse(String[] args) {
        if (args.length == 1 && "server".equals(args[0])) {
            return new ServerCommand();
        };

        if (args.length >= 3 && "catalog".equals(args[0]) && "search".equals(args[1])) {
            String query = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
            return new CatalogSearchCommand(query);
        };

        if (args.length == 3 && "catalog".equals(args[0]) && "details".equals(args[1])) {
            return new CatalogDetailsCommand(args[2]);
        };

        throw new IllegalArgumentException("""
                Usage:
                    server
                    catalog search <query...>
                    catalog search <externalId>
                """.trim());
    }
}
