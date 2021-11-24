package gdsmith.neo4j.oracle;

import gdsmith.common.oracle.TestOracle;
import gdsmith.cypher.CypherQueryAdapter;
import gdsmith.neo4j.Neo4jGlobalState;
import gdsmith.neo4j.ast.ClauseSequence;
import gdsmith.neo4j.ast.Symtab;
import gdsmith.neo4j.gen.random.RandomQueryGenerator;

public class Neo4jSmithCrashOracle implements TestOracle {

    private final Neo4jGlobalState globalState;
    private RandomQueryGenerator randomQueryGenerator;

    public Neo4jSmithCrashOracle(Neo4jGlobalState globalState){
        this.globalState = globalState;
        //todo 整个oracle的check会被执行多次，一直是同一个oracle实例，因此oracle本身可以管理种子库
        this.randomQueryGenerator = new RandomQueryGenerator();
    }

    @Override
    public void check() throws Exception {
        //todo oracle 的检测逻辑，会被调用多次
        ClauseSequence sequence = randomQueryGenerator.generateQuery(globalState);
        StringBuilder sb = new StringBuilder();
        sequence.toTextRepresentation(sb);
        System.out.println(sb);
        globalState.executeStatement(new CypherQueryAdapter(sb.toString()));

        //todo 上层通过抛出的异常检测是否通过，所以这里可以捕获并检测异常的类型，可以计算一些统计数据，然后重抛异常
        randomQueryGenerator.addSeed(new RandomQueryGenerator.Seed(
                sequence, false, 0
        ));//添加seed
    }
}
