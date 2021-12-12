package gdsmith.neo4j;

import gdsmith.common.query.GDSmithResultSet;
import gdsmith.cypher.CypherConnection;
import org.neo4j.driver.*;

public class Neo4jConnection extends CypherConnection {

    private static Driver driver;

    public Neo4jConnection(Driver driver){
        this.driver = driver;
    }


    @Override
    public String getDatabaseVersion() throws Exception {
        //todo complete
        return "";
    }

    @Override
    public void close() throws Exception {
        Neo4jDriverManager.closeDriver(driver);
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
            System.out.println( greeting );
        }
    }


    @Override
    public GDSmithResultSet executeStatementAndGet(String arg) throws Exception{
        try ( Session session = driver.session() )
        {
            return new GDSmithResultSet(session.run(arg));
        }
    }
}
