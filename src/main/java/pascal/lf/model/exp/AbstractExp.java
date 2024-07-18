package pascal.lf.model.exp;

import java.util.Collections;
import java.util.Set;

/**
 * Provide common facilities for all implementing classes of {@link Exp}.
 */
public abstract class AbstractExp implements Exp {

    private Set<Var> cachedUses = null;

    /**
     * @return the fresh computed used variable set.
     */
    protected abstract Set<Var> computeUses();

    @Override
    public Set<Var> getUses() {
        if (cachedUses == null) {
            cachedUses = Collections.unmodifiableSet(computeUses());
        }
        return cachedUses;
    }

}
