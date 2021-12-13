package gdsmith.agensGraph;

import gdsmith.cypher.CypherGlobalState;
import gdsmith.agensGraph.gen.AgensGraphSchemaGenerator;

public class AgensGraphGlobalState extends CypherGlobalState<AgensGraphOptions, AgensGraphSchema> {

    private AgensGraphSchema agensGraphSchema = null;

    public AgensGraphGlobalState(){
        super();
        System.out.println("new global state");
    }

    @Override
    protected AgensGraphSchema readSchema() throws Exception {
        if(agensGraphSchema == null){
            agensGraphSchema = new AgensGraphSchemaGenerator(this).generateSchema();
        }
        return agensGraphSchema;
    }
}
