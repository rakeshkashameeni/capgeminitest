package com.capgemini;

import io.cucumber.java.en.*;
//import cucumber.api.java.en.Given;
//import cucumber.api.java.en.Then;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.io.FileWriter;
import java.io.IOException;

public class SeleniumSteps {
    WebDriver driver;
    public SeleniumSteps (MySharedClass mySharedClass){
        this.driver = mySharedClass.getDriver();
    }




    @Given("^I am on the home page of vic roads site$")
    public void iAmOnTheHomePageOfVicRoads() throws Throwable {
         driver.get("https://www.vicroads.vic.gov.au/");

    }

    public By GetXpath(String x){
            return  By.xpath("//*[contains(text(),'"+x+"')]");
    }

    public By GetFee(String month){
        return  By.xpath("//*[contains(text(),'"+month+" months')]/following-sibling::dd");

    }

    @Then("^I should be able to calculate and see the rego fees for various vehicle types$")
    public void IShouldBeAbleToSeeRegoFees() throws Throwable {

        JSONArray carFees = GetThreeMonthFees();
        String threeMonthFee = carFees.get(0).toString().replace("$","");
        String sixMonthFee = carFees.get(1).toString().replace("$","");
        String twelveMonthFee = carFees.get(2).toString().replace("$","");

        Assert.assertTrue(Double.parseDouble(threeMonthFee) < Double.parseDouble(sixMonthFee) );
        Assert.assertTrue(Double.parseDouble(sixMonthFee) < Double.parseDouble(twelveMonthFee) );

        JSONArray peopleMoverFees = new JSONArray();
        JSONArray uteFees = new JSONArray();
        JSONArray motorCycleFees = new JSONArray();

        JSONObject obj = new JSONObject();

        obj.put("car", carFees);
        obj.put("people mover - mini bus", peopleMoverFees);
        obj.put("ute", uteFees);
        obj.put("motorcycle", motorCycleFees);

        WriteToFile(obj);
    }

    public JSONArray GetThreeMonthFees() throws  Exception{
        new Actions(driver).moveToElement(driver.findElement(GetXpath("I want to"))).build().perform();
        WebElement w1 =  driver.findElements(GetXpath("Calculate registration fees")).get(0);
        ((JavascriptExecutor)driver).executeScript("arguments[0].click()",w1);
        //Thread.sleep(2000);
        try {
            driver.findElement(GetXpath("get started")).click();
        }catch (StaleElementReferenceException ex){
            driver.findElement(GetXpath("get started")).click();
        }
        driver.findElement(By.id("txtPostCode")).sendKeys("3001");
        driver.findElement(GetXpath("Next")).click();

        driver.findElement(By.id("rbVehicleTypeCar")).click();
        //rbVehicleTypeMiniBus , rbVehicleWeightLessThanFourDotFiveTonnes  rbSeatingCapcityUptoNine rbFuelTypePetrolDieselLpg
        //rbVehicleTypeLightTruck  rbVehicleWeightLessThanFourDotFiveTonnes   rbCarryCapacity2TonnesOrLess petrol  rbPrimaryProductionYes which-discount_which-discount-1
        //rbVehicleTypeMotorCycle rbEngineCapacityLessThan61cc petrol rbPrimaryProductionYes conession, healthcared which-discount_which-discount-1

        driver.findElement(GetXpath("Next")).click();

        driver.findElement(By.id("rbFuelTypePetrolDieselLpg")).click();

        driver.findElement(GetXpath("Next")).click();

        ((JavascriptExecutor)driver).executeScript("arguments[0].click()",driver.findElement(By.id("rbConcessionCardYes")));
        WebElement w = driver.findElement(By.xpath("//*[@for='concession-type_concession-type-1']"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click()",w);


        WebElement calculate = driver.findElement(By.xpath("//button[contains(text(),'Calculate')]"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click()",calculate);

        String threeMonthAmount = driver.findElement(GetFee("3")).getText();
        String sixMonthAmount = driver.findElement(GetFee("6")).getText();
        String twelveMonthAmount = driver.findElement(GetFee("12")).getText();

        JSONArray carFees = new JSONArray();
        carFees.add(threeMonthAmount);
        carFees.add(sixMonthAmount);
        carFees.add(twelveMonthAmount);

        return carFees;
    }

    public void WriteToFile(JSONObject obj) throws Exception{
        FileWriter file = null;
        try {
            file = new FileWriter("out.json");
            file.write(obj.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + obj);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            file.flush();
            file.close();
        }
    }


}
