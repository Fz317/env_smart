package com.briup.smart.implement;

import com.briup.smart.env.Configuration;
import com.briup.smart.env.entity.Environment;
import com.briup.smart.env.server.DBStore;
import com.briup.smart.env.support.ConfigurationAware;
import com.briup.smart.env.support.PropertiesAware;
import com.briup.smart.util.Jdbcutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.*;

/**
 * @BelongsProject: JD2407_envsmart
 * @BelongsPackage: com.briup.smart.implement
 * @Author: Fz
 * @CreateTime: 2024-09-25  09:50
 * @Description: TODO
 * @Version: 1.0
 */
public class DBStoreimpl implements DBStore, PropertiesAware, ConfigurationAware {
    @Override
    public void saveDB(Collection<Environment> collection){
        Connection  conn = null;
        PreparedStatement pstmt = null;
        int savecount = 0;
        try {
            conn = Jdbcutils.getConnection();
            conn.setAutoCommit(false);
            int count = 0;
            int preDay = -1;
            for (Environment env : collection) {
                Timestamp gatherDate = env.getGatherDate();
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(gatherDate.getTime());
                int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
                if (preDay != dayOfMonth) {
                    if (preDay != -1) {
                        pstmt.executeBatch();
                        conn.commit();
                        savecount += count;
                        count = 0;
                        pstmt.close();
                    }
                    String sql = "insert into e_detail_" + dayOfMonth + "(name,srcId,desId,devId,sersorAddress,count,cmd,status,data,gather_date) " + "values(?,?,?,?,?,?,?,?,?,?)";
                    pstmt = conn.prepareStatement(sql);
                    System.out.println("创建新pstmt: " + sql);
                }
                pstmt.setString(1, env.getName());
                pstmt.setString(2, env.getSrcId());
                pstmt.setString(3, env.getDesId());
                pstmt.setString(4, env.getDevId());
                pstmt.setString(5, env.getSensorAddress());
                pstmt.setInt(6, env.getCount());
                pstmt.setString(7, env.getCmd());
                pstmt.setInt(8, env.getStatus());
                pstmt.setFloat(9, env.getData());
                pstmt.setTimestamp(10, env.getGatherDate());
                count++;
                pstmt.addBatch();
                if (count % 2000 == 0) {
                    pstmt.executeBatch();
                    conn.commit();
                    savecount += count;
                    count = 0;
                }
                preDay = dayOfMonth;
            }
            if (pstmt != null) {
                pstmt.executeBatch();
            }
            conn.commit();
            savecount += count;
            System.out.println("成功入库数据: "+savecount);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            Jdbcutils.close(pstmt,conn);
        }
    }

    @Override
    public void setConfiguration(Configuration configuration) {

    }
    @Override
    public void init(Properties properties) {

    }
}
