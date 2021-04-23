package com.capgemini;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
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

        if (scenario.isFailed()) {
           scenario.embed(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES), "image/png");
           scenario.write("Scenario failed");
        }else{
           scenario.write("Scenario passed");
        }

        try {
            this.driver.quit();
        }catch(Exception ex){

        }
    }
}
