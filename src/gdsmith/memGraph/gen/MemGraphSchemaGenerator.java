package gdsmith.memGraph.gen;

import gdsmith.Randomly;
import gdsmith.cypher.CypherQueryAdapter;
import gdsmith.cypher.gen.CypherSchemaGenerator;
import gdsmith.cypher.schema.CypherSchema;
import gdsmith.memGraph.MemGraphGlobalState;
import gdsmith.cypher.standard_ast.CypherType;
import gdsmith.cypher.schema.IPatternElementInfo;
import gdsmith.cypher.schema.IPropertyInfo;
import gdsmith.memGraph.MemGraphSchema;
import gdsmith.neo4j.schema.Neo4jSchema;

import java.util.ArrayList;
import java.util.List;

public class MemGraphSchemaGenerator extends CypherSchemaGenerator<MemGraphSchema, MemGraphGlobalState> {


    public MemGraphSchemaGenerator(MemGraphGlobalState globalState){
        super(globalState);
    }

    @Override
    public MemGraphSchema generateSchemaObject(MemGraphGlobalState globalState, List<CypherSchema.CypherLabelInfo> labels, List<CypherSchema.CypherRelationTypeInfo> relationTypes, List<CypherSchema.CypherPatternInfo> patternInfos) {
        return new MemGraphSchema(new ArrayList<>(), labels, relationTypes, patternInfos);
    }

}
