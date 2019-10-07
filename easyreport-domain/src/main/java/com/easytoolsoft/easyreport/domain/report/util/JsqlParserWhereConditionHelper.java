package com.easytoolsoft.easyreport.domain.report.util;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;

public class JsqlParserWhereConditionHelper {
	
    public static String addAndCondition(String coreQuery, String columnName,String operator,String status) throws Exception{       
        if(!operatorCkeck(operator)){
        	throw new Exception("暂不支持的SQL 运算符:"+operator);
        }       
        Select select = createSelect(coreQuery);
        addWhereAndCondition(select, expressionFactory(operator,new Column(columnName),new StringValue(status)));       
        return select.toString();
    }
    
    public static String addAndCondition(String coreQuery, String columnName,String operator,Long status) throws Exception{       
    	 if(!operatorCkeck(operator)){
         	throw new Exception("暂不支持的SQL 运算符:"+operator);
         }
    	 
        Select select = createSelect(coreQuery);
        addWhereAndCondition(select, expressionFactory(operator,new Column(columnName),new LongValue(status)));
        
        return select.toString();
    }
    
    public static String addOrCondition(String coreQuery, String columnName,String operator,String status) throws Exception{
    	 if(!operatorCkeck(operator)){
          	throw new Exception("暂不支持的SQL 运算符:"+operator);
          }
    	Select select = createSelect(coreQuery);       
        addWhereOrCondition(select, expressionFactory(operator,new Column(columnName),new StringValue(status)));
        return select.toString();
    }
    
    public static String addOrCondition(String coreQuery, String columnName,String operator,Long status) throws Exception{
   	 if(!operatorCkeck(operator)){
         	throw new Exception("暂不支持的SQL 运算符:"+operator);
         }
   	 	Select select = createSelect(coreQuery);       
       addWhereOrCondition(select, expressionFactory(operator,new Column(columnName),new LongValue(status)));
       return select.toString();
   }

    private static Expression expressionFactory(String operator,Expression left, Expression right){
    	 Expression exp=null;
    	 if(operator.equals(WhereConditionOperator.EQUALS)){
    		 exp= equalsTo(left,right);
    	 }else if(operator.equals(WhereConditionOperator.NOT_EQUALS)){
    		 exp= notEqualsTo(left,right);
    	 }else if(operator.equals(WhereConditionOperator.GRANTER_THAN)){
    		 exp= greaterThan(left,right);
    	 }else if(operator.equals(WhereConditionOperator.LIKE)){
    		 exp= likeExpression(left,right);
    	 }else if(operator.equals(WhereConditionOperator.NOT_LIKE)){
    		 LikeExpression le=likeExpression(left,right);
    		 le.setNot(true);
    		 exp= le;
    	 }else if(operator.equals(WhereConditionOperator.GRANTER_THAN_EQUALS)){
    		 exp= greaterThanEquals(left,right);
    	 }
    	 return exp;
    }
    private static boolean operatorCkeck(String operator){
    	if(null==operator || "".equals(operator)){
    		return false;
    	}else if(operator.equals(WhereConditionOperator.EQUALS)
    			|| operator.equals(WhereConditionOperator.NOT_EQUALS)
    			|| operator.equals(WhereConditionOperator.GRANTER_THAN)
    			|| operator.equals(WhereConditionOperator.LIKE)
    			|| operator.equals(WhereConditionOperator.NOT_LIKE)
    			|| operator.equals(WhereConditionOperator.GRANTER_THAN_EQUALS)
    			){
    		return true;
    	}
    	
    	return false;
    }
    
    private static EqualsTo equalsTo(Expression left, Expression right) {
        EqualsTo equalsTo = new EqualsTo();
        equalsTo.setLeftExpression(left);
        equalsTo.setRightExpression(right);
        return equalsTo;
    }
    
    private static NotEqualsTo notEqualsTo(Expression left, Expression right) {       
        NotEqualsTo notEqualsTo=new NotEqualsTo();
        notEqualsTo.setLeftExpression(left);
        notEqualsTo.setRightExpression(right);
        return notEqualsTo;
    }

    private static GreaterThan greaterThan(Expression left, Expression right) {
        GreaterThan greaterThan = new GreaterThan();
        greaterThan.setLeftExpression(left);
        greaterThan.setRightExpression(right);
        return greaterThan;
    }
    private static GreaterThanEquals greaterThanEquals(Expression left, Expression right) {
        GreaterThanEquals greaterThanEquals = new GreaterThanEquals();
        greaterThanEquals.setLeftExpression(left);
        greaterThanEquals.setRightExpression(right);
        return greaterThanEquals;
    }
    private static LikeExpression likeExpression(Expression left, Expression right) {
    	LikeExpression like = new LikeExpression();
    	like.setLeftExpression(left);
//		if(! (right.toString().indexOf('%')>0)) {
//				String temp="%"+right.toString()+"%";
//				like.setRightExpression(new StringValue( temp));
//		}else{
//				like.setRightExpression(right);
//		}
    	like.setRightExpression(right);
    	//like.setCaseInsensitive(true);
        return like;
    }

    public static Select createSelect(String sql) {
        try {
            return (Select) CCJSqlParserUtil.parse(sql);
        } catch (JSQLParserException e) {
            throw new IllegalStateException("SQL parsing problem!", e);
        }
    }

    public static void addWhereAndCondition(Select select, Expression condition) {
    	addWhereAndCondition(getPlainSelect(select), condition);
    }
    public static void addWhereOrCondition(Select select, Expression condition) {
    	addWhereOrCondition(getPlainSelect(select), condition);
    }

    private static void addWhereAndCondition(PlainSelect plainSelect, Expression condition) {
        if (plainSelect.getWhere() == null) {
            plainSelect.setWhere(condition);
            return;
        }
        AndExpression andExpression = new AndExpression(plainSelect.getWhere(), condition);
        plainSelect.setWhere(andExpression);
    }
    
    private static void addWhereOrCondition(PlainSelect plainSelect, Expression condition) {
        if (plainSelect.getWhere() == null) {
            plainSelect.setWhere(condition);
            return;
        }
        OrExpression orExpression = new OrExpression(plainSelect.getWhere(), condition);
        plainSelect.setWhere(orExpression);
    }

    private static PlainSelect getPlainSelect(Select select) {
        if (select.getSelectBody() instanceof PlainSelect) {
            return (PlainSelect) select.getSelectBody();
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
