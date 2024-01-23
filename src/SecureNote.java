import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class SecureNote extends Note{
    SecureNote(String title, File place){
        super(title, place);
    }

    public boolean check(){
        try {
            File passwords = new File(place.getParent(), "passwords.txt");
            Scanner checker = new Scanner(passwords);
            Scanner in = new Scanner(System.in);
            System.out.print("Please enter the password of the note titled " + this.title + ": ");
            String pass = in.next();
            while(checker.hasNextLine()){
                String[] temp = checker.nextLine().split(",");
                if(temp[0].equals(this.title)){
                    return temp[1].equals(pass);
                }
            }
            PrintWriter out = new PrintWriter(new FileOutputStream(passwords, true));
            out.println(this.title + ',' + pass);
            out.flush();
        }catch (FileNotFoundException e){
            System.out.println("Passwords file is missing.");
        }
        return false;
    }
}
