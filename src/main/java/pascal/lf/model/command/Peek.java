package pascal.lf.model.command;

import pascal.lf.model.exp.Var;

public record Peek(
        Var var
) implements Command {

    public static Peek of(Var var) {
        return new Peek(var);
    }

    @Override
    public <T> T accept(CommandVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "peek " + var;
    }

}
