import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class User {
    File folder;
    Note workSpace;
    User(String title){
        folder = new File(Main.usersFolders.getAbsolutePath()+"\\"+title);
    }
    public void createNote(String title, boolean secure){
        if(secure) title += "-secure";
        File f = new File(folder, title+".txt");
        if(f.exists()) {
            System.out.println("There exist a note of the same title, choose other title");
            return;
        }
        try{
            f.createNewFile();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        if(secure) {
            workSpace = new SecureNote(title, f);
            workSpace.check();
        }
        else workSpace = new Note(title, f);
    }
    public void openNote(String title){
        File f = new File(folder, title+".txt");
        if(!f.exists()) {
            System.out.println("No such note exist in your notes");
            return;
        }
        if(title.contains("-secure")) {
            this.workSpace = new SecureNote(title, f);
            if(!workSpace.check()){
                this.workSpace = null;
                this.folder = null;
                return;
            }
        }
        else this.workSpace = new Note(title, f);
        Scanner in = new Scanner(System.in);
        String op;
        while(true) {
            System.out.println("1- Edit");
            System.out.println("2- Append");
            System.out.println("3- Overwrite");
            System.out.println("4- Delete");
            System.out.println("5- Display");
            System.out.println("6- Exit");
            op = in.next();
            switch (op) {
                case "1" -> workSpace.edit();
                case "2" -> {
                    System.out.println("Please type your note:");
                    workSpace.append();
                }
                case "3" -> {
                    System.out.println("Please type your note:");
                    workSpace.overwrite();
                }
                case "4" -> {
                    workSpace.delete();
                    return;
                }
                case "5" -> this.workSpace.display();
                default -> {
                    return;
                }
            }
        }
    }
    public void listNotes(){
        String [] a = folder.list();
        if(folder.isDirectory()) {
            for (String s : a) {
                System.out.println(s);
            }
        }
    }
}
