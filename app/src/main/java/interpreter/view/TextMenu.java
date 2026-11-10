package interpreter.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private final Map<String, Command> commands = new HashMap<>();

    public void addCommand(Command c) {
        commands.put(c.getKey(), c);
    }

    private void printMenu() {
        System.out.println("========== MENU ==========");
        for (Command c : commands.values()) {
            String line = String.format("%4s : %s", c.getKey(), c.getDescription());
            System.out.println(line);
        }
        System.out.println("==========================");
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("Input option: ");
            String key = scanner.nextLine();
            Command com = commands.get(key);
            if (com == null) {
                System.out.println("Invalid Option");
                continue;
            }
            com.execute();
        }
    }
}
