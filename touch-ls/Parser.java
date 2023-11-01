public class Parser {
    private String commandName;
    private String[] args;

    public boolean parse(String input) {
        String[] parts = input.trim().split("\\s+");
        if (parts.length == 0) {
            return false; // in case no command
        }
        commandName = parts[0];

        if (parts.length > 1) {
            args = new String[parts.length - 1];
            System.arraycopy(parts, 1, args, 0, parts.length - 1);
        } else {
            args = new String[0];
        }
        return true;
    }
    public String getCommandName() {
        return commandName;
    }

    public String[] getArgs() {
        return args;
    }
}
