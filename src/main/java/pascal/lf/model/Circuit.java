package pascal.lf.model;

import pascal.lf.model.exp.Exp;
import pascal.lf.model.exp.Var;

import javax.annotation.Nullable;
import java.util.*;

/**
 * This class represents the preprocessed circuit.
 */
public class Circuit {

    private final Set<Var> wires;

    private final Map<Var, Exp> regs;

    private final Set<Connection> connections;

    private final Map<Var, Connection> varToDefiner;

    private final Map<Var, Set<Connection>> varToUsers;

    public Circuit() {
        this.wires = new HashSet<>();
        this.regs = new HashMap<>();
        this.connections = new HashSet<>();
        this.varToDefiner = new HashMap<>();
        this.varToUsers = new HashMap<>();
    }

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
