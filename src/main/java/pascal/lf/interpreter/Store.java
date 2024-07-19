package pascal.lf.interpreter;

import pascal.lf.model.exp.Exps;
import pascal.lf.model.exp.Value;
import pascal.lf.model.exp.Var;

import java.util.HashMap;
import java.util.Map;

/**
 * The var-value store used in {@link ExpEvaluator} and {@link Interpreter}.
 */
public class Store {

    private final Map<Var, Value> data;

    public static Store empty() {
        return new Store(new HashMap<>());
    }

    public static Store of(Var x, Value n) {
        return empty().put(x, n);
    }

    public static Store of(Var x1, Value n1, Var x2, Value n2) {
        return of(x1, n1).put(x2, n2);
    }

    public static Store of(Var x1, Value n1, Var x2, Value n2, Var x3, Value n3) {
        return of(x1, n1, x2, n2).put(x3, n3);
    }

    private Store(Map<Var, Value> data) {
        this.data = data;
    }

    public Value get(Var x) {
        return data.getOrDefault(x, Exps.ZERO);
    }

    public Store put(Var x, Value n) {
        data.put(x, n);
        return this;
    }

}
