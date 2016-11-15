package cn.bmob.relationdemo;

import cn.bmob.v3.BmobUser;

/**
 * Created by nothing on 2016/11/14.
 */

public class MyUser extends BmobUser {
    private Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "age=" + age +
                '}';
    }
}
