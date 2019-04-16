package com.jia.system;

import org.junit.Test;

import java.util.Properties;

public class ShowProperties {

    @Test
    public void getProperties(){
        // 获取系统属性，虚拟机属性
        Properties properties = System.getProperties();

        // 当然也可以通过 System.getProperty("参数"); 直接获取信息

        // jvm 运行时环境供应商
        System.out.println(properties.getProperty("java.vendor"));
        // 供应商网址
        System.out.println("java_vendor_url:" + properties.getProperty("java.vendor.url"));
        // java home 路径
        System.out.println("java_home:" + properties.getProperty("java.home"));
        // java 类格式版本号
        System.out.println("java_class_version:" + properties.getProperty("java.class.version"));
        // classpath 路径
        System.out.println("java_class_path:" + properties.getProperty("java.class.path"));
        // 操作系统的名称
        System.out.println("os_name:" + properties.getProperty("os.name"));
        // 操作系统的架构
        System.out.println("os_arch:" + properties.getProperty("os.arch"));
        // 操作系统的版本
        System.out.println("os_version:" + properties.getProperty("os.version"));
        // 操作系统当前的用户的名称
        System.out.println("user_name:" + properties.getProperty("user.name"));
        // 操作系统当前用户的根目录
        System.out.println("user_home:" + properties.getProperty("user.home"));
        // 当前工程的根目录的绝对路径
        System.out.println("user_dir:" + properties.getProperty("user.dir"));
        // java 运行环境的规范版本号
        System.out.println("java_vm_specification_version:" + properties.getProperty("java.vm.specification.version"));
        // java 运行环境的规范供应商
        System.out.println("java_vm_specification_vendor:" + properties.getProperty("java.vm.specification.vendor"));
        // Java 运行时环境规范名称
        System.out.println("java_vm_specification_name:" + properties.getProperty("java.vm.specification.name"));
        // jvm 的实现版本号
        System.out.println("java_vm_version:" + properties.getProperty("java.vm.version"));
        // jvm实现供应商
        System.out.println("java_vm_vendor:" + properties.getProperty("java.vm.vendor"));
        // jvm 实现名称
        System.out.println("java_vm_name:" + properties.getProperty("java.vm.name"));
        // 一个或者多个拓展目录的路径
        System.out.println("java_ext_dirs:" + properties.getProperty("java.ext.dirs"));
        // 文件分隔符，Linux下是 “/”
        System.out.println("file_separator:" + properties.getProperty("file.separator"));
        // 路径分隔符，Linux下是 ”：“
        System.out.println("path_separator:" + properties.getProperty("path.separator"));
        // 行分隔符，Linux下是 “\n"
        System.out.println("line_separator:" + properties.getProperty("line.separator"));

        // 也可以自己设置一些属性
        properties.setProperty("myProperty", "java");
        System.out.println("myProperty:" + properties.getProperty("myProperty"));
    }
}
