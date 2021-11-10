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
    public List<IAlias> getLocalAliasDefs() {
        if(parentClause == null){
            return null;
        }
        List<IAlias> aliases = new ArrayList<>();
        if(parentClause instanceof IWith || parentClause instanceof IReturn){
            for(IRet aliasDef : aliasDefinitions){
                if(aliasDef.isAlias()){
                    aliases.add((IAlias) aliasDef.getIdentifier());
                }
                if(aliasDef.isAll()){
                    return parentClause.getPrevClause().getAvailableAliases();
                }
            }
            linkAliasDefinitions(aliases);
        }
        return aliases;
    }

    @Override
    public List<IAlias> getAvailableAliasDefs() {
        if(!extendParent || parentClause.getPrevClause() == null){
            return getLocalAliasDefs();
        }
        List<IAlias> aliases = getLocalAliasDefs();
        for(IAlias alias : aliases){
            if(alias instanceof Alias){
                ((NodeIdentifier) alias).setFormerDef(null);
            }
        }
        List<IAlias> extendedAliases = parentClause.getPrevClause().getAvailableAliases();
        for(IAlias extendedAlias : extendedAliases){
            if(!aliases.contains(extendedAlias)){
                aliases.add(extendedAlias);
            }
            else{
                IAlias alias = aliases.get(aliases.indexOf(extendedAlias));
                if(alias.getExpression() == null){
                    ((Alias)alias).setFormerDef(extendedAlias);
                }
            }
        }
        return aliases;
    }


    private void linkNodeDefinitions(List<INodeIdentifier> curNodes){
        if(parentClause == null){
            return;
        }

        List<INodeIdentifier> prevDefs = new ArrayList<>();
        if(parentClause.getPrevClause() != null){
            prevDefs = parentClause.getPrevClause().getAvailableNodeIdentifiers();
        }

        for(INodeIdentifier node : curNodes){
            if(node instanceof NodeIdentifier){
                ((NodeIdentifier) node).setFormerDef(null);
            }
        }
        for(INodeIdentifier prevDef : prevDefs){
            if(curNodes.contains(prevDef)){
                INodeIdentifier node = curNodes.get(curNodes.indexOf(prevDef));
                if(node instanceof NodeIdentifier){
                    ((NodeIdentifier)node).setFormerDef(prevDef);
                }
            }
        }
    }

    private void linkRelationDefinitions(List<IRelationIdentifier> curRelations){
        if(parentClause == null){
            return;
        }

        List<IRelationIdentifier> prevDefs = new ArrayList<>();
        if(parentClause.getPrevClause() != null){
            prevDefs = parentClause.getPrevClause().getAvailableRelationIdentifiers();
        }

        for(IRelationIdentifier relation : curRelations){
            if(relation instanceof RelationIdentifier){
                ((RelationIdentifier) relation).setFormerDef(null);
            }
        }
        for(IRelationIdentifier prevDef : prevDefs){
            if(curRelations.contains(prevDef)){
                IRelationIdentifier relation = curRelations.get(curRelations.indexOf(prevDef));
                if(relation instanceof RelationIdentifier){
                    ((RelationIdentifier)relation).setFormerDef(prevDef);
                }
            }
        }
    }

    private void linkAliasDefinitions(List<IAlias> curAlias){
        if(parentClause == null){
            return;
        }

        List<IAlias> prevDefs = new ArrayList<>();
        if(parentClause.getPrevClause() != null){
            prevDefs = parentClause.getPrevClause().getAvailableAliases();
        }

        for(IAlias alias : curAlias){
            if(alias instanceof Alias){
                ((Alias) alias).setFormerDef(null);
            }
        }
        for(IAlias prevDef : prevDefs){
            if(curAlias.contains(prevDef)){
                IAlias alias = curAlias.get(curAlias.indexOf(prevDef));
                if(alias instanceof Alias && alias.getExpression() == null){
                    ((Alias)alias).setFormerDef(prevDef);
                }
            }
        }
    }

    @Override
    public List<INodeIdentifier> getLocalNodePatterns() {
        if(parentClause == null)
            return null;

        List<INodeIdentifier> nodes = new ArrayList<>();


        if(parentClause instanceof IMatch){
            for(IPattern pattern : patterns){
                for(IPatternElement patternElement : pattern.getPatternElements()){
                    if(patternElement instanceof INodeIdentifier && !patternElement.isAnonymous()){
                        nodes.add((INodeIdentifier) patternElement);
                    }
                }
            }
            linkNodeDefinitions(nodes);
            return nodes;
        }


        if(parentClause instanceof IWith || parentClause instanceof IReturn){
            for(IRet aliasDef : aliasDefinitions){
                if(aliasDef.isNodeIdentifier()){
                    nodes.add((INodeIdentifier) aliasDef.getIdentifier());
                }
                if(aliasDef.isAll() && parentClause.getPrevClause() != null){
                    return parentClause.getPrevClause().getAvailableNodeIdentifiers();
                }
            }
            linkNodeDefinitions(nodes);
        }

        return nodes;
    }

    @Override
    public List<IRelationIdentifier> getLocalRelationPatterns() {
        if(parentClause == null)
            return null;

        List<IRelationIdentifier> relations = new ArrayList<>();

        List<IRelationIdentifier> extendedRelations = new ArrayList<>();
        if(parentClause.getPrevClause() != null){
            extendedRelations = parentClause.getPrevClause().getAvailableRelationIdentifiers();
        }

        if(parentClause instanceof IMatch){
            for(IPattern pattern : patterns){
                for(IPatternElement patternElement : pattern.getPatternElements()){
                    if(patternElement instanceof IRelationIdentifier && !patternElement.isAnonymous()){
                        relations.add((IRelationIdentifier) patternElement);
                    }
                }
            }
            linkRelationDefinitions(relations);
            return relations;
        }

        if(parentClause instanceof IWith || parentClause instanceof IReturn){
            for(IRet aliasDef : aliasDefinitions){
                if(aliasDef.isRelationIdentifier()){
                    relations.add((IRelationIdentifier) aliasDef.getIdentifier());
                }
                if(aliasDef.isAll() && parentClause.getPrevClause() != null){
                    return parentClause.getPrevClause().getAvailableRelationIdentifiers();
                }
            }
            linkRelationDefinitions(relations);
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
