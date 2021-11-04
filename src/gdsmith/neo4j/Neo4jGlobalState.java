package gdsmith.neo4j;

import gdsmith.cypher.CypherGlobalState;

public class Neo4jGlobalState extends CypherGlobalState<Neo4jOptions, Neo4jSchema> {
    @Override
    protected Neo4jSchema readSchema() throws Exception {
        //todo complete
        return Neo4jSchema.createEmptyNewSchema();
    }
}
