package Winbull.trade;

import org.openqa.selenium.*;
import com.aventstack.extentreports.Status;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

public class Premium {

    public static int[] execute(WebDriver driver) {
        int passCount = 0;
        int failCount = 0;
        By masters = By.xpath("//span[text()='Masters']");
        Bfun.click(driver, masters);

        By premi = By.xpath("//span[text()='Premium Group']");
        Bfun.click(driver, premi);

        int rows = ExcelUtils.premiumSheet.getLastRowNum();

        for (int i = 1; i <= rows; i++) {
            Row row = ExcelUtils.premiumSheet.getRow(i);

            if (row == null) {
                continue;
            }

            try {
                DataFormatter formatter = new DataFormatter();

                String grpname = formatter.formatCellValue(row.getCell(1)).trim();
                String gdes = formatter.formatCellValue(row.getCell(2)).trim();
                String activeStatus = formatter.formatCellValue(row.getCell(3)).trim();
                String updto = formatter.formatCellValue(row.getCell(4)).trim();
                String comnam = formatter.formatCellValue(row.getCell(5)).trim();
                String buy = formatter.formatCellValue(row.getCell(6)).trim();
                String sell = formatter.formatCellValue(row.getCell(6)).trim();
                String buyval = formatter.formatCellValue(row.getCell(6)).trim();
                String sellval = formatter.formatCellValue(row.getCell(7)).trim();

                Bfun.click(driver, By.xpath("//h4/a"));
                Bfun.type(driver, By.id("prem_group_name"), grpname);
                Bfun.type(driver, By.id("prem_group_desc"), gdes);
                if (activeStatus.equalsIgnoreCase("yes")) {

                    Bfun.setRadioButton(driver,
                    		 By.xpath("//*[@id=\"iframeForm\"]/div[2]/div[1]/div/div/div/label[1]"));

                    

                } else if (activeStatus.equalsIgnoreCase("no")) {
                    Bfun.setRadioButton(driver,
                    		 By.xpath("//*[@id=\"iframeForm\"]/div[2]/div[1]/div/div/div/label[2]"));
                }

                Bfun.setCheckboxWithBSValue(driver, "prem_table", comnam, buy,sell, buyval, sellval);
                Bfun.click(driver, By.xpath("//button[normalize-space()='Save']"));

                Thread.sleep(2000);
				Bfun.captureScreenshot(driver, "Commodity group updated" + i);

                Main.test.log(Status.PASS, "Premium group created successfully: ");
                ExcelUtils.writepremResult(i, "Pass", "");
                passCount++;
            } catch (Exception e) {

                Main.test.log(Status.FAIL, "Exception occurred: " + e.getMessage());
				Bfun.captureScreenshot(driver, "Update failed");

                ExcelUtils.writepremResult(i, "Fail", "");
                failCount++;

            }
        }
        return new int[]{passCount, failCount};

    }
}
