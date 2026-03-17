package Winbull.trade;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.Status;

public class RpanelSet {

    public static int[] execute(WebDriver driver) {
        int passCount = 0;
        int failCount = 0;

        // Navigate to Masters > R-Panel Setting
        Bfun.click(driver, By.xpath("//span[text()='Settings']"));
        Bfun.click(driver, By.xpath("//*[@id='sidebar']//li[6]//li[2]//span"));

        int rows = ExcelUtils.rpanelsetSheet == null ? 0 : ExcelUtils.rpanelsetSheet.getLastRowNum();
        System.out.println("RpanelSet: rpanelsetSheet rows=" + rows);

        for (int i = 1; i <= rows; i++) {

            Row row = ExcelUtils.rpanelsetSheet.getRow(i);

            if (row == null) {
                continue;
            }

            try {
                DataFormatter formatter = new DataFormatter();

                String goldWeight = formatter.formatCellValue(row.getCell(1)).trim();
                String silverWeight = formatter.formatCellValue(row.getCell(2)).trim();
                String goldRoundOff = formatter.formatCellValue(row.getCell(3)).trim();
                String silverRoundOff = formatter.formatCellValue(row.getCell(4)).trim();
                String exec = formatter.formatCellValue(row.getCell(5)).trim();

                if (!exec.equalsIgnoreCase("yes")) {
                    Main.test.log(Status.INFO, "Skipped row " + i + " - Execution flag: " + exec);
                    continue;
                }

                System.out.println("Processing RpanelSet: GoldWeight=" + goldWeight
                        + " | SilverWeight=" + silverWeight
                        + " | GoldRoundOff=" + goldRoundOff
                        + " | SilverRoundOff=" + silverRoundOff);

                // Select Gold Weight from dropdown
                if (!goldWeight.isEmpty()) {
                    Bfun.type(driver, By.id("rpsg_weight"), goldWeight);
                    Thread.sleep(500);
                }

                // Select Silver Weight from dropdown
                if (!silverWeight.isEmpty()) {
                    Bfun.type(driver, By.id("rpss_weight"), silverWeight);
                    Thread.sleep(500);
                }

                // Select Gold Round Off from dropdown
                if (!goldRoundOff.isEmpty()) {
                    Bfun.selectDropdownCaseInsensitive(driver, By.id("rpsg_roundoff"), goldRoundOff);
                    Thread.sleep(500);
                }

                // Select Silver Round Off from dropdown
                if (!silverRoundOff.isEmpty()) {
                    Bfun.selectDropdownCaseInsensitive(driver, By.id("rpss_roundoff"), silverRoundOff);
                    Thread.sleep(500);
                }

                // Click Update/Save
                Bfun.click(driver, By.xpath("//button[normalize-space()='Update' or normalize-space()='Save']"));
                Thread.sleep(2000);

                // Check toast message
                String toast = Bfun.getToastMessageIfPresent(driver);
                System.out.println("Toast: " + toast);

                // Capture screenshot
                Bfun.captureScreenshot(driver, "RpanelSet_Row_" + i);

                Main.test.log(Status.PASS, "R-Panel Setting updated successfully: "
                        + "GoldWeight=" + goldWeight + " | SilverWeight=" + silverWeight
                        + " | GoldRoundOff=" + goldRoundOff + " | SilverRoundOff=" + silverRoundOff);
                ExcelUtils.writerpanelsetResult(i, "Pass", "");
                passCount++;

            } catch (Exception e) {
                System.out.println("Error at row " + i + ": " + e.getMessage());
                Main.test.log(Status.FAIL, "Exception at row " + i + ": " + e.getMessage());
                Bfun.captureScreenshot(driver, "RpanelSet_Failed_" + i);
                ExcelUtils.writerpanelsetResult(i, "Fail", e.getMessage());
                failCount++;

                // Navigate back so next row can still work
                try {
                    Bfun.click(driver, By.xpath("//span[text()='Masters']"));
                    Bfun.click(driver, By.xpath("//span[text()='R-Panel Setting']"));
                } catch (Exception nav) {
                    System.out.println("Recovery navigation failed: " + nav.getMessage());
                }
            }
        }

        return new int[]{passCount, failCount};
    }
}
