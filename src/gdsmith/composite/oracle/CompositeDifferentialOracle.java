package gdsmith.composite.oracle;

import gdsmith.common.query.GDSmithResultSet;
import gdsmith.composite.CompositeSchema;
import gdsmith.common.oracle.TestOracle;
import gdsmith.cypher.CypherQueryAdapter;
import gdsmith.cypher.ast.IClauseSequence;
import gdsmith.composite.CompositeGlobalState;
import gdsmith.cypher.gen.random.RandomQueryGenerator;

import java.util.List;

public class CompositeDifferentialOracle implements TestOracle {

    private final CompositeGlobalState globalState;
    private RandomQueryGenerator<CompositeSchema, CompositeGlobalState> randomQueryGenerator;

    public CompositeDifferentialOracle(CompositeGlobalState globalState){
        this.globalState = globalState;
        //todo 整个oracle的check会被执行多次，一直是同一个oracle实例，因此oracle本身可以管理种子库
        this.randomQueryGenerator = new RandomQueryGenerator<CompositeSchema, CompositeGlobalState>();
    }

    @Override
    public void check() throws Exception {
        //todo oracle 的检测逻辑，会被调用多次
        IClauseSequence sequence = randomQueryGenerator.generateQuery(globalState);
        StringBuilder sb = new StringBuilder();
        sequence.toTextRepresentation(sb);
        System.out.println(sb);
        List<GDSmithResultSet> results = globalState.executeStatementAndGet(new CypherQueryAdapter(sb.toString()));

        //todo 判断不同的resultSet返回是否一致
        boolean isBugDetected = false;
        for(int i = 1; i < results.size(); i++) {
            if (!results.get(i).compareWithOutOrder(results.get(i - 1))) {
                throw new AssertionError("the content of the result sets mismatch!");
            }
        }

        boolean isCoverageIncreasing = false;
        int resultLength = results.get(0).getRowNum();
        //todo 上层通过抛出的异常检测是否通过，所以这里可以捕获并检测异常的类型，可以计算一些统计数据，然后重抛异常

        if (isCoverageIncreasing || resultLength > 0) {
            randomQueryGenerator.addSeed(new RandomQueryGenerator.Seed(
                    sequence, isBugDetected, resultLength
            ));//添加seed
        }
    }
}
