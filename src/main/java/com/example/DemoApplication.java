package com.example;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;
import java.util.Calendar;
import java.util.Date;


@MapperScan("com.example.mapper") //扫描的mapper
@SpringBootApplication
//函数的启动


public class DemoApplication {



	Calendar calendar = Calendar.getInstance();
	Date dateone = calendar.getTime();
	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);

	}


	/**
	 * 2019-10-24
	 * 文件上传大小的配置
	 * 2020-03-21修改，进行测试用，已恢复
	 * 2020-06-12修改，数据测试使用，已恢复
	 * @return
	 */
	

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		//单个文件最大
		factory.setMaxFileSize("20480KB");
		/// 设置总上传数据总大小
		factory.setMaxRequestSize("20971520KB");

		return factory.createMultipartConfig();
	}

}
