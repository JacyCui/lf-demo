package pascal.lf.model.command;

public interface Command {

    <T> T accept(CommandVisitor<T> visitor);

}
