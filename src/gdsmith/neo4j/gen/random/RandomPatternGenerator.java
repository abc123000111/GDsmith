package gdsmith.neo4j.gen.random;

import gdsmith.Randomly;
import gdsmith.cypher.ast.Direction;
import gdsmith.cypher.ast.ILabel;
import gdsmith.cypher.ast.IPattern;
import gdsmith.cypher.ast.IType;
import gdsmith.cypher.ast.analyzer.IMatchAnalyzer;
import gdsmith.cypher.ast.analyzer.INodeAnalyzer;
import gdsmith.cypher.standard_ast.Label;
import gdsmith.cypher.standard_ast.Pattern;
import gdsmith.cypher.standard_ast.RelationType;
import gdsmith.cypher.dsl.BasicPatternGenerator;
import gdsmith.cypher.dsl.IIdentifierBuilder;
import gdsmith.neo4j.schema.Neo4jSchema;

import java.util.ArrayList;
import java.util.List;

public class RandomPatternGenerator extends BasicPatternGenerator {

    public RandomPatternGenerator(Neo4jSchema schema, IIdentifierBuilder identifierBuilder) {
        super(schema, identifierBuilder);
    }

    @Override
    public List<IPattern> generatePattern(IMatchAnalyzer matchClause, IIdentifierBuilder identifierBuilder, Neo4jSchema schema) {
        List<IPattern> matchPattern = matchClause.getPatternTuple();
        if (matchPattern.size() > 0) {
            return matchPattern;
        }

        List<IPattern> patternTuple = new ArrayList<>();
        Randomly r = new Randomly();
        int sizeOfLabels = schema.getLabels().size();
        int sizeOfTypes = schema.getRelationTypes().size();

        /*int numOfPatterns = Randomly.smallNumber();
        if (numOfPatterns == 0 || numOfPatterns >= 3) {
            numOfPatterns = 1;
        }*/
        int numOfPatterns = Randomly.getBooleanWithRatherLowProbability() ? 2 : 1;
        matchClause.getSource().setOptional(Randomly.getBooleanWithRatherLowProbability());

        for (int i = 0; i < numOfPatterns; i++) {
            int lenOfPattern = Randomly.fromOptions(1, 3);
            if (lenOfPattern == 1) {
                boolean isNew = Randomly.getBoolean();
                if (isNew) {
                    boolean withLabel = Randomly.getBoolean();
                    //boolean isNamed = Randomly.getBoolean();
                    boolean isNamed = !Randomly.getBooleanWithSmallProbability();
                    if (withLabel) {
                        Neo4jSchema.Neo4jLabelInfo labelInfo = schema.getLabels().get(r.getInteger(0, sizeOfLabels - 1));
                        ILabel label = new Label(labelInfo.getName());
                        if (isNamed) {
                            patternTuple.add(new Pattern.PatternBuilder(identifierBuilder).newNamedNode().withLabels(label).build());
                        } else {
                            patternTuple.add(new Pattern.PatternBuilder(identifierBuilder).newAnonymousNode().withLabels(label).build());
                        }
                    } else {
                        if (isNamed) {
                            patternTuple.add(new Pattern.PatternBuilder(identifierBuilder).newNamedNode().build());
                        } else {
                            patternTuple.add(new Pattern.PatternBuilder(identifierBuilder).newAnonymousNode().build());
                        }
                    }
                } else {
                    List<INodeAnalyzer> idNode = matchClause.getExtendableNodeIdentifiers();
                    if (idNode.size() == 0) {
                        patternTuple.add(new Pattern.PatternBuilder(identifierBuilder).newNamedNode().build());
                    } else {
                        INodeAnalyzer node = idNode.get(r.getInteger(0, idNode.size() - 1));
                        patternTuple.add(new Pattern.PatternBuilder(identifierBuilder).newRefDefinedNode(node).build());
                    }
                }
            } else {
                Pattern.PatternBuilder.OngoingNode leftNode;
                boolean isNewLeft = Randomly.getBoolean();
                if (isNewLeft) {
                    boolean withLabelLeft = Randomly.getBoolean();
                    //boolean isNamedLeft = Randomly.getBoolean();
                    boolean isNamedLeft = !Randomly.getBooleanWithSmallProbability();
                    if (withLabelLeft) {
                        Neo4jSchema.Neo4jLabelInfo labelInfo = schema.getLabels().get(r.getInteger(0, sizeOfLabels - 1));
                        ILabel label = new Label(labelInfo.getName());
                        if (isNamedLeft) {
                            leftNode = new Pattern.PatternBuilder(identifierBuilder).newNamedNode().withLabels(label);
                        } else {
                            leftNode = new Pattern.PatternBuilder(identifierBuilder).newAnonymousNode().withLabels(label);
                        }
                    } else {
                        if (isNamedLeft) {
                            leftNode = new Pattern.PatternBuilder(identifierBuilder).newNamedNode();
                        } else {
                            leftNode = new Pattern.PatternBuilder(identifierBuilder).newAnonymousNode();
                        }
                    }
                } else {
                    List<INodeAnalyzer> idNode = matchClause.getExtendableNodeIdentifiers();
                    if (idNode.size() == 0) {
                        leftNode = new Pattern.PatternBuilder(identifierBuilder).newNamedNode();
                    } else {
                        INodeAnalyzer node = idNode.get(r.getInteger(0, idNode.size() - 1));
                        leftNode = new Pattern.PatternBuilder(identifierBuilder).newRefDefinedNode(node);
                    }
                }

                Pattern.PatternBuilder.OngoingRelation relation;
                boolean withType = Randomly.getBoolean();
                //boolean isNamed = Randomly.getBoolean();
                boolean isNamed = !Randomly.getBooleanWithSmallProbability();
                Direction direction = Randomly.fromOptions(Direction.LEFT, Direction.RIGHT, Direction.BOTH);
                int typeOfLength = r.getInteger(0, 3);
                if (withType) {
                    Neo4jSchema.Neo4jRelationTypeInfo typeInfo = schema.getRelationTypes().get(r.getInteger(0, sizeOfTypes - 1));
                    IType type = new RelationType(typeInfo.getName());
                    if (isNamed) {
                        if (typeOfLength == 0) {
                            relation = leftNode.newNamedRelation().withType(type).withDirection(direction).withLengthUnbounded();
                        } else if (typeOfLength == 1) {
                            relation = leftNode.newNamedRelation().withType(type).withDirection(direction).withOnlyLengthLowerBound(1);
                        } else if (typeOfLength == 2) {
                            relation = leftNode.newNamedRelation().withType(type).withDirection(direction).withOnlyLengthUpperBound(2);
                        } else {
                            relation = leftNode.newNamedRelation().withType(type).withDirection(direction).withLength(1);
                        }
                    } else {
                        if (typeOfLength == 0) {
                            relation = leftNode.newAnonymousRelation().withType(type).withDirection(direction).withLengthUnbounded();
                        } else if (typeOfLength == 1) {
                            relation = leftNode.newAnonymousRelation().withType(type).withDirection(direction).withOnlyLengthLowerBound(1);
                        } else if (typeOfLength == 2) {
                            relation = leftNode.newAnonymousRelation().withType(type).withDirection(direction).withOnlyLengthUpperBound(2);
                        } else {
                            relation = leftNode.newAnonymousRelation().withType(type).withDirection(direction).withLength(1);
                        }
                    }
                } else {
                    if (isNamed) {
                        if (typeOfLength == 0) {
                            relation = leftNode.newNamedRelation().withDirection(direction).withLengthUnbounded();
                        } else if (typeOfLength == 1) {
                            relation = leftNode.newNamedRelation().withDirection(direction).withOnlyLengthLowerBound(1);
                        } else if (typeOfLength == 2) {
                            relation = leftNode.newNamedRelation().withDirection(direction).withOnlyLengthUpperBound(2);
                        } else {
                            relation = leftNode.newNamedRelation().withDirection(direction).withLength(1);
                        }
                    } else {
                        if (typeOfLength == 0) {
                            relation = leftNode.newAnonymousRelation().withDirection(direction).withLengthUnbounded();
                        } else if (typeOfLength == 1) {
                            relation = leftNode.newAnonymousRelation().withDirection(direction).withOnlyLengthLowerBound(1);
                        } else if (typeOfLength == 2) {
                            relation = leftNode.newAnonymousRelation().withDirection(direction).withOnlyLengthUpperBound(2);
                        } else {
                            relation = leftNode.newAnonymousRelation().withDirection(direction).withLength(1);
                        }
                    }
                }

                Pattern.PatternBuilder.OngoingNode rightNode;
                boolean isNewRight = Randomly.getBoolean();
                if (isNewRight) {
                    boolean withLabelRight = Randomly.getBoolean();
                    //boolean isNamedRight = Randomly.getBoolean();
                    boolean isNamedRight = !Randomly.getBooleanWithSmallProbability();
                    if (withLabelRight) {
                        Neo4jSchema.Neo4jLabelInfo labelInfo = schema.getLabels().get(r.getInteger(0, sizeOfLabels - 1));
                        ILabel label = new Label(labelInfo.getName());
                        if (isNamedRight) {
                            rightNode = relation.newNamedNode().withLabels(label);
                        } else {
                            rightNode = relation.newAnonymousNode().withLabels(label);
                        }
                    } else {
                        if (isNamedRight) {
                            rightNode = relation.newNamedNode();
                        } else {
                            rightNode = relation.newAnonymousNode();
                        }
                    }
                } else {
                    List<INodeAnalyzer> idNode = matchClause.getExtendableNodeIdentifiers();
                    if (idNode.size() == 0) {
                        rightNode = new Pattern.PatternBuilder(identifierBuilder).newNamedNode();
                    } else {
                        INodeAnalyzer node = idNode.get(r.getInteger(0, idNode.size() - 1));
                        rightNode = new Pattern.PatternBuilder(identifierBuilder).newRefDefinedNode(node);
                    }
                }
                patternTuple.add(rightNode.build());
            }
        }
        return patternTuple;
    }
}
