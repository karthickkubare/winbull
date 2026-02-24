package Bullion.Bullion_Automation;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import Bullion_Automation.Functions;


import utils.ExcelUtils;


public class Login {
	
	 Functions fn = new Functions(driver);
    public static int[] execute(WebDriver driver, ExcelUtils excel) {

        int pass = 0;
        int fail = 0;

        int lastRow = excel.getLastRow("Login");
       

        for (int i = 1; i <= lastRow; i++) {

            try {
                String url  = excel.getCellData("Login", i, 0);
                String user = excel.getCellData("Login", i, 1);
                String pwd  = excel.getCellData("Login", i, 2);

                driver.get(url);

                fn.enterTextById("user_name", user);
                fn.enterTextById("user_password", pwd);
                fn.clickById("login");

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1")));

                excel.setCellData("Login", i, 3, "PASS");
                pass++;

            } catch (Exception e) {
                excel.setCellData("Login", i, 3, "FAIL");
                fail++;
                System.out.println("Login failed at row: " + i + " | Reason: " + e.getMessage());
            }
        }

        return new int[] { fail, pass };
    }
}
