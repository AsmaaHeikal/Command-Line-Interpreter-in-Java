import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Terminal {
    private Parser parser;

    public Terminal() {
        parser = new Parser();
        displayWelcomeMessage();
    }
//home.page
    private void displayWelcomeMessage() {
        System.out.println("Welcome to the Custom Command Line Interface (CLI)!");
        System.out.println("Available commands:");
        System.out.println(" touch [filename] - Creates a file with the specified name.");
        System.out.println(" ls - Lists the contents of the current directory.");
        System.out.println(" ls -r - Lists the contents of the current directory in reverse order.");
        System.out.println(" exit - Exits the CLI.");
        System.out.println("Please enter a command below:");
    }

    public void touch(String[] args) {
        if (args.length != 1) {
            System.out.println("touch: wrong number of arguments");
            System.out.println("Usage: touch [filename]");
            return;
        }

        File file = new File(args[0]);
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("file already exists.");
            }
        } catch (IOException e) {
            System.out.println("error.");
            e.printStackTrace();
        }
    }

    public void ls() {
        File currentDir = new File(".");
        String[] files = currentDir.list();
        if (files != null) {
            Arrays.sort(files);
            for (String file : files) {
                System.out.println(file);
            }
        }
    }

    public void lsR() {
        File currentDir = new File(".");
        String[] files = currentDir.list();
        if (files != null) {
            Arrays.sort(files, (a, b) -> b.compareTo(a));
            for (String file : files) {
                System.out.println(file);
            }
        }
    }

    public void chooseCommandAction() {
        switch (parser.getCommandName()) {
            case "touch":
                touch(parser.getArgs());
                break;
            case "ls":
                if (parser.getArgs().length == 0) {
                    ls();
                } else if (parser.getArgs().length == 1 && "-r".equals(parser.getArgs()[0])) {
                    lsR();
                } else {
                    System.out.println("Invalid arguments for ls.");
                }
                break;
            default:
                System.out.println("Unknown command: " + parser.getCommandName());
                System.out.println("Please refer to the available commands listed above.");
        }
    }

    public boolean parseAndExecute(String input) {
        if (parser.parse(input)) {
            chooseCommandAction();
            return true;
        }
        return false;
    }
}
