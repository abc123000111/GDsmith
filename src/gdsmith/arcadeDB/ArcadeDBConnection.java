package gdsmith.arcadeDB;

import com.arcadedb.database.Database;
import gdsmith.cypher.CypherConnection;
import java.sql.Connection;
import java.sql.Statement;

public class ArcadeDBConnection extends CypherConnection {

    private Database database;

    public ArcadeDBConnection(Database database){
        this.database = database;
    }

    @Override
    public String getDatabaseVersion() throws Exception {
        return "";
    }

    @Override
    public void close() throws Exception {
        database.close();
    }

    @Override
    public void executeStatement(String arg) throws Exception{
        try {
            database.begin();
            database.query("CYPHER", arg);
            database.commit();

        } catch (Exception e) {
            database.rollback();
        } finally {
            database.close();
        }
    }
}
