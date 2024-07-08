package com.GuideIn;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import jakarta.activation.DataSource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:mysql://localhost:3306/testdb",
        "spring.datasource.username=testuser",
        "spring.datasource.password=testpassword",
        "spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class GuideInApplicationTests {
	
    @MockBean
    private DataSource dataSource;

	@Test
	void contextLoads() {
	}

}
