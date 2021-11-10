package gdsmith.neo4j;

import gdsmith.cypher.CypherGlobalState;
import gdsmith.neo4j.gen.Neo4jSchemaGenerator;
import gdsmith.neo4j.schema.Neo4jSchema;

public class Neo4jGlobalState extends CypherGlobalState<Neo4jOptions, Neo4jSchema> {

    private Neo4jSchema neo4jSchema;

    public Neo4jGlobalState(){
        super();
        System.out.println("new global state");
        neo4jSchema = new Neo4jSchemaGenerator().generateSchema();
    }

    @Override
    protected Neo4jSchema readSchema() throws Exception {
        return neo4jSchema;
    }
}
