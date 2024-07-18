package pascal.lf.model.exp;

import java.util.Set;

/**
 * Interface for all expressions in lambdaF.
 */
public interface Exp {

    /**
     * @return the variables used in this expression
     */
    Set<Var> getUses();

    <T> T accept(ExpVisitor<T> visitor);

}
