package com.briup.smart.implement;

import com.briup.smart.env.Configuration;
import com.briup.smart.env.entity.Environment;
import com.briup.smart.env.server.DBStore;
import com.briup.smart.env.server.Server;
import com.briup.smart.env.support.ConfigurationAware;
import com.briup.smart.env.support.PropertiesAware;
import com.briup.smart.env.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

/**
 * @BelongsProject: JD2407_envsmart
 * @BelongsPackage: com.briup.smart.implement
 * @Author: Fz
 * @CreateTime: 2024-09-24  16:00
 * @Description: TODO
 * @Version: 1.0
 */
public class Serverimpl implements Server, ConfigurationAware, PropertiesAware {
    private boolean isStop;
    private DBStore dbStore;
    private int port;
    private ServerSocket serverSocket;
    private Log log;
    @Override
    public void receive() throws Exception {
        serverSocket = new ServerSocket(port);
        log.info("网络模块服务端启动成功,port: " + port + ",等待客户端连接...");
        while (!isStop) {
            Socket socket = serverSocket.accept();
            log.info("客户端成功连接,socket: " + socket);
            Thread th = new Thread() {
                @Override
                public void run() {
                    ObjectInputStream ois = null;
                    try {
                        if (isStop) return;
                        ois = new ObjectInputStream(socket.getInputStream());
                        Object o = ois.readObject();
                        Collection<Environment> list = new ArrayList<>();
                        if (o instanceof Collection<?>) {
                            Collection<?> l = (Collection<?>) o;
                            for (Object object : l) {
                                if (object instanceof Environment) {
                                    list.add((Environment) object);
                                }
                            }
                        }
                        log.info("成功接收到集合对象，内含环境数据个数: " + list.size());
                        list.forEach(System.out::println);
                        dbStore.saveDB(list);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }finally {
                        if(ois!=null){
                            try {
                                ois.close();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        if(socket != null){
                            try {
                                socket.close();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }

                }
            };
            th.start();
        }
    }
    //关闭服务器端
    @Override
    public void shutdown() throws Exception {
        isStop = true;
        log.info("修改关闭标识isStop为true");
        if (serverSocket != null)
            serverSocket.close();
        log.info("服务端网络模块: shutdown执行完毕");
    }

    @Override
    public void setConfiguration(Configuration configuration) throws Exception {
        this.dbStore = configuration.getDbStore();
        this.log = configuration.getLogger();
    }

    @Override
    public void init(Properties properties) throws Exception {
        this.port =Integer.parseInt(properties.getProperty("port"));
        this.isStop = Boolean.parseBoolean(properties.getProperty("isStop"));
    }
}
