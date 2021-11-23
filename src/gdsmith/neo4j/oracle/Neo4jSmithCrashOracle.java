package gdsmith.neo4j.oracle;

import gdsmith.common.oracle.TestOracle;
import gdsmith.cypher.CypherQueryAdapter;
import gdsmith.neo4j.Neo4jGlobalState;
import gdsmith.neo4j.ast.ClauseSequence;
import gdsmith.neo4j.ast.Symtab;
import gdsmith.neo4j.gen.random.RandomQueryGenerator;

public class Neo4jSmithCrashOracle implements TestOracle {

    private final Neo4jGlobalState globalState;

    public Neo4jSmithCrashOracle(Neo4jGlobalState globalState){
        this.globalState = globalState;
        //todo 整个oracle的check会被执行多次，一直是同一个oracle实例，因此oracle本身可以管理种子库
    }

    @Override
    public void check() throws Exception {
        //todo oracle 的检测逻辑，会被调用多次
        ClauseSequence sequence = new RandomQueryGenerator().generateQuery(globalState);
        StringBuilder sb = new StringBuilder();
        sequence.toTextRepresentation(sb);
        globalState.executeStatement(new CypherQueryAdapter("MATCH (n0)-[r0 :T1 *1..]->(n1), (n3) MATCH (n0), (n1)<-[ :T0 *]-(n5 :L2) MATCH (n1) MATCH (n5) RETURN 2"));

        //todo 上层通过抛出的异常检测是否通过，所以这里可以捕获并检测异常的类型，可以计算一些统计数据，然后重抛异常
    }
}
