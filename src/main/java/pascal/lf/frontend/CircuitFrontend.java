package pascal.lf.frontend;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import pascal.lf.model.Circuit;
import pascal.lf.model.Connection;
import pascal.lf.model.exp.Exp;
import pascal.lf.model.exp.Exps;
import pascal.lf.parser.CircuitBaseVisitor;
import pascal.lf.parser.CircuitLexer;
import pascal.lf.parser.CircuitParser;
import pascal.lf.util.ShallNotReachHereException;

import java.io.IOException;
import java.nio.file.Path;

public final class CircuitFrontend {

    private final CharStream charStream;

    private Circuit circuit = null;

    private CircuitFrontend(CharStream charStream) {
        this.charStream = charStream;
    }

    public static CircuitFrontend loadPath(Path path) throws IOException {
        return new CircuitFrontend(CharStreams.fromPath(path));
    }

    public static CircuitFrontend loadFileByName(String fileName) throws IOException {
        return new CircuitFrontend(CharStreams.fromFileName(fileName));
    }

    public static CircuitFrontend loadString(String s) {
        return new CircuitFrontend(CharStreams.fromString(s));
    }

    /**
     * @return the compiled and preprocessed circuit
     */
    public Circuit compile() {
        if (circuit != null) {
            return circuit;
        }
        buildCircuit();
        return circuit;
    }

    // ---------- Private Helper Methods ----------

    private void buildCircuit() {
        circuit = new Circuit();
        CircuitLexer lexer = new CircuitLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CircuitParser parser = new CircuitParser(tokens);
        parser.circuit().accept(new CircuitBaseVisitor<Void>() {

            @Override
            public Void visitWireDecl(CircuitParser.WireDeclContext ctx) {
                circuit.addWire(Exps.var(ctx.ID().getText()));
                return null;
            }

            @Override
            public Void visitRegDecl(CircuitParser.RegDeclContext ctx) {
                circuit.addReg(
                        Exps.var(ctx.ID().getText()),
                        ctx.exp().accept(ExpVisitor.INSTANCE));
                return null;
            }

            @Override
            public Void visitConnection(CircuitParser.ConnectionContext ctx) {
                circuit.addConnection(Connection.of(
                        Exps.var(ctx.ID().getText()),
                        ctx.exp().accept(ExpVisitor.INSTANCE)));
                return null;
            }

        });
    }

    private static class ExpVisitor extends CircuitBaseVisitor<Exp> {

        public final static ExpVisitor INSTANCE = new ExpVisitor();

        @Override
        public Exp visitValue(CircuitParser.ValueContext ctx) {
            return Exps.value(ctx.NUM().getText());
        }

        @Override
        public Exp visitVar(CircuitParser.VarContext ctx) {
            return Exps.var(ctx.ID().getText());
        }

        @Override
        public Exp visitUnaryExp(CircuitParser.UnaryExpContext ctx) {
            Exp e = ctx.exp().accept(this);
            return switch (ctx.uop().getText()) {
                case "neg" -> Exps.neg(e);
                case "not" -> Exps.not(e);
                default -> throw new ShallNotReachHereException();
            };
        }

        @Override
        public Exp visitBinaryExp(CircuitParser.BinaryExpContext ctx) {
            Exp e1 = ctx.exp(0).accept(this);
            Exp e2 = ctx.exp(1).accept(this);
            return switch (ctx.bop().getText()) {
                case "add" -> Exps.add(e1, e2);
                case "sub" -> Exps.sub(e1, e2);
                case "mul" -> Exps.mul(e1, e2);
                case "div" -> Exps.div(e1, e2);
                case "rem" -> Exps.rem(e1, e2);
                case "lt" -> Exps.lt(e1, e2);
                case "leq" -> Exps.leq(e1, e2);
                case "gt" -> Exps.gt(e1, e2);
                case "geq" -> Exps.geq(e1, e2);
                case "eq" -> Exps.eq(e1, e2);
                case "neq" -> Exps.neq(e1, e2);
                case "shl" -> Exps.shl(e1, e2);
                case "shr" -> Exps.shr(e1, e2);
                case "and" -> Exps.and(e1, e2);
                case "or" -> Exps.or(e1, e2);
                case "xor" -> Exps.xor(e1, e2);
                default -> throw new ShallNotReachHereException();
            };
        }

        @Override
        public Exp visitMuxExp(CircuitParser.MuxExpContext ctx) {
            Exp cond = ctx.exp(0).accept(this);
            Exp e1 = ctx.exp(1).accept(this);
            Exp e2 = ctx.exp(2).accept(this);
            return Exps.mux(cond, e1, e2);
        }

    }

}
