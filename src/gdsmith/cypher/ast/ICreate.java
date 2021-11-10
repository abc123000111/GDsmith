package gdsmith.cypher.ast;

public interface ICreate extends ICypherClause{
    IPattern getPattern();
    void setPattern(IPattern pattern);
}
