package pascal.lf;

import pascal.lf.frontend.CircuitFrontend;
import pascal.lf.frontend.ScriptFrontend;
import pascal.lf.interpreter.Interpreter;
import pascal.lf.model.Circuit;
import pascal.lf.model.Script;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: lf <circuit file> <script file>");
            return;
        }
        Circuit circuit = CircuitFrontend.loadFileByName(args[0]).compile();
        Script script = ScriptFrontend.loadFileByName(args[1]).compile();
        Interpreter interpreter = new Interpreter(circuit);
        interpreter.runScript(script);
    }

}