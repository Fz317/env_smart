package com.briup.smart.env.implement;

import com.briup.smart.env.Configuration;
import com.briup.smart.env.support.ConfigurationAware;
import com.briup.smart.env.support.PropertiesAware;
import com.briup.smart.env.util.Log;
import org.apache.log4j.Logger;

import java.util.Properties;


/**
 * @BelongsProject: JD2407_envsmart
 * @BelongsPackage: com.briup.smart.env.implement
 * @Author: Fz
 * @CreateTime: 2024-09-30  16:40
 * @Description: TODO
 * @Version: 1.0
 */
public class Logimpl implements Log, ConfigurationAware, PropertiesAware {

    private Logger logger = Logger.getLogger(Logimpl.class);
    @Override
    public void setConfiguration(Configuration configuration) throws Exception {

    }

    @Override
    public void init(Properties properties) throws Exception {

    }

    @Override
    public void debug(String message) {
        logger.debug(message);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }

    @Override
    public void fatal(String message) {
        logger.fatal(message);
    }
}
