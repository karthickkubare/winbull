package Winbull.trade;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.aventstack.extentreports.Status;

public class Marquee {

	public static int[] execute(WebDriver driver) {
		int passCount = 0;
		int failCount = 0;

        Bfun.click(driver, By.xpath("//span[contains(text(),'Settings')]"));
		Bfun.click(driver, By.xpath("//span[contains(text(),'Marquee Text')]"));

		int rows = ExcelUtils.marqueeSheet == null ? 0 : ExcelUtils.marqueeSheet.getLastRowNum();
		System.out.println("Marquee: marqueeSheet rows=" + rows);

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
				} catch (Exception e) {}
			}


			Row row = ExcelUtils.marqueeSheet.getRow(i);

			if (row == null) {
				continue;
			}

			try {
				DataFormatter formatter = new DataFormatter();

				String marqueeText = formatter.formatCellValue(row.getCell(1)).trim();
				String active = formatter.formatCellValue(row.getCell(2)).trim();
				String exec = formatter.formatCellValue(row.getCell(3)).trim();
				String add = formatter.formatCellValue(row.getCell(4)).trim();
				String edit = formatter.formatCellValue(row.getCell(5)).trim();
				String editMarquee = formatter.formatCellValue(row.getCell(6)).trim();
				String delete = formatter.formatCellValue(row.getCell(7)).trim();

				if (marqueeText.isEmpty()) {
					continue;
				}

				if (!exec.equalsIgnoreCase("yes")) {
					Main.test.log(Status.INFO, "Skipped row " + i + " - Execution flag: " + exec);
					continue;
				}

				System.out.println("Processing Marquee row " + i + ": Text=" + marqueeText + " | Active=" + active
						+ " | Add=" + add + " | Edit=" + edit + " | Delete=" + delete);

				// ========== ADD ==========
				if (add.equalsIgnoreCase("yes")) {
					System.out.println("hii");
					// Click Add button - robust locator
					Bfun.click(driver, By.xpath("//h4/a[contains(.,'Add')] | //h4/a[i[contains(@class,'plus')]] | //h4/a"));
					Thread.sleep(2000);

                    try {
                        WebElement iframe = driver.findElement(By.id("mrq_text_ifr"));
                        driver.switchTo().frame(iframe);
                        WebElement body = driver.findElement(By.id("tinymce"));
                        body.clear();
                        body.sendKeys(marqueeText);
                        driver.switchTo().defaultContent();
                    } catch (Exception err) {
                        driver.switchTo().defaultContent();
                        throw err;
                    }

					// Set Active status
					if (active.equalsIgnoreCase("yes")) {
						Bfun.setRadioButton(driver, By.id("mrq_active_yes"));
					} else if (active.equalsIgnoreCase("no")) {
						Bfun.setRadioButton(driver, By.id("mrq_active_no"));
					}

					// Click Save
					Bfun.click(driver, By.xpath("//button[normalize-space()='Save']"));
					Thread.sleep(2000);

					// Check toast message
					String toast = Bfun.getToastMessageIfPresent(driver);
					System.out.println("Add Toast: " + toast);

					Bfun.captureScreenshot(driver, "Marquee_Add_" + i);
					
					if (toast.toLowerCase().contains("success") || toast.toLowerCase().contains("successfully") || toast.isEmpty()) {
						Main.test.log(Status.PASS, "Marquee added successfully: " + marqueeText);
						ExcelUtils.writemarqueeResult(i, "Pass", toast);
						passCount++;
					} else {
						Main.test.log(Status.FAIL, "Marquee add failed: " + toast);
						ExcelUtils.writemarqueeResult(i, "Fail", toast);
						failCount++;
					}
				}

				// ========== EDIT ==========
				if (edit.equalsIgnoreCase("yes")) {

					// Search for the marquee text in the table
					Bfun.click(driver, By.xpath("//input[@type='search']"));
					Bfun.type(driver, By.xpath("//input[@type='search']"), marqueeText);
					Thread.sleep(1000);

					// Click Edit button on the first matching row
					Bfun.click(driver, By.xpath("//table//tbody//tr[1]//td[last()]//a[1]"));
					Thread.sleep(1000);

					// Update Marquee Text with edit value
					if (!editMarquee.isEmpty()) {
						try {
							WebElement iframe = driver.findElement(By.id("mrq_text_ifr"));
							driver.switchTo().frame(iframe);
							WebElement body = driver.findElement(By.id("tinymce"));
							body.clear();
							body.sendKeys(editMarquee);
							driver.switchTo().defaultContent();
						} catch (Exception err) {
							driver.switchTo().defaultContent();
							throw err;
						}
					}

					// Click Update
					Bfun.click(driver, By.xpath("//button[normalize-space()='Update']"));
					Thread.sleep(2000);

					// Check toast message
					String toast = Bfun.getToastMessageIfPresent(driver);
					System.out.println("Edit Toast: " + toast);

					Bfun.captureScreenshot(driver, "Marquee_Edit_" + i);
					
					if (toast.toLowerCase().contains("success") || toast.toLowerCase().contains("successfully") || toast.isEmpty()) {
						Main.test.log(Status.PASS, "Marquee edited successfully: " + marqueeText + " -> " + editMarquee);
						ExcelUtils.writemarqueeResult(i, "Pass", toast);
						passCount++;
					} else {
						Main.test.log(Status.FAIL, "Marquee edit failed: " + toast);
						ExcelUtils.writemarqueeResult(i, "Fail", toast);
						failCount++;
					}
				}

				// ========== DELETE ==========
				if (delete.equalsIgnoreCase("yes")) {

					// Determine the name to search — use editMarquee if it was edited, else
					// original
					String searchName = (edit.equalsIgnoreCase("yes") && !editMarquee.isEmpty()) ? editMarquee
							: marqueeText;

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

					Bfun.captureScreenshot(driver, "Marquee_Delete_" + i);
					
					if (toast.toLowerCase().contains("success") || toast.toLowerCase().contains("successfully") || toast.isEmpty()) {
						Main.test.log(Status.PASS, "Marquee deleted successfully: " + searchName);
						ExcelUtils.writemarqueeResult(i, "Pass", toast);
						passCount++;
					} else {
						Main.test.log(Status.FAIL, "Marquee delete failed: " + toast);
						ExcelUtils.writemarqueeResult(i, "Fail", toast);
						failCount++;
					}
				}

				// If none of add/edit/delete is yes, just log it
				if (!add.equalsIgnoreCase("yes") && !edit.equalsIgnoreCase("yes") && !delete.equalsIgnoreCase("yes")) {
					Main.test.log(Status.INFO, "No action (add/edit/delete) for row " + i);
				}

			} catch (Exception e) {
				System.out.println("Error at row " + i + ": " + e.getMessage());
				Main.test.log(Status.FAIL, "Exception at row " + i + ": " + e.getMessage());
				Bfun.captureScreenshot(driver, "Marquee_Failed_" + i);
				ExcelUtils.writemarqueeResult(i, "Fail", e.getMessage());
				failCount++;

				// Navigate back so next row can still work
				try {
					Bfun.click(driver, By.xpath("//span[contains(text(),'Settings')]"));
					Bfun.click(driver, By.xpath("//span[contains(text(),'Marquee Text')]"));
				} catch (Exception nav) {
					System.out.println("Recovery navigation failed: " + nav.getMessage());
				}
			}
		}

		return new int[] { passCount, failCount };
	}
}
