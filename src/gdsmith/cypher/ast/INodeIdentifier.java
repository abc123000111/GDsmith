package gdsmith.cypher.ast;

import gdsmith.cypher.ast.analyzer.INodeAnalyzer;

import java.util.List;

public interface INodeIdentifier extends IPatternElement{
    List<IProperty> getProperties();
    List<ILabel> getLabels();
    INodeIdentifier createRef();
}
