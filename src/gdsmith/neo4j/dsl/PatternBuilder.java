package gdsmith.neo4j.dsl;

import gdsmith.cypher.ast.*;
import gdsmith.neo4j.ast.NodePattern;
import gdsmith.neo4j.ast.Pattern;
import gdsmith.neo4j.ast.RelationPattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PatternBuilder {
    private final IIdentifierBuilder identifierBuilder;
    private final List<IPatternElement> patternElements;

    public PatternBuilder(IIdentifierBuilder identifierBuilder){
        this.identifierBuilder = identifierBuilder;
        this.patternElements = new ArrayList<>();
    }

    public static class NodeBuilder {
        private PatternBuilder patternBuilder;
        private String curNodeName = "";
        private List<ILabel> curNodeLabels;
        private List<IProperty> curNodeProperties;

        private NodeBuilder(PatternBuilder patternBuilder){
            this.patternBuilder = patternBuilder;
            curNodeLabels = new ArrayList<>();
            curNodeProperties = new ArrayList<>();
        }

        public NodeBuilder withLabels(ILabel ...labels){
            curNodeLabels.addAll(Arrays.asList(labels));
            curNodeLabels = curNodeLabels.stream().distinct().collect(Collectors.toList());
            return this;
        }

        public NodeBuilder withProperties(IProperty ...properties){
            curNodeProperties.addAll(Arrays.asList(properties));
            curNodeProperties = curNodeProperties.stream().distinct().collect(Collectors.toList());
            return this;
        }

        public RelationBuilder newNamedRelation(){
            NodePattern node = new NodePattern(curNodeName, curNodeLabels, curNodeProperties);
            patternBuilder.patternElements.add(node);
            RelationBuilder relationBuilder = new RelationBuilder(patternBuilder);
            relationBuilder.curRelationName = patternBuilder.identifierBuilder.getNewRelationName();
            return relationBuilder;
        }

        public RelationBuilder newAnonymousRelation(){
            NodePattern node = new NodePattern(curNodeName, curNodeLabels, curNodeProperties);
            patternBuilder.patternElements.add(node);
            RelationBuilder relationBuilder = new RelationBuilder(patternBuilder);
            relationBuilder.curRelationName = "";
            return relationBuilder;
        }

        public RelationBuilder newRefDefinedRelation(IRelationPattern relation){
            NodePattern node = new NodePattern(curNodeName, curNodeLabels, curNodeProperties);
            patternBuilder.patternElements.add(node);
            RelationBuilder relationBuilder = new RelationBuilder(patternBuilder);
            relationBuilder.curRelationName = relation.getName();
            return relationBuilder;
        }

        public IPattern build(){
            NodePattern node = new NodePattern(curNodeName, curNodeLabels, curNodeProperties);
            patternBuilder.patternElements.add(node);
            RelationBuilder relationBuilder = new RelationBuilder(patternBuilder);
            return new Pattern(patternBuilder.patternElements);
        }
    }

    public static class RelationBuilder {
        private PatternBuilder patternBuilder;
        private String curRelationName = "";
        private IType curRelationType = null;
        private Direction curDirection = Direction.BOTH;
        private List<IProperty> curRelationProperties;

        private RelationBuilder(PatternBuilder patternBuilder){
            this.patternBuilder = patternBuilder;
            curRelationProperties = new ArrayList<>();
        }

        public RelationBuilder withType(IType relationType){
            curRelationType = relationType;
            return this;
        }

        public RelationBuilder withDirection(Direction direction){
            curDirection =direction;
            return this;
        }

        public RelationBuilder withProperties(IProperty ...properties){
            curRelationProperties.addAll(Arrays.asList(properties));
            curRelationProperties = curRelationProperties.stream().distinct().collect(Collectors.toList());
            return this;
        }

        public NodeBuilder newNamedNode(){
            RelationPattern relation = new RelationPattern(curRelationName, curRelationType, curDirection, curRelationProperties);
            patternBuilder.patternElements.add(relation);
            return patternBuilder.newNamedNode();
        }

        public NodeBuilder newAnonymousNode(){
            RelationPattern relation = new RelationPattern(curRelationName, curRelationType, curDirection, curRelationProperties);
            patternBuilder.patternElements.add(relation);
            return patternBuilder.newAnonymousNode();
        }

        public NodeBuilder newRefDefinedNode(INodePattern node){
            RelationPattern relation = new RelationPattern(curRelationName, curRelationType, curDirection, curRelationProperties);
            patternBuilder.patternElements.add(relation);
            return patternBuilder.newRefDefinedNode(node);
        }
    }

    public NodeBuilder newNamedNode(){
        NodeBuilder builder = new NodeBuilder(this);
        builder.curNodeName = identifierBuilder.getNewNodeName();
        return builder;
    }

    public NodeBuilder newAnonymousNode(){
        NodeBuilder builder = new NodeBuilder(this);
        builder.curNodeName = "";
        return builder;
    }

    public NodeBuilder newRefDefinedNode(INodePattern node){
        NodeBuilder builder = new NodeBuilder(this);
        builder.curNodeName = node.getName();
        return builder;
    }
}
