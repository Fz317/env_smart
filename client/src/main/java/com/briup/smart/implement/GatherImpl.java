package com.briup.smart.implement;

import com.briup.smart.env.Configuration;
import com.briup.smart.env.client.Gather;
import com.briup.smart.env.entity.Environment;
import com.briup.smart.env.support.ConfigurationAware;
import com.briup.smart.env.support.PropertiesAware;
import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

/**
 * @BelongsProject: JD2407_envsmart
 * @BelongsPackage: com.briup.smart.implement
 * @Author: Fz
 * @CreateTime: 2024-09-24  10:37
 * @Description: TODO
 * @Version: 1.0
 */
public class GatherImpl implements Gather,PropertiesAware,ConfigurationAware{
    private String filePath;
    @Override
    public Collection<Environment> gather() throws Exception {
        ArrayList<Environment> list = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line = null;
        while ((line = br.readLine()) != null) {
            String[] split = line.split("[|]");
            System.out.println(Arrays.toString(split));
            Environment environment = new Environment();
            //设置发送端id
            environment.setSrcId(split[0]);
            //设置树莓派系统id
            environment.setDesId(split[1]);
            //设置实验箱区域模块id(1-8)
            environment.setDevId(split[2]);
            //设置模块上传感器地址
            environment.setSensorAddress(split[3]);
            //设置传感器个数 String转int
            environment.setCount(Integer.parseInt(split[4]));
            //设置发送指令标号 3表示接收数据 16表示发送命令
            environment.setCmd(split[5]);
            //设置状态(默认1,表示成功) String类型转换为int类型
            environment.setStatus(Integer.parseInt(split[7]));
            //设置采集时间 时间戳 String转long
            environment.setGatherDate(new Timestamp(Long.parseLong(split[8])));
            //3.核心功能：根据arr[3](传感器地址)计算 name、环境值
            switch (split[3]) {
                case "16":
                    environment.setName("温度");
                    String temperature = split[6].substring(0, 4);
                    int t = Integer.parseInt(temperature, 16);
                    environment.setData((t * (0.00268127F)) - 46.85F);
                    list.add(environment);
                    Environment environment1 = cloneEnvironment(environment);
                    environment1.setName("湿度");
                    String humidity = split[6].substring(4, 8);
                    int h = Integer.parseInt(humidity, 16);
                    environment1.setData((h * 0.00190735F) - 6);
                    list.add(environment1);
                    break;
                case "256":
                    environment.setName("光照强度");
                    environment.setData(Integer.parseInt(split[6].substring(0, 4), 16));
                    list.add(environment);
                    break;
                case "1280":
                    environment.setName("二氧化碳浓度");
                    environment.setData(Integer.parseInt(split[6].substring(0, 4), 16));
                    list.add(environment);
                    break;
                default:
                    System.out.println("数据格式错误: " + split);
                    break;
            }
        }
        System.out.println("成功采集数据量"+list.size());
        return list;
    }
    private Environment cloneEnvironment(Environment e) {
        return new Environment(null, e.getSrcId(), e.getDesId(),
                e.getDevId(), e.getSensorAddress(), e.getCount(), e.getCmd(),
                e.getStatus(), 0, e.getGatherDate());
    }

    @Override
    public void init(Properties properties) throws Exception {
        this.filePath= properties.getProperty("gather-file-path");
    }

    @Override
    public void setConfiguration(Configuration configuration) throws Exception {

    }
}

