package gdsmith.composite.oracle;

import gdsmith.common.query.GDSmithResultSet;
import gdsmith.composite.CompositeSchema;
import gdsmith.common.oracle.TestOracle;
import gdsmith.cypher.CypherQueryAdapter;
import gdsmith.cypher.ast.IClauseSequence;
import gdsmith.composite.CompositeGlobalState;
import gdsmith.cypher.gen.random.RandomQueryGenerator;
import gdsmith.cypher.schema.CypherSchema;
import gdsmith.cypher.schema.IPropertyInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                String msg = "The contents of the result sets mismatch!\n";
                msg = msg + "First: " + results.get(i - 1).getRowNum() + " --- " + results.get(i - 1).resultToStringList() + "\n";
                msg = msg + "Second: " + results.get(i).getRowNum() + " --- " + results.get(i).resultToStringList() + "\n";
                throw new AssertionError(msg);
            }
        }

        int resultLength = results.get(0).getRowNum();
        //todo 上层通过抛出的异常检测是否通过，所以这里可以捕获并检测异常的类型，可以计算一些统计数据，然后重抛异常

        if (resultLength > 0) {
            randomQueryGenerator.addSeed(new RandomQueryGenerator.Seed(
                    sequence, isBugDetected, resultLength
            ));//添加seed

            //todo 更新属性选择概率
            List<String> coveredProperty = new ArrayList<>();
            Pattern allProps = Pattern.compile("\\.(k\\d{1,2})\\)");
            Matcher matcher = allProps.matcher(sb);
            while(matcher.find()){
                if (!coveredProperty.contains(matcher.group(1))) {
                    coveredProperty.add(matcher.group(1));
                }
            }

            List<CypherSchema.CypherLabelInfo> labels = globalState.getSchema().getLabels();
            List<CypherSchema.CypherRelationTypeInfo> relations = globalState.getSchema().getRelationTypes();
            for (String name: coveredProperty) {
                found:{
                    for (CypherSchema.CypherLabelInfo label: labels) {
                        List<IPropertyInfo> props = label.getProperties();
                        for (IPropertyInfo prop: props) {
                            if (Objects.equals(prop.getKey(), name)) {
                                ((CypherSchema.CypherPropertyInfo)prop).addFreq();
                                //System.out.println(prop.getKey() + ":" + ((CypherSchema.CypherPropertyInfo)prop).getFreq());
                                break found;
                            }
                        }
                    }
                    for (CypherSchema.CypherRelationTypeInfo relation: relations) {
                        List<IPropertyInfo> props = relation.getProperties();
                        for (IPropertyInfo prop: props) {
                            if (Objects.equals(prop.getKey(), name)) {
                                ((CypherSchema.CypherPropertyInfo)prop).addFreq();
                                //System.out.println(prop.getKey() + ":" + ((CypherSchema.CypherPropertyInfo)prop).getFreq());
                                break found;
                            }
                        }
                    }
                }
            }


        }
    }
}
