package gdsmith.agensGraph;

import gdsmith.common.schema.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gdsmith.cypher.ICypherSchema;
import gdsmith.cypher.ast.*;
import gdsmith.cypher.ast.analyzer.ICypherTypeAnalyzer;
import gdsmith.cypher.schema.*;
import gdsmith.agensGraph.AgensGraphGlobalState;
import gdsmith.agensGraph.AgensGraphSchema.AgensGraphTable;
import gdsmith.cypher.standard_ast.CypherType;

public class AgensGraphSchema extends CypherSchema<AgensGraphGlobalState, AgensGraphTable> {


    public static AgensGraphSchema createEmptyNewSchema(){
        return new AgensGraphSchema(new ArrayList<AgensGraphTable>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    //todo complete
    public AgensGraphSchema(List<AgensGraphTable> databaseTables, List<CypherLabelInfo> labels,
                       List<CypherRelationTypeInfo> relationTypes, List<CypherPatternInfo> patternInfos) {
        super(databaseTables, labels, relationTypes, patternInfos);
    }


    public enum AgensGraphBuiltInFunctions implements IFunctionInfo{
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

        AgensGraphBuiltInFunctions(String name, String signature, CypherType expectedReturnType, IParamInfo... params){
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

    public enum AgensGraphDataType{

    }
    public static class AgensGraphTable extends AbstractTable<AgensGraphTableColumn, TableIndex, AgensGraphGlobalState> {

        //todo complete
        public AgensGraphTable(String name, List<AgensGraphTableColumn> columns, List<TableIndex> indexes, boolean isView) {
            super(name, columns, indexes, isView);
        }

        @Override
        public long getNrRows(AgensGraphGlobalState globalState) {
            return 0;
        }
    }

    public static class AgensGraphTableColumn extends AbstractTableColumn<AgensGraphTable, AgensGraphDataType>{
        //todo complete
        public AgensGraphTableColumn(String name, AgensGraphTable table, AgensGraphDataType type) {
            super(name, table, type);
        }
    }
}
