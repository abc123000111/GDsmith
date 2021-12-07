package gdsmith.neo4j.gen.random;

import gdsmith.Randomly;
import gdsmith.cypher.ast.*;
import gdsmith.cypher.ast.analyzer.*;
import gdsmith.neo4j.ast.Label;
import gdsmith.neo4j.ast.Neo4jType;
import gdsmith.neo4j.ast.Property;
import gdsmith.neo4j.ast.Ret;
import gdsmith.neo4j.ast.expr.ConstExpression;
import gdsmith.neo4j.ast.expr.GetPropertyExpression;
import gdsmith.neo4j.ast.expr.IdentifierExpression;
import gdsmith.neo4j.dsl.BasicAliasGenerator;
import gdsmith.neo4j.dsl.IIdentifierBuilder;
import gdsmith.neo4j.schema.IPropertyInfo;
import gdsmith.neo4j.schema.Neo4jSchema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RandomAliasGenerator extends BasicAliasGenerator {
    public RandomAliasGenerator(Neo4jSchema schema, IIdentifierBuilder identifierBuilder) {
        super(schema, identifierBuilder);
    }

    @Override
    public List<IRet> generateReturnAlias(IReturnAnalyzer returnClause, IIdentifierBuilder identifierBuilder, Neo4jSchema schema) {
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

        int numOfExpressions = r.getInteger(1, 4);

        for (int i = 0; i < numOfExpressions; i++) {
            Ret result = null;
            if (i == 0) {
                int kind = r.getInteger(0, 6);
                if (kind == 0) {
                    if (sizeOfAlias > 0) {
                        IAliasAnalyzer alias = idAlias.get(r.getInteger(0, sizeOfAlias - 1));
                        result = Ret.createAliasRef(alias);
                    }
                } else if (kind == 1) {
                    if (sizeOfNode > 0) {
                        INodeAnalyzer node = idNode.get(r.getInteger(0, sizeOfNode - 1));
                        result = Ret.createNodeRef(node);
                    }
                } else if (kind == 2) {
                    if (sizeOfRelation > 0) {
                        IRelationAnalyzer relation = idRelation.get(r.getInteger(0, sizeOfRelation - 1));
                        result = Ret.createRelationRef(relation);
                    }
                } else if (kind == 3) {
                    if (sizeOfNode > 0) {
                        INodeAnalyzer node = idNode.get(r.getInteger(0, sizeOfNode - 1));
                        List<ILabel> labels = node.getLabels();
                        if (labels.size() > 0) {
                            String label = labels.get(r.getInteger(0, labels.size() - 1)).getName();
                            List<IPropertyInfo> props = null;
                            for (Neo4jSchema.Neo4jLabelInfo l: schema.getLabels()) {
                                if (label == l.getName()) {
                                    props = l.getProperties();
                                    IPropertyInfo prop = props.get(r.getInteger(0, props.size() - 1));
                                    IdentifierExpression ie = new IdentifierExpression(node);
                                    GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                                    result = Ret.createNewExpressionReturnVal(exp);
                                    break;
                                }
                            }
                        }
                    }
                } else if (kind == 4) {
                    if (sizeOfRelation > 0) {
                        IRelationAnalyzer relation = idRelation.get(r.getInteger(0, sizeOfRelation - 1));
                        if (relation.getLengthLowerBound() == relation.getLengthUpperBound() && relation.getLengthLowerBound() == 1) {
                            List<IType> types = relation.getTypes();
                            if (types.size() > 0) {
                                IType type = types.get(r.getInteger(0, types.size() - 1));
                                if (type != null) {
                                    List<IPropertyInfo> props = null;
                                    for (Neo4jSchema.Neo4jRelationTypeInfo t : schema.getRelationTypes()) {
                                        if (type.getName() == t.getName()) {
                                            props = t.getProperties();
                                            IPropertyInfo prop = props.get(r.getInteger(0, props.size() - 1));
                                            IdentifierExpression ie = new IdentifierExpression(relation);
                                            GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                                            result = Ret.createNewExpressionReturnVal(exp);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (kind == 5) {
                    Neo4jType type = Randomly.fromOptions(Neo4jType.NUMBER, Neo4jType.STRING, Neo4jType.BOOLEAN, Neo4jType.NODE, Neo4jType.RELATION);
                    result = Ret.createNewExpressionReturnVal(new RandomExpressionGenerator(returnClause, schema).generateFunction(type));
                } else {
                    result = Ret.createStar();
                }
            } else {
                int kind = r.getInteger(0, 5);
                if (kind == 0) {
                    if (sizeOfAlias > 0) {
                        IAliasAnalyzer alias = idAlias.get(r.getInteger(0, sizeOfAlias - 1));
                        result = Ret.createAliasRef(alias);
                    }
                } else if (kind == 1) {
                    if (sizeOfNode > 0) {
                        INodeAnalyzer node = idNode.get(r.getInteger(0, sizeOfNode - 1));
                        result = Ret.createNodeRef(node);
                    }
                } else if (kind == 2) {
                    if (sizeOfRelation > 0) {
                        IRelationAnalyzer relation = idRelation.get(r.getInteger(0, sizeOfRelation - 1));
                        result = Ret.createRelationRef(relation);
                    }
                } else if (kind == 3) {
                    if (sizeOfNode > 0) {
                        INodeAnalyzer node = idNode.get(r.getInteger(0, sizeOfNode - 1));
                        List<ILabel> labels = node.getLabels();
                        if (labels.size() > 0) {
                            String label = labels.get(r.getInteger(0, labels.size() - 1)).getName();
                            List<IPropertyInfo> props = null;
                            for (Neo4jSchema.Neo4jLabelInfo l: schema.getLabels()) {
                                if (label == l.getName()) {
                                    props = l.getProperties();
                                    IPropertyInfo prop = props.get(r.getInteger(0, props.size() - 1));
                                    IdentifierExpression ie = new IdentifierExpression(node);
                                    GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                                    result = Ret.createNewExpressionReturnVal(exp);
                                    break;
                                }
                            }
                        }
                    }
                } else if (kind == 4) {
                    Neo4jType type = Randomly.fromOptions(Neo4jType.NUMBER, Neo4jType.STRING, Neo4jType.BOOLEAN, Neo4jType.NODE, Neo4jType.RELATION);
                    result = Ret.createNewExpressionReturnVal(new RandomExpressionGenerator(returnClause, schema).generateFunction(type));
                } else {
                    if (sizeOfRelation > 0) {
                        IRelationAnalyzer relation = idRelation.get(r.getInteger(0, sizeOfRelation - 1));
                        if (relation.getLengthLowerBound() == relation.getLengthUpperBound() && relation.getLengthLowerBound() == 1) {
                            List<IType> types = relation.getTypes();
                            if (types.size() > 0) {
                                IType type = types.get(r.getInteger(0, types.size() - 1));
                                if (type != null) {
                                    List<IPropertyInfo> props = null;
                                    for (Neo4jSchema.Neo4jRelationTypeInfo t : schema.getRelationTypes()) {
                                        if (type.getName() == t.getName()) {
                                            props = t.getProperties();
                                            IPropertyInfo prop = props.get(r.getInteger(0, props.size() - 1));
                                            IdentifierExpression ie = new IdentifierExpression(relation);
                                            GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                                            result = Ret.createNewExpressionReturnVal(exp);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
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
        if (Randomly.getBooleanWithRatherLowProbability() == true) {
            returnClause.setLimit(new ConstExpression(Randomly.smallNumber()));
        }
        if (Randomly.getBooleanWithRatherLowProbability() == true) {
            returnClause.setSkip(new ConstExpression(Randomly.smallNumber()));
        }
        if (Randomly.getBooleanWithRatherLowProbability() == true) {
            returnClause.setOrderBy(
                    Arrays.asList(results.get(r.getInteger(0, results.size() - 1)).getExpression()),
                    Randomly.getBoolean());

        }
        return results;
    }

    @Override
    public List<IRet> generateWithAlias(IWithAnalyzer withClause, IIdentifierBuilder identifierBuilder, Neo4jSchema schema) {
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
        int numOfExpressions = r.getInteger(1, 4);

        for (int i = 0; i < numOfExpressions; i++) {
            Ret result = null;
            if (i == 0) {
                int kind = r.getInteger(0, 6);
                if (kind == 0) {
                    if (sizeOfAlias > 0) {
                        IAliasAnalyzer alias = idAlias.get(r.getInteger(0, sizeOfAlias - 1));
                        result = Ret.createAliasRef(alias);
                    }
                } else if (kind == 1) {
                    if (sizeOfNode > 0) {
                        INodeAnalyzer node = idNode.get(r.getInteger(0, sizeOfNode - 1));
                        result = Ret.createNodeRef(node);
                    }
                } else if (kind == 2) {
                    if (sizeOfRelation > 0) {
                        IRelationAnalyzer relation = idRelation.get(r.getInteger(0, sizeOfRelation - 1));
                        result = Ret.createRelationRef(relation);
                    }
                } else if (kind == 3) {
                    if (sizeOfNode > 0) {
                        INodeAnalyzer node = idNode.get(r.getInteger(0, sizeOfNode - 1));
                        List<ILabel> labels = node.getLabels();
                        if (labels.size() > 0) {
                            String label = labels.get(r.getInteger(0, labels.size() - 1)).getName();
                            List<IPropertyInfo> props = null;
                            for (Neo4jSchema.Neo4jLabelInfo l: schema.getLabels()) {
                                if (label == l.getName()) {
                                    props = l.getProperties();
                                    IPropertyInfo prop = props.get(r.getInteger(0, props.size() - 1));
                                    IdentifierExpression ie = new IdentifierExpression(node);
                                    GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                                    result = Ret.createNewExpressionAlias(identifierBuilder, exp);
                                    break;
                                }
                            }
                        }
                    }
                } else if (kind == 4) {
                    if (sizeOfRelation > 0) {
                        IRelationAnalyzer relation = idRelation.get(r.getInteger(0, sizeOfRelation - 1));
                        if (relation.getLengthLowerBound() == relation.getLengthUpperBound() && relation.getLengthLowerBound() == 1) {
                            List<IType> types = relation.getTypes();
                            if (types.size() > 0) {
                                IType type = types.get(r.getInteger(0, types.size() - 1));
                                if (type != null) {
                                    List<IPropertyInfo> props = null;
                                    for (Neo4jSchema.Neo4jRelationTypeInfo t: schema.getRelationTypes()) {
                                        if (type.getName() == t.getName()) {
                                            props = t.getProperties();
                                            IPropertyInfo prop = props.get(r.getInteger(0, props.size() - 1));
                                            IdentifierExpression ie = new IdentifierExpression(relation);
                                            GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                                            result = Ret.createNewExpressionAlias(identifierBuilder, exp);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (kind == 5) {
                    Neo4jType type = Randomly.fromOptions(Neo4jType.NUMBER, Neo4jType.STRING, Neo4jType.BOOLEAN, Neo4jType.NODE, Neo4jType.RELATION);
                    result = Ret.createNewExpressionAlias(identifierBuilder, new RandomExpressionGenerator(withClause, schema).generateFunction(type));
                } else {
                    result = Ret.createStar();
                }
            } else {
                int kind = r.getInteger(0, 5);
                if (kind == 0) {
                    if (sizeOfAlias > 0) {
                        IAliasAnalyzer alias = idAlias.get(r.getInteger(0, sizeOfAlias - 1));
                        result = Ret.createAliasRef(alias);
                    }
                } else if (kind == 1) {
                    if (sizeOfNode > 0) {
                        INodeAnalyzer node = idNode.get(r.getInteger(0, sizeOfNode - 1));
                        result = Ret.createNodeRef(node);
                    }
                } else if (kind == 2) {
                    if (sizeOfRelation > 0) {
                        IRelationAnalyzer relation = idRelation.get(r.getInteger(0, sizeOfRelation - 1));
                        result = Ret.createRelationRef(relation);
                    }
                } else if (kind == 3) {
                    if (sizeOfNode > 0) {
                        INodeAnalyzer node = idNode.get(r.getInteger(0, sizeOfNode - 1));
                        List<ILabel> labels = node.getLabels();
                        if (labels.size() > 0) {
                            String label = labels.get(r.getInteger(0, labels.size() - 1)).getName();
                            List<IPropertyInfo> props = null;
                            for (Neo4jSchema.Neo4jLabelInfo l: schema.getLabels()) {
                                if (label == l.getName()) {
                                    props = l.getProperties();
                                    IPropertyInfo prop = props.get(r.getInteger(0, props.size() - 1));
                                    IdentifierExpression ie = new IdentifierExpression(node);
                                    GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                                    result = Ret.createNewExpressionAlias(identifierBuilder, exp);
                                    break;
                                }
                            }
                        }
                    }
                } else if (kind == 4) {
                    Neo4jType type = Randomly.fromOptions(Neo4jType.NUMBER, Neo4jType.STRING, Neo4jType.BOOLEAN, Neo4jType.NODE, Neo4jType.RELATION);
                    result = Ret.createNewExpressionAlias(identifierBuilder, new RandomExpressionGenerator(withClause, schema).generateFunction(type));
                } else {
                    if (sizeOfRelation > 0) {
                        IRelationAnalyzer relation = idRelation.get(r.getInteger(0, sizeOfRelation - 1));
                        if (relation.getLengthLowerBound() == relation.getLengthUpperBound() && relation.getLengthLowerBound() == 1) {
                            List<IType> types = relation.getTypes();
                            if (types.size() > 0) {
                                IType type = types.get(r.getInteger(0, types.size() - 1));
                                if (type != null) {
                                    List<IPropertyInfo> props = null;
                                    for (Neo4jSchema.Neo4jRelationTypeInfo t: schema.getRelationTypes()) {
                                        if (type.getName() == t.getName()) {
                                            props = t.getProperties();
                                            IPropertyInfo prop = props.get(r.getInteger(0, props.size() - 1));
                                            IdentifierExpression ie = new IdentifierExpression(relation);
                                            GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                                            result = Ret.createNewExpressionAlias(identifierBuilder, exp);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
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
        if (Randomly.getBooleanWithRatherLowProbability() == true) {
            withClause.setLimit(new ConstExpression(Randomly.smallNumber()));
        }
        if (Randomly.getBooleanWithRatherLowProbability() == true) {
            withClause.setSkip(new ConstExpression(Randomly.smallNumber()));
        }
        if (Randomly.getBooleanWithRatherLowProbability() == true) {
            withClause.setOrderBy(
                    Arrays.asList(results.get(r.getInteger(0, results.size() - 1)).getExpression()),
                    Randomly.getBoolean());
        }
        return results;
    }
}
