package Winbull.trade;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;

import Utils.PropertyUtil;

public class Popup {

    public static int[] execute(WebDriver driver) {
        int passCount = 0;
        int failCount = 0;

        Bfun.click(driver, By.xpath("//span[contains(text(),'Settings')]"));
        Bfun.click(driver, By.xpath("//span[contains(text(),'Popup')]"));

        int rows = ExcelUtils.popupSheet == null ? 0 : ExcelUtils.popupSheet.getLastRowNum();
        System.out.println("Popup: popupSheet rows=" + rows);

        String imagePath = "";
        try {
            imagePath = PropertyUtil.getProperty("imagePath");
            if (imagePath == null) {
                imagePath = "";
            }
        } catch (Exception e) {
            System.out.println("No imagePath in config: " + e.getMessage());
        }

        for (int i = 1; i <= rows; i++) {
            try {
                Bfun.refresh(driver);
                Thread.sleep(1000);
            } catch (Exception e) {}

            // Proactively ensure we are on the main listing page at start of each row 
            if (i > 1) {
                try {
                    if (driver.findElements(By.xpath("//h4/a[contains(text(),'Close')]")).size() > 0) {
                        Bfun.click(driver, By.xpath("//h4/a[contains(text(),'Close')]"));
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                }
            }

            Row row = ExcelUtils.popupSheet.getRow(i);

            if (row == null) {
                continue;
            }

            try {
                DataFormatter formatter = new DataFormatter();

                String popName = formatter.formatCellValue(row.getCell(1)).trim();
                String image = formatter.formatCellValue(row.getCell(2)).trim();
                String active = formatter.formatCellValue(row.getCell(3)).trim();
                String exec = formatter.formatCellValue(row.getCell(4)).trim();
                String add = formatter.formatCellValue(row.getCell(5)).trim();
                String edit = formatter.formatCellValue(row.getCell(6)).trim();
                String editName = formatter.formatCellValue(row.getCell(7)).trim();
                String editImage = formatter.formatCellValue(row.getCell(8)).trim();
                String delete = formatter.formatCellValue(row.getCell(9)).trim();

                if (popName.isEmpty()) {
                    continue;
                }

                if (!exec.equalsIgnoreCase("yes")) {
                    Main.test.log(Status.INFO, "Skipped row " + i + " - Execution flag: " + exec);
                    continue;
                }

                System.out.println("Processing Popup row " + i + ": Name=" + popName
                        + " | Image=" + image + " | Active=" + active
                        + " | Add=" + add + " | Edit=" + edit + " | Delete=" + delete);

                // ========== ADD ==========
                if (add.equalsIgnoreCase("yes")) {

                    // Click Add button - robust locator
                    Bfun.click(driver, By.xpath("//h4/a[contains(.,'Add')] | //h4/a[i[contains(@class,'plus')]] | //h4/a"));
                    Thread.sleep(2000);

                    // Enter Popup Name
                    Bfun.type(driver, By.id("pop_name"), popName);

                    // Upload Image if provided
                    if (!image.isEmpty() && !imagePath.isEmpty()) {
                        String fullImagePath = imagePath + "\\" + image;
                        java.io.File file = new java.io.File(fullImagePath);
                        if (!file.exists()) {
                            // Try adding .webp extension if base name doesn't exist
                            fullImagePath += ".webp";
                        }
                        System.out.println("Uploading image: " + fullImagePath);
                        Bfun.uploadFile(driver, By.id("pop_image"), fullImagePath);
                        Thread.sleep(1000);
                    }

                    // Set Active status
                    if (active.equalsIgnoreCase("yes")) {
                        try {
                            Bfun.setRadioButton(driver, By.xpath("//input[@id='pop_active_yes' or @id='active_yes' or contains(@id,'active_yes')] | //label[contains(.,'Yes')]/input"));
                        } catch (Exception e) {
                            Bfun.setRadioButton(driver, By.xpath("//*[@id='iframeForm']//label[1]//input | //*[@id='iframeForm']//div[contains(@class,'radio')]//label[1]"));
                        }
                    } else if (active.equalsIgnoreCase("no")) {
                        try {
                            Bfun.setRadioButton(driver, By.xpath("//input[@id='pop_active_no' or @id='active_no' or contains(@id,'active_no')] | //label[contains(.,'No')]/input"));
                        } catch (Exception e) {
                            Bfun.setRadioButton(driver, By.xpath("//*[@id='iframeForm']//label[2]//input | //*[@id='iframeForm']//div[contains(@class,'radio')]//label[2]"));
                        }
                    }

                    // Click Save
                    Bfun.click(driver, By.xpath("//button[normalize-space()='Save']"));
                    Thread.sleep(2000);

                    // Check toast message
                    String toast = Bfun.getToastMessageIfPresent(driver);
                    System.out.println("Add Toast: " + toast);

                    Bfun.captureScreenshot(driver, "Popup_Add_" + i);
                    
                    if (toast.toLowerCase().contains("success") || toast.toLowerCase().contains("successfully") || toast.isEmpty()) {
                        Main.test.log(Status.PASS, "Popup added successfully: " + popName);
                        ExcelUtils.writepopupResult(i, "Pass", toast);
                        passCount++;
                    } else {
                        Main.test.log(Status.FAIL, "Popup add failed: " + toast);
                        ExcelUtils.writepopupResult(i, "Fail", toast);
                        failCount++;
                    }
                }

                // ========== EDIT ==========
                if (edit.equalsIgnoreCase("yes")) {

                    // Search for the popup in the table
                    Bfun.click(driver, By.xpath("//input[@type='search']"));
                    Bfun.type(driver, By.xpath("//input[@type='search']"), popName);
                    Thread.sleep(1000);

                    // Click Edit button on the first matching row
                    Bfun.click(driver, By.xpath("//table//tbody//tr[1]//td[last()]//a[1]"));
                    Thread.sleep(1000);

                    // Update Popup Name with edit value
                    if (!editName.isEmpty()) {
                        Bfun.type(driver, By.id("pop_name"), editName);
                    } else {
                        // If editName is empty, we must still be on the edit form
                        // and might want to update the image only.
                    }

                    // Upload new image if provided
                    if (!editImage.isEmpty() && !imagePath.isEmpty()) {
                        String fullEditImagePath = imagePath + "\\" + editImage;
                        java.io.File file = new java.io.File(fullEditImagePath);
                        if (!file.exists()) {
                            fullEditImagePath += ".webp";
                        }
                        System.out.println("Uploading edit image: " + fullEditImagePath);
                        Bfun.uploadFile(driver, By.id("pop_image"), fullEditImagePath);
                        Thread.sleep(1000);
                    }

                    // Click Update
                    Bfun.click(driver, By.xpath("//button[normalize-space()='Update']"));
                    Thread.sleep(2000);

                    // Check toast message
                    String toast = Bfun.getToastMessageIfPresent(driver);
                    System.out.println("Edit Toast: " + toast);

                    Bfun.captureScreenshot(driver, "Popup_Edit_" + i);
                    
                    if (toast.toLowerCase().contains("success") || toast.toLowerCase().contains("successfully") || toast.isEmpty()) {
                        Main.test.log(Status.PASS, "Popup edited successfully: " + popName + " -> " + editName);
                        ExcelUtils.writepopupResult(i, "Pass", toast);
                        passCount++;
                    } else {
                        Main.test.log(Status.FAIL, "Popup edit failed: " + toast);
                        ExcelUtils.writepopupResult(i, "Fail", toast);
                        failCount++;
                    }
                }

                // ========== DELETE ==========
                if (delete.equalsIgnoreCase("yes")) {

                    // Determine the name to search — use editName if it was edited, else original
                    String searchName = (edit.equalsIgnoreCase("yes") && !editName.isEmpty())
                            ? editName : popName;

                    Bfun.click(driver, By.xpath("//input[@type='search']"));
                    Bfun.type(driver, By.xpath("//input[@type='search']"), searchName);
                    Thread.sleep(1000);

                    // Click Delete button on the first matching row
                    Bfun.click(driver, By.xpath("//table//tbody//tr[1]//td[last()]//a[2]"));

                    // Confirm deletion
                    Bfun.click(driver, By.id("commonConfirmBtn"));
                    Thread.sleep(2000);

                    // Check toast message
                    String toast = Bfun.getToastMessageIfPresent(driver);
                    System.out.println("Delete Toast: " + toast);

                    Bfun.captureScreenshot(driver, "Popup_Delete_" + i);
                    
                    if (toast.toLowerCase().contains("success") || toast.toLowerCase().contains("successfully") || toast.isEmpty()) {
                        Main.test.log(Status.PASS, "Popup deleted successfully: " + searchName);
                        ExcelUtils.writepopupResult(i, "Pass", toast);
                        passCount++;
                    } else {
                        Main.test.log(Status.FAIL, "Popup delete failed: " + toast);
                        ExcelUtils.writepopupResult(i, "Fail", toast);
                        failCount++;
                    }
                }

                // If none of add/edit/delete is yes, just log it
                if (!add.equalsIgnoreCase("yes") && !edit.equalsIgnoreCase("yes")
                        && !delete.equalsIgnoreCase("yes")) {
                    Main.test.log(Status.INFO, "No action (add/edit/delete) for row " + i);
                }

            } catch (Exception e) {
                System.out.println("Error at row " + i + ": " + e.getMessage());
                Main.test.log(Status.FAIL, "Exception at row " + i + ": " + e.getMessage());
                Bfun.captureScreenshot(driver, "Popup_Failed_" + i);
                ExcelUtils.writepopupResult(i, "Fail", e.getMessage());
                failCount++;

                // Navigate back so next row can still work
                try {
                    Bfun.click(driver, By.xpath("//span[contains(text(),'Settings')]"));
                    Bfun.click(driver, By.xpath("//span[contains(text(),'Popup')]"));
                } catch (Exception nav) {
                    System.out.println("Recovery navigation failed: " + nav.getMessage());
                }
            }
        }

        return new int[]{passCount, failCount};
    }
}
