package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;

public class RunExampleCommand extends Command {
    private Controller ctr;

    public RunExampleCommand(String key, String description, Controller ctr) {
        super(key, description);
        this.ctr = ctr;
    }

    @Override
    public void execute() {
        try {
            ctr.allStep();
            IO.println("Execution finished successfully. Check the log file.");
        } catch (MyException e) {
            IO.println("Runtime Error: " + e.getMessage());
        } catch (Exception e) {
            IO.println("Error: " + e.getMessage());
        }
    }
}