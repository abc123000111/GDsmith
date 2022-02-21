package gdsmith.cypher.gen.random;

import gdsmith.Randomly;
import gdsmith.cypher.ast.*;
import gdsmith.cypher.ast.analyzer.*;
import gdsmith.cypher.schema.CypherSchema;
import gdsmith.cypher.standard_ast.Alias;
import gdsmith.cypher.standard_ast.CypherType;
import gdsmith.cypher.standard_ast.expr.*;
import gdsmith.cypher.schema.IPropertyInfo;
import gdsmith.neo4j.schema.Neo4jSchema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RandomExpressionGenerator<S extends CypherSchema<?,?>>
{
    IClauseAnalyzer clauseAnalyzer;
    S schema;
    public RandomExpressionGenerator(IClauseAnalyzer clauseAnalyzer, S schema){
        this.clauseAnalyzer = clauseAnalyzer;
        this.schema = schema;
    }

    private IExpression generateNumberAgg(){
        Randomly randomly = new Randomly();
        int randNum = randomly.getInteger(0, 50);
        // int randNum = randomly.getInteger(0, 20); //todo
        IExpression param = generateGetProperty(CypherType.NUMBER);
        if(param == null){
            param = generateConstExpression(CypherType.NUMBER);
        }
        if( randNum < 10){
            return new CallExpression(Neo4jSchema.Neo4jBuiltInFunctions.MAX_NUMBER, Arrays.asList(param));
        }
        if( randNum < 20){
            return new CallExpression(Neo4jSchema.Neo4jBuiltInFunctions.MIN_NUMBER, Arrays.asList(param));
        }
        if( randNum < 30){
            return new CallExpression(Neo4jSchema.Neo4jBuiltInFunctions.ST_DEV, Arrays.asList(param));
        }
        if( randNum < 40){
            return new CallExpression(Neo4jSchema.Neo4jBuiltInFunctions.ST_DEV_P, Arrays.asList(param));
        }
        return new CallExpression(Neo4jSchema.Neo4jBuiltInFunctions.SUM, Arrays.asList(param));
    }

    private IExpression generateStringAgg(){
        Randomly randomly = new Randomly();
        int randNum = randomly.getInteger(0, 20);
        IExpression param = generateGetProperty(CypherType.STRING);
        if(param == null){
            param = generateConstExpression(CypherType.STRING);
        }
        if( randNum < 10){
            return new CallExpression(Neo4jSchema.Neo4jBuiltInFunctions.MAX_STRING, Arrays.asList(param));
        }
        return new CallExpression(Neo4jSchema.Neo4jBuiltInFunctions.MIN_STRING, Arrays.asList(param));
    }

    public IExpression generateFunction(CypherType type){
        if(type == CypherType.NUMBER){
            return generateNumberAgg();
        }
        return generateStringAgg();
    }

    private IExpression generateUseAlias(CypherType type){
        List<IAliasAnalyzer> aliasAnalyzers = clauseAnalyzer.getAvailableAliases();
        aliasAnalyzers = aliasAnalyzers.stream().filter(a->a.analyzeType(schema).getType()==type).collect(Collectors.toList());
        IAliasAnalyzer aliasAnalyzer =  aliasAnalyzers.stream().findAny().orElse(null);
        if(aliasAnalyzer!=null){
            return new IdentifierExpression(Alias.createAliasRef(aliasAnalyzer));
        }
        return generateConstExpression(type);
    }

    private IExpression generateGetProperty(CypherType type){
        Randomly randomly = new Randomly();

        List<INodeAnalyzer> nodeAnalyzers = clauseAnalyzer.getAvailableNodeIdentifiers();
        List<IRelationAnalyzer> relationAnalyzers = clauseAnalyzer.getAvailableRelationIdentifiers();

        List<IIdentifierAnalyzer> identifierAnalyzers = new ArrayList<>();

        identifierAnalyzers.addAll(nodeAnalyzers.stream()
                .filter(n->n.getAllPropertiesWithType(schema,type).size()>0).collect(Collectors.toList()));
        identifierAnalyzers.addAll(relationAnalyzers.stream().
                filter(r->(r.getAllPropertiesWithType(schema,type).size()>0 && r.isSingleRelation())).collect(Collectors.toList()));

        if(identifierAnalyzers.size() == 0){
            return generateConstExpression(type);
        }
        IIdentifierAnalyzer identifierAnalyzer = identifierAnalyzers
                .get(randomly.getInteger(0, identifierAnalyzers.size()));

        if(identifierAnalyzer instanceof IRelationAnalyzer){
            List<IPropertyInfo> propertyInfos = ((IRelationAnalyzer) identifierAnalyzer).getAllPropertiesWithType(schema, type);
            return new GetPropertyExpression(new IdentifierExpression(identifierAnalyzer),
                    propertyInfos.get(randomly.getInteger(0, propertyInfos.size())).getKey());
        }
        if(identifierAnalyzer instanceof INodeAnalyzer){
            List<IPropertyInfo> propertyInfos = ((INodeAnalyzer) identifierAnalyzer).getAllPropertiesWithType(schema, type);
            return new GetPropertyExpression(new IdentifierExpression(identifierAnalyzer),
                    propertyInfos.get(randomly.getInteger(0, propertyInfos.size())).getKey());
        }
        return generateConstExpression(type);
    }

    public IExpression generateConstExpression(CypherType type){
        Randomly randomly = new Randomly();
        switch (type){
            case NUMBER:
                return new ConstExpression((int)randomly.getInteger());
            case STRING:
                return new ConstExpression(randomly.getString());
            case BOOLEAN:
                return new ConstExpression(randomly.getInteger(0, 100) < 50);
            default:
                //todo 对其他类型random的支持
                return null;
        }
    }

    public IExpression generateCondition(int depth){
        return booleanExpression(depth);
    }

    public IExpression generateListWithBasicType(int depth, CypherType type){
        Randomly randomly = new Randomly();
        int randomNum = randomly.getInteger(0,4);
        List<IExpression> expressions = new ArrayList<>();
        for(int i = 0; i < randomNum; i++){
            //todo 更复杂的列表生成
            expressions.add(basicTypeExpression(depth, type));
        }
        return new CreateListExpression(expressions);
    }

    private IExpression basicTypeExpression(int depth, CypherType type){
        switch (type){
            case BOOLEAN:
                return booleanExpression(depth);
            case STRING:
                return stringExpression(depth);
            case NUMBER:
                return numberExpression(depth);
            default:
                return null;
        }
    }



    private IExpression booleanExpression(int depth){
        Randomly randomly = new Randomly();
        int expressionChoice = randomly.getInteger(0, 100);
        if(depth == 0 || expressionChoice < 30){
            //深度用尽，快速收束，对于BOOLEAN而言： 返回true/false，返回boolean类型property，返回boolean变量引用
            int randomNum = randomly.getInteger(0,100);
            if(randomNum < 20){
                return generateConstExpression(CypherType.BOOLEAN);
            }
            if(randomNum < 60){
                return generateGetProperty(CypherType.BOOLEAN);
            }
            //todo Is_NULL的单独处理逻辑
            return generateUseAlias(CypherType.BOOLEAN);
        }

        //尚有深度

        if(expressionChoice < 50){
            return BinaryComparisonExpression.randomComparison(numberExpression(depth - 1), numberExpression(depth - 1));
        }
        if(expressionChoice < 60){
            return BinaryComparisonExpression.randomComparison(stringExpression(depth - 1), stringExpression(depth - 1));
        }
        if(expressionChoice < 70){
            return StringMatchingExpression.randomMatching(stringExpression(depth - 1), stringExpression(depth - 1));
        }
        if(expressionChoice < 80){
            return SingleLogicalExpression.randomLogical(booleanExpression(depth - 1));
        }
        return BinaryLogicalExpression.randomLogical(booleanExpression(depth - 1), booleanExpression(depth - 1));
    }

    private IExpression stringExpression(int depth){
        Randomly randomly = new Randomly();
        int expressionChoice = randomly.getInteger(0, 100);
        if(depth == 0 || expressionChoice < 70){
            //深度用尽，快速收束，对于string而言： 返回随机字符串，返回string类型property，返回string变量引用
            int randomNum = randomly.getInteger(0,100);
            if(randomNum < 20){
                return generateConstExpression(CypherType.STRING);
            }
            if(randomNum < 40){
                return generateGetProperty(CypherType.STRING);
            }
            if (randomNum < 50){
                //return generateStringAgg();
            }
            return generateUseAlias(CypherType.STRING);
        }
        return new StringCatExpression(stringExpression(depth - 1), stringExpression(depth - 1));
    }

    private IExpression numberExpression(int depth){
        Randomly randomly = new Randomly();
        int expressionChoice = randomly.getInteger(0, 100);
        if(depth == 0 || expressionChoice < 50){
            //深度用尽，快速收束，对于string而言： 返回随机字符串，返回string类型property，返回string变量引用
            int randomNum = randomly.getInteger(0,100);
            if(randomNum < 20){
                return generateConstExpression(CypherType.NUMBER);
            }
            if(randomNum < 60){
                return generateGetProperty(CypherType.NUMBER);
            }
            if(randomNum < 70){
                //return generateNumberAgg();
            }
            return generateUseAlias(CypherType.NUMBER);
        }
        return generateConstExpression(CypherType.NUMBER);
        //return BinaryNumberExpression.randomBinaryNumber(numberExpression(depth - 1), numberExpression(depth - 1));
    }

}
