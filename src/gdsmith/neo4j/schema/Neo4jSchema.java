package gdsmith.neo4j.schema;

import gdsmith.common.schema.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gdsmith.cypher.ast.IExpression;
import gdsmith.neo4j.Neo4jGlobalState;
import gdsmith.neo4j.schema.Neo4jSchema.Neo4jTable;
import gdsmith.neo4j.ast.Neo4jType;

public class Neo4jSchema extends AbstractSchema<Neo4jGlobalState, Neo4jTable> {

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

    public List<Neo4jLabelInfo> getLabels(){
        return labels;
    }

    public List<Neo4jRelationTypeInfo> getRelationTypes(){
        return relationTypes;
    }

    public static class Neo4jPatternInfo implements IPatternInfo{

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
    }

    public static class Neo4jPropertyInfo implements IPropertyInfo{
        private String key;
        private Neo4jType type;
        private boolean isOptional;

        public Neo4jPropertyInfo(String key, Neo4jType type, boolean isOptional) {
            this.key = key;
            this.type = type;
            this.isOptional = isOptional;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public Neo4jType getType() {
            return type;
        }

        @Override
        public boolean isOptional() {
            return isOptional;
        }
    }

    public static abstract class Neo4jFunctionInfo implements IFunctionInfo{
        private String name;
        private List<IParamInfo> params;
        private Neo4jType expectedReturnType;

        public Neo4jFunctionInfo(String name, Neo4jType expectedReturnType, IParamInfo ...params){
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
        public Neo4jType getExpectedReturnType() {
            return expectedReturnType;
        }
    }

    public enum Neo4jBuiltInFunctions implements IFunctionInfo{
        MAX("MAX", Neo4jType.UNKNOWN, new Neo4jParamInfo(Neo4jType.UNKNOWN, true)){
            @Override
            public Neo4jType calculateReturnType(List<IExpression> params) {
                return null;
            }
        };

        Neo4jBuiltInFunctions(String name, Neo4jType expectedReturnType, IParamInfo... params){
            this.name = name;
            this.params = Arrays.asList(params);
            this.expectedReturnType = expectedReturnType;
        }

        private String name;
        private List<IParamInfo> params;
        private Neo4jType expectedReturnType;

        @Override
        public String getName() {
            return null;
        }

        @Override
        public List<IParamInfo> getParams() {
            return null;
        }

        @Override
        public Neo4jType getExpectedReturnType() {
            return null;
        }
    }

    public static class Neo4jParamInfo implements IParamInfo{
        private boolean isOptionalLength;
        private Neo4jType paramType;

        public Neo4jParamInfo(Neo4jType type, boolean isOptionalLength){
                paramType = type;
                this.isOptionalLength = isOptionalLength;
        }

        @Override
        public boolean isOptionalLength() {
            return isOptionalLength;
        }

        @Override
        public Neo4jType getParamType() {
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
