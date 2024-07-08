package com.GuideIn;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.activation.DataSource;

@SpringBootTest
class GuideInApplicationTests {
	
    @MockBean
    private DataSource dataSource;

	@Test
	void contextLoads() {
	}

}
