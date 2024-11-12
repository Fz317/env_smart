package com.briup.smart.Main;
import com.briup.smart.env.Configuration;
import com.briup.smart.env.client.Client;
import com.briup.smart.env.client.Gather;
import com.briup.smart.env.entity.Environment;
import com.briup.smart.env.implement.ConfigurationImpl;

import java.util.Collection;

/**
 * @BelongsProject: JD2407_envsmart
 * @BelongsPackage: com.briup.smart.Main
 * @Author: Fz
 * @CreateTime: 2024-09-25  10:01
 * @Description: TODO
 * @Version: 1.0
 */
public class ClientMain {
    public static void main(String[] args) throws Exception {
        Configuration configuration = ConfigurationImpl.getInstance();
        Gather gt = configuration.getGather();
        Collection<Environment> envs = gt.gather();
        Client client = configuration.getClient();
        client.send(envs);
    }
}
