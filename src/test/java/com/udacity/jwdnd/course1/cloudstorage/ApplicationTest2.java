package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest2 {
        protected static WebDriver driver;
    @LocalServerPort
    private int port;

    private static final String FIRSTNAME="Dukuze";
    private static final String LASTNAME="Orla";
    private static final String USERNAME="dukuze";
    private static final String PASSWORD="Dukuze01";

    @BeforeAll
    static void beforeAll(){
        WebDriverManager.firefoxdriver().setup();
    }
    @BeforeEach
    public void beforeEach(){
        this.driver =new FirefoxDriver();
    }

    @AfterEach
    public void afterEach(){
        if(this.driver != null){
            driver.quit();
        }
    }

    @Test
    public void unAuthorisedUserAccess(){
        driver.get("http://localhost:8080/home");
        
    }


    @Test
    public void testUserSignupAndLogin() throws InterruptedException {



    }

}
