package pascal.lf.model.exp;

import java.math.BigInteger;

/**
 * {@link Exps} is the factory class for {@link Exp}.
 */
public class Exps {

    public static Value value(String num) {
        return new Value(new BigInteger(num));
    }

    public static Value value(long num) {
        return new Value(BigInteger.valueOf(num));
    }

    public static Var var(String name) {
        return new Var(name);
    }

    /**
     * @return {@code cond ? e1 : e2}
     */
    public static MuxExp mux(Exp cond, Exp e1, Exp e2) {
        return new MuxExp(cond, e1, e2);
    }

    /**
     * @return {@code -e}
     */
    public static UnaryExp neg(Exp e) {
        return new UnaryExp(UnaryExp.Op.NEG, e);
    }

    /**
     * @return {@code ~e}
     */
    public static UnaryExp not(Exp e) {
        return new UnaryExp(UnaryExp.Op.NOT, e);
    }

    /**
     * @return {@code e1 + e2}
     */
    public static BinaryExp add(Exp e1, Exp e2) {
        return new BinaryExp(BinaryExp.Op.ADD, e1, e2);
    }

    /**
     * @return {@code e1 - e2}
     */
    public static BinaryExp sub(Exp e1, Exp e2) {
        return new BinaryExp(BinaryExp.Op.SUB, e1, e2);
    }

    /**
     * @return {@code e1 * e2}
     */
    public static BinaryExp mul(Exp e1, Exp e2) {
        return new BinaryExp(BinaryExp.Op.MUL, e1, e2);
    }

    /**
     * @return {@code e1 / e2}
     */
    public static BinaryExp div(Exp e1, Exp e2) {
        return new BinaryExp(BinaryExp.Op.DIV, e1, e2);
    }

    /**
     * @return {@code e1 % e2}
     */
    public static BinaryExp rem(Exp e1, Exp e2) {
        return new BinaryExp(BinaryExp.Op.REM, e1, e2);
    }

    /**
     * @return {@code e1 < e2}
     */
    public static BinaryExp lt(Exp e1, Exp e2) {
        return new BinaryExp(BinaryExp.Op.LT, e1, e2);
    }

    /**
     * @return {@code e1 <= e2}
     */
    public static BinaryExp leq(Exp e1, Exp e2) {
        return new BinaryExp(BinaryExp.Op.LEQ, e1, e2);
    }

    /**
     * @return {@code e1 > e2}
     */
    public static BinaryExp gt(Exp e1, Exp e2) {
        return new BinaryExp(BinaryExp.Op.GT, e1, e2);
    }

    /**
     * @return {@code e1 >= e2}
     */
    public static BinaryExp geq(Exp e1, Exp e2) {
        return new BinaryExp(BinaryExp.Op.GEQ, e1, e2);
    }

    /**
     * @return {@code e1 == e2}
     */
    public static BinaryExp eq(Exp e1, Exp e2) {
        return new BinaryExp(BinaryExp.Op.EQ, e1, e2);
    }

    /**
     * @return {@code e1 != e2}
     */
    public static BinaryExp neq(Exp e1, Exp e2) {
        return new BinaryExp(BinaryExp.Op.NEQ, e1, e2);
    }

    /**
     * @return {@code e1 << e2}
     */
    public static BinaryExp shl(Exp e1, Exp e2) {
        return new BinaryExp(BinaryExp.Op.SHL, e1, e2);
    }

    /**
     * @return {@code e1 >> e2}
     */
    public static BinaryExp shr(Exp e1, Exp e2) {
        return new BinaryExp(BinaryExp.Op.SHR, e1, e2);
    }

    /**
     * @return {@code e1 & e2}
     */
    public static BinaryExp and(Exp e1, Exp e2) {
        return new BinaryExp(BinaryExp.Op.AND, e1, e2);
    }

    /**
     * @return {@code e1 | e2}
     */
    public static BinaryExp or(Exp e1, Exp e2) {
        return new BinaryExp(BinaryExp.Op.OR, e1, e2);
    }

    /**
     * @return {@code e1 ^ e2}
     */
    public static BinaryExp xor(Exp e1, Exp e2) {
        return new BinaryExp(BinaryExp.Op.XOR, e1, e2);
    }

}
