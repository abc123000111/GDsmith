package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.*;
import gdsmith.cypher.ast.analyzer.IClauseAnalyzer;
import gdsmith.cypher.ast.analyzer.IRelationAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RelationAnalyzer extends RelationIdentifier implements IRelationAnalyzer {
    IRelationAnalyzer formerDef = null;
    IRelationIdentifier source;
    IClauseAnalyzer clauseAnalyzer;
    IExpression sourceExpression = null;

    RelationAnalyzer(IRelationIdentifier relationIdentifier, IClauseAnalyzer clauseAnalyzer, IExpression sourceExpression){
        this(relationIdentifier.getName(), relationIdentifier.getTypes().get(0), relationIdentifier.getDirection(),
                relationIdentifier.getProperties(), relationIdentifier.getLengthLowerBound(), relationIdentifier.getLengthUpperBound());
        source = relationIdentifier;
        this.clauseAnalyzer = clauseAnalyzer;
        this.sourceExpression = sourceExpression;
    }

    RelationAnalyzer(IRelationIdentifier relationIdentifier, IClauseAnalyzer clauseAnalyzer){
        this(relationIdentifier, clauseAnalyzer, null);
    }

    RelationAnalyzer(String name, IType relationType, Direction direction, List<IProperty> properties, int lengthLowerBound, int lengthUpperBound) {
        super(name, relationType, direction, properties, lengthLowerBound, lengthUpperBound);
    }

    @Override
    public IRelationIdentifier getSource() {
        return source;
    }

    @Override
    public IExpression getSourceRefExpression() {
        return sourceExpression;
    }

    @Override
    public IRelationAnalyzer getFormerDef() {
        return formerDef;
    }

    @Override
    public IClauseAnalyzer getClauseAnalyzer() {
        return clauseAnalyzer;
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
