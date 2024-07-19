package pascal.lf.frontend;

import pascal.lf.model.Script;
import pascal.lf.model.command.Command;
import pascal.lf.model.command.Peek;
import pascal.lf.model.command.Poke;
import pascal.lf.model.command.Step;
import pascal.lf.model.exp.Exps;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;

public class ScriptFrontend {

    private final BufferedReader reader;

    private Script script = null;

    private ScriptFrontend(BufferedReader reader) {
        this.reader = reader;
    }

    public static ScriptFrontend loadPath(Path path) throws IOException {
        return new ScriptFrontend(new BufferedReader(new FileReader(path.toFile())));
    }

    public static ScriptFrontend loadFileByName(String fileName) throws IOException {
        return new ScriptFrontend(new BufferedReader(new FileReader(fileName)));
    }

    public static ScriptFrontend loadString(String string) {
        return new ScriptFrontend(new BufferedReader(new StringReader(string)));
    }

    public Script compile() {
        if (script != null) {
            return script;
        }
        buildScript();
        return script;
    }

    public static Command parseCommand(String input) {
        String[] parts = input.split("\\s+");
        if (parts.length == 0) {
            return Step.SKIP;
        }
        return switch (parts[0]) {
            case "skip" -> Step.SKIP;
            case "reset" -> Step.RESET;
            case "clock" -> Step.CLOCK;
            case "poke" -> {
                if (parts.length >= 3) {
                    yield Poke.of(Exps.var(parts[1]), Exps.value(parts[2]));
                }
                throw new IllegalStateException("Missing arguments for poke.");
            }
            case "peek" -> {
                if (parts.length >= 2) {
                    yield Peek.of(Exps.var(parts[1]));
                }
                throw new IllegalStateException("Missing arguments for peek.");
            }
            default -> throw new IllegalStateException("Unknown command: " + parts[0]);
        };
    }

    private void buildScript() {
        script = new Script();
        reader.lines().forEach(input -> script.addCommand(parseCommand(input)));
    }

}
