package com.briup.smart.implement;

/**
 * @BelongsProject: JD2407_envsmart
 * @BelongsPackage: com.briup.smart.implement
 * @Author: Fz
 * @CreateTime: 2024-09-24  11:39
 * @Description: TODO
 * @Version: 1.0
 */

import java.io.Serializable;
public class Student implements Serializable {
    int id;
    String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
