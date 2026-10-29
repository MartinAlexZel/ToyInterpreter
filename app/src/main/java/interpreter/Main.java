package interpreter;

import interpreter.controller.Controller;
import interpreter.model.PrgState;
import interpreter.model.adt.*;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.stmt.*;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.repository.IRepo;
import interpreter.repository.MyRepo;
import interpreter.view.ExitCommand;
import interpreter.view.RunExampleCommand;
import interpreter.view.TextMenu;

public class Main {
    public static void main(String[] args) {
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        // Program 1: int v; v=2; print(v)
        IStmt ex1 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))
                )
        );
        PrgState prg1 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex1);
        IRepo repo1 = new MyRepo("log1.txt");
        Controller ctr1 = new Controller(repo1);
        repo1.add(prg1);
        menu.addCommand(new RunExampleCommand("1", ex1.toString(), ctr1));

        // Program 2: int a; int b; a=2+3*5; b=a+1; print(b)
        IStmt ex2 = new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new VarDeclStmt("b", new IntType()),
                        new CompStmt(
                                new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)), new ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(
                                        new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new IntValue(1)))),
                                        new PrintStmt(new VarExp("b"))
                                )
                        )
                )
        );
        PrgState prg2 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), ex2);
        IRepo repo2 = new MyRepo("log2.txt");
        Controller ctr2 = new Controller(repo2);
        repo2.add(prg2);
        menu.addCommand(new RunExampleCommand("2", ex2.toString(), ctr2));


        menu.show();
    }
}