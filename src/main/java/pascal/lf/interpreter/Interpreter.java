package pascal.lf.interpreter;

import pascal.lf.model.Circuit;
import pascal.lf.model.Connection;
import pascal.lf.model.exp.Exps;
import pascal.lf.model.exp.Value;
import pascal.lf.model.exp.Var;

import java.util.*;

/**
 * The interpreter of lambdaF.
 */
public final class Interpreter {

    private final Circuit circuit;

    private final Map<Var, Value> store;

    private final LinkedHashSet<Connection> worklist;

    private final ExpEvaluator evaluator;

    public Interpreter(Circuit circuit) {
        this.circuit = circuit;
        store = new HashMap<>();
        circuit.getWires().forEach(w -> store.put(w, Exps.ZERO));
        circuit.getRegs().forEach(r -> store.put(r, Exps.ZERO));
        worklist = new LinkedHashSet<>();
        evaluator = new ExpEvaluator(store);
    }

    /**
     * Reset the circuit.
     */
    public void reset() {
        worklist.addAll(circuit.getResetConnections());
        propagate();
    }

    /**
     * Step forward the clock.
     */
    public void clock() {
        circuit.getRegs().forEach(r -> worklist.add(circuit.getDefinerOf(r)));
        propagate();
    }

    /**
     * Poke the input signal {@code x} to be value {@code n}
     */
    public void poke(Var x, Value n) {
        if (circuit.getDefinerOf(x) != null) {
            throw new IllegalStateException(x + " is not a free input signal.");
        }
        worklist.add(Connection.of(x, n));
        propagate();
    }

    /**
     * @return the current value of {@code x}
     */
    public Value peek(Var x) {
        return store.get(x);
    }

    private void propagate() {
        while (!worklist.isEmpty()) {
            Connection c = worklist.removeFirst();
            Var x = c.getDef();
            Value n = c.getExp().accept(evaluator);
            if (store.get(x).equals(n)) {
                continue;
            }
            store.put(x, n);
            circuit.getUsersOf(x).stream()
                    .filter(u -> circuit.isWire(u.getDef()))
                    .forEach(worklist::add);
        }
    }

}
