package com.example.phonenumbersapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PhoneNumbersApiApplicationTests {
	@Autowired
	private ApplicationContext context;
	@Test
	void contextLoads() {
		assertNotNull(context);
	}

}
