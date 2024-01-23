import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Note {
    protected String title;
    protected File place;

    Note(String title, File place){
        this.title = title;
        this.place = place;
    }

    public void edit(){
        this.display();
        Scanner in = new Scanner(System.in);
        System.out.print("Please choose:\n1- Delete line.\t2-Replace line.\n");
        int op = in.nextInt();
        System.out.print("Please enter line number: ");
        int x = in.nextInt();
        if(op == 1) deleteLine(x);
        else{
            System.out.println("Enter the new line: ");
            String toInsert = in.nextLine();
            replaceLine(x, toInsert);
        }
    }

    private boolean deleteLine(int x){
        try{
            Scanner stepper = new Scanner(place);
            ArrayList<String> before = new ArrayList<>(), after = new ArrayList<>();
            while(--x > 0 && stepper.hasNextLine()){
                before.add(stepper.nextLine());
            }
            if(!stepper.hasNextLine() && x >= 0){
                System.out.println("Invalid line number.");
                return false;
            }
            stepper.nextLine();
            while(stepper.hasNextLine()){
                after.add(stepper.nextLine());
            }
            PrintWriter out = new PrintWriter(new FileOutputStream(place), true);
            for(String elem : before){
                out.println(elem);
            }
            for(String elem : after){
                out.println(elem);
            }
        }catch(FileNotFoundException e){
            System.out.println("Note file lost.");
        }
        return true;
    }

    private boolean replaceLine(int x, String toInsert){
        try{
            Scanner stepper = new Scanner(place);
            PrintWriter out = new PrintWriter(new FileOutputStream(place), true);
            ArrayList<String> before = new ArrayList<>(), after = new ArrayList<>();
            while(--x > 0 && stepper.hasNextLine()){
                before.add(stepper.nextLine());
            }
            if(!stepper.hasNextLine() && x >= 0){
                System.out.println("Invalid line number.");
                return false;
            }
            stepper.nextLine();
            while(stepper.hasNextLine()){
                after.add(stepper.nextLine());
            }
            for(String elem : before){
                out.println(elem);
            }
            out.println(toInsert);
            for(String elem : after){
                out.println(elem);
            }
        }catch(FileNotFoundException e){
            System.out.println("Note file lost.");
        }
        return true;
    }

    public void display(){
        try{
            Scanner stepper = new Scanner(place);
            for(int counter = 1; stepper.hasNextLine(); counter++){
                System.out.println(counter + "\t" + stepper.nextLine());
            }
        }catch (FileNotFoundException e){
            System.out.println("Note file lost.");
        }
    }

    public boolean delete(){
        return this.place.delete();
    }

    public void append(){
        try {
            Scanner in = new Scanner(System.in);
            StringBuilder toAdd = new StringBuilder();
            String temp = "";
            while(true){
                temp = in.nextLine();
                if(temp.equals("/c")) break;
                else toAdd.append(temp).append('\n');
            }
            PrintWriter adder = new PrintWriter(new FileOutputStream(place, true));
            adder.print(toAdd);
            adder.flush();
        }catch (FileNotFoundException e){
            System.out.println("Note file lost.");
        }
    }

    public void overwrite(){
        try {
            Scanner in = new Scanner(System.in);
            StringBuilder toAdd = new StringBuilder();
            String temp = "";
            while(true){
                temp = in.nextLine();
                if(temp.equals("/c")) break;
                else toAdd.append(temp).append('\n');
            }
            PrintWriter adder = new PrintWriter(new FileOutputStream(place, true));
            adder.print(toAdd);
            adder.flush();
        }catch (FileNotFoundException e){
            System.out.println("Note file lost.");
        }
    }

    public boolean check(){
        return true;
    }
}
