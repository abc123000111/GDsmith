package gdsmith.neo4j.gen.random;

import gdsmith.cypher.CypherQueryAdapter;
import gdsmith.neo4j.Neo4jGlobalState;
import gdsmith.neo4j.ast.ClauseSequence;
import gdsmith.neo4j.dsl.ClauseSequenceBuilder;
import gdsmith.neo4j.gen.examples.NaiveAliasGenerator;
import gdsmith.neo4j.gen.examples.NaiveConditionGenerator;
import gdsmith.neo4j.gen.examples.NaivePatternGenerator;
import gdsmith.neo4j.schema.Neo4jSchema;

public class RandomQueryGenerator {

    public ClauseSequence generateQuery(Neo4jGlobalState globalState){
        ClauseSequenceBuilder builder = new ClauseSequenceBuilder();

        Neo4jSchema schema = globalState.getSchema();

        //示例：使用ClauseSequenceBuilder流式生成query，在build时需要传入条件，别名，模式的生成策略，以及全局符号表
        ClauseSequence sequence = builder.MatchClause().MatchClause().
                ReturnClause().build(new NaiveConditionGenerator(schema),
                new NaiveAliasGenerator(schema, builder.getIdentifierBuilder()),
                new NaivePatternGenerator(schema, builder.getIdentifierBuilder()), schema);


        return sequence;
    }
}
