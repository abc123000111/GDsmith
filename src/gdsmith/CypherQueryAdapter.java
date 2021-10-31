package gdsmith;

import gdsmith.common.query.ExpectedErrors;
import gdsmith.common.query.Query;

public class CypherQueryAdapter extends Query<CypherConnection> {

    private final String query;

    public CypherQueryAdapter(String query) {
        this.query = query;
    }

    private String canonicalizeString(String s) {
        if (s.endsWith(";")) {
            return s;
        } else if (!s.contains("--")) {
            return s + ";";
        } else {
            // query contains a comment
            return s;
        }
    }

    @Override
    public String getLogString() {
        return "";
    }

    @Override
    public String getQueryString() {
        return "";
    }

    @Override
    public String getUnterminatedQueryString() {
        return "";
    }

    @Override
    public boolean couldAffectSchema() {
        return false;
    }

    @Override
    public <G extends GlobalState<?, ?, CypherConnection>> boolean execute(G globalState, String... fills) throws Exception {
        globalState.getConnection().executeStatement(query);
        System.out.println(query);
        return true;
    }

    @Override
    public ExpectedErrors getExpectedErrors() {
        //todo complete
        return null;
    }
}
