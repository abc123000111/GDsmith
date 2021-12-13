package gdsmith.agensGraph.gen;

import gdsmith.Randomly;
import gdsmith.cypher.gen.CypherSchemaGenerator;
import gdsmith.cypher.schema.CypherSchema;
import gdsmith.agensGraph.AgensGraphGlobalState;
import gdsmith.cypher.standard_ast.CypherType;
import gdsmith.cypher.schema.IPatternElementInfo;
import gdsmith.cypher.schema.IPropertyInfo;
import gdsmith.agensGraph.AgensGraphSchema;
import gdsmith.neo4j.schema.Neo4jSchema;

import java.util.ArrayList;
import java.util.List;

public class AgensGraphSchemaGenerator extends CypherSchemaGenerator<AgensGraphSchema, AgensGraphGlobalState> {


    public AgensGraphSchemaGenerator(AgensGraphGlobalState globalState){
        super(globalState);
    }

    @Override
    public AgensGraphSchema generateSchemaObject(AgensGraphGlobalState globalState, List<CypherSchema.CypherLabelInfo> labels, List<CypherSchema.CypherRelationTypeInfo> relationTypes, List<CypherSchema.CypherPatternInfo> patternInfos) {
        return new AgensGraphSchema(new ArrayList<>(), labels, relationTypes, patternInfos);
    }

}
