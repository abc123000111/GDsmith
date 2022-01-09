package gdsmith.common.query;

import gdsmith.GlobalState;
import gdsmith.SQLancerDBConnection;
import gdsmith.common.log.Loggable;

public abstract class Query<C extends SQLancerDBConnection> implements Loggable {

    /**
     * Gets the query string, which is guaranteed to be terminated with a semicolon.
     *
     * @return the query string.
     */
    public abstract String getQueryString();

    /**
     * Gets the query string without trailing semicolons.
     *
     * @return the query string that does not end with a ";".
     */
    public abstract String getUnterminatedQueryString();

    /**
     * Whether the query could affect the schema (i.e., by add/deleting columns or tables).
     *
     * @return true if the query can affect the database's schema, false otherwise
     */
    public abstract boolean couldAffectSchema();

    public abstract <G extends GlobalState<?, ?, C>> boolean execute(G globalState, String... fills) throws Exception;

    public abstract ExpectedErrors getExpectedErrors();

    @Override
    public String toString() {
        return getQueryString();
    }

    public <G extends GlobalState<?, ?, C>> GDSmithResultSet executeAndGet(G globalState, String... fills)
            throws Exception {
        throw new AssertionError();
    }

    public <G extends GlobalState<?, ?, C>> boolean executeLogged(G globalState) throws Exception {
        logQueryString(globalState);
        return execute(globalState);
    }

    public <G extends GlobalState<?, ?, C>> GDSmithResultSet executeAndGetLogged(G globalState) throws Exception {
        logQueryString(globalState);
        return executeAndGet(globalState);
    }

    private <G extends GlobalState<?, ?, C>> void logQueryString(G globalState) {
        if (globalState.getOptions().logEachSelect()) {
            globalState.getLogger().writeCurrent(getQueryString());
        }
    }

}
