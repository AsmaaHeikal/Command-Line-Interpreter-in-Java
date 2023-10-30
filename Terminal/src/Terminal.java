import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;
import java.io.File;
import java.nio.file.Path;

class Parser {
    String commandName; String[] args;
    //This method will divide the input into commandName and args
    //where "input" is the string command entered by the user
    public boolean parse(String input){
        String[] parts = input.split(" ");
        commandName = parts[0];
        args = new String[parts.length-1];
        for(int i=1; i<parts.length; i++){
            args[i-1] = parts[i];
        }
        return true;
    }
    public String getCommandName(){
        return commandName;
    }
    public String[] getArgs(){
        return args;
    }
}

class Terminal {
    Parser parser;

    public Terminal() {
        this.parser = new Parser(); // Initialize the parser object
    }
        //Implement each command in a method, for example:
    public void pwd(){
        File file = new File(".");
        String path = file.getAbsolutePath();
        System.out.println(path);
    }
    public void cd(String[] args){

    }
    public void mkdir(String[] args){
        for(int i=0; i<args.length; i++){
            File file = new File(args[i]);
            if(!file.exists()){
                file.mkdir();
            }
        }
        System.out.println("Created");
    }
    public void rmdir(String[] args){
        if(Objects.equals(args[0], "*")) {
            File file = new File(".");
            String path = file.getAbsolutePath();
            File[] files = file.listFiles();
            if (files!= null && files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        File[] itemContent = files[i].listFiles();
                        if (itemContent != null && itemContent.length == 0) {
                            files[i].delete();
                        }
                    }
                }
            }
            System.out.println("Deleted");
        }
        else{
            Path path = Paths.get(args[0]);
            Path absPath = path.toAbsolutePath();
            File file = new File(absPath.toString());
            System.out.println(file.getAbsolutePath());
            if(file.exists()) {
                if (file.delete()) {
                    System.out.println("Directory deleted");
                } else {
                    System.out.println("Directory is not empty");
                }
            }
            else{
                System.out.println("Directory not found");
            }
        }
    }
    //this method will choose the suitable command method to be called
    public void chooseCommandAction(){
        System.out.print(">");
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        parser.parse(str);
        while(!str.equals("exit")) {
            if (Objects.equals(parser.getCommandName(), "pwd")) {
                pwd();
            } else if (Objects.equals(parser.getCommandName(), "cd")) {
                cd(parser.getArgs());
            } else if (Objects.equals(parser.getCommandName(), "mkdir")) {
                mkdir(parser.getArgs());
            } else if (Objects.equals(parser.getCommandName(), "rmdir")) {
                rmdir(parser.getArgs());
            } else {
                System.out.println("Command not found");
            }
            System.out.print(">");
            str = sc.nextLine();
            parser.parse(str);
        }
    }
    public static void main(String[] args) {
        Terminal terminal = new Terminal();
        terminal.chooseCommandAction();
    }
}