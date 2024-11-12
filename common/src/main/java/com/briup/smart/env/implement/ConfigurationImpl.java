package com.briup.smart.env.implement;

import com.briup.smart.env.Configuration;
import com.briup.smart.env.client.Client;
import com.briup.smart.env.client.Gather;
import com.briup.smart.env.server.DBStore;
import com.briup.smart.env.server.Server;
import com.briup.smart.env.support.ConfigurationAware;
import com.briup.smart.env.support.PropertiesAware;
import com.briup.smart.env.util.Backup;
import com.briup.smart.env.util.Log;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.util.*;

/**
 * @BelongsProject: JD2407_envsmart
 * @BelongsPackage: com.briup.smart.env.implement
 * @Author: Fz
 * @CreateTime: 2024-09-27  14:54
 * @Description: TODO
 * @Version: 1.0
 */
public class ConfigurationImpl implements Configuration {
    private static Map<String, Object> map = new HashMap<>();
    private static ConfigurationImpl configuration = new ConfigurationImpl();
    private static Properties prop = new Properties();
    private ConfigurationImpl() {}
    public static ConfigurationImpl getInstance() {
        return configuration;
    }
    static {
        String filePath = "conf.xml";
        try {
            InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream(filePath);
            SAXReader saxReader = new SAXReader();
            Document read = saxReader.read(resourceAsStream);
            List<Element> elements = read.getRootElement().elements();
            for (Element e : elements) {
                String name = e.getName();
                Attribute aClass = e.attribute("class");
                String stringValue = aClass.getStringValue();
                Object o = Class.forName(stringValue).newInstance();
                map.put(name, o);
                List<org.dom4j.Element> secElements = e.elements();
                if (secElements != null && secElements.size() > 0) {
                    for (Element element : secElements) {
                        prop.setProperty(element.getName(), element.getText());
                    }
                }
            }
            Collection<Object> coll = map.values();
            for (Object obj : coll) {
                if (obj instanceof PropertiesAware) {
                    PropertiesAware propertiesAware = (PropertiesAware) obj;
                    propertiesAware.init(prop);
                }
                if (obj instanceof ConfigurationAware) {
                    ConfigurationAware configurationAware = (ConfigurationAware) obj;
                    configurationAware.setConfiguration(configuration);
                }
            }
        }catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Log getLogger() throws Exception {
        return (Log)map.get("logger");
    }

    @Override
    public Server getServer() throws Exception {
        return (Server)map.get("server");
    }

    @Override
    public Client getClient() throws Exception {
        return (Client)map.get("client");
    }

    @Override
    public DBStore getDbStore() throws Exception {
        return (DBStore)map.get("dbStore");
    }

    @Override
    public Gather getGather() throws Exception {
        return (Gather)map.get("gather");
    }

    @Override
    public Backup getBackup() throws Exception {
        return (Backup)map.get("backup");
    }
}
