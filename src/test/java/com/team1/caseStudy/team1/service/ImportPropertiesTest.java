package com.team1.caseStudy.team1.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ImportPropertiesTest {

    @Test
    void testGettersAndSetters() {
        ImportProperties importProperties = new ImportProperties();
        String testFilePath = "test/path/to/file";

        // Test setter
        importProperties.setFilepath(testFilePath);

        // Test getter
        assertEquals(testFilePath, importProperties.getFilepath());
    }
//
//    @Test
//    void testConfigurationPropertiesBinding() {
//        ApplicationContextRunner contextRunner = new ApplicationContextRunner()
//                .withUserConfiguration(TestConfig.class)
//                .withPropertyValues("import.filepath=application/test/path");
//
//        contextRunner.run(context -> {
//            ImportProperties importProperties = context.getBean(ImportProperties.class);
//
//            assertNotNull(importProperties);
//            assertEquals("application/test/path", importProperties.getFilepath());
//        });
//    }

    @Configuration
    static class TestConfig {
        @Bean
        public ImportProperties importProperties() {
            return new ImportProperties();
        }
    }
}
