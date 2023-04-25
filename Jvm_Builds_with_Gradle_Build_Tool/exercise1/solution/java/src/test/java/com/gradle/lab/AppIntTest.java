package com.gradle.lab;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppIntTest {

    /*@Test void messageHash() throws InterruptedException {
        Thread.sleep(5000);
    }*/

    @Test void selenium() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        //WebDriver driver = new FirefoxDriver();
        try {
            driver.get("https://www.google.com/");
            String title = driver.getTitle();
            assertEquals("Google", title);
        } finally {
            Thread.sleep(3000);
            driver.quit();
        }
    }

}
