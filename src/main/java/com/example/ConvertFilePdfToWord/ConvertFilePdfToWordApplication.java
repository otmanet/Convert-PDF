package com.example.ConvertFilePdfToWord;

import javax.annotation.Resource;

import com.example.ConvertFilePdfToWord.Service.StorageService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class ConvertFilePdfToWordApplication implements CommandLineRunner {
	@Resource
	StorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(ConvertFilePdfToWordApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		storageService.deleteAll();
		storageService.init();

	}

}
