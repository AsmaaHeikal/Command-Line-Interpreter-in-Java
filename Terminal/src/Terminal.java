class Parser {
    String commandName; String[] args;
    //This method will divide the input into commandName and args
    //where "input" is the string command entered by the user
    public boolean parse(String input){
        return true;
    }
    public String getCommandName(){
        return commandName;
    }
    public String[] getArgs(){
        return args;
    }
}

public class Terminal {
    Parser parser;
    //Implement each command in a method, for example:
    public String pwd(){
        return "current directory";
    }
    public void cd(String[] args){
        //change directory
    }
    //...

    //this method will choose the suitable command method to be called
    public void chooseCommandAction(){
        //...
    }
    public static void main(String[] args) {

    }
}