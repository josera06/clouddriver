package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

    private WebDriver driver = null;
    private long timeSleep = 2500;
    
    public void uploadFileTest(String user){
    
    }

    public void userLoginLogout(String user) throws InterruptedException {
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputUsername.sendKeys(user);
        inputPassword.sendKeys("1234");
        Thread.sleep(timeSleep);
        driver.findElement(By.id("submit")).click();
        Thread.sleep(timeSleep);
        driver.findElement(By.id("logout-element")).click();
    }

    public void createUser(String user) throws InterruptedException {
        driver.get("http:localhost:8080");
        Thread.sleep(timeSleep);
        driver.findElement(By.id("singup-link")).click();
        Thread.sleep(timeSleep);
        WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
        WebElement inputLastName = driver.findElement(By.id("inputLastName"));
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));

        inputFirstName.sendKeys(user);
        inputLastName.sendKeys(user);
        inputUsername.sendKeys(user);
        inputPassword.sendKeys("1234");
        Thread.sleep(timeSleep);
        driver.findElement(By.id("submit")).click();
        Thread.sleep(timeSleep);
        driver.findElement(By.id("backhome")).click();
    }

    public ApplicationTest(String browser) throws Exception {
        if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else {
            throw new Exception("navegador no sopordado");
        }
    }

    public void closeDriver() throws InterruptedException {
        Thread.sleep(timeSleep * 2);
        driver.quit();
    }

    public long getTimeSleep() {
        return timeSleep;
    }

}
