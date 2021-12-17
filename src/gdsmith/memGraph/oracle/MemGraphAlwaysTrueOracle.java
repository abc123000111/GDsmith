package gdsmith.memGraph.oracle;

import gdsmith.memGraph.MemGraphSchema;
import gdsmith.common.oracle.TestOracle;
import gdsmith.cypher.CypherQueryAdapter;
import gdsmith.cypher.ast.IClauseSequence;
import gdsmith.memGraph.MemGraphGlobalState;
import gdsmith.cypher.gen.random.RandomQueryGenerator;

public class MemGraphAlwaysTrueOracle implements TestOracle {

    private final MemGraphGlobalState globalState;
    private RandomQueryGenerator<MemGraphSchema, MemGraphGlobalState> randomQueryGenerator;

    public MemGraphAlwaysTrueOracle(MemGraphGlobalState globalState){
        this.globalState = globalState;
        //todo 整个oracle的check会被执行多次，一直是同一个oracle实例，因此oracle本身可以管理种子库
        this.randomQueryGenerator = new RandomQueryGenerator<MemGraphSchema, MemGraphGlobalState>();
    }

    @Override
    public void check() throws Exception {
        //todo oracle 的检测逻辑，会被调用多次
        IClauseSequence sequence = randomQueryGenerator.generateQuery(globalState);
        StringBuilder sb = new StringBuilder();
        sequence.toTextRepresentation(sb);
        System.out.println(sb);
        globalState.executeStatement(new CypherQueryAdapter(sb.toString()));

        boolean isCoverageIncreasing = false;
        boolean isBugDetected = false;
        int resultLength = 0;
        //todo 上层通过抛出的异常检测是否通过，所以这里可以捕获并检测异常的类型，可以计算一些统计数据，然后重抛异常

        if (isCoverageIncreasing || isBugDetected || resultLength > 0) {
            randomQueryGenerator.addSeed(new RandomQueryGenerator.Seed(
                    sequence, isBugDetected, resultLength
            ));//添加seed
        }
    }
}
