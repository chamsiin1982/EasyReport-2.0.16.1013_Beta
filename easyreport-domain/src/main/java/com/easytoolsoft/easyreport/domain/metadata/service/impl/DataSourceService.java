package com.easytoolsoft.easyreport.domain.metadata.service.impl;

import com.easytoolsoft.easyreport.data.common.service.AbstractCrudService;
import com.easytoolsoft.easyreport.data.metadata.dao.IDataSourceDao;
import com.easytoolsoft.easyreport.data.metadata.example.DataSourceExample;
import com.easytoolsoft.easyreport.data.metadata.po.DataSource;
import com.easytoolsoft.easyreport.domain.metadata.service.IDataSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
@Service("EzrptMetaSourceService")
public class DataSourceService
        extends AbstractCrudService<IDataSourceDao, DataSource, DataSourceExample>
        implements IDataSourceService {

    @Override
    protected DataSourceExample getPageExample(String fieldName, String keyword) {
        DataSourceExample example = new DataSourceExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    /**
     * @param url
     * @param user
     * @param password
     * @return
     */
    public boolean testConnection(String url, String user, String password) {
        Connection conn = null;
        try {
        	//this.getClass().getClassLoader().loadClass("oracle.jdbc.driver.OracleDriver");//
        	Class.forName("org.elasticsearch.xpack.sql.jdbc.jdbc.JdbcDriver");
        	//DriverManager.registerDriver(Class.forName("oracle.jdbc.driver.OracleDriver"));
        	if(StringUtils.isEmpty(user)){
        			conn =DriverManager.getConnection(url);
        	}
        	else{
        		conn = DriverManager.getConnection(url, user, password);
        	}
        	
            return true;
        } catch (Exception e) {
            log.error("testConnection", e);
            return false;
        } finally {
            this.releaseConnection(conn);
        }
    }
    public boolean testConnection(String url, String user, String password,String driverClass) {
        Connection conn = null;
        try {
        	//this.getClass().getClassLoader().loadClass(driverClass);//
        	Class.forName(driverClass);
        	//DriverManager.registerDriver(Class.forName("oracle.jdbc.driver.OracleDriver"));
        	if(StringUtils.isEmpty(user)){
        			conn =DriverManager.getConnection(url);
        	}
        	else{
        		conn = DriverManager.getConnection(url, user, password);
        	}
        	
            return true;
        } catch (Exception e) {
            log.error("testConnection", e);
            return false;
        } finally {
            this.releaseConnection(conn);
        }
    }

    private void releaseConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                log.error("测试数据库连接后释放资源失败", ex);
            }
        }
    }
}