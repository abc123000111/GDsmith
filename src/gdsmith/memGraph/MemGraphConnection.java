package gdsmith.memGraph;

import gdsmith.common.query.GDSmithResultSet;
import gdsmith.cypher.CypherConnection;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;

import java.util.Arrays;
import java.util.List;

public class MemGraphConnection extends CypherConnection {

    private Driver driver;

    public MemGraphConnection(Driver driver){
        this.driver = driver;
    }


    @Override
    public String getDatabaseVersion() throws Exception {
        //todo complete
        return "";
    }

    @Override
    public void close() throws Exception {
        MemGraphDriverManager.closeDriver(driver);
    }

    @Override
    public void executeStatement(String arg) throws Exception{
        try ( Session session = driver.session() )
        {
            String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    tx.run(arg);
                    return "";
                }
            } );
            //System.out.println( greeting );
        }
    }

    @Override
    public List<GDSmithResultSet> executeStatementAndGet(String arg) throws Exception{
        try ( Session session = driver.session() )
        {
            return Arrays.asList(new GDSmithResultSet(session.run(arg)));
        }
    }
}
