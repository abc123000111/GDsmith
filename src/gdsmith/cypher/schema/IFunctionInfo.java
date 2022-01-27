package gdsmith.cypher.schema;

import gdsmith.cypher.ast.IExpression;
import gdsmith.cypher.ast.analyzer.ICypherTypeDescriptor;
import gdsmith.cypher.standard_ast.CypherType;

import java.util.List;

public interface IFunctionInfo {
    String getName();
    String getSignature();
    List<IParamInfo> getParams();
    CypherType getExpectedReturnType();
    ICypherTypeDescriptor calculateReturnType(List<IExpression> params);
}
