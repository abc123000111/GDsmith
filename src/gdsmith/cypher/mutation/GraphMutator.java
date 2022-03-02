package gdsmith.cypher.mutation;

import gdsmith.Randomly;
import gdsmith.cypher.ast.IClauseSequence;
import gdsmith.cypher.schema.CypherSchema;
import gdsmith.cypher.schema.IPropertyInfo;
import gdsmith.cypher.standard_ast.ClauseSequence;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GraphMutator <S extends CypherSchema<?,?>>{

    private S schema;
    private List<IPropertyInfo> propertyInfos;
    private IPropertyInfo propertyForDeletion;

    public GraphMutator(S schema) {
        this.schema = schema;
        propertyInfos = new ArrayList<>();
        schema.getLabels().stream().forEach(l->{
            propertyInfos.addAll(l.getProperties());
        });
        if(propertyInfos.size() != 0){
            propertyForDeletion = propertyInfos.get(new Randomly().getInteger(0, propertyInfos.size()));
        }
    }

    public List<ClauseSequence> mutate(List<ClauseSequence> clauseSequences){
        if(propertyForDeletion == null){
            return clauseSequences.stream().map(c->c.getCopy()).collect(Collectors.toList());
        }
        return clauseSequences.stream().map(clauseSequence -> {
            ClauseSequence result = clauseSequence.getCopy();
            GraphCreatingMutator mutator = new GraphCreatingMutator(result, schema, propertyForDeletion);
            mutator.startVisit();
            return result;
        }).collect(Collectors.toList());
    }
}
