import com.briup.smart.env.Configuration;
import com.briup.smart.env.client.Client;
import com.briup.smart.env.client.Gather;
import com.briup.smart.env.entity.Environment;
import com.briup.smart.env.implement.ConfigurationImpl;
import com.briup.smart.implement.Clientimpl;
import com.briup.smart.implement.GatherBackImpl;
import org.junit.Test;

import java.util.Collection;

/**
 * @BelongsProject: JD2407_envsmart
 * @BelongsPackage: PACKAGE_NAME
 * @Author: Fz
 * @CreateTime: 2024-09-24  16:14
 * @Description: TODO
 * @Version: 1.0
 */
public class Client_test {

    @Test
    public void test1() throws Exception {
        Gather gather = ConfigurationImpl.getInstance().getGather();
        Collection<Environment> gather1 = gather.gather();
        gather1.forEach(System.out::println);
        System.out.println("共采集："+gather1.size()+"条数据");
    }
    @Test
    public void test2() throws Exception {
        Configuration configuration = ConfigurationImpl.getInstance();
        Gather gt = configuration.getGather();
        Collection<Environment> envs = gt.gather();
        Client client = configuration.getClient();
        client.send(envs);
    }

}
