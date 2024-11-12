package com.briup.smart.util;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @BelongsProject: maven_task1
 * @BelongsPackage: com.briup.util
 * @Author: Fz
 * @CreateTime: 2024-09-20  10:53
 * @Description: TODO
 * @Version: 1.0
 */
public class Jdbcutils {
    private static DataSource dataSource;
    static {
        try {
            Properties properties = new Properties();
            properties.load(Jdbcutils.class.getClassLoader().getResourceAsStream("druid.properties"));
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //私有的构造函数
    private Jdbcutils(){}
    //返回一个数据库连接对象
    public static Connection getConnection() throws SQLException {
        return  dataSource.getConnection();
    }
    //关闭资源
    public static void close(ResultSet rs, Statement stmt,Connection conn){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(stmt!=null){
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    //关闭资源方法重载
    public  static  void close(Statement stmt,Connection conn){
        close(null,stmt,conn);
    }
}
