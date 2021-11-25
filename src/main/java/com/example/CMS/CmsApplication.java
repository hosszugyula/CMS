package com.example.CMS;

import com.example.CMS.utils.DatabaseInitializer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class CmsApplication {

	private final DatabaseInitializer dbInitializer;

	public static void main(String[] args) {
		SpringApplication.run(CmsApplication.class, args);
	}

}
