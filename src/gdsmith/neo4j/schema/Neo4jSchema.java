package gdsmith.neo4j.schema;

import gdsmith.common.schema.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gdsmith.cypher.ast.*;
import gdsmith.cypher.ast.analyzer.ICypherTypeDescriptor;
import gdsmith.cypher.schema.*;
import gdsmith.neo4j.Neo4jGlobalState;
import gdsmith.neo4j.schema.Neo4jSchema.Neo4jTable;
import gdsmith.cypher.standard_ast.CypherType;

public class Neo4jSchema extends CypherSchema<Neo4jGlobalState, Neo4jTable> {


    public static Neo4jSchema createEmptyNewSchema(){
        return new Neo4jSchema(new ArrayList<Neo4jTable>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    //todo complete
    public Neo4jSchema(List<Neo4jTable> databaseTables, List<CypherLabelInfo> labels,
                       List<CypherRelationTypeInfo> relationTypes, List<CypherPatternInfo> patternInfos) {
        super(databaseTables, labels, relationTypes, patternInfos);
    }
    

    public enum Neo4jBuiltInFunctions implements IFunctionInfo{
        AVG("avg", "avg@number", CypherType.NUMBER, new CypherParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeDescriptor calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        MAX_NUMBER("max", "max@number", CypherType.NUMBER, new CypherParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeDescriptor calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        MAX_STRING("max", "max@string", CypherType.STRING, new CypherParamInfo(CypherType.STRING, false)){
            @Override
            public ICypherTypeDescriptor calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        MIN_NUMBER("min", "min@number", CypherType.NUMBER, new CypherParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeDescriptor calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        MIN_STRING("min", "min@string", CypherType.STRING, new CypherParamInfo(CypherType.STRING, false)){
            @Override
            public ICypherTypeDescriptor calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        PERCENTILE_COUNT_NUMBER("percentileCount", "percentileCount@number", CypherType.NUMBER,
                new CypherParamInfo(CypherType.NUMBER, false), new CypherParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeDescriptor calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        PERCENTILE_COUNT_STRING("percentileCount", "percentileCount@string", CypherType.NUMBER,
                new CypherParamInfo(CypherType.STRING, false), new CypherParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeDescriptor calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        PERCENTILE_DISC_NUMBER("percentileDisc", "percentileDisc@number", CypherType.NUMBER,
                new CypherParamInfo(CypherType.NUMBER, false), new CypherParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeDescriptor calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        PERCENTILE_DISC_STRING("percentileDisc", "percentileDisct@string", CypherType.NUMBER,
                new CypherParamInfo(CypherType.STRING, false), new CypherParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeDescriptor calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        ST_DEV("stDev", "stDev", CypherType.NUMBER, new CypherParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeDescriptor calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        ST_DEV_P("stDevP", "stDevP", CypherType.NUMBER, new CypherParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeDescriptor calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        SUM("sum", "sum", CypherType.NUMBER, new CypherParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeDescriptor calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        COLLECT("collect", "collect", CypherType.LIST, new CypherParamInfo(CypherType.ANY, false)){
            @Override
            public ICypherTypeDescriptor calculateReturnType(List<IExpression> params) {
                return null;
            }
        }
        ;

        Neo4jBuiltInFunctions(String name, String signature, CypherType expectedReturnType, IParamInfo... params){
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
    
    public enum Neo4jDataType{

    }
    public static class Neo4jTable extends AbstractTable<Neo4jTableColumn, TableIndex, Neo4jGlobalState> {

        //todo complete
        public Neo4jTable(String name, List<Neo4jTableColumn> columns, List<TableIndex> indexes, boolean isView) {
            super(name, columns, indexes, isView);
        }

        @Override
        public long getNrRows(Neo4jGlobalState globalState) {
            return 0;
        }
    }

    public static class Neo4jTableColumn extends AbstractTableColumn<Neo4jTable, Neo4jDataType>{
        //todo complete
        public Neo4jTableColumn(String name, Neo4jTable table, Neo4jDataType type) {
            super(name, table, type);
        }
    }
}
