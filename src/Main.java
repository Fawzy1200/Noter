import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileOutputStream;

public class Main {
    private static final File usernamesAndPasswords = new File("C:\\Users\\abdul\\IdeaProjects\\Noter\\src\\usernamesAndPasswords.txt");
    static final File usersFolders = new File("C:\\Users\\abdul\\IdeaProjects\\Noter\\src\\usersFolders");
    private static String[] currUserinfo;
    private static User user;

    public static boolean register(String username, String password){
        try {
            if (find(username)) {
                System.out.println("This username is already taken, try again");
                return false;
            }
            PrintWriter adder = new PrintWriter(new FileOutputStream(usernamesAndPasswords, true));
            adder.println(username + "," + password);
            adder.close();
            File f1 = new File(usersFolders, username);
            f1.mkdir();
            File f2 = new File(f1, "passwords.txt");
            f2.createNewFile();
            user = new User(username);
            System.out.println("User have been registered successfully.");
            return true;
        }catch(IOException ex){
            System.out.println(ex);
            return false;
        }
    }
    public static boolean login(String username, String password){
        if (!find(username)) {
            System.out.println("No such username exist, try again");
            return false;
        }
        if(currUserinfo[1].equals(password)){
            user = new User(username);
            System.out.println("You logged in successfully");
            return true;
        }
        else {
            System.out.println("The Password incorrect try again");
            return false;
        }
    }
    public static void loginAndRegister(){
        boolean state = false;
        do{
            Scanner in = new Scanner(System.in);
            System.out.println("\n1- Login");
            System.out.println("2- Register");
            String op = in.next();
            if (op.equals("1")) {
                System.out.print("Username: ");
                String username = in.next();
                System.out.print("Password: ");
                String password = in.next();

                state = login(username, password);
            } else if (op.equals("2")) {
                System.out.print("Username: ");
                String username = in.next();
                System.out.print("Password: ");
                String password = in.next();

                state = register(username, password);
            } else {
                System.out.println("No such operation exist, try again");
            }
        }while(!state);
    }
    public static boolean find(String toFind){
        try{
            Scanner scan = new Scanner(usernamesAndPasswords);
            while(scan.hasNextLine()) {
                String line = scan.nextLine();
                currUserinfo = line.split(",", 2);
                if (currUserinfo[0].equals(toFind))
                    return true;
            }
            return false;
        }catch(FileNotFoundException ex){
            System.out.println(ex);
        }
        return false;
    }
    public static void main(String[] args){
        loginAndRegister();
        Scanner in = new Scanner(System.in);
        String op, title;
        while(true) {
            System.out.println("1- Create Note");
            System.out.println("2- Open Note");
            System.out.println("3- List");
            System.out.println("4- Log out");
            System.out.println("5- Exit");
            op = in.next();
            switch (op) {
                case "1" -> {
                    System.out.print("Title: ");
                    title = in.next();
                    System.out.print("Do you want the note to be secure? (Y/N)\t");
                    String choice = in.next();
                    if(choice.equalsIgnoreCase("Y")) user.createNote(title, true);
                    else user.createNote(title, false);
                }
                case "2" -> {
                    System.out.print("Title: ");
                    title = in.next();
                    user.openNote(title);
                }
                case "3" -> user.listNotes();
                case "4" -> loginAndRegister();
                case "5" -> System.exit(0);
            }
        }
    }
}