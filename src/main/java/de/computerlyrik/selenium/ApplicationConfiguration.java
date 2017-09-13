package de.computerlyrik.selenium;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan(basePackages = { "de.computerlyrik.selenium", "it.ozimov.springboot" })
@EnableAsync
@EnableScheduling
public class ApplicationConfiguration {

}
