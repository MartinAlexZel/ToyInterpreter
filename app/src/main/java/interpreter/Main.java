package interpreter;

import interpreter.controller.Controller;
import interpreter.model.PrgState;
import interpreter.model.adt.FileTable;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.repository.Repository;
import interpreter.view.*;

public class Main {
    public static void main(String[] args) {
        PrgState state1 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(),new FileTable(), Examples.EX1);
        Controller ctr1 = new Controller(new Repository(state1,"log1.txt"), true);

        PrgState state2 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(),new FileTable(), Examples.EX2);
        Controller ctr2 = new Controller(new Repository(state2,"log2.txt"), true);

        PrgState state3 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(),new FileTable(), Examples.EX3);
        Controller ctr3 = new Controller(new Repository(state3,"log3.txt"), true);

        PrgState state4a = new PrgState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),new FileTable(),Examples.EX4A);
        Controller ctr4a = new Controller(new Repository(state4a,"log4a.txt"), true);
        PrgState state4b = new PrgState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),new FileTable(),Examples.EX4B);
        Controller ctr4b = new Controller(new Repository(state4b,"log4b.txt"), true);

        PrgState state5 = new PrgState(new MyStack<>(),new MyDictionary<>(),new MyList<>(),new FileTable(),Examples.EX5);
        Controller ctr5 = new Controller(new Repository(state5,"log5.txt"), true);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExampleCommand("1", Examples.EX1.toString(), ctr1));
        menu.addCommand(new RunExampleCommand("2", Examples.EX2.toString(), ctr2));
        menu.addCommand(new RunExampleCommand("3", Examples.EX3.toString(), ctr3));
        menu.addCommand(new RunExampleCommand("4a","(no file name) "+Examples.EX4A.toString(),ctr4a));
        menu.addCommand(new RunExampleCommand("4b",Examples.EX4B.toString(),ctr4b));
        menu.addCommand(new RunExampleCommand("5",Examples.EX5.toString(),ctr5));

        menu.show();
    }
}
