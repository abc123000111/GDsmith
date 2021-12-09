package gdsmith.neo4j.schema;

import gdsmith.common.schema.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gdsmith.cypher.ICypherSchema;
import gdsmith.cypher.ast.*;
import gdsmith.cypher.ast.analyzer.ICypherTypeAnalyzer;
import gdsmith.cypher.schema.*;
import gdsmith.neo4j.Neo4jGlobalState;
import gdsmith.neo4j.schema.Neo4jSchema.Neo4jTable;
import gdsmith.cypher.standard_ast.CypherType;

public class Neo4jSchema extends AbstractSchema<Neo4jGlobalState, Neo4jTable> implements ICypherSchema {

    private List<Neo4jLabelInfo> labels; //所有的Label信息
    private List<Neo4jRelationTypeInfo> relationTypes; //所有的relationship type信息
    private List<Neo4jPatternInfo> patternInfos; //存在的pattern结构


    private static Neo4jSchema schema;

    public static Neo4jSchema createEmptyNewSchema(){
        return new Neo4jSchema(new ArrayList<Neo4jTable>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    //todo complete
    public Neo4jSchema(List<Neo4jTable> databaseTables, List<Neo4jLabelInfo> labels,
                       List<Neo4jRelationTypeInfo> relationTypes, List<Neo4jPatternInfo> patternInfos) {
        super(databaseTables);

        this.labels = labels;
        this.relationTypes = relationTypes;
        this.patternInfos = patternInfos;
    }

    public boolean containsLabel(ILabel label){
        for(ILabelInfo labelInfo : labels){
            if (labelInfo.getName().equals(label.getName())){
                return true;
            }
        }
        return false;
    }

    public ILabelInfo getLabelInfo(ILabel label){
        for(ILabelInfo labelInfo : labels){
            if (labelInfo.getName().equals(label.getName())){
                return labelInfo;
            }
        }
        return null;
    }

    public boolean containsRelationType(IType relation){
        if(relation == null){
            return false;
        }
        for(IRelationTypeInfo relationInfo : relationTypes){
            if (relationInfo != null && relationInfo.getName().equals(relation.getName())){
                return true;
            }
        }
        return false;
    }

    public IRelationTypeInfo getRelationInfo(IType relation){
        if(relation == null){
            return null;
        }
        for(IRelationTypeInfo relationInfo : relationTypes){
            if (relationInfo != null && relationInfo.getName().equals(relation.getName())){
                return relationInfo;
            }
        }
        return null;
    }


    public List<Neo4jLabelInfo> getLabels(){
        return labels;
    }

    public List<Neo4jRelationTypeInfo> getRelationTypes(){
        return relationTypes;
    }

    public static class Neo4jPatternInfo implements IPatternInfo {

        private List<IPatternElementInfo> patternElementInfos = new ArrayList<>();

        public Neo4jPatternInfo(List<IPatternElementInfo> patternElementInfos) {
            this.patternElementInfos = patternElementInfos;
        }

        @Override
        public List<IPatternElementInfo> getPattern() {
            return patternElementInfos;
        }
    }

    public static class Neo4jRelationTypeInfo implements IRelationTypeInfo {
        private String name;
        private List<IPropertyInfo> properties = new ArrayList<>();

        public Neo4jRelationTypeInfo(String name, List<IPropertyInfo> properties) {
            this.name = name;
            this.properties = properties;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<IPropertyInfo> getProperties() {
            return properties;
        }

        @Override
        public boolean hasPropertyWithType(ICypherType type) {
            for(IPropertyInfo propertyInfo : properties){
                if(propertyInfo.getType() == type){
                    return true;
                }
            }
            return false;
        }

        @Override
        public List<IPropertyInfo> getPropertiesWithType(ICypherType type) {
            List<IPropertyInfo> returnProperties = new ArrayList<>();
            for(IPropertyInfo propertyInfo : properties){
                if(propertyInfo.getType() == type){
                    returnProperties.add(propertyInfo);
                }
            }
            return returnProperties;
        }
    }

    public static class Neo4jLabelInfo implements ILabelInfo {
        private String name;
        private List<IPropertyInfo> properties = new ArrayList<>();

        public Neo4jLabelInfo(String name, List<IPropertyInfo> properties) {
            this.name = name;
            this.properties = properties;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public List<IPropertyInfo> getProperties() {
            return properties;
        }

        @Override
        public boolean hasPropertyWithType(ICypherType type) {
            for(IPropertyInfo propertyInfo : properties){
                if(propertyInfo.getType() == type){
                    return true;
                }
            }
            return false;
        }

        @Override
        public List<IPropertyInfo> getPropertiesWithType(ICypherType type) {
            List<IPropertyInfo> returnProperties = new ArrayList<>();
            for(IPropertyInfo propertyInfo : properties){
                if(propertyInfo.getType() == type){
                    returnProperties.add(propertyInfo);
                }
            }
            return returnProperties;
        }
    }

    public static class Neo4jPropertyInfo implements IPropertyInfo{
        private String key;
        private CypherType type;
        private boolean isOptional;

        public Neo4jPropertyInfo(String key, CypherType type, boolean isOptional) {
            this.key = key;
            this.type = type;
            this.isOptional = isOptional;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public CypherType getType() {
            return type;
        }

        @Override
        public boolean isOptional() {
            return isOptional;
        }
    }

    public static abstract class Neo4jFunctionInfo implements IFunctionInfo {
        private String name;
        private List<IParamInfo> params;
        private CypherType expectedReturnType;

        public Neo4jFunctionInfo(String name, CypherType expectedReturnType, IParamInfo ...params){
            this.name = name;
            this.params = Arrays.asList(params);
            this.expectedReturnType = expectedReturnType;
        }

        @Override
        public String getName() {
            return name;
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

    public enum Neo4jBuiltInFunctions implements IFunctionInfo{
        AVG("avg", "avg@number", CypherType.NUMBER, new Neo4jParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeAnalyzer calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        MAX_NUMBER("max", "max@number", CypherType.NUMBER, new Neo4jParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeAnalyzer calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        MAX_STRING("max", "max@string", CypherType.STRING, new Neo4jParamInfo(CypherType.STRING, false)){
            @Override
            public ICypherTypeAnalyzer calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        MIN_NUMBER("min", "min@number", CypherType.NUMBER, new Neo4jParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeAnalyzer calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        MIN_STRING("min", "min@string", CypherType.STRING, new Neo4jParamInfo(CypherType.STRING, false)){
            @Override
            public ICypherTypeAnalyzer calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        PERCENTILE_COUNT_NUMBER("percentileCount", "percentileCount@number", CypherType.NUMBER,
                new Neo4jParamInfo(CypherType.NUMBER, false), new Neo4jParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeAnalyzer calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        PERCENTILE_COUNT_STRING("percentileCount", "percentileCount@string", CypherType.NUMBER,
                new Neo4jParamInfo(CypherType.STRING, false), new Neo4jParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeAnalyzer calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        PERCENTILE_DISC_NUMBER("percentileDisc", "percentileDisc@number", CypherType.NUMBER,
                new Neo4jParamInfo(CypherType.NUMBER, false), new Neo4jParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeAnalyzer calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        PERCENTILE_DISC_STRING("percentileDisc", "percentileDisct@string", CypherType.NUMBER,
                new Neo4jParamInfo(CypherType.STRING, false), new Neo4jParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeAnalyzer calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        ST_DEV("stDev", "stDev", CypherType.NUMBER, new Neo4jParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeAnalyzer calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        ST_DEV_P("stDevP", "stDevP", CypherType.NUMBER, new Neo4jParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeAnalyzer calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
        SUM("sum", "sum", CypherType.NUMBER, new Neo4jParamInfo(CypherType.NUMBER, false)){
            @Override
            public ICypherTypeAnalyzer calculateReturnType(List<IExpression> params) {
                return null;
            }
        },
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

    public static class Neo4jParamInfo implements IParamInfo{
        private boolean isOptionalLength;
        private CypherType paramType;

        public Neo4jParamInfo(CypherType type, boolean isOptionalLength){
                paramType = type;
                this.isOptionalLength = isOptionalLength;
        }

        @Override
        public boolean isOptionalLength() {
            return isOptionalLength;
        }

        @Override
        public CypherType getParamType() {
            return paramType;
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
