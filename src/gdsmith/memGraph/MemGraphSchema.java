package gdsmith.memGraph;

import gdsmith.common.schema.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gdsmith.cypher.ast.*;
import gdsmith.cypher.ast.analyzer.ICypherTypeAnalyzer;
import gdsmith.cypher.schema.*;
import gdsmith.memGraph.MemGraphSchema.MemGraphTable;
import gdsmith.cypher.standard_ast.CypherType;

public class MemGraphSchema extends CypherSchema<MemGraphGlobalState, MemGraphTable> {


    public static MemGraphSchema createEmptyNewSchema(){
        return new MemGraphSchema(new ArrayList<MemGraphTable>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    //todo complete
    public MemGraphSchema(List<MemGraphTable> databaseTables, List<CypherLabelInfo> labels,
                            List<CypherRelationTypeInfo> relationTypes, List<CypherPatternInfo> patternInfos) {
        super(databaseTables, labels, relationTypes, patternInfos);
    }


    public enum MemGraphBuiltInFunctions implements IFunctionInfo{
        AVG("avg", "avg@number", CypherType.NUMBER, new CypherParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeAnalyzer calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        MAX_NUMBER("max", "max@number", CypherType.NUMBER, new CypherParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeAnalyzer calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        MAX_STRING("max", "max@string", CypherType.STRING, new CypherParamInfo(CypherType.STRING, false)){
            @Override
            public ICypherTypeAnalyzer calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        MIN_NUMBER("min", "min@number", CypherType.NUMBER, new CypherParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeAnalyzer calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        MIN_STRING("min", "min@string", CypherType.STRING, new CypherParamInfo(CypherType.STRING, false)){
            @Override
            public ICypherTypeAnalyzer calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        PERCENTILE_COUNT_NUMBER("percentileCount", "percentileCount@number", CypherType.NUMBER,
                new CypherParamInfo(CypherType.NUMBER, false), new CypherParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeAnalyzer calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        PERCENTILE_COUNT_STRING("percentileCount", "percentileCount@string", CypherType.NUMBER,
                new CypherParamInfo(CypherType.STRING, false), new CypherParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeAnalyzer calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        PERCENTILE_DISC_NUMBER("percentileDisc", "percentileDisc@number", CypherType.NUMBER,
                new CypherParamInfo(CypherType.NUMBER, false), new CypherParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeAnalyzer calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        PERCENTILE_DISC_STRING("percentileDisc", "percentileDisct@string", CypherType.NUMBER,
                new CypherParamInfo(CypherType.STRING, false), new CypherParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeAnalyzer calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        ST_DEV("stDev", "stDev", CypherType.NUMBER, new CypherParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeAnalyzer calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        ST_DEV_P("stDevP", "stDevP", CypherType.NUMBER, new CypherParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeAnalyzer calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        SUM("sum", "sum", CypherType.NUMBER, new CypherParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeAnalyzer calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        ;

        MemGraphBuiltInFunctions(String name, String signature, CypherType expectedReturnType, IParamInfo... params){
            this.name = name;
            this.params = Arrays.asList(params);
            this.expectedReturnType = expectedReturnType;
        }

        private String name, signature;
        private List<IParamInfo> params;
        private CypherType expectedReturnType;

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getSignature() {
            return signature;
        }

        @Override
        public List<IParamInfo> getParams() {
            return params;
        }

        @Override
        public CypherType getExpectedReturnType() {
            return expectedReturnType;
        }
    }

    public enum MemGraphDataType{

    }
    public static class MemGraphTable extends AbstractTable<MemGraphTableColumn, TableIndex, MemGraphGlobalState> {

        //todo complete
        public MemGraphTable(String name, List<MemGraphTableColumn> columns, List<TableIndex> indexes, boolean isView) {
            super(name, columns, indexes, isView);
        }

        @Override
        public long getNrRows(MemGraphGlobalState globalState) {
            return 0;
        }
    }

    public static class MemGraphTableColumn extends AbstractTableColumn<MemGraphTable, MemGraphDataType>{
        //todo complete
        public MemGraphTableColumn(String name, MemGraphTable table, MemGraphDataType type) {
            super(name, table, type);
        }
    }
}
