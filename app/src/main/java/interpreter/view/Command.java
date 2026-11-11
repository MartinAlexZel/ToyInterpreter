package interpreter.view;

import interpreter.exceptions.MyException;
import interpreter.exceptions.RepoException;

public abstract class Command {
    private final String key;
    private final String description;

    public Command(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public abstract void execute()  throws MyException, RepoException;

    public String getKey() { return key; }
    public String getDescription() { return description; }
}
