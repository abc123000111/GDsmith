package gdsmith;

public interface CypherQueryProvider<S> {
    CypherQueryAdapter getQuery(S globalState) throws Exception;
}
