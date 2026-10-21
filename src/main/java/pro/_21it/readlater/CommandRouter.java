package pro._21it.readlater;

import java.util.Arrays;

public class CommandRouter {
    private static final String USAGE = "USAGE: search <query> | details <id>";

    public CatalogCommand run(String[] args) {
        if (args.length == 0) {
            printUsage();
            return null;
        }

        CatalogCommand catalogCommand = switch (args[0]) {
            case "search" -> runSearch(args);
            case "details" -> runDetails(args);
            default -> {
                printUsage();
                yield null;
            }
        };

        return catalogCommand;
    }

    private CatalogCommand runSearch(String[] args) {
        if (args.length < 2) {
            printUsage();
            return null;
        }

        String query = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        return new CatalogCommand(CatalogCommandType.SEARCH, query);
    }

    private CatalogCommand runDetails(String[] args) {
        if (args.length != 2) {
            printUsage();
            return null;
        }

        return new CatalogCommand(CatalogCommandType.DETAILS, args[1]);
    }

    private void printUsage() {
        System.out.println(USAGE);
    }
}
