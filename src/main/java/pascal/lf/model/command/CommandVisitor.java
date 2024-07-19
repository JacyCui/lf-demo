package pascal.lf.model.command;

public interface CommandVisitor<T> {

    default T visit(Step c) {
        return visitDefault(c);
    }

    default T visit(Peek c) {
        return visitDefault(c);
    }

    default T visit(Poke c) {
        return visitDefault(c);
    }

    default T visitDefault(Command ignore) {
        return null;
    }

}
