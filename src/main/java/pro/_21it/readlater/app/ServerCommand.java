
package pro._21it.readlater.app;

public record ServerCommand() implements LaunchCommand {
    @Override
    public String summary() {
        return "launch=server";
    }
}
