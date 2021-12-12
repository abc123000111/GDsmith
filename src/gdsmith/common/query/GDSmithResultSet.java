package gdsmith.common.query;


import java.io.Closeable;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;


public class GDSmithResultSet implements Closeable {

    int resultRowNum;

    public GDSmithResultSet(org.neo4j.driver.Result rs) {
        resultRowNum = rs.list().size();
    }

    public GDSmithResultSet(ResultSet rs) throws SQLException {
        resultRowNum = rs.getRow();
    }

    public GDSmithResultSet(com.redislabs.redisgraph.ResultSet rs) throws SQLException {
        resultRowNum = rs.size();
    }


    public int getRowNum() {
        return resultRowNum;
    }

    @Override
    public void close() {

    }

    public void registerEpilogue(Runnable runnableEpilogue) {


    }

    public boolean next() throws SQLException {
        return true;
    }

    public int getInt(int i) throws SQLException {
        return 1;
    }

    public String getString(int i) throws SQLException {
        return "zzz";
    }

    public long getLong(int i) throws SQLException {
        return 1;
    }


    public boolean isClosed() throws SQLException {
        return true;
    }

}
