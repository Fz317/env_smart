import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @BelongsProject: JD2407_envsmart
 * @BelongsPackage: PACKAGE_NAME
 * @Author: Fz
 * @CreateTime: 2024-09-30  16:33
 * @Description: TODO
 * @Version: 1.0
 */
public class log4j_test {
    @Test
    public void test1(){
        //获取日志生成器
        Logger logger = Logger.getRootLogger();
        logger.debug("调试级别");
        logger.info("信息级别");
        logger.warn("警告级别");
        logger.error("错误级别");
    }
}
