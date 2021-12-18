package gdsmith.memGraph;

import gdsmith.cypher.CypherGlobalState;
import gdsmith.memGraph.gen.MemGraphSchemaGenerator;

public class MemGraphGlobalState extends CypherGlobalState<MemGraphOptions, MemGraphSchema> {

    private MemGraphSchema memGraphSchema = null;

    public MemGraphGlobalState(){
        super();
        System.out.println("new global state");
    }

    @Override
    protected MemGraphSchema readSchema() throws Exception {
        if(memGraphSchema == null){
            memGraphSchema = new MemGraphSchemaGenerator(this).generateSchema();
        }
        return memGraphSchema;
    }
}
