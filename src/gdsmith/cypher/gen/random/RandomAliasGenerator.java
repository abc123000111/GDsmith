package gdsmith.cypher.gen.random;

import gdsmith.Randomly;
import gdsmith.cypher.ast.*;
import gdsmith.cypher.ast.analyzer.*;
import gdsmith.cypher.schema.CypherSchema;
import gdsmith.cypher.standard_ast.*;
import gdsmith.cypher.standard_ast.expr.ConstExpression;
import gdsmith.cypher.standard_ast.expr.GetPropertyExpression;
import gdsmith.cypher.standard_ast.expr.IdentifierExpression;
import gdsmith.cypher.dsl.BasicAliasGenerator;
import gdsmith.cypher.dsl.IIdentifierBuilder;
import gdsmith.cypher.schema.IPropertyInfo;
import gdsmith.neo4j.schema.Neo4jSchema;

import java.util.ArrayList;
import java.util.List;

public class RandomAliasGenerator<S extends CypherSchema<?,?>> extends BasicAliasGenerator<S> {
    public RandomAliasGenerator(S schema, IIdentifierBuilder identifierBuilder) {
        super(schema, identifierBuilder);
    }

    @Override
    public List<IRet> generateReturnAlias(IReturnAnalyzer returnClause, IIdentifierBuilder identifierBuilder, S schema) {
        List<IRet> results = new ArrayList<>();
        List<INodeAnalyzer> idNode = returnClause.getExtendableNodeIdentifiers();
        List<IRelationAnalyzer> idRelation = returnClause.getExtendablePatternIdentifiers();
        List<IAliasAnalyzer> idAlias = returnClause.getExtendableAliases();
        Randomly r = new Randomly();
        int sizeOfAlias = idAlias.size();
        int sizeOfNode = idNode.size();
        int sizeOfRelation = idRelation.size();

        /*int numOfExpressions = Randomly.smallNumber();
        if (numOfExpressions == 0 || numOfExpressions >= 3) {
            numOfExpressions = 1;
        }*/

        int numOfExpressions = r.getInteger(1, 5);
        ArrayList<IExpression> orderByExpression = new ArrayList<>();

        for (int i = 0; i < numOfExpressions; i++) {
            Ret result = null;
            if (i == 0) {
                int kind = r.getInteger(0, 6);
                if (kind == 0) {
                    if (sizeOfAlias > 0) {
                        IAliasAnalyzer alias = idAlias.get(r.getInteger(0, sizeOfAlias - 1));
                        result = Ret.createAliasRef(alias);
                        orderByExpression.add(new IdentifierExpression(result.getIdentifier()));
                    }
                } else if (kind == 1) {
                    if (sizeOfNode > 0) {
                        INodeAnalyzer node = idNode.get(r.getInteger(0, sizeOfNode - 1));
                        result = Ret.createNodeRef(node);
                        List<IPropertyInfo> props = node.getAllPropertiesAvailable(schema);
                        for(int j = 0; j < props.size(); j++) {
                            IPropertyInfo prop = props.get(i);
                            IdentifierExpression ie = new IdentifierExpression(node);
                            GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                            orderByExpression.add(exp);
                        }
                    }
                } else if (kind == 2) {
                    if (sizeOfRelation > 0) {
                        IRelationAnalyzer relation = idRelation.get(r.getInteger(0, sizeOfRelation - 1));
                        result = Ret.createRelationRef(relation);
                        if (relation.isSingleRelation()) {
                            //result = Ret.createRelationRef(relation);//todo
                            List<IPropertyInfo> props = relation.getAllPropertiesAvailable(schema);
                            for(int j = 0; j < props.size(); j++) {
                                IPropertyInfo prop = props.get(i);
                                IdentifierExpression ie = new IdentifierExpression(relation);
                                GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                                orderByExpression.add(exp);
                            }
                        }
                    }
                } else if (kind == 3) {
                    if (sizeOfNode > 0) {
                        INodeAnalyzer node = idNode.get(r.getInteger(0, sizeOfNode - 1));
                        List<IPropertyInfo> props = node.getAllPropertiesAvailable(schema);
                        if (props.size() > 0) {
                            IPropertyInfo prop = props.get(r.getInteger(0, props.size() - 1));
                            IdentifierExpression ie = new IdentifierExpression(node);
                            GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                            result = Ret.createNewExpressionAlias(identifierBuilder, exp);
                            orderByExpression.add(new IdentifierExpression(result.getIdentifier()));
                        }
                    }
                } else if (kind == 4) {
                    if (sizeOfRelation > 0) {
                        IRelationAnalyzer relation = idRelation.get(r.getInteger(0, sizeOfRelation - 1));
                        if (relation.isSingleRelation()) {
                            List<IPropertyInfo> props = relation.getAllPropertiesAvailable(schema);
                            if (props.size() > 0) {
                                IPropertyInfo prop = props.get(r.getInteger(0, props.size() - 1));
                                IdentifierExpression ie = new IdentifierExpression(relation);
                                GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                                result = Ret.createNewExpressionAlias(identifierBuilder, exp);
                                orderByExpression.add(new IdentifierExpression(result.getIdentifier()));
                            }
                        }
                    }
                } else if (kind == 5) {
                    CypherType type = Randomly.fromOptions(CypherType.NUMBER, CypherType.STRING, CypherType.BOOLEAN, CypherType.NODE, CypherType.RELATION);
                    result = Ret.createNewExpressionAlias(identifierBuilder, 
                            new RandomExpressionGenerator<>(returnClause, schema).generateFunction(type));
                    orderByExpression.add(new IdentifierExpression(result.getIdentifier()));
                } else {
                    result = Ret.createStar();
                }
            } else {
                int kind = r.getInteger(0, 5);
                if (kind == 0) {
                    if (sizeOfAlias > 0) {
                        IAliasAnalyzer alias = idAlias.get(r.getInteger(0, sizeOfAlias - 1));
                        result = Ret.createAliasRef(alias);
                        orderByExpression.add(new IdentifierExpression(result.getIdentifier()));
                    }
                } else if (kind == 1) {
                    if (sizeOfNode > 0) {
                        INodeAnalyzer node = idNode.get(r.getInteger(0, sizeOfNode - 1));
                        result = Ret.createNodeRef(node);
                        List<IPropertyInfo> props = node.getAllPropertiesAvailable(schema);
                        for(int j = 0; j < props.size(); j++) {
                            IPropertyInfo prop = props.get(i);
                            IdentifierExpression ie = new IdentifierExpression(node);
                            GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                            orderByExpression.add(exp);
                        }
                    }
                } else if (kind == 2) {
                    if (sizeOfRelation > 0) {
                        IRelationAnalyzer relation = idRelation.get(r.getInteger(0, sizeOfRelation - 1));
                        result = Ret.createRelationRef(relation);
                        if (relation.isSingleRelation()) {
                            //result = Ret.createRelationRef(relation);//todo
                            List<IPropertyInfo> props = relation.getAllPropertiesAvailable(schema);
                            for(int j = 0; j < props.size(); j++) {
                                IPropertyInfo prop = props.get(i);
                                IdentifierExpression ie = new IdentifierExpression(relation);
                                GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                                orderByExpression.add(exp);
                            }
                        }
                    }
                } else if (kind == 3) {
                    if (sizeOfNode > 0) {
                        INodeAnalyzer node = idNode.get(r.getInteger(0, sizeOfNode - 1));
                        List<IPropertyInfo> props = node.getAllPropertiesAvailable(schema);
                        if (props.size() > 0) {
                            IPropertyInfo prop = props.get(r.getInteger(0, props.size() - 1));
                            IdentifierExpression ie = new IdentifierExpression(node);
                            GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                            result = Ret.createNewExpressionAlias(identifierBuilder, exp);
                            orderByExpression.add(new IdentifierExpression(result.getIdentifier()));
                        }
                    }
                } else if (kind == 4){
                    if (sizeOfRelation > 0) {
                        IRelationAnalyzer relation = idRelation.get(r.getInteger(0, sizeOfRelation - 1));
                        if (relation.isSingleRelation()) {
                            List<IPropertyInfo> props = relation.getAllPropertiesAvailable(schema);
                            if (props.size() > 0) {
                                IPropertyInfo prop = props.get(r.getInteger(0, props.size() - 1));
                                IdentifierExpression ie = new IdentifierExpression(relation);
                                GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                                result = Ret.createNewExpressionAlias(identifierBuilder, exp);
                                orderByExpression.add(new IdentifierExpression(result.getIdentifier()));
                            }
                        }
                    }
                } else {
                    CypherType type = Randomly.fromOptions(CypherType.NUMBER, CypherType.STRING, CypherType.BOOLEAN, CypherType.NODE, CypherType.RELATION);
                    result = Ret.createNewExpressionAlias(identifierBuilder, 
                            new RandomExpressionGenerator<>(returnClause, schema).generateFunction(type));
                    orderByExpression.add(new IdentifierExpression(result.getIdentifier()));
                }
            }
            if (result != null) {
                boolean flag = true;
                for (IRet res: results) {
                    if (res.equals(result)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    results.add(result);
                }
            }
        }
        if (results.isEmpty()) {
            results.add(Ret.createNewExpressionReturnVal(new ConstExpression(Randomly.smallNumber())));
        }
        returnClause.setDistinct(Randomly.getBooleanWithRatherLowProbability());
        if (Randomly.getBooleanWithRatherLowProbability()) {
            returnClause.setLimit(new ConstExpression(Randomly.smallNumber()));
        }
        if (Randomly.getBooleanWithRatherLowProbability()) {
            returnClause.setSkip(new ConstExpression(Randomly.smallNumber()));
        }
        if (Randomly.getBooleanWithRatherLowProbability()) {
            int numOfOrderBy = r.getInteger(1, results.size());
            while (orderByExpression.size() > numOfOrderBy) {
                orderByExpression.remove(r.getInteger(0, orderByExpression.size() - 1));
            }
            if (orderByExpression.size() > 0) {
                returnClause.setOrderBy(orderByExpression, Randomly.getBoolean());
            }
        }
        return results;
    }

    @Override
    public List<IRet> generateWithAlias(IWithAnalyzer withClause, IIdentifierBuilder identifierBuilder, S schema) {
        List<IRet> withAlias = withClause.getReturnList();
        if (withAlias.size() > 0) {
            return withAlias;
        }

        List<IRet> results = new ArrayList<>();
        List<INodeAnalyzer> idNode = withClause.getExtendableNodeIdentifiers();
        List<IRelationAnalyzer> idRelation = withClause.getExtendablePatternIdentifiers();
        List<IAliasAnalyzer> idAlias = withClause.getExtendableAliases();
        Randomly r = new Randomly();
        int sizeOfAlias = idAlias.size();
        int sizeOfNode = idNode.size();
        int sizeOfRelation = idRelation.size();
        /*int numOfExpressions = Randomly.smallNumber();
        if (numOfExpressions == 0 || numOfExpressions >= 3) {
            numOfExpressions = 1;
        }*/
        int numOfExpressions = r.getInteger(1, 5);
        ArrayList<IExpression> orderByExpression = new ArrayList<>();

        for (int i = 0; i < numOfExpressions; i++) {
            Ret result = null;
            if (i == 0) {
                int kind = r.getInteger(0, 6);
                if (kind == 0) {
                    if (sizeOfAlias > 0) {
                        IAliasAnalyzer alias = idAlias.get(r.getInteger(0, sizeOfAlias - 1));
                        result = Ret.createAliasRef(alias);
                        orderByExpression.add(new IdentifierExpression(result.getIdentifier()));
                    }
                } else if (kind == 1) {
                    if (sizeOfNode > 0) {
                        INodeAnalyzer node = idNode.get(r.getInteger(0, sizeOfNode - 1));
                        result = Ret.createNodeRef(node);
                        List<IPropertyInfo> props = node.getAllPropertiesAvailable(schema);
                        for(int j = 0; j < props.size(); j++) {
                            IPropertyInfo prop = props.get(i);
                            IdentifierExpression ie = new IdentifierExpression(node);
                            GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                            orderByExpression.add(exp);
                        }
                    }
                } else if (kind == 2) {
                    if (sizeOfRelation > 0) {
                        IRelationAnalyzer relation = idRelation.get(r.getInteger(0, sizeOfRelation - 1));
                        result = Ret.createRelationRef(relation);
                        if (relation.isSingleRelation()) {
                            List<IPropertyInfo> props = relation.getAllPropertiesAvailable(schema);
                            for(int j = 0; j < props.size(); j++) {
                                IPropertyInfo prop = props.get(i);
                                IdentifierExpression ie = new IdentifierExpression(relation);
                                GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                                orderByExpression.add(exp);
                            }
                        }
                    }
                } else if (kind == 3) {
                    if (sizeOfNode > 0) {
                        INodeAnalyzer node = idNode.get(r.getInteger(0, sizeOfNode - 1));
                        List<IPropertyInfo> props = node.getAllPropertiesAvailable(schema);
                        if (props.size() > 0) {
                            IPropertyInfo prop = props.get(r.getInteger(0, props.size() - 1));
                            IdentifierExpression ie = new IdentifierExpression(node);
                            GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                            result = Ret.createNewExpressionAlias(identifierBuilder, exp);
                            orderByExpression.add(new IdentifierExpression(result.getIdentifier()));
                        }
                    }
                } else if (kind == 4) {
                    if (sizeOfRelation > 0) {
                        IRelationAnalyzer relation = idRelation.get(r.getInteger(0, sizeOfRelation - 1));
                        if (relation.isSingleRelation()) {
                            List<IPropertyInfo> props = relation.getAllPropertiesAvailable(schema);
                            if (props.size() > 0) {
                                IPropertyInfo prop = props.get(r.getInteger(0, props.size() - 1));
                                IdentifierExpression ie = new IdentifierExpression(relation);
                                GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                                result = Ret.createNewExpressionAlias(identifierBuilder, exp);
                                orderByExpression.add(new IdentifierExpression(result.getIdentifier()));
                            }
                        }
                    }
                } else if (kind == 5) {
                    CypherType type = Randomly.fromOptions(CypherType.NUMBER, CypherType.STRING, CypherType.BOOLEAN, CypherType.NODE, CypherType.RELATION);
                    result = Ret.createNewExpressionAlias(identifierBuilder, 
                            new RandomExpressionGenerator<>(withClause, schema).generateFunction(type));
                    orderByExpression.add(new IdentifierExpression(result.getIdentifier()));
                } else {
                    result = Ret.createStar();
                }
            } else {
                int kind = r.getInteger(0, 5);
                if (kind == 0) {
                    if (sizeOfAlias > 0) {
                        IAliasAnalyzer alias = idAlias.get(r.getInteger(0, sizeOfAlias - 1));
                        result = Ret.createAliasRef(alias);
                        orderByExpression.add(new IdentifierExpression(result.getIdentifier()));
                    }
                } else if (kind == 1) {
                    if (sizeOfNode > 0) {
                        INodeAnalyzer node = idNode.get(r.getInteger(0, sizeOfNode - 1));
                        result = Ret.createNodeRef(node);
                        List<IPropertyInfo> props = node.getAllPropertiesAvailable(schema);
                        for(int j = 0; j < props.size(); j++) {
                            IPropertyInfo prop = props.get(i);
                            IdentifierExpression ie = new IdentifierExpression(node);
                            GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                            orderByExpression.add(exp);
                        }
                    }
                } else if (kind == 2) {
                    if (sizeOfRelation > 0) {
                        IRelationAnalyzer relation = idRelation.get(r.getInteger(0, sizeOfRelation - 1));
                        result = Ret.createRelationRef(relation);
                        if (relation.isSingleRelation()) {
                            List<IPropertyInfo> props = relation.getAllPropertiesAvailable(schema);
                            for(int j = 0; j < props.size(); j++) {
                                IPropertyInfo prop = props.get(i);
                                IdentifierExpression ie = new IdentifierExpression(relation);
                                GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                                orderByExpression.add(exp);
                            }
                        }
                    }
                } else if (kind == 3) {
                    if (sizeOfNode > 0) {
                        INodeAnalyzer node = idNode.get(r.getInteger(0, sizeOfNode - 1));
                        List<IPropertyInfo> props = node.getAllPropertiesAvailable(schema);
                        if (props.size() > 0) {
                            IPropertyInfo prop = props.get(r.getInteger(0, props.size() - 1));
                            IdentifierExpression ie = new IdentifierExpression(node);
                            GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                            result = Ret.createNewExpressionAlias(identifierBuilder, exp);
                            orderByExpression.add(new IdentifierExpression(result.getIdentifier()));
                        }
                    }
                } else if (kind == 4) {
                    if (sizeOfRelation > 0) {
                        IRelationAnalyzer relation = idRelation.get(r.getInteger(0, sizeOfRelation - 1));
                        if (relation.isSingleRelation()) {
                            List<IPropertyInfo> props = relation.getAllPropertiesAvailable(schema);
                            if (props.size() > 0) {
                                IPropertyInfo prop = props.get(r.getInteger(0, props.size() - 1));
                                IdentifierExpression ie = new IdentifierExpression(relation);
                                GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                                result = Ret.createNewExpressionAlias(identifierBuilder, exp);
                                orderByExpression.add(new IdentifierExpression(result.getIdentifier()));
                            }
                        }
                    }
                } else {
                    CypherType type = Randomly.fromOptions(CypherType.NUMBER, CypherType.STRING, CypherType.BOOLEAN, CypherType.NODE, CypherType.RELATION);
                    result = Ret.createNewExpressionAlias(identifierBuilder, 
                            new RandomExpressionGenerator<>(withClause, schema).generateFunction(type));
                    orderByExpression.add(new IdentifierExpression(result.getIdentifier()));
                }
            }
            if (result != null) {
                boolean flag = true;
                for (IRet res: results) {
                    if (res.equals(result)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    results.add(result);
                }
            }
        }
        if (results.isEmpty()) {
            results.add(Ret.createStar());
        }
        withClause.setDistinct(Randomly.getBooleanWithRatherLowProbability());
        if (Randomly.getBooleanWithRatherLowProbability()) {
            withClause.setLimit(new ConstExpression(Randomly.smallNumber()));
        }
        if (Randomly.getBooleanWithRatherLowProbability()) {
            withClause.setSkip(new ConstExpression(Randomly.smallNumber()));
        }
        if (Randomly.getBooleanWithRatherLowProbability()) {
            int numOfOrderBy = r.getInteger(1, results.size());
            while (orderByExpression.size() > numOfOrderBy) {
                orderByExpression.remove(r.getInteger(0, orderByExpression.size() - 1));
            }
            if (orderByExpression.size() > 0) {
                withClause.setOrderBy(orderByExpression, Randomly.getBoolean());
            }
        }
        return results;
    }
}