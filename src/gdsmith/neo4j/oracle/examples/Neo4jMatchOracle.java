package gdsmith.neo4j.oracle.examples;

import gdsmith.common.oracle.TestOracle;
import gdsmith.neo4j.Neo4jGlobalState;
import gdsmith.neo4j.gen.examples.NaiveQueryGenerator;

public class Neo4jMatchOracle implements TestOracle {

    private final Neo4jGlobalState globalState;

    @Override
    public void check() throws Exception {
        this.globalState.executeStatement(new NaiveQueryGenerator().generateQuery(globalState));
    }

    public Neo4jMatchOracle(Neo4jGlobalState globalState){
        this.globalState = globalState;
    }
}