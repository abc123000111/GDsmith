package gdsmith.neo4j.schema;

import gdsmith.cypher.ast.IExpression;
import gdsmith.cypher.ast.analyzer.ICypherTypeAnalyzer;
import gdsmith.neo4j.ast.Neo4jType;

import java.util.List;

public interface IFunctionInfo {
    String getName();
    String getSignature();
    List<IParamInfo> getParams();
    Neo4jType getExpectedReturnType();
    ICypherTypeAnalyzer calculateReturnType(List<IExpression> params);
}
