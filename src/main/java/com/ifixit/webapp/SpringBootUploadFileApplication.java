/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ifixit.webapp;

import com.ifixit.webapp.service.StorageService;
import javax.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author thuyetlv
 */
@SpringBootApplication
public class SpringBootUploadFileApplication implements CommandLineRunner {

    @Resource
    StorageService storageService;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootUploadFileApplication.class, args);
    }

    @Override
    public void run(String... arg) throws Exception {
        System.out.println("----------run----------------");
//        storageService.deleteAll();
//        System.out.println("----------deleteAll----------------");
//        storageService.init();
//        System.out.println("----------init----------------");
    }
}
