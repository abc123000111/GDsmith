package gdsmith.arcadeDB;

import gdsmith.cypher.CypherConnection;
import java.sql.Connection;
import java.sql.Statement;

public class ArcadeDBConnection extends CypherConnection {

    private Connection connection;

    public ArcadeDBConnection(Connection connection){
        this.connection = connection;
    }

    @Override
    public String getDatabaseVersion() throws Exception {
        return "";
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

    @Override
    public void executeStatement(String arg) throws Exception{
        Statement stmt = connection.createStatement();
        stmt.execute(arg);
    }
}
