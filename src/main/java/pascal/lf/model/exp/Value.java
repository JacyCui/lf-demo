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

    // ---------- Value Computation Methods ---------

    public Value neg() {
        return new Value(num.negate());
    }

    public Value not() {
        if (num.equals(BigInteger.ZERO)) {
            return Exps.ONE;
        }
        return Exps.ZERO;
    }

    public Value add(Value other) {
        return new Value(num.add(other.num));
    }

    public Value sub(Value other) {
       return new Value(num.subtract(other.num));
    }

    public Value mul(Value other) {
        return new Value(num.multiply(other.num));
    }

    public Value div(Value other) {
        return new Value(num.divide(other.num));
    }

    public Value rem(Value other) {
        return new Value(num.remainder(other.num));
    }

    public Value lt(Value other) {
        return num.compareTo(other.num) < 0 ? Exps.ONE : Exps.ZERO;
    }

    public Value leq(Value other) {
        return num.compareTo(other.num) <= 0 ? Exps.ONE : Exps.ZERO;
    }

    public Value gt(Value other) {
        return num.compareTo(other.num) > 0 ? Exps.ONE : Exps.ZERO;
    }

    public Value geq(Value other) {
        return num.compareTo(other.num) >= 0 ? Exps.ONE : Exps.ZERO;
    }

    public Value eq(Value other) {
        return num.compareTo(other.num) == 0 ? Exps.ONE : Exps.ZERO;
    }

    public Value neq(Value other) {
        return num.compareTo(other.num) != 0 ? Exps.ONE : Exps.ZERO;
    }

    public Value shl(Value other) {
        return new Value(num.shiftLeft(other.num.intValue()));
    }

    public Value shr(Value other) {
        return new Value(num.shiftRight(other.num.intValue()));
    }

    public Value and(Value other) {
       return new Value(num.and(other.num));
    }

    public Value or(Value other) {
        return new Value(num.or(other.num));
    }

    public Value xor(Value other) {
        return new Value(num.xor(other.num));
    }

}
