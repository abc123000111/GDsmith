package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.*;
import gdsmith.cypher.ast.analyzer.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Return extends Neo4jClause implements IReturnAnalyzer {

    private List<IRet> returnList = new ArrayList<>();

    public Return(){
        super(false);
    }


    @Override
    public List<IRet> getReturnList() {
        return returnList;
    }

    @Override
    public void setReturnList(List<IRet> returnList) {
        this.returnList = returnList;
    }

    @Override
    public IReturnAnalyzer toAnalyzer() {
        return this;
    }

    @Override
    public ICypherClause getCopy() {
        Return returnClause = new Return();
        returnClause.returnList = returnList.stream().map(ret -> ret.getCopy()).collect(Collectors.toList());
        if(symtab != null){
            if(symtab != null){
                returnClause.symtab.setPatterns(symtab.getPatterns().stream().map(p->p.getCopy()).collect(Collectors.toList()));
                returnClause.symtab.setAliasDefinition(symtab.getAliasDefinitions().stream().map(a->a.getCopy()).collect(Collectors.toList()));
            }
        }
        return returnClause;
    }

    @Override
    public void toTextRepresentation(StringBuilder sb) {
        sb.append("RETURN ");
        List<IRet> returnList = getReturnList();
        for(int i = 0; i < returnList.size(); i++){
            returnList.get(i).toTextRepresentation(sb);
            if(i != returnList.size()-1){
                sb.append(", ");
            }
        }
    }

    @Override
    public List<IPattern> getLocalPatternContainsIdentifier(IIdentifier identifier) {
        return new ArrayList<>();
    }

    @Override
    public IReturn getSource() {
        return this;
    }
}
