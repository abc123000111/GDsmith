package gdsmith.neo4j.oracle;

import gdsmith.cypher.CypherQueryAdapter;
import gdsmith.common.oracle.TestOracle;
import gdsmith.neo4j.Neo4jGlobalState;
import gdsmith.neo4j.gen.Neo4jNaiveQueryGenerator;

public class Neo4jMatchOracle implements TestOracle {

    private final Neo4jGlobalState globalState;

    @Override
    public void check() throws Exception {
        this.globalState.executeStatement(new Neo4jNaiveQueryGenerator().generateQuery(globalState));
    }

    public Neo4jMatchOracle(Neo4jGlobalState globalState){
        this.globalState = globalState;
    }
}