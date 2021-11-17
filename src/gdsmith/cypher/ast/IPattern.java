package gdsmith.cypher.ast;

import org.neo4j.cypherdsl.core.PatternElement;

import java.util.List;

public interface IPattern extends ITextRepresentation{
    List<IPatternElement> getPatternElements();
}