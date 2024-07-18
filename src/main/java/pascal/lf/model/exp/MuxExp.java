package pascal.lf.model.exp;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * {@link MuxExp} represents the ternary conditional expression in lambdaF.
 */
public class MuxExp extends AbstractExp {

    private final Exp condition;

    private final Exp trueBranch;

    private final Exp falseBranch;

    MuxExp(Exp condition, Exp trueBranch, Exp falseBranch) {
        this.condition = condition;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    @Override
    protected Set<Var> computeUses() {
        Set<Var> result = new HashSet<>(condition.getUses());
        result.addAll(trueBranch.getUses());
        result.addAll(falseBranch.getUses());
        return result;
    }

    @Override
    public <T> T accept(ExpVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "mux(%s, %s, %s)".formatted(condition, trueBranch, falseBranch);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MuxExp muxExp)) return false;
        return Objects.equals(condition, muxExp.condition) &&
                Objects.equals(trueBranch, muxExp.trueBranch) &&
                Objects.equals(falseBranch, muxExp.falseBranch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(condition, trueBranch, falseBranch);
    }

}
