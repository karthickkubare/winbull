package Winbull.trade;

import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.Status;

public class Margin {

    public static int[] execute(WebDriver driver) {
        int passCount = 0;
        int failCount = 0;

        // Navigate to Masters > Customer Margin
        Bfun.click(driver, By.xpath("//span[text()='Masters']"));
        Bfun.click(driver, By.xpath("//span[text()='Customer Margin']"));

        int rows = ExcelUtils.cusmarginSheet == null ? 0 : ExcelUtils.cusmarginSheet.getLastRowNum();
        System.out.println("Margin: cusmarginSheet rows=" + rows);

        for (int i = 1; i <= rows; i++) {

            Row row = ExcelUtils.cusmarginSheet.getRow(i);

            if (row == null) {
                continue;
            }

            try {
                DataFormatter formatter = new DataFormatter();

                String cusname  = formatter.formatCellValue(row.getCell(1)).trim();
                String maramnt  = formatter.formatCellValue(row.getCell(2)).trim();
                String paytyp   = formatter.formatCellValue(row.getCell(3)).trim();
                String exec     = formatter.formatCellValue(row.getCell(4)).trim();

                if (cusname.isEmpty()) {
                    continue;
                }

                if (!exec.equalsIgnoreCase("yes")) {
                    Main.test.log(Status.INFO, "Skipped row " + i + " - Execution flag: " + exec);
                    continue;
                }

                System.out.println("Processing Margin: Customer=" + cusname
                        + " | Amount=" + maramnt + " | PayType=" + paytyp);

                // Click the Add (+) button to open the form
                Bfun.click(driver, By.xpath("//a[contains(@class,'add_new')]"));
                Thread.sleep(1000);

                // Select Customer Name from dropdown (case-insensitive)
                Bfun.selectDropdownCaseInsensitive(driver, By.id("mar_customer"), cusname);

                // Wait for Available Balance to auto-load
                Thread.sleep(1000);

                // Enter Margin Amount
                Bfun.type(driver, By.id("mar_amount"), maramnt);

                // Select Payment Type (case-insensitive)
                Bfun.selectDropdownCaseInsensitive(driver, By.id("mar_mode"), paytyp);

                // Click Save
                Bfun.click(driver, By.id("submit"));
                Thread.sleep(2000);

                // Check toast message
                String toast = Bfun.getToastMessageIfPresent(driver);
                System.out.println("Toast: " + toast);

                // Capture screenshot
                Bfun.captureScreenshot(driver, "Margin_Row_" + i);

                Main.test.log(Status.PASS, "Margin added for: " + cusname
                        + " | Amount: " + maramnt + " | PayType: " + paytyp);
                ExcelUtils.writemarginResult(i, "Pass", "");
                passCount++;

            } catch (Exception e) {
                System.out.println("Error at row " + i + ": " + e.getMessage());
                Main.test.log(Status.FAIL, "Exception at row " + i + ": " + e.getMessage());
                ExcelUtils.writemarginResult(i, "Fail", e.getMessage());
                failCount++;

                // Navigate back to list page so next row can still work
                try {
                    Bfun.click(driver, By.xpath("//span[text()='Masters']"));
                    Bfun.click(driver, By.xpath("//span[text()='Customer Margin']"));
                } catch (Exception nav) {
                    System.out.println("Recovery navigation failed: " + nav.getMessage());
                }
            }
        }

        return new int[]{passCount, failCount};
    }

}