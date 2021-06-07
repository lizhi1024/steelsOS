package com.lzos.steels;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lzos.steels.admin.dao")
public class SteelsAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SteelsAdminApplication.class, args);
    }

}
