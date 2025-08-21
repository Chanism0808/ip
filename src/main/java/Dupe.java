import java.util.ArrayList;
import java.util.Scanner;

public class Dupe {
    public static void main(String[] args) {
        //initialising Task
        initialiseTask();

        String greetings = "____________________\n"
                + "Hello! I'm Dupe";
        System.out.println(greetings);
        query();
        while (true){
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            if(!input.equals("bye")){
                System.out.println("____________________\n"
                                    + input
                                    + "\n____________________");
            }
            else{
                exit();
                break;
            }
        }
    }

    public static void query() {
        System.out.println("What can I do for you?\n"
                + "____________________");
    }

    public static void exit() {
        System.out.println("____________________\n"
                        + "Goodbye! Hope to see you again soon!\n"
                        + "____________________");
    }

    public static void initialiseTask() {
        ArrayList<Task>  taskArrayList = new ArrayList<>();
        Task t1 = new Task("read book");
        Task t2 = new Task("return book");
        Task t3 = new Task("buy book");
        taskArrayList.add(t1);
        taskArrayList.add(t2);
        taskArrayList.add(t3);
    }
}
