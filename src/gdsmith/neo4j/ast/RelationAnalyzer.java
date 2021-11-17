package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.Direction;
import gdsmith.cypher.ast.IProperty;
import gdsmith.cypher.ast.IRelationIdentifier;
import gdsmith.cypher.ast.IType;
import gdsmith.cypher.ast.analyzer.IRelationAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RelationAnalyzer extends RelationIdentifier implements IRelationAnalyzer {
    IRelationAnalyzer formerDef = null;

    RelationAnalyzer(IRelationIdentifier relationIdentifier){
        this(relationIdentifier.getName(), relationIdentifier.getTypes().get(0), relationIdentifier.getDirection(),
                relationIdentifier.getProperties());
    }

    RelationAnalyzer(String name, IType relationType, Direction direction, List<IProperty> properties) {
        super(name, relationType, direction, properties);
    }

    @Override
    public IRelationAnalyzer getFormerDef() {
        return formerDef;
    }

    @Override
    public void setFormerDef(IRelationAnalyzer formerDef) {
        this.formerDef = formerDef;
    }

    @Override
    public List<IType> getAllRelationTypesInDefChain() {
        if(this.relationType == null){
            if(formerDef != null){
                return formerDef.getTypes();
            }
            return new ArrayList<>();
        }
        return new ArrayList<IType>(Arrays.asList(this.relationType));
    }

    @Override
    public List<IProperty> getAllPropertiesInDefChain() {
        List<IProperty> properties = new ArrayList<>(this.properties);
        if(formerDef != null){
            properties.addAll(formerDef.getProperties());
            properties = properties.stream().distinct().collect(Collectors.toList());
        }
        return properties;
    }
}
