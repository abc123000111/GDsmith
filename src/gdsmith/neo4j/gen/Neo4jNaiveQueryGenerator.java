package gdsmith.neo4j.gen;

import gdsmith.cypher.CypherQueryAdapter;
import gdsmith.neo4j.Neo4jGlobalState;
import gdsmith.neo4j.schema.Neo4jSchema;
import gdsmith.neo4j.ast.ClauseSequence;
import gdsmith.neo4j.dsl.*;

public class Neo4jNaiveQueryGenerator {

    public CypherQueryAdapter generateQuery(Neo4jGlobalState globalState){
        ClauseSequenceBuilder builder = new ClauseSequenceBuilder();

        Neo4jSchema schema = globalState.getSchema();

        //示例：使用ClauseSequenceBuilder流式生成query，在build时需要传入条件，别名，模式的生成策略，以及全局符号表
        ClauseSequence sequence = builder.MatchClause().MatchClause().
                ReturnClause().build(new NaiveConditionGenerator(schema),
                    new NaiveAliasGenerator(schema, builder.getIdentifierBuilder()),
                    new NaivePatternGenerator(schema, builder.getIdentifierBuilder()), schema);

        StringBuilder sb = new StringBuilder();

        //转化为字符串
        sequence.toTextRepresentation(sb);
        String query = sb.toString();

        return new CypherQueryAdapter(query);
    }
}
