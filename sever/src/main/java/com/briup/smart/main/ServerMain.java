package com.briup.smart.main;
import com.briup.smart.env.implement.ConfigurationImpl;
/**
 * @BelongsProject: JD2407_envsmart
 * @BelongsPackage: com.briup.smart.main
 * @Author: Fz
 * @CreateTime: 2024-09-25  10:00
 * @Description: TODO
 * @Version: 1.0
 */
public class ServerMain {
    public static void main(String[] args) throws Exception {
        ConfigurationImpl.getInstance().getServer().receive();
    }
}
