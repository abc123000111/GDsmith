package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.*;

import java.util.ArrayList;
import java.util.List;

public class Symtab implements ICypherSymtab {

    private ICypherSymtab parentSymtab = null;
    private boolean extendParent = false;

    private List<IPatternTuple> patternTuples = new ArrayList<>();

    public Symtab(boolean extendParent){
        this.extendParent = extendParent;
    }

    public Symtab(ICypherSymtab parentSymtab, boolean extendParent){
        this.parentSymtab = parentSymtab;
        this.extendParent = extendParent;
    }


    @Override
    public List<IPatternTuple> getPatterns() {
        return patternTuples;
    }

    @Override
    public void deletePattern(IPatternTuple pattern) {

    }

    @Override
    public void addPattern(IPatternTuple pattern) {

    }

    @Override
    public List<INodePattern> getLocalNodePatterns() {
        List<INodePattern> nodes = new ArrayList<>();

        for(IPatternTuple patternTuple : patternTuples){
            for(IPattern pattern : patternTuple.getPatterns()){
                if(pattern instanceof INodePattern){
                    nodes.add((INodePattern) pattern);
                }

            }
        }

        return nodes;
    }

    @Override
    public List<IRelationPattern> getLocalRelationPatterns() {
        List<IRelationPattern> relations = new ArrayList<>();

        for(IPatternTuple patternTuple : patternTuples){
            for(IPattern pattern : patternTuple.getPatterns()){
                if(pattern instanceof IRelationPattern){
                    relations.add((IRelationPattern) pattern);
                }

            }
        }

        return relations;
    }

    @Override
    public List<INodePattern> getAvailableNodePatterns() {
        if(extendParent){
            return getLocalNodePatterns();
        }
        List<INodePattern> nodes = parentSymtab.getAvailableNodePatterns();
        for(IPatternTuple patternTuple : patternTuples){
            for(IPattern pattern : patternTuple.getPatterns()){
                if(pattern instanceof INodePattern && nodes.contains(pattern)){ //todo 是否应该覆盖equals
                    nodes.add((INodePattern) pattern);
                }
            }
        }
        return nodes;
    }

    @Override
    public List<IRelationPattern> getAvailableRelationPatterns() {
        if(extendParent){
            return getLocalRelationPatterns();
        }
        List<IRelationPattern> relations = parentSymtab.getAvailableRelationPatterns();
        for(IPatternTuple patternTuple : patternTuples){
            for(IPattern pattern : patternTuple.getPatterns()){
                if(pattern instanceof IRelationPattern && relations.contains(pattern)){ //todo 是否应该覆盖equals
                    relations.add((IRelationPattern) pattern);
                }
            }
        }
        return relations;
    }

    @Override
    public ICypherSymtab getParent() {
        return parentSymtab;
    }

    @Override
    public void setParent(ICypherSymtab parent) {
        this.parentSymtab = parent;
    }
}
