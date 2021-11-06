package gdsmith.cypher.ast;

import java.util.List;

public interface INodePattern extends IPatternElement{
    List<IProperty> getProperties();
    List<ILabel> getLabels();
    INodePattern getFormerDef();
    INodePattern createRef();
}
