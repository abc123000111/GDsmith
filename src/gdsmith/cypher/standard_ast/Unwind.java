package gdsmith.cypher.standard_ast;

import gdsmith.cypher.ast.*;
import gdsmith.cypher.ast.analyzer.IUnwindAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Unwind extends CypherClause implements IUnwindAnalyzer {

    public Unwind() {
        super(true);
    }

    @Override
    public IUnwindAnalyzer toAnalyzer() {
        return this;
    }

    @Override
    public ICypherClause getCopy() {
        Unwind unwind = new Unwind();
        unwind.setListAsAliasRet(getListAsAliasRet().getCopy());
        return unwind;
    }

    @Override
    public void toTextRepresentation(StringBuilder sb) {
        sb.append("UNWIND ");
        IRet listAsAlias = getListAsAliasRet();
        if(listAsAlias == null){
            sb.append("null");
        }
        else {
            listAsAlias.toTextRepresentation(sb);
        }
    }

    @Override
    public List<IPattern> getLocalPatternContainsIdentifier(IIdentifier identifier) {
        List<IPattern> patterns = symtab.getPatterns();
        List<IPattern> result = new ArrayList<>();
        for(IPattern pattern: patterns){
            for(IPatternElement element: pattern.getPatternElements()){
                if(element.equals(identifier)){
                    result.add(pattern);
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public IUnwind getSource() {
        return this;
    }

    @Override
    public IRet getListAsAliasRet() {
        if(symtab.getAliasDefinitions() == null || symtab.getAliasDefinitions().size() != 1){
            //todo 错误处理
            return null;
        }
        return symtab.getAliasDefinitions().get(0);
    }

    @Override
    public void setListAsAliasRet(IRet listAsAlias) {
        symtab.setAliasDefinition(Arrays.asList(listAsAlias));
    }
}