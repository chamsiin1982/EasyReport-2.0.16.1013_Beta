package com.easytoolsoft.easyreport.engine.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.easytoolsoft.easyreport.engine.data.ReportDataSource;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataCell;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataColumn;
import com.easytoolsoft.easyreport.engine.data.ReportMetaDataRow;
import com.easytoolsoft.easyreport.engine.data.ReportParameter;
import com.easytoolsoft.easyreport.engine.exception.SQLQueryException;
import com.easytoolsoft.easyreport.engine.util.JdbcUtils;
import com.mchange.lang.IntegerUtils;

public class EsQueryer extends AbstractQueryer implements Queryer {

	public EsQueryer(ReportDataSource dataSource, ReportParameter parameter) {
		super(dataSource, parameter);
	}
	
  @Override
    protected String preprocessSqlText(String sqlText) {
        sqlText = StringUtils.stripEnd(sqlText.trim(), ";");
        Pattern pattern = Pattern.compile("limit.*?$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sqlText);
        if (matcher.find()) {
            sqlText = matcher.replaceFirst("");
        }
        return sqlText + " limit 1";
    }
  
  @Override
  public List<ReportMetaDataColumn> parseMetaDataColumns(String sqlText) {
      Connection conn = null;
      Statement stmt = null;
      ResultSet rs = null;
      List<ReportMetaDataColumn> columns = null;

      try {
          logger.debug("Parse Report MetaDataColumns SQL:{},", sqlText);
          conn = this.getJdbcConnection();
          stmt = conn.createStatement();
          rs = stmt.executeQuery(this.preprocessSqlText(sqlText));
          ResultSetMetaData rsMataData = rs.getMetaData();
          int count = rsMataData.getColumnCount();
          columns = new ArrayList<>(count);
          for (int i = 1; i <= count; i++) {
              ReportMetaDataColumn column = new ReportMetaDataColumn();
              column.setCaseSentitive(true);
              column.setName(StringUtils.isEmpty(rsMataData.getColumnLabel(i))==true?rsMataData.getColumnName(i):rsMataData.getColumnLabel(i));
              column.setDataType(rsMataData.getColumnTypeName(i));
              column.setWidth(rsMataData.getColumnDisplaySize(i)>0 ? rsMataData.getColumnDisplaySize(i) : 100);
              columns.add(column);
          }
      } catch (SQLException ex) {
          throw new RuntimeException(ex);
      } finally {
          JdbcUtils.releaseJdbcResource(conn, stmt, rs);
      }
      return columns;
  }
  
  
	  protected List<ReportMetaDataRow> getMetaDataRows(ResultSet rs, List<ReportMetaDataColumn> sqlColumns)
	          throws SQLException {
	      List<ReportMetaDataRow> rows = new ArrayList<>();
	      while (rs.next()) {
	          ReportMetaDataRow row = new ReportMetaDataRow();
	          for (ReportMetaDataColumn column : sqlColumns) {
	        	  //ES 大小写敏感
	        	  column.setCaseSentitive(true);
	              Object value = rs.getObject(column.getName());
	              if (column.getDataType().contains("BINARY")) {
	                  value = new String((byte[]) value);
	              }
	              row.add(new ReportMetaDataCell(column, column.getName(), value));
	          }
	          rows.add(row);
	      }
	      return rows;
	  }
	  public List<ReportMetaDataRow> getMetaDataRows() {
	        Connection conn = null;
	        Statement stmt = null;
	        ResultSet rs = null;

	        try {
	            logger.debug(this.parameter.getSqlText());
	            conn = this.getJdbcConnection();
	            stmt = conn.createStatement();
	            rs = stmt.executeQuery(this.parameter.getSqlText());
	            return this.getMetaDataRows(rs, this.getSqlColumns(this.parameter.getMetaColumns()));
	        } catch (Exception ex) {
	            logger.error(String.format("SqlText:%s，Msg:%s", this.parameter.getSqlText(), ex));
	            throw new SQLQueryException(ex);
	        } finally {
	            JdbcUtils.releaseJdbcResource(conn, stmt, rs);
	        }
	    }
}
