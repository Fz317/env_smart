import com.briup.smart.env.entity.Environment;
import com.briup.smart.env.implement.ConfigurationImpl;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @BelongsProject: JD2407_envsmart
 * @BelongsPackage: PACKAGE_NAME
 * @Author: Fz
 * @CreateTime: 2024-10-07  15:19
 * @Description: TODO
 * @Version: 1.0
 */
public class Server_test {
    @Test
    public  void test1() throws Exception {
        ConfigurationImpl instance = ConfigurationImpl.getInstance();
        Collection<Environment> list = new ArrayList<>();
        Environment e1 = new Environment("温度","","","","",1,"",1,16.1f,null);
        Environment e2 = new Environment("温度","","","","",1,"",1,16.1f,null);
        Environment e3 = new Environment("温度","","","","",1,"",1,16.1f,null);
        Collections.addAll(list,e1,e2,e3);
        instance.getDbStore().saveDB(list);
    }
    @Test
    public void test2() throws Exception {
        ConfigurationImpl.getInstance().getServer().receive();
    }
}
