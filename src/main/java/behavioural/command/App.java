
package behavioural.command;

import java.util.Deque;
import java.util.LinkedList;

// Receiver: Performs the actual text editing operations.
class TextEditor {

    private final StringBuilder text = new StringBuilder();

    void append(String value) {
        text.append(value);
    }

    String deleteLast(int count) {
        int start = Math.max(0, text.length() - count);

        String removed = text.substring(start);
        text.delete(start, text.length());

        return removed;
    }

    String content() {
        return text.toString();
    }
}

// Command: Defines execute and undo operations.
interface Command {
    void execute();
    void undo();
}

// Concrete Command: Appends text and supports undo.
class AppendCommand implements Command {

    private final TextEditor editor;
    private final String value;

    AppendCommand(TextEditor editor, String value) {
        this.editor = editor;
        this.value = value;
    }

    @Override
    public void execute() {
        editor.append(value);
    }

    @Override
    public void undo() {
        editor.deleteLast(value.length());
    }
}

// Concrete Command: Deletes text and supports undo.
class DeleteCommand implements Command {

    private final TextEditor editor;
    private final int count;
    private String removed;

    DeleteCommand(TextEditor editor, int count) {
        this.editor = editor;
        this.count = count;
    }

    @Override
    public void execute() {
        removed = editor.deleteLast(count);
    }

    @Override
    public void undo() {
        editor.append(removed);
    }
}

// Invoker: Executes commands and maintains undo history.
class CommandHistory {

    private final Deque<Command> done = new LinkedList<>();

    void run(Command command) {
        command.execute();
        done.push(command);
    }

    void undo() {
        if (!done.isEmpty()) {
            done.pop().undo();
        }
    }
}

public class App {

    public static void main(String[] args) {

        TextEditor editor = new TextEditor();
        CommandHistory history = new CommandHistory();

        history.run(new AppendCommand(editor, "Hello"));
        history.run(new AppendCommand(editor, " World"));

        System.out.println("After append: " + editor.content());

        history.run(new DeleteCommand(editor, 6));

        System.out.println("After delete: " + editor.content());

        history.undo();

        System.out.println("After undo delete: " + editor.content());

        history.undo();

        System.out.println("After undo append: " + editor.content());
    }
}
