package pascal.lf.model.command;

import pascal.lf.model.exp.Value;
import pascal.lf.model.exp.Var;

public record Poke(
        Var var,
        Value value
) implements Command {

    public static Poke of(Var var, Value value) {
        return new Poke(var, value);
    }

    @Override
    public <T> T accept(CommandVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "poke %s %s".formatted(var, value);
    }

}
