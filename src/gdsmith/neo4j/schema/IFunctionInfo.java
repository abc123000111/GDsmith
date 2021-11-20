package gdsmith.neo4j.schema;

import gdsmith.cypher.ast.IExpression;
import gdsmith.neo4j.ast.Neo4jType;

import java.util.List;

public interface IFunctionInfo {
    String getName();
    List<IParamInfo> getParams();
    Neo4jType getExpectedReturnType();
    Neo4jType calculateReturnType(List<IExpression> params);
}
