package pascal.lf.interpreter;

import pascal.lf.model.exp.*;

import java.util.Collections;
import java.util.Map;

/**
 * The visitor for expression evaluation.
 */
class ExpEvaluator implements ExpVisitor<Value> {

    private final Map<Var, Value> store;

    public ExpEvaluator(Map<Var, Value> store) {
        this.store = Collections.unmodifiableMap(store);
    }

    @Override
    public Value visit(Value e) {
        return e;
    }

    @Override
    public Value visit(Var e) {
        return store.get(e);
    }

    @Override
    public Value visit(UnaryExp e) {
        Value v = e.getOperand().accept(this);
        return switch (e.getOperator()) {
            case NOT -> v.not();
            case NEG -> v.neg();
        };
    }

    @Override
    public Value visit(BinaryExp e) {
        Value v1 = e.getOperand1().accept(this);
        Value v2 = e.getOperand2().accept(this);
        return switch (e.getOperator()) {
            case ADD -> v1.add(v2);
            case SUB -> v1.sub(v2);
            case MUL -> v1.mul(v2);
            case DIV -> v1.div(v2);
            case REM -> v1.rem(v2);
            case LT -> v1.lt(v2);
            case LEQ -> v1.leq(v2);
            case GT -> v1.gt(v2);
            case GEQ -> v1.geq(v2);
            case EQ -> v1.eq(v2);
            case NEQ -> v1.neq(v2);
            case SHL -> v1.shl(v2);
            case SHR -> v1.shr(v2);
            case AND -> v1.and(v2);
            case OR -> v1.or(v2);
            case XOR -> v1.xor(v2);
        };
    }

    @Override
    public Value visit(MuxExp e) {
        return e.getCondition().accept(this).equals(Exps.ZERO) ?
                e.getFalseBranch().accept(this) :
                e.getTrueBranch().accept(this);
    }

}
