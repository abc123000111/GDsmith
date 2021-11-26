package gdsmith.neo4j.gen.random;

import gdsmith.Randomly;
import gdsmith.cypher.ICypherSchema;
import gdsmith.cypher.ast.*;
import gdsmith.cypher.ast.analyzer.IClauseAnalyzer;
import gdsmith.cypher.ast.analyzer.IIdentifierAnalyzer;
import gdsmith.cypher.ast.analyzer.INodeAnalyzer;
import gdsmith.cypher.ast.analyzer.IRelationAnalyzer;
import gdsmith.neo4j.ast.Neo4jType;
import gdsmith.neo4j.ast.expr.*;
import gdsmith.neo4j.schema.ILabelInfo;
import gdsmith.neo4j.schema.IPropertyInfo;
import gdsmith.neo4j.schema.Neo4jSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RandomExpressionGenerator
{
    IClauseAnalyzer clauseAnalyzer;
    Neo4jSchema schema;
    public RandomExpressionGenerator(IClauseAnalyzer clauseAnalyzer, Neo4jSchema schema){
        this.clauseAnalyzer = clauseAnalyzer;
        this.schema = schema;
    }

    public IExpression generateGetProperty(Neo4jType type){
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
                .get(randomly.getInteger(0, identifierAnalyzers.size() - 1));

        if(identifierAnalyzer instanceof IRelationAnalyzer){
            List<IPropertyInfo> propertyInfos = ((IRelationAnalyzer) identifierAnalyzer).getAllPropertiesWithType(schema, type);
            return new GetPropertyExpression(new IdentifierExpression(identifierAnalyzer),
                    propertyInfos.get(randomly.getInteger(0, propertyInfos.size()-1)).getKey());
        }
        if(identifierAnalyzer instanceof INodeAnalyzer){
            List<IPropertyInfo> propertyInfos = ((INodeAnalyzer) identifierAnalyzer).getAllPropertiesWithType(schema, type);
            return new GetPropertyExpression(new IdentifierExpression(identifierAnalyzer),
                    propertyInfos.get(randomly.getInteger(0, propertyInfos.size()-1)).getKey());
        }
        return generateConstExpression(type);
    }

    public IExpression generateConstExpression(Neo4jType type){
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
            default:
                //todo 对其他类型random的支持
                return null;
        }
    }

    public IExpression generateComparison(){
        Randomly randomly = new Randomly();
        int randomNum = randomly.getInteger(0, 100);
        Neo4jType type = Neo4jType.getRandomBasicType();
        if(randomNum < 60){
            return BinaryComparisonExpression.randomComparison(generateGetProperty(type),
                    generateConstExpression(type));
        }
        return BinaryComparisonExpression.randomComparison(generateGetProperty(type), generateGetProperty(type));
    }


    private IExpression generateBooleanExpression(){
        Randomly randomly = new Randomly();
        int randomNum = randomly.getInteger(0, 100);
        //todo more
        return generateComparison();
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
