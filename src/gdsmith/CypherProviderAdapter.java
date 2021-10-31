package gdsmith;

import gdsmith.common.schema.AbstractSchema;

public abstract  class CypherProviderAdapter <G extends CypherGlobalState<O, ? extends AbstractSchema<G, ?>>, O extends DBMSSpecificOptions<? extends OracleFactory<G>>> extends ProviderAdapter<G, O, CypherConnection>{

    public CypherProviderAdapter(Class<G> globalClass, Class<O> optionClass) {
        super(globalClass, optionClass);
    }

}
