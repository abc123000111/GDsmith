package org.example.gdsmith.tugraph.oracle;

import org.example.gdsmith.cypher.oracle.NoRecOracle;
import org.example.gdsmith.tugraph.TuGraphGlobalState;
import org.example.gdsmith.tugraph.schema.TuGraphSchema;

public class TuGraphNoRecOracle extends NoRecOracle<TuGraphGlobalState, TuGraphSchema> {
    public TuGraphNoRecOracle(TuGraphGlobalState globalState) {
        super(globalState);
    }
}
