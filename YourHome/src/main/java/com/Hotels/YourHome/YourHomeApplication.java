package com.Hotels.YourHome;


import com.Hotels.YourHome.config.FileStorageProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;


@MapperScan("com.Hotels.YourHome.dao")
@MapperScan("com.Hotels.YourHome.projections")
@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties({FileStorageProperties.class})
public class YourHomeApplication{

	public static void main(String[] args) {
		SpringApplication.run(YourHomeApplication.class, args);
	}

}
