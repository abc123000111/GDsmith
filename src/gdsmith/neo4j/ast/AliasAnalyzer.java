package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.IAlias;
import gdsmith.cypher.ast.IExpression;
import gdsmith.cypher.ast.IPattern;
import gdsmith.cypher.ast.analyzer.IAliasAnalyzer;
import gdsmith.cypher.ast.analyzer.IClauseAnalyzer;

public class AliasAnalyzer extends Alias implements IAliasAnalyzer {
    IAliasAnalyzer formerDef = null;
    IClauseAnalyzer clauseAnalyzer;
    IExpression sourceExpression;
    IAlias source;

    AliasAnalyzer(IAlias alias, IClauseAnalyzer clauseAnalyzer){
        this(alias, clauseAnalyzer, null);
    }

    AliasAnalyzer(IAlias alias, IClauseAnalyzer clauseAnalyzer, IExpression sourceExpression){
        super(alias.getName(), alias.getExpression());
        this.source = alias;
        this.clauseAnalyzer = clauseAnalyzer;
        this.sourceExpression = sourceExpression;
    }


    @Override
    public IAliasAnalyzer getFormerDef() {
        return formerDef;
    }

    @Override
    public IClauseAnalyzer getClauseAnalyzer() {
        return clauseAnalyzer;
    }

    @Override
    public void setFormerDef(IAliasAnalyzer formerDef) {
        this.formerDef = formerDef;
    }

    @Override
    public IExpression getAliasDefExpression() {
        if(formerDef == null){
            return this.expression;
        }
        return formerDef.getAliasDefExpression();
    }

    @Override
    public IAlias getSource() {
        return source;
    }


    @Override
    public IExpression getSourceRefExpression() {
        return sourceExpression;
    }
}
