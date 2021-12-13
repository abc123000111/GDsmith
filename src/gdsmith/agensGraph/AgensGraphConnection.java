package gdsmith.agensGraph;

import gdsmith.cypher.CypherConnection;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;

public class AgensGraphConnection extends CypherConnection {

    private Connection connection;

    public AgensGraphConnection(Connection connection){
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
