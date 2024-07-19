package pascal.lf.interpreter;

import pascal.lf.model.Circuit;
import pascal.lf.model.Connection;
import pascal.lf.model.Script;
import pascal.lf.model.command.*;
import pascal.lf.model.exp.Value;
import pascal.lf.model.exp.Var;

import java.util.LinkedHashSet;

/**
 * The interpreter of lambdaF.
 */
public final class Interpreter {

    private final Circuit circuit;

    private final Store store = Store.empty();

    private final LinkedHashSet<Connection> worklist = new LinkedHashSet<>();

    public Interpreter(Circuit circuit) {
        this.circuit = circuit;
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
        if (!circuit.isWire(x)) {
            throw new IllegalStateException("There's no wire named " + x + " .");
        }
        if (circuit.getDefinerOf(x) != null) {
            throw new IllegalStateException(x + " is not a free signal.");
        }
        worklist.add(Connection.of(x, n));
        propagate();
    }

    /**
     * @return the current value of {@code x}
     */
    public Value peek(Var x) {
        if (!circuit.isWire(x) && !circuit.isReg(x)) {
            throw new IllegalStateException(x + " doesn't exist.");
        }
        return store.get(x);
    }

    public void runScript(Script script) {
        script.forEach(this::execute);
    }

    public void execute(Command command) {
        command.accept(new CommandVisitor<Void>() {

            @Override
            public Void visit(Step c) {
                switch (c) {
                    case RESET -> reset();
                    case CLOCK -> clock();
                    case SKIP -> {}
                }
                return null;
            }

            @Override
            public Void visit(Poke c) {
                poke(c.var(), c.value());
                return null;
            }

            @Override
            public Void visit(Peek c) {
                System.out.println(peek(c.var()));
                return null;
            }

        });
    }

    private void propagate() {
        while (!worklist.isEmpty()) {
            Connection c = worklist.removeFirst();
            Var x = c.getDef();
            Value n = ExpEvaluator.evaluate(c.getExp(), store);
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
