import java.util.Objects;
import java.util.Scanner;
import java.io.File;

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

    }
    public void rmdir(String[] args){

    }
    //this method will choose the suitable command method to be called
    public void chooseCommandAction(){
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        parser.parse(str);
        if(Objects.equals(parser.getCommandName(), "pwd")){
            pwd();
        }
    }
    public static void main(String[] args) {
        Terminal terminal = new Terminal();
        terminal.chooseCommandAction();
    }
}