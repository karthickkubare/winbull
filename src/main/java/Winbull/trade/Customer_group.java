package Winbull.trade;

import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.Status;

public class Customer_group {

    public static int[] execute(WebDriver driver) {
        int passCount = 0;
        int failCount = 0;
        By masters = By.xpath("//span[text()='Masters']");
        Bfun.click(driver, masters);

        By cgroup = By.xpath("//span[text()='Customer Group']");
        Bfun.click(driver, cgroup);

        Bfun.click(driver, By.xpath("//*[@id='grid-data']/tbody/tr/td[4]/a[1]"));

        int rows = ExcelUtils.cusgrpSheet.getLastRowNum();

        for (int i = 1; i <= rows; i++) {

            Row row = ExcelUtils.cusgrpSheet.getRow(i);

            if (row == null) {
                continue;
            }

            try {
                DataFormatter formatter = new DataFormatter();

                String customerName = formatter.formatCellValue(row.getCell(0));
                String groupName = formatter.formatCellValue(row.getCell(1));
                String execu = formatter.formatCellValue(row.getCell(2));

                System.out.println("DEBUG row " + i + ": Cust=" + customerName + ", Grp=" + groupName + ", Exec=" + execu);

                if (customerName.isEmpty()) {
                    continue;
                }

                if (!execu.equalsIgnoreCase("yes")) {
                    Main.test.log(Status.INFO, "Skipped row " + i + " - Execution: " + execu);
                    continue;
                }

                System.out.println("Processing Customer: " + customerName + " | Group: " + groupName);

                // Find the matching customer row in the table and set the group dropdown
                boolean customerFound = Bfun.setCustomerGroup(driver, customerName, groupName);

                if (!customerFound) {
                    Main.test.log(Status.FAIL, "Customer not found in table: " + customerName);
                    ExcelUtils.writecusgrpResult(i, "Fail", "Customer not found: " + customerName);
                    failCount++;
                    continue;
                }

                // If found and dropdown set successfully, mark row as PASS in Excel
                ExcelUtils.writecusgrpResult(i, "Pass", "");
                passCount++;

            } catch (Exception e) {
                Main.test.log(Status.FAIL, "Exception at row " + i + ": " + e.getMessage());
                ExcelUtils.writecusgrpResult(i, "Fail", e.getMessage());
                failCount++;
            }
        }
        
        // After configuring all customers in the form, perform a single bulk Update
        try {
            // Click the Update button once at the end
            Bfun.click(driver, By.xpath("//button[normalize-space()='Update']"));
            Thread.sleep(2000);

            // Check toast message
            String toast = Bfun.getToastMessageIfPresent(driver);
            System.out.println("Bulk Update Toast: " + toast);

            Bfun.captureScreenshot(driver, "CustomerGroup_BulkUpdate");
            Main.test.log(Status.PASS, "Customer Groups successfully updated in bulk.");
        } catch (Exception e) {
            Main.test.log(Status.FAIL, "Exception during final bulk update: " + e.getMessage());
        }

        return new int[]{passCount, failCount};
    }

}
