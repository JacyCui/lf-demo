package pascal.lf.model.exp;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * {@link Var} represents variables in lambdaF.
 */
public class Var extends AbstractExp {

    private final String name;

    Var(String name) {
        this.name = name;
    }

    @Override
    protected Set<Var> computeUses() {
        return Collections.singleton(this);
    }

    @Override
    public <T> T accept(ExpVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Var var)) return false;
        return Objects.equals(name, var.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

}
