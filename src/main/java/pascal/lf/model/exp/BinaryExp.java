package pascal.lf.model.exp;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * {@link BinaryExp} represents binary expressions in lambdaF.
 */
public class BinaryExp extends AbstractExp {

    public enum Op {

        ADD, SUB, MUL, DIV, REM,
        LT, LEQ, GT, GEQ, EQ, NEQ,
        SHL, SHR, AND, OR, XOR;

        @Override
        public String toString() {
            return name().toLowerCase();
        }

    }

    private final Op operator;

    private final Exp operand1;

    private final Exp operand2;

    BinaryExp(Op operator, Exp operand1, Exp operand2) {
        this.operator = operator;
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    /**
     * @return the operator of this binary expression
     */
    public Op getOperator() {
        return operator;
    }

    /**
     * @return the first / left operand of this binary expression
     */
    public Exp getOperand1() {
        return operand1;
    }

    /**
     * @return the second / right operand of this binary expression
     */
    public Exp getOperand2() {
        return operand2;
    }

    @Override
    protected Set<Var> computeUses() {
        Set<Var> result = new HashSet<>(operand1.getUses());
        result.addAll(operand2.getUses());
        return result;
    }

    @Override
    public <T> T accept(ExpVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "%s(%s, %s)".formatted(operator, operand1, operand2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BinaryExp binaryExp)) return false;
        return operator == binaryExp.operator &&
                Objects.equals(operand1, binaryExp.operand1) &&
                Objects.equals(operand2, binaryExp.operand2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operator, operand1, operand2);
    }

}
