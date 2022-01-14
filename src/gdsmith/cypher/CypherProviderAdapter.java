package gdsmith.cypher;

import com.google.gson.JsonObject;
import gdsmith.DBMSSpecificOptions;
import gdsmith.MainOptions;
import gdsmith.OracleFactory;
import gdsmith.ProviderAdapter;
import gdsmith.common.schema.AbstractSchema;

public abstract  class CypherProviderAdapter <G extends CypherGlobalState<O, ? extends AbstractSchema<G, ?>>, O extends DBMSSpecificOptions<? extends OracleFactory<G>>> extends ProviderAdapter<G, O, CypherConnection> {

    public CypherProviderAdapter(Class<G> globalClass, Class<O> optionClass) {
        super(globalClass, optionClass);
    }

    public abstract O generateOptionsFromConfig(JsonObject config);
    public abstract CypherConnection createDatabaseWithOptions(MainOptions mainOptions, O specificOptions) throws Exception;

}
