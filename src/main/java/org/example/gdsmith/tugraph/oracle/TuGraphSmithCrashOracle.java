package org.example.gdsmith.tugraph.oracle;

import org.example.gdsmith.common.oracle.TestOracle;
import org.example.gdsmith.common.query.GDSmithResultSet;
import org.example.gdsmith.cypher.CypherQueryAdapter;
import org.example.gdsmith.cypher.ast.*;
import org.example.gdsmith.cypher.ast.IClauseSequence;
import org.example.gdsmith.tugraph.TuGraphGlobalState;
import org.example.gdsmith.cypher.gen.query.RandomQueryGenerator;
import org.example.gdsmith.tugraph.schema.TuGraphSchema;

public class TuGraphSmithCrashOracle implements TestOracle {

    private final TuGraphGlobalState globalState;
    private RandomQueryGenerator<TuGraphSchema, TuGraphGlobalState> randomQueryGenerator;

    public TuGraphSmithCrashOracle(TuGraphGlobalState globalState){
        this.globalState = globalState;
        //todo 整个oracle的check会被执行多次，一直是同一个oracle实例，因此oracle本身可以管理种子库
        this.randomQueryGenerator = new RandomQueryGenerator<TuGraphSchema, TuGraphGlobalState>();
    }

    @Override
    public void check() throws Exception {
        //todo oracle 的检测逻辑，会被调用多次
        IClauseSequence sequence = randomQueryGenerator.generateQuery(globalState);

        StringBuilder sb = new StringBuilder();
        sequence.toTextRepresentation(sb);

        System.out.println(sb);
        GDSmithResultSet r = globalState.executeStatementAndGet(new CypherQueryAdapter(sb.toString())).get(0);
        System.out.println(r.getResult());
    }
}
