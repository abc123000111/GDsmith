package org.example.gdsmith.tugraph;

import org.example.gdsmith.cypher.CypherGlobalState;
import org.example.gdsmith.tugraph.gen.TuGraphSchemaGenerator;
import org.example.gdsmith.tugraph.schema.TuGraphSchema;

public class TuGraphGlobalState extends CypherGlobalState<TuGraphOptions, TuGraphSchema> {

    private TuGraphSchema tugraphSchema = null;

    public TuGraphGlobalState(){
        super();
        System.out.println("new global state");
    }

    @Override
    protected TuGraphSchema readSchema() throws Exception {
        if(tugraphSchema == null){
            tugraphSchema = new TuGraphSchemaGenerator(this).generateSchema();
        }
        return tugraphSchema;
    }
}
