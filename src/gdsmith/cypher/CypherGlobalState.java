package gdsmith.cypher;

import gdsmith.DBMSSpecificOptions;
import gdsmith.ExecutionTimer;
import gdsmith.GlobalState;
import gdsmith.common.query.Query;
import gdsmith.common.schema.AbstractSchema;

public abstract class CypherGlobalState <O extends DBMSSpecificOptions<?>, S extends AbstractSchema<?, ?>>
        extends GlobalState<O, S, CypherConnection> {
    @Override
    protected void executeEpilogue(Query<?> q, boolean success, ExecutionTimer timer) throws Exception {
        boolean logExecutionTime = getOptions().logExecutionTime();
        if (success && getOptions().printSucceedingStatements()) {
            System.out.println(q.getQueryString());
        }
        if (logExecutionTime) {
            getLogger().writeCurrent(" -- " + timer.end().asString());
        }
        if (q.couldAffectSchema()) {
            updateSchema();
        }
    }
}
