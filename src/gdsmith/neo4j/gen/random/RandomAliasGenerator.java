package gdsmith.neo4j.gen.random;

import gdsmith.Randomly;
import gdsmith.cypher.ast.IExpression;
import gdsmith.cypher.ast.ILabel;
import gdsmith.cypher.ast.IProperty;
import gdsmith.cypher.ast.IRet;
import gdsmith.cypher.ast.analyzer.*;
import gdsmith.neo4j.ast.Label;
import gdsmith.neo4j.ast.Property;
import gdsmith.neo4j.ast.Ret;
import gdsmith.neo4j.ast.expr.GetPropertyExpression;
import gdsmith.neo4j.ast.expr.IdentifierExpression;
import gdsmith.neo4j.dsl.BasicAliasGenerator;
import gdsmith.neo4j.dsl.IIdentifierBuilder;
import gdsmith.neo4j.schema.Neo4jSchema;

import java.util.ArrayList;
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
        int sizeOfNode = idAlias.size();
        int sizeOfRelation = idAlias.size();

        int numOfExpressions = Randomly.smallNumber();
        if (numOfExpressions == 0 || numOfExpressions >= 3) {
            numOfExpressions = 1;
        }

        for (int i = 0; i < numOfExpressions; i++) {
            Ret result = null;
            if (i == 0) {
                int type = r.getInteger(0, 5);
                if (type == 0) {
                    if (sizeOfAlias > 0) {
                        IAliasAnalyzer alias = idAlias.get(r.getInteger(0, sizeOfAlias - 1));
                        result = Ret.createAliasRef(alias);
                    }
                } else if (type == 1) {
                    if (sizeOfNode > 0) {
                        INodeAnalyzer node = idNode.get(r.getInteger(0, sizeOfNode - 1));
                        result = Ret.createNodeRef(node);
                    }
                } else if (type == 2) {
                    if (sizeOfRelation > 0) {
                        IRelationAnalyzer relation = idRelation.get(r.getInteger(0, sizeOfRelation - 1));
                        result = Ret.createRelationRef(relation);
                    }
                } else if (type == 3) {
                    if (sizeOfNode > 0) {
                        INodeAnalyzer node = idNode.get(r.getInteger(0, sizeOfNode - 1));
                        List<IProperty> props = node.getProperties();
                        IProperty prop = props.get(r.getInteger(0, props.size() - 1));
                        IdentifierExpression ie = new IdentifierExpression(node);
                        GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                        result = Ret.createNewExpressionReturnVal(exp);
                    }
                } else if (type == 4) {
                    if (sizeOfRelation > 0) {
                        IRelationAnalyzer relation = idRelation.get(r.getInteger(0, sizeOfRelation - 1));
                        List<IProperty> props = relation.getProperties();
                        IProperty prop = props.get(r.getInteger(0, props.size() - 1));
                        IdentifierExpression ie = new IdentifierExpression(relation);
                        GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                        result = Ret.createNewExpressionReturnVal(exp);
                    }
                } else {
                    result = Ret.createStar();
                }
            } else {
                int type = r.getInteger(0, 4);
                if (type == 0) {
                    if (sizeOfAlias > 0) {
                        IAliasAnalyzer alias = idAlias.get(r.getInteger(0, sizeOfAlias - 1));
                        result = Ret.createAliasRef(alias);
                    }
                } else if (type == 1) {
                    if (sizeOfNode > 0) {
                        INodeAnalyzer node = idNode.get(r.getInteger(0, sizeOfNode - 1));
                        result = Ret.createNodeRef(node);
                    }
                } else if (type == 2) {
                    if (sizeOfRelation > 0) {
                        IRelationAnalyzer relation = idRelation.get(r.getInteger(0, sizeOfRelation - 1));
                        result = Ret.createRelationRef(relation);
                    }
                } else if (type == 3) {
                    if (sizeOfNode > 0) {
                        INodeAnalyzer node = idNode.get(r.getInteger(0, sizeOfNode - 1));
                        List<IProperty> props = node.getProperties();
                        IProperty prop = props.get(r.getInteger(0, props.size() - 1));
                        IdentifierExpression ie = new IdentifierExpression(node);
                        GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                        result = Ret.createNewExpressionReturnVal(exp);
                    }
                } else {
                    if (sizeOfRelation > 0) {
                        IRelationAnalyzer relation = idRelation.get(r.getInteger(0, sizeOfRelation - 1));
                        List<IProperty> props = relation.getProperties();
                        IProperty prop = props.get(r.getInteger(0, props.size() - 1));
                        IdentifierExpression ie = new IdentifierExpression(relation);
                        GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                        result = Ret.createNewExpressionReturnVal(exp);
                    }
                }
            }
            if (result != null) {
                results.add(result);
            }
        }
        if (results.isEmpty()) {
            results.add(Ret.createStar());
        }
        return results;
    }

    @Override
    public List<IRet> generateWithAlias(IWithAnalyzer withClause, IIdentifierBuilder identifierBuilder, Neo4jSchema schema) {
        List<IRet> results = new ArrayList<>();
        List<INodeAnalyzer> idNode = withClause.getExtendableNodeIdentifiers();
        List<IRelationAnalyzer> idRelation = withClause.getExtendablePatternIdentifiers();
        List<IAliasAnalyzer> idAlias = withClause.getExtendableAliases();
        Randomly r = new Randomly();
        int sizeOfAlias = idAlias.size();
        int sizeOfNode = idAlias.size();
        int sizeOfRelation = idAlias.size();

        int numOfExpressions = Randomly.smallNumber();
        if (numOfExpressions == 0 || numOfExpressions >= 3) {
            numOfExpressions = 1;
        }

        for (int i = 0; i < numOfExpressions; i++) {
            Ret result = null;
            if (i == 0) {
                int type = r.getInteger(0, 5);
                if (type == 0) {
                    if (sizeOfAlias > 0) {
                        IAliasAnalyzer alias = idAlias.get(r.getInteger(0, sizeOfAlias - 1));
                        result = Ret.createAliasRef(alias);
                    }
                } else if (type == 1) {
                    if (sizeOfNode > 0) {
                        INodeAnalyzer node = idNode.get(r.getInteger(0, sizeOfNode - 1));
                        result = Ret.createNodeRef(node);
                    }
                } else if (type == 2) {
                    if (sizeOfRelation > 0) {
                        IRelationAnalyzer relation = idRelation.get(r.getInteger(0, sizeOfRelation - 1));
                        result = Ret.createRelationRef(relation);
                    }
                } else if (type == 3) {
                    if (sizeOfNode > 0) {
                        INodeAnalyzer node = idNode.get(r.getInteger(0, sizeOfNode - 1));
                        List<IProperty> props = node.getProperties();
                        IProperty prop = props.get(r.getInteger(0, props.size() - 1));
                        IdentifierExpression ie = new IdentifierExpression(node);
                        GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                        result = Ret.createNewExpressionAlias(identifierBuilder, exp);
                    }
                } else if (type == 4) {
                    if (sizeOfRelation > 0) {
                        IRelationAnalyzer relation = idRelation.get(r.getInteger(0, sizeOfRelation - 1));
                        List<IProperty> props = relation.getProperties();
                        IProperty prop = props.get(r.getInteger(0, props.size() - 1));
                        IdentifierExpression ie = new IdentifierExpression(relation);
                        GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                        result = Ret.createNewExpressionAlias(identifierBuilder, exp);
                    }
                } else {
                    result = Ret.createStar();
                }
            } else {
                int type = r.getInteger(0, 4);
                if (type == 0) {
                    if (sizeOfAlias > 0) {
                        IAliasAnalyzer alias = idAlias.get(r.getInteger(0, sizeOfAlias - 1));
                        result = Ret.createAliasRef(alias);
                    }
                } else if (type == 1) {
                    if (sizeOfNode > 0) {
                        INodeAnalyzer node = idNode.get(r.getInteger(0, sizeOfNode - 1));
                        result = Ret.createNodeRef(node);
                    }
                } else if (type == 2) {
                    if (sizeOfRelation > 0) {
                        IRelationAnalyzer relation = idRelation.get(r.getInteger(0, sizeOfRelation - 1));
                        result = Ret.createRelationRef(relation);
                    }
                } else if (type == 3) {
                    if (sizeOfNode > 0) {
                        INodeAnalyzer node = idNode.get(r.getInteger(0, sizeOfNode - 1));
                        List<IProperty> props = node.getProperties();
                        IProperty prop = props.get(r.getInteger(0, props.size() - 1));
                        IdentifierExpression ie = new IdentifierExpression(node);
                        GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                        result = Ret.createNewExpressionAlias(identifierBuilder, exp);
                    }
                } else {
                    if (sizeOfRelation > 0) {
                        IRelationAnalyzer relation = idRelation.get(r.getInteger(0, sizeOfRelation - 1));
                        List<IProperty> props = relation.getProperties();
                        IProperty prop = props.get(r.getInteger(0, props.size() - 1));
                        IdentifierExpression ie = new IdentifierExpression(relation);
                        GetPropertyExpression exp = new GetPropertyExpression(ie, prop.getKey());
                        result = Ret.createNewExpressionAlias(identifierBuilder, exp);
                    }
                }
            }
            if (result != null) {
                results.add(result);
            }
        }
        if (results.isEmpty()) {
            results.add(Ret.createStar());
        }
        return results;
    }
}
