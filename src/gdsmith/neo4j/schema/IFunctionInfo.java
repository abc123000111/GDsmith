package gdsmith.neo4j.schema;

import gdsmith.cypher.ast.IExpression;
import gdsmith.cypher.ast.analyzer.ICypherTypeAnalyzer;
import gdsmith.cypher.standard_ast.CypherType;

import java.util.List;

public interface IFunctionInfo {
    String getName();
    String getSignature();
    List<IParamInfo> getParams();
    CypherType getExpectedReturnType();
    ICypherTypeAnalyzer calculateReturnType(List<IExpression> params);
}
