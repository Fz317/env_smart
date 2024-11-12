package com.briup.smart.implement;

import com.briup.smart.env.Configuration;
import com.briup.smart.env.client.Client;
import com.briup.smart.env.entity.Environment;
import com.briup.smart.env.support.ConfigurationAware;
import com.briup.smart.env.support.PropertiesAware;
import com.briup.smart.env.util.Log;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;

/**
 * @BelongsProject: JD2407_envsmart
 * @BelongsPackage: com.briup.smart.implement
 * @Author: Fz
 * @CreateTime: 2024-09-24  11:40
 * @Description: TODO
 * @Version: 1.0
 */
public class Clientimpl implements Client, PropertiesAware,ConfigurationAware {
    private  String host;
    private int port;
    private Log log;
    @Override
    public void send(Collection<Environment> collection) throws Exception {
        if(collection==null||collection.size()==0){
            log.error("接受的数据有错误");
            return;
        }
        Socket socket = null;
        ObjectOutputStream oos = null;
        socket = new Socket(host,port);
        log.info("客户端网络模块搭建成功");
        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(collection);
        log.info("客户端网络模块: 数据发送成功,共" + collection.size() + "条");
        oos.close();
        socket.close();
        log.info("客户端成功关闭");
    }
    @Override
    public void init(Properties properties) throws Exception {
        this.host = properties.getProperty("client-host");
        this.port = Integer.parseInt(properties.getProperty("client-port"));
    }

    @Override
    public void setConfiguration(Configuration configuration) throws Exception {
        log = configuration.getLogger();
    }
}
