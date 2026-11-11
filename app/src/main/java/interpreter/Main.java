package interpreter;

import interpreter.controller.Controller;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.repository.Repository;
import interpreter.view.*;

public class Main {
    public static void main(String[] args) {
        PrgState state1 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), Examples.EX1);
        Controller ctr1 = new Controller(new Repository(state1), true);

        PrgState state2 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), Examples.EX2);
        Controller ctr2 = new Controller(new Repository(state2), true);

        PrgState state3 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), Examples.EX3);
        Controller ctr3 = new Controller(new Repository(state3), true);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExampleCommand("1", Examples.EX1.toString(), ctr1));
        menu.addCommand(new RunExampleCommand("2", Examples.EX2.toString(), ctr2));
        menu.addCommand(new RunExampleCommand("3", Examples.EX3.toString(), ctr3));

        menu.show();
    }
}
