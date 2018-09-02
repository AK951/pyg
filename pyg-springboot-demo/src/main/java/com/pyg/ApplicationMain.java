package com.pyg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Description:
 *
 * @author AK
 * @date 2018/8/23 16:15
 * @since 1.0.0
 */
@SpringBootApplication
public class ApplicationMain {
    // 入口代码,固定写法
    // 执行入口函数后
    // 1,加载内容tomcat服务器
    // 2,加载web项目服务环境
    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class, args);
    }

}