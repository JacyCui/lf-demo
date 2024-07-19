package pascal.lf.model.command;

public enum Step implements Command {

    SKIP, RESET, CLOCK;

    @Override
    public <T> T accept(CommandVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }

}
