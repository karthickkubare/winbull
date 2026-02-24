package Bullion.Bullion_Automation;

import org.openqa.selenium.WebDriver;

import utils.ExcelUtils;

public class Main {

    static WebDriver driver;
y
    public static void main(String[] args) {

     
        driver.manage().window().maximize();
        driver.get("http://bullion_v4.logimaxindia.com/admin/");

        // Excel file path
        String path = "D:\\Bullion\\bull\\Bullion.xlsx";
        ExcelUtils excel = new ExcelUtils(path);
        SheetRunner.runMaster(driver, excel);

  
        excel.saveAndClose();   // preferred over static call
        driver.quit();
    }
}
