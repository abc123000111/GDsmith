package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.*;

import java.util.ArrayList;
import java.util.List;

public class Symtab implements ICypherSymtab {

    private final ICypherClause parentClause;
    private boolean extendParent = false;

    private List<IPattern> patterns = new ArrayList<>();
    private List<IRet> aliasDefinitions = new ArrayList<>();


    public Symtab(ICypherClause parentClause, boolean extendParent){
        this.parentClause = parentClause;
        this.extendParent = extendParent;
    }


    @Override
    public List<IPattern> getPatterns() {
        return patterns;
    }

    @Override
    public void setPatterns(List<IPattern> patterns) {
        this.patterns = patterns;
    }

    @Override
    public List<IRet> getAliasDefinitions() {
        return aliasDefinitions;
    }

    @Override
    public void setAliasDefinition(List<IRet> aliasDefinitions) {
        this.aliasDefinitions = aliasDefinitions;
    }


    @Override
    public List<INodeIdentifier> getLocalNodePatterns() {
        List<INodeIdentifier> nodes = new ArrayList<>();

        for(IPattern pattern : patterns){
            for(IPatternElement patternElement : pattern.getPatternElements()){
                if(patternElement instanceof INodeIdentifier && !patternElement.isAnonymous()){
                    nodes.add((INodeIdentifier) patternElement);
                }
            }
        }

        return nodes;
    }

    @Override
    public List<IRelationIdentifier> getLocalRelationPatterns() {
        List<IRelationIdentifier> relations = new ArrayList<>();

        for(IPattern pattern : patterns){
            for(IPatternElement patternElement : pattern.getPatternElements()){
                if(patternElement instanceof IRelationIdentifier && !patternElement.isAnonymous()){
                    relations.add((IRelationIdentifier) patternElement);
                }
            }
        }

        return relations;
    }

    @Override
    public List<INodeIdentifier> getAvailableNodePatterns() {
        if(!extendParent || parentClause.getPrevClause() == null){
            return getLocalNodePatterns();
        }
        List<INodeIdentifier> nodes = getLocalNodePatterns();
        for(INodeIdentifier node : nodes){
            if(node instanceof NodeIdentifier){
                ((NodeIdentifier) node).setFormerDef(null);
            }
        }
        List<INodeIdentifier> extendedNodes = parentClause.getPrevClause().getAvailableNodeIdentifiers();
        for(INodeIdentifier extendedNode : extendedNodes){
            if(!nodes.contains(extendedNode)){
                nodes.add(extendedNode);
            }
            else {
                INodeIdentifier node = nodes.get(nodes.indexOf(extendedNode));
                ((NodeIdentifier)node).setFormerDef(extendedNode);
            }
        }
        return nodes;
    }

    @Override
    public List<IRelationIdentifier> getAvailableRelationPatterns() {
        if(!extendParent || parentClause.getPrevClause() == null){
            return getLocalRelationPatterns();
        }
        List<IRelationIdentifier> relations = getLocalRelationPatterns();
        for(IRelationIdentifier relation : relations){
            if(relation instanceof RelationIdentifier){
                ((RelationIdentifier) relation).setFormerDef(null);
            }
        }
        List<IRelationIdentifier> extendedRelations = parentClause.getPrevClause().getAvailableRelationIdentifiers();
        for(IRelationIdentifier extendedRelation : extendedRelations){
            if(!relations.contains(extendedRelation)){
                relations.add(extendedRelation);
            }
            else {
                IRelationIdentifier relationPattern = relations.get(relations.indexOf(extendedRelation));
                ((RelationIdentifier)relationPattern).setFormerDef(extendedRelation);
            }
        }
        return relations;
    }

}
