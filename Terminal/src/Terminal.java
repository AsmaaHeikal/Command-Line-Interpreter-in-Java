import java.io.BufferedWriter;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;
import java.io.File;
import java.nio.file.Path;
import java.io.FileWriter;
import java.io.IOException;

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
    public int getArgsLength(){
        return args.length;
    }
}

class Terminal {
    Parser parser;
    String currentDirectory=System.getProperty("user.dir");

    public Terminal() {
        this.parser = new Parser(); // Initialize the parser object
    }
    //Implement each command in a method, for example:
    public String pwd(){
        return currentDirectory;
    }
    public void cd(String[] args){
        if(args.length==0){
            String homeDirectory = System.getProperty("user.home");
            System.out.println("Home directory: " + homeDirectory);
            currentDirectory = homeDirectory;
        }
        else{
            if(Objects.equals(args[0], "..")){
                Path currentPath = Paths.get(currentDirectory);
                Path parentPath = currentPath.toAbsolutePath().getParent();
                if (parentPath != null) {
                    System.out.println("Previous directory: " + parentPath);
                    currentDirectory = parentPath.toString();
                } else {
                    System.out.println("Previous directory not found.");
                }
            }
            else{
                Path path = Paths.get(args[0]);
                Path absPath = path.toAbsolutePath();
                File file = new File(absPath.toString()).getAbsoluteFile();
                System.out.println("Current directory: " + file);
                currentDirectory = file.toString();
            }
        }
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
            File file = new File(absPath.toString()).getAbsoluteFile();
            if (file.delete()) {
                System.out.println("Directory deleted");
            } else {
                System.out.println("Directory is not empty");
            }
        }
    }
    public void rm(String[] args){
        File file = new File(args[0]);
        if(file.delete()){
            System.out.println("File deleted");
        }
        else{
            System.out.println("File not found");
        }
    }
    public String[] echo(String[] args) {
        return args;
    }
    public void writeToFile(String[] args){
        try {
            File myFile = new File(args[args.length-1]);
            myFile.createNewFile();
            FileWriter myWriter = new FileWriter(args[args.length-1]);
            if(Objects.equals(parser.getCommandName(), "pwd")) {
                myWriter.write(pwd());
            }
            else if(Objects.equals(parser.getCommandName(), "echo")){
                for(String i :echo(parser.getArgs())){
                    if(Objects.equals(i, ">")) break;
                    else {
                        myWriter.write(i + " ");
                    }
                }
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

    }
    public void appendToFile(String[] args){
        try {
            File myFile = new File(args[args.length-1]);
            myFile.createNewFile();
            BufferedWriter myWriter = new BufferedWriter(new FileWriter(args[args.length-1],true));

            if(Objects.equals(parser.getCommandName(), "pwd")) {
                myWriter.write(pwd());
            }
            else if(Objects.equals(parser.getCommandName(), "echo")){
                for(String i :echo(parser.getArgs())){
                    if(Objects.equals(i, ">>")) break;
                    else {
                        myWriter.write(i + " ");
                    }
                }
            }
            myWriter.close();
            System.out.println("Successfully appended to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }

    }
    //this method will choose the suitable command method to be called
    public void chooseCommandAction(){
        System.out.print(">");
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        parser.parse(str);
        while(!str.equals("exit")) {
            if( parser.getArgsLength()>2&&parser.getArgs()[parser.getArgsLength()-2].equals(">") ){
                writeToFile(parser.getArgs());
            }else if(parser.getArgsLength()>2&&parser.getArgs()[parser.getArgsLength()-2].equals(">>") ){
                appendToFile(parser.getArgs());
            }else if (Objects.equals(parser.getCommandName(), "pwd")) {
                System.out.println(pwd());
            } else if (Objects.equals(parser.getCommandName(), "cd")) {
                cd(parser.getArgs());
            } else if (Objects.equals(parser.getCommandName(), "mkdir")) {
                mkdir(parser.getArgs());
            } else if (Objects.equals(parser.getCommandName(), "rmdir")) {
                rmdir(parser.getArgs());
            } else if(Objects.equals(parser.getCommandName(), "rm")){
                rm(parser.getArgs());
            } else if(Objects.equals(parser.getCommandName(), "echo")){
                for(String i : echo(parser.getArgs())){
                    System.out.print(i);
                }
                System.out.println();
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
