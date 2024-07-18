package pascal.lf.model.exp;

import java.util.Set;

/**
 * {@link UnaryExp} represents unary expressions in lambdaF.
 */
public class UnaryExp extends AbstractExp {

    public enum Op {

        NEG, NOT;

        @Override
        public String toString() {
            return name().toLowerCase();
        }

    }

    private final Op operator;

    private final Exp operand;

    UnaryExp(Op operator, Exp operand) {
        this.operator = operator;
        this.operand = operand;
    }

    public Op getOperator() {
        return operator;
    }

    public Exp getOperand() {
        return operand;
    }

    @Override
    protected Set<Var> computeUses() {
        return operand.getUses();
    }

    @Override
    public <T> T accept(ExpVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "%s(%s)".formatted(operator, operand);
    }

}