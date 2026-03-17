package Winbull.trade;

import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.aventstack.extentreports.Status;

public class RcommodityType {

	public static int[] execute(WebDriver driver) {

		int passCount = 0;
		int failCount = 0;
		By masters = By.xpath("//span[text()='Masters']");
		Bfun.click(driver, masters);

		By Rcomtype = By.xpath("//span[text()='R-Panel Commodity Type']");
		Bfun.click(driver, Rcomtype);

		int rows = ExcelUtils.RcomtypeSheet.getLastRowNum();

		for (int i = 1; i <= rows; i++) {

			Row row = ExcelUtils.RcomtypeSheet.getRow(i);

			if (row == null)
				continue;

			try {

				DataFormatter formatter = new DataFormatter();
				String commodityName = row.getCell(1).getStringCellValue();
				String c_type = row.getCell(2).getStringCellValue().toLowerCase();

				String stax = formatter.formatCellValue(row.getCell(5));
				String btax = formatter.formatCellValue(row.getCell(6));
				String Stcs = formatter.formatCellValue(row.getCell(7));
				String btcs = formatter.formatCellValue(row.getCell(8));
				String snum = formatter.formatCellValue(row.getCell(9));
				String edit = row.getCell(11).getStringCellValue();
				String delete = row.getCell(12).getStringCellValue();
				String active = row.getCell(10).getStringCellValue();

				Bfun.click(driver, By.xpath("//*[@id=\"content\"]/div[1]/div/div/div/div/div/h4/a"));
				Main.test.log(Status.INFO, "Creating R-panel Commodity Type " + commodityName);

				Bfun.type(driver, By.id("rcom_disname"), commodityName);
				if (c_type.equalsIgnoreCase("gold")) {

					Bfun.setRadioButton(driver,
							By.xpath("//*[@id=\"RpanelCommodityEntryForm\"]/div[1]/div[2]/div/div/div/label[1]"));
				} else if (c_type.equalsIgnoreCase("silver")) {
					Bfun.setRadioButton(driver,
							By.xpath("//*[@id=\"RpanelCommodityEntryForm\"]/div[1]/div[2]/div/div/div/label[2]"));
				}
				
				  Bfun.type(driver, By.id("rcom_sell_tax"), stax); Thread.sleep(1000);
				  Bfun.type(driver, By.id("rcom_buy_tax"), btax); Thread.sleep(1000);
				  Bfun.type(driver, By.id("rcom_sell_tcs"), Stcs);
				 Thread.sleep(1000);
				Bfun.type(driver, By.id("rcom_buy_tcs"), btcs);
				Thread.sleep(1000);
				Bfun.type(driver, By.name("fv[rcom_orderno]"), snum);
				Thread.sleep(1000);

				if (active.equalsIgnoreCase("yes")) {

					Bfun.setRadioButton(driver,
							By.xpath("//*[@id=\"RpanelCommodityEntryForm\"]/div[5]/div[2]/div/div/div/label[1]"));
				} else if (active.equalsIgnoreCase("No")) {
					Bfun.setRadioButton(driver,
							By.xpath("//*[@id=\"RpanelCommodityEntryForm\"]/div[5]/div[2]/div/div/div/label[2]"));
				}

				Bfun.scrollToBottom(driver, By.xpath("//button[normalize-space()='Save']"));

				Bfun.scrollToElement(driver, By.xpath("//button[normalize-space()='Save']"));
				Bfun.scrollToBottom(driver, By.xpath("//button[normalize-space()='Save']"));
				Bfun.click(driver, By.xpath("//button[normalize-space()='Save']"));
				Thread.sleep(2000);
				Bfun.captureScreenshot(driver, "Sucess" + i);

				Bfun.click(driver, By.xpath("//input[@type='search']"));
				Bfun.type(driver, By.xpath("//input[@type='search']"), commodityName);

				List<WebElement> rowsList = driver.findElements(By.xpath("//table//tbody//tr"));

				boolean recordFound = false;

				for (int j = 0; j < rowsList.size(); j++) {

					String dName = rowsList.get(j).findElement(By.xpath("//*[@id=\"grid-data\"]/tbody/tr/td[2]"))
							.getText();
					if (dName.equalsIgnoreCase(commodityName)) {
						recordFound = true;
						Main.test.log(Status.PASS, "R-panel commodity created successfully: " + commodityName);

						if (edit.equalsIgnoreCase("yes")) {
							Bfun.clickEdit(driver, dName);
							Bfun.type(driver, By.id("rcom_disname"), dName);
							Bfun.click(driver, By.xpath("//button[normalize-space()='Update']"));
						}
						if (delete.equalsIgnoreCase("yes")) {
							Bfun.click(driver, By.xpath("//table/tbody/tr/td[7]/a[2]"));
							Bfun.click(driver, By.id("commonConfirmBtn"));
							Thread.sleep(2000);
							Bfun.captureScreenshot(driver, "Deleted" + i);

							Main.test.log(Status.PASS, "R-panel_commodity_type deleted successfully: ");
						}
					}
				}
				if (recordFound) {
					Main.test.log(Status.PASS, "R-panel_Commodity_type Runned successfully: ");
					row.createCell(13).setCellValue("PASS");
					passCount++;
				} else {
					Main.test.log(Status.FAIL, "R-panel_Commodity_type creation failed: " + commodityName);
					row.createCell(13).setCellValue("FAIL");
					failCount++;
				}
				Bfun.captureScreenshot(driver, "Trader failed" + i);
				break;
			}

			catch (Exception e) {

				Main.test.log(Status.FAIL, "Exception occurred: " + e.getMessage());
				Bfun.captureScreenshot(driver, " failed" + i);

				ExcelUtils.writetraderResult(i, "Fail", "");
				failCount++;
			}
		}
		return new int[] { passCount, failCount };

	}
}