package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;

public class RunExampleCommand extends Command {
    private final Controller controller;

    public RunExampleCommand(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.allStep();
        } catch (MyException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
