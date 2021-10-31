package gdsmith.neo4j.oracle;

import gdsmith.CypherQueryAdapter;
import gdsmith.common.oracle.TestOracle;
import gdsmith.neo4j.Neo4jGlobalState;

public class Neo4jMatchOracle implements TestOracle {

    private final Neo4jGlobalState globalState;

    @Override
    public void check() throws Exception {
        this.globalState.executeStatement(new CypherQueryAdapter("MATCH (p) RETURN p"));
    }

    public Neo4jMatchOracle(Neo4jGlobalState globalState){
        this.globalState = globalState;
    }
}