package org.example.gdsmith.tugraph.gen;

import org.example.gdsmith.cypher.CypherQueryAdapter;
import org.example.gdsmith.tugraph.TuGraphGlobalState;

public class TuGraphNodeGenerator {

    private final TuGraphGlobalState globalState;
    public TuGraphNodeGenerator(TuGraphGlobalState globalState){
        this.globalState = globalState;
    }

    public static CypherQueryAdapter createNode(TuGraphGlobalState globalState){
        return new TuGraphNodeGenerator(globalState).generateCreate();
    }

    public CypherQueryAdapter generateCreate(){
        return new CypherQueryAdapter("CREATE (p:Person{id: 1})");
    }
}
