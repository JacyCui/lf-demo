package pascal.lf.model.exp;

/**
 * Visitor interface for expressions {@link Exp}.
 * @param <T> type of visiting return value
 */
public interface ExpVisitor<T> {

    default T visit(Value e) {
        return visitDefault(e);
    }

    default T visit(Var e) {
        return visitDefault(e);
    }

    default T visit(BinaryExp e) {
        return visitDefault(e);
    }

    default T visit(UnaryExp e) {
        return visitDefault(e);
    }

    default T visit(MuxExp e) {
        return visitDefault(e);
    }

    default T visitDefault(Exp ignore) {
        return null;
    }

}
