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
    public List<INodePattern> getLocalNodePatterns() {
        List<INodePattern> nodes = new ArrayList<>();

        for(IPattern pattern : patterns){
            for(IPatternElement patternElement : pattern.getPatternElements()){
                if(patternElement instanceof INodePattern && !patternElement.isAnonymous()){
                    nodes.add((INodePattern) patternElement);
                }
            }
        }

        return nodes;
    }

    @Override
    public List<IRelationPattern> getLocalRelationPatterns() {
        List<IRelationPattern> relations = new ArrayList<>();

        for(IPattern pattern : patterns){
            for(IPatternElement patternElement : pattern.getPatternElements()){
                if(patternElement instanceof IRelationPattern && !patternElement.isAnonymous()){
                    relations.add((IRelationPattern) patternElement);
                }
            }
        }

        return relations;
    }

    @Override
    public List<INodePattern> getAvailableNodePatterns() {
        if(!extendParent || parentClause.getPrevClause() == null){
            return getLocalNodePatterns();
        }
        List<INodePattern> nodes = getLocalNodePatterns();
        for(INodePattern node : nodes){
            if(node instanceof NodePattern){
                ((NodePattern) node).setFormerDef(null);
            }
        }
        List<INodePattern> extendedNodes = parentClause.getPrevClause().getAvailableNodeIdentifiers();
        for(INodePattern extendedNode : extendedNodes){
            if(!nodes.contains(extendedNode)){
                nodes.add(extendedNode);
            }
            else {
                INodePattern node = nodes.get(nodes.indexOf(extendedNode));
                ((NodePattern)node).setFormerDef(extendedNode);
            }
        }
        return nodes;
    }

    @Override
    public List<IRelationPattern> getAvailableRelationPatterns() {
        if(!extendParent || parentClause.getPrevClause() == null){
            return getLocalRelationPatterns();
        }
        List<IRelationPattern> relations = getLocalRelationPatterns();
        for(IRelationPattern relation : relations){
            if(relation instanceof RelationPattern){
                ((RelationPattern) relation).setFormerDef(null);
            }
        }
        List<IRelationPattern> extendedRelations = parentClause.getPrevClause().getAvailableRelationIdentifiers();
        for(IRelationPattern extendedRelation : extendedRelations){
            if(!relations.contains(extendedRelation)){
                relations.add(extendedRelation);
            }
            else {
                IRelationPattern relationPattern = relations.get(relations.indexOf(extendedRelation));
                ((RelationPattern)relationPattern).setFormerDef(extendedRelation);
            }
        }
        return relations;
    }

}
