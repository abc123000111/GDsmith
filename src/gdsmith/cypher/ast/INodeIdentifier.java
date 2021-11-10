package gdsmith.cypher.ast;

import java.util.List;

public interface INodeIdentifier extends IPatternElement{
    List<IProperty> getProperties();
    List<ILabel> getLabels();
    INodeIdentifier getFormerDef();
    INodeIdentifier createRef();
}
