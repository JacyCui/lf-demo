package pascal.lf.model;

import pascal.lf.model.exp.Exp;
import pascal.lf.model.exp.Var;

import javax.annotation.Nullable;
import java.util.*;

/**
 * This class represents the preprocessed circuit.
 */
public class Circuit {

    private final Set<Var> wires = new HashSet<>();

    private final Map<Var, Exp> regs = new HashMap<>();

    private final Set<Connection> resetConnections = new HashSet<>();

    private final Set<Connection> connections = new HashSet<>();

    private final Map<Var, Connection> varToDefiner = new HashMap<>();

    private final Map<Var, Set<Connection>> varToUsers = new HashMap<>();

    // ---------- Public Query Methods ----------

    /**
     * @return all the wire variables in this circuit
     */
    public Set<Var> getWires() {
        return Collections.unmodifiableSet(wires);
    }

    /**
     * @return {@code true} if {@code var} is a wire, otherwise false
     */
    public boolean isWire(Var var) {
        return wires.contains(var);
    }

    /**
     * @return all the register variables in this circuit
     */
    public Set<Var> getRegs() {
        return Collections.unmodifiableSet(regs.keySet());
    }

    /**
     * @return {@code true} if {@code var} is a register, otherwise false
     */
    public boolean isReg(Var var) {
        return regs.containsKey(var);
    }

    /**
     * @return the reset expression of register {@code var},
     *         {@code null} if {@code var} is not a register
     */
    @Nullable
    public Exp getResetValue(Var var) {
        return regs.get(var);
    }

    /**
     * @return the set of connections representing a reset operation
     */
    public Set<Connection> getResetConnections() {
        return Collections.unmodifiableSet(resetConnections);
    }

    /**
     * @return all connections in this circuit
     */
    public Set<Connection> getConnections() {
        return Collections.unmodifiableSet(connections);
    }

    /**
     * @return the definer of {@code var} or {@code null} if undefined
     */
    @Nullable
    public Connection getDefinerOf(Var var) {
        return varToDefiner.get(var);
    }

    /**
     * @return the users of {@code var}
     */
    public Set<Connection> getUsersOf(Var var) {
        return Collections.unmodifiableSet(varToUsers
                .getOrDefault(var, Collections.emptySet()));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        wires.forEach(w -> builder.append("wire ").append(w).append("\n"));
        regs.forEach((r, e) -> builder.append("reg ").append(r)
                .append(" <- ").append(e).append("\n"));
        connections.forEach(c -> builder.append(c).append("\n"));
        return builder.toString();
    }

    // ---------- Circuit Building Methods ----------

    /**
     * Adds a wire to this circuit.
     * @param var the wire variable
     * @throws ModelException if {@code var} is already declared
     */
    public void addWire(Var var) {
        checkFreshness(var);
        wires.add(var);
    }

    /**
     * Adds a register to this circuit.
     * @param var the register variable
     * @param exp the reset expression of the added register.
     * @throws ModelException if {@code var} is already declared
     */
    public void addReg(Var var, Exp exp) {
        checkFreshness(var);
        regs.put(var, exp);
        resetConnections.add(Connection.of(var, exp));
    }

    /**
     * Adds a connection to this circuit.
     * @param connection the connection to be added
     * @throws ModelException if the LHS variable of {@code connection}
     *                        is already defined
     */
    public void addConnection(Connection connection) {
        Var def = connection.getDef();
        if (varToDefiner.containsKey(def)) {
            throw new ModelException(def + " is already defined.");
        }
        connections.add(connection);
        varToDefiner.put(def, connection);
        connection.getUses().forEach(use ->
            varToUsers.computeIfAbsent(use, ignore -> new HashSet<>()).add(connection)
        );
    }

    // ---------- Private Helper Methods ----------

    private void checkFreshness(Var var) {
        if (wires.contains(var) || regs.containsKey(var)) {
            throw new ModelException(var + " is already declared.");
        }
    }

}
