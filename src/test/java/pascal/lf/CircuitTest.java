package pascal.lf;

import org.junit.jupiter.api.Test;
import pascal.lf.frontend.CircuitFrontend;
import pascal.lf.interpreter.Interpreter;
import pascal.lf.model.Circuit;
import pascal.lf.model.Connection;
import pascal.lf.model.exp.Exp;
import pascal.lf.model.exp.Exps;
import pascal.lf.model.exp.Var;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CircuitTest {

    @Test
    void testCounter() {
        String design = """
                wire en
                wire out
                reg counter <- 0
                counter <= mux(en, add(counter, 1), counter)
                out <= counter
                """;
        Circuit circuit = CircuitFrontend.loadString(design).compile();

        Var en = Exps.var("en");
        Var out = Exps.var("out");
        Var counter = Exps.var("counter");

        Exp next = Exps.mux(en, Exps.add(counter, Exps.ONE), counter);
        Connection c0 = Connection.of(counter, Exps.ZERO);
        Connection c1 = Connection.of(counter, next);
        Connection c2 = Connection.of(out, counter);

        assertEquals(Set.of(en, out), circuit.getWires());
        assertEquals(Collections.singleton(counter), circuit.getRegs());
        assertEquals(Exps.ZERO, circuit.getResetValue(counter));
        assertEquals(Collections.singleton(c0), circuit.getResetConnections());
        assertEquals(Set.of(c1, c2), circuit.getConnections());
        assertEquals(c1, circuit.getDefinerOf(counter));
        assertEquals(c2, circuit.getDefinerOf(out));
        assertEquals(Collections.singleton(c1), circuit.getUsersOf(en));
        assertEquals(Set.of(c1, c2), circuit.getUsersOf(counter));

        Interpreter interpreter = new Interpreter(circuit);
        interpreter.reset();
        assertEquals(Exps.ZERO, interpreter.peek(out));
        interpreter.poke(en, Exps.ZERO);
        assertEquals(Exps.ZERO, interpreter.peek(out));
        interpreter.clock();
        assertEquals(Exps.ZERO, interpreter.peek(out));
        interpreter.poke(en, Exps.ONE);
        for (int i = 0; i < 10; i++) {
            assertEquals(Exps.value(i), interpreter.peek(out));
            interpreter.clock();
        }
        assertEquals(Exps.value(10), interpreter.peek(out));
        interpreter.poke(en, Exps.ZERO);
        for (int i = 0; i < 3; i++) {
            interpreter.clock();
            assertEquals(Exps.value(10), interpreter.peek(out));
        }
        interpreter.reset();
        assertEquals(Exps.ZERO, interpreter.peek(out));
    }

}
