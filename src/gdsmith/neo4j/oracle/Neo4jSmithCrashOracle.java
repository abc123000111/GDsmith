package gdsmith.neo4j.oracle;

import gdsmith.common.oracle.TestOracle;
import gdsmith.common.query.GDSmithResultSet;
import gdsmith.cypher.CypherQueryAdapter;
import gdsmith.cypher.ast.*;
import gdsmith.neo4j.Neo4jGlobalState;
import gdsmith.cypher.gen.random.RandomQueryGenerator;
import gdsmith.neo4j.schema.Neo4jSchema;

public class Neo4jSmithCrashOracle implements TestOracle {

    private final Neo4jGlobalState globalState;
    private RandomQueryGenerator<Neo4jSchema, Neo4jGlobalState> randomQueryGenerator;

    public Neo4jSmithCrashOracle(Neo4jGlobalState globalState){
        this.globalState = globalState;
        //todo 整个oracle的check会被执行多次，一直是同一个oracle实例，因此oracle本身可以管理种子库
        this.randomQueryGenerator = new RandomQueryGenerator<Neo4jSchema, Neo4jGlobalState>();
    }

    @Override
    public void check() throws Exception {
        //todo oracle 的检测逻辑，会被调用多次
        IClauseSequence sequence = randomQueryGenerator.generateQuery(globalState);
        StringBuilder sb = new StringBuilder();
        sequence.toTextRepresentation(sb);
        sequence.getClauseList().stream().forEach(s->{
            if(s instanceof IMatch){
                System.out.print("MATCH ");
            }
            if(s instanceof IWith){
                System.out.print("WITH ");
            }
            if(s instanceof IReturn){
                System.out.print("RETURN ");
            }
            if(s instanceof IUnwind){
                System.out.print("UNWIND ");
            }
            System.out.print(" local: ");
            s.toAnalyzer().getLocalAliases().stream().forEach(
                    a->{
                        System.out.print(a.getName()+" ");
                    }
            );
            System.out.print(" extendable: ");
            s.toAnalyzer().getExtendableAliases().stream().forEach(
                    a->{
                        System.out.print(a.getName()+" ");
                    }
            );
            System.out.print(" avaialble: ");
            s.toAnalyzer().getAvailableAliases().stream().forEach(
                    a->{
                        System.out.print(a.getName()+" ");
                    }
            );
            System.out.print("\n");
        });

        System.out.println(sb);
        // globalState.executeStatement(new CypherQueryAdapter(sb.toString()));
        GDSmithResultSet r = globalState.executeStatementAndGet(new CypherQueryAdapter(sb.toString())).get(0);
        
        boolean isCoverageIncreasing = false;
        boolean isBugDetected = false;
        int resultLength = r.getRowNum();
        //todo 上层通过抛出的异常检测是否通过，所以这里可以捕获并检测异常的类型，可以计算一些统计数据，然后重抛异常

        // resultLength = 0;

        if (isCoverageIncreasing || isBugDetected || resultLength > 0) {
            randomQueryGenerator.addSeed(new RandomQueryGenerator.Seed(
                    sequence, isBugDetected, resultLength
            ));//添加seed
        }
    }
}
