package gdsmith.cypher.ast;

import gdsmith.cypher.ast.analyzer.IRelationAnalyzer;

import java.util.List;

public interface IRelationIdentifier extends IPatternElement{
    List<IProperty> getProperties();
    List<IType> getTypes();
    Direction getDirection();
    void setDirection(Direction direction);
    IRelationIdentifier createRef();
}
