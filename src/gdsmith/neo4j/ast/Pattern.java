package gdsmith.neo4j.ast;

import gdsmith.cypher.ast.IPattern;
import gdsmith.cypher.ast.IPatternElement;
import org.neo4j.cypherdsl.core.PatternElement;

import java.util.List;

public class Pattern implements IPattern {
    private final List<IPatternElement> patternElements;

    public Pattern(List<IPatternElement> pattern){
        this.patternElements = pattern;
    }

    @Override
    public List<IPatternElement> getPatternElements() {
        return patternElements;
    }

    @Override
    public void toTextRepresentation(StringBuilder sb) {
        if(patternElements != null){
            for(IPatternElement element : patternElements){
                element.toTextRepresentation(sb);
            }
        }
    }
}
