package gdsmith.cypher.gen.random;

import gdsmith.Randomly;
import gdsmith.cypher.ast.*;
import gdsmith.cypher.ast.analyzer.IClauseAnalyzer;
import gdsmith.cypher.ast.analyzer.IIdentifierAnalyzer;
import gdsmith.cypher.ast.analyzer.INodeAnalyzer;
import gdsmith.cypher.ast.analyzer.IRelationAnalyzer;
import gdsmith.cypher.schema.CypherSchema;
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

    public IExpression generateNumberAgg(){
        Randomly randomly = new Randomly();
        int randNum = randomly.getInteger(0, 50);
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

    public IExpression generateStringAgg(){
        Randomly randomly = new Randomly();
        int randNum = randomly.getInteger(0, 20);
        IExpression param = generateGetProperty(CypherType.NUMBER);
        if(param == null){
            param = generateConstExpression(CypherType.NUMBER);
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

    public IExpression generateGetProperty(CypherType type){
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

        if(randomly.getInteger(0, 100) < 25){
            IExpression result = generateGetProperty(type);
            if(result != null){
                return result;
            }
        }
        switch (type){
            case NUMBER:
                return new ConstExpression((int)randomly.getInteger());
            case STRING:
                return new ConstExpression(randomly.getString());
            default:
                //todo 对其他类型random的支持
                return null;
        }
    }

    public IExpression generateComparison(){
        Randomly randomly = new Randomly();
        int randomNum = randomly.getInteger(0, 100);
        CypherType type = CypherType.NUMBER;
        if(randomNum < 60){
            return BinaryComparisonExpression.randomComparison(generateGetProperty(type),
                    generateConstExpression(type));
        }
        return BinaryComparisonExpression.randomComparison(generateGetProperty(type), generateGetProperty(type));
    }

    private IExpression generateStringMatching(){
        Randomly randomly = new Randomly();
        //todo more
        IExpression getProperty = generateGetProperty(CypherType.STRING);
        if(getProperty == null){
            return StringMatchingExpression.
                    randomMatching(generateConstExpression(CypherType.STRING), generateConstExpression(CypherType.STRING));
        }
        return StringMatchingExpression.randomMatching(getProperty, generateConstExpression(CypherType.STRING));
    }


    private IExpression generateBooleanExpression(){
        Randomly randomly = new Randomly();
        int randomNum = randomly.getInteger(0, 100);
        //todo more
        if(randomNum < 50){
            return generateComparison();
        }
        return generateStringMatching();
    }

    public IExpression generateCondition(int depth){
        if(depth == 0){
            return generateBooleanExpression();
        }
        Randomly randomly = new Randomly();
        int randomNum = randomly.getInteger(0, 100);
        if(randomNum < 5){
            return generateBooleanExpression();
        }
        if(randomNum < 20){
            return new SingleLogicalExpression(generateCondition(depth - 1),
                    SingleLogicalExpression.SingleLogicalOperation.NOT);
        }
        return BinaryLogicalExpression.randomLogical(generateCondition(depth - 1),
                generateCondition(depth - 1));
    }
}
