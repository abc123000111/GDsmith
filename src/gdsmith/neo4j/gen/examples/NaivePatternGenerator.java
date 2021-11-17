package gdsmith.neo4j.gen.examples;

import gdsmith.cypher.ast.*;
import gdsmith.cypher.ast.analyzer.IMatchAnalyzer;
import gdsmith.cypher.ast.analyzer.INodeAnalyzer;
import gdsmith.cypher.ast.analyzer.IRelationAnalyzer;
import gdsmith.neo4j.schema.Neo4jSchema;
import gdsmith.neo4j.ast.Label;
import gdsmith.neo4j.ast.Pattern;
import gdsmith.neo4j.dsl.BasicPatternGenerator;
import gdsmith.neo4j.dsl.IIdentifierBuilder;

import java.util.ArrayList;
import java.util.List;

public class NaivePatternGenerator extends BasicPatternGenerator {

    public NaivePatternGenerator(Neo4jSchema schema, IIdentifierBuilder identifierBuilder) {
        super(schema, identifierBuilder);
    }

    @Override
    public List<IPattern> generatePattern(IMatchAnalyzer matchClause, IIdentifierBuilder identifierBuilder, Neo4jSchema schema) {
        List<IPattern> patternTuple = new ArrayList<>();

        //示例，从全局符号表中选择一个label，作为新生成pattern中node的label
        Neo4jSchema.Neo4jLabelInfo labelInfo = schema.getLabels().get((int)(Math.random()*schema.getLabels().size()));
        ILabel label = new Label(labelInfo.getName());

        //示例：获取一个clause的上下文信息
        List<INodeAnalyzer> id1s = matchClause.getAvailableNodeIdentifiers();
        List<IRelationAnalyzer> id2s = matchClause.getAvailableRelationIdentifiers();


        //示例：使用pattern builder流式地生成pattern，生成的结果是(nx: labelx)-[rx]->()
        patternTuple.add(new Pattern.PatternBuilder(identifierBuilder).newNamedNode().withLabels(label).newNamedRelation()
            .withDirection(Direction.RIGHT).newAnonymousNode().build());

        return patternTuple;
    }
}