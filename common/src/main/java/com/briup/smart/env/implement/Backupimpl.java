package com.briup.smart.env.implement;

import com.briup.smart.env.Configuration;
import com.briup.smart.env.support.ConfigurationAware;
import com.briup.smart.env.util.Backup;
import com.briup.smart.env.util.Log;

import java.io.*;

/**
 * @BelongsProject: JD2407_envsmart
 * @BelongsPackage: com.briup.smart.implement
 * @Author: Fz
 * @CreateTime: 2024-09-29  17:00
 * @Description: TODO
 * @Version: 1.0
 */
public class Backupimpl implements Backup, ConfigurationAware {
    private Log log;
    @Override
    public Object load(String filePath, boolean del) throws Exception {
        File file = new File(filePath);
        if(!file.exists()||!file.isFile()){
            log.warn("备份文件不存在,请检查"+file.getAbsolutePath());
            return null;
        }
        if(file.length()<=0){
            log.warn("文件的内容为空,请检查+"+file.getAbsolutePath());
            return null;
        }
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        Object o = ois.readObject();
        ois.close();
        if(del){
            boolean delete = file.delete();
            if(delete) {
                log.info("备份文件已经删除");
            }else{
                log.error("删除失败");
                return null;
            }
        }
        return o;
    }

    @Override
    public void store(String filePath, Object obj, boolean append) throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath,append));
        oos.writeObject(obj);
        log.info("备份文件已生成");
        oos.flush();
        oos.close();
    }

    @Override
    public void setConfiguration(Configuration configuration) throws Exception {
        log = configuration.getLogger();
    }
}
