package pascal.lf.model;

import pascal.lf.model.exp.Exp;
import pascal.lf.model.exp.Var;

import java.util.Objects;
import java.util.Set;

/**
 * {@link Connection} represents connections like {@code x <= e}
 * in lambdaF circuit.
 */
public class Connection {

    private Var lhs;

    private Exp rhs;

    Connection(Var lhs, Exp rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public static Connection of(Var lhs, Exp rhs) {
        return new Connection(lhs, rhs);
    }

    public Var getDef() {
        return lhs;
    }

    public Set<Var> getUses() {
        return rhs.getUses();
    }

    public Exp getExp() {
        return rhs;
    }

    @Override
    public String toString() {
        return "%s <= %s".formatted(lhs, rhs);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Connection that)) return false;
        return Objects.equals(lhs, that.lhs) && Objects.equals(rhs, that.rhs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lhs, rhs);
    }

}
