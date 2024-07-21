package pascal.lf.model;

import pascal.lf.model.command.Command;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Script implements Iterable<Command> {

    List<Command> commands = new ArrayList<>();

    public void addCommand(Command command) {
        commands.add(command);
    }

    @Override
    @Nonnull
    public Iterator<Command> iterator() {
        return commands.iterator();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        commands.forEach(c -> builder.append(c.toString()).append("\n"));
        return builder.toString();
    }

}
