package pascal.lf.model.exp;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * {@link Value} represents numeral values in lambdaF.
 */
public class Value extends AbstractExp {

    private final BigInteger num;

    Value(BigInteger num) {
        this.num = num;
    }

    public BigInteger getNumber() {
        return num;
    }

    @Override
    protected Set<Var> computeUses() {
        return Collections.emptySet();
    }

    @Override
    public <T> T accept(ExpVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return num.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Value value)) return false;
        return Objects.equals(num, value.num);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(num);
    }

}
