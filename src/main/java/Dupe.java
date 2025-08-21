public class Dupe {
    public static void main(String[] args) {
        String greetings = "____________________\n"
                + "Hello! I'm Dupe";
        System.out.println(greetings);
        query();
        exit();
    }

    public static void query() {
        System.out.println("What can I do for you?\n"
                + "____________________");
    }

    public static void exit() {
        System.out.println("Goodbye! Hope to see you again soon!");
    }
}
