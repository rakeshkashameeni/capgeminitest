package com.capgemini;

//import io.cucumber.co
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class MySharedClass {
    WebDriver driver = null;

   @Before
    public void startBrowser(){

        System.setProperty("webdriver.chrome.driver", "chromedriver89");
        if (driver==null)
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    public WebDriver getDriver(){
        return this.driver;
    }


    @After
    public void closeBrowser(Scenario scenario){
        try
        {
            driver.quit();
        }catch (Exception ex){

        }


    }
}
