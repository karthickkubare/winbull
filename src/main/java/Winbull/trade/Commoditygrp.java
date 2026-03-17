package Winbull.trade;

import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;

public class Commoditygrp {

	public static int[] execute(WebDriver driver) {
		int passCount = 0;
		int failCount = 0;
		By masters = By.xpath("//span[text()='Masters']");
		Bfun.click(driver, masters);

		By comgroup = By.xpath("//span[text()='Commodity Group']");
		Bfun.click(driver, comgroup);

		int rows = ExcelUtils.comgrp.getLastRowNum();

		for (int i = 1; i <= rows; i++) {

			Row row = ExcelUtils.comgrp.getRow(i);

			if (row == null)
				continue;

			try {
				DataFormatter formatter = new DataFormatter();
				Bfun.click(driver, By.xpath("//*[@id=\"grid-data\"]/tbody/tr/td[4]/a[1]"));

				List<WebElement> uiRows = driver.findElements(By.xpath("//*[@id='com_table']/tbody/tr"));

				for (int j = 1; j <=uiRows.size(); j++) {

					WebElement uiRow = uiRows.get(j);

				
					String excelCommodity = formatter.formatCellValue(row.getCell(1));
					String execu = formatter.formatCellValue(row.getCell(2));
					String buyValue = formatter.formatCellValue(row.getCell(3));
					String sellValue = formatter.formatCellValue(row.getCell(4));
					String diffTypeValue = formatter.formatCellValue(row.getCell(5));
					String prebuy = formatter.formatCellValue(row.getCell(6));
					String presel = formatter.formatCellValue(row.getCell(7));
					String tradeBuyValue = formatter.formatCellValue(row.getCell(8));
					String tradeSellValue = formatter.formatCellValue(row.getCell(9));
					String delday = formatter.formatCellValue(row.getCell(10));					
					System.out.println(buyValue);
					if(execu.equalsIgnoreCase("yes"))
					{

						Bfun.setCommodityValues(driver, "com_table", excelCommodity, buyValue, sellValue, diffTypeValue, tradeBuyValue,
								tradeSellValue, prebuy, presel, delday);
						
						Bfun.click(driver, By.xpath("//button[normalize-space()='Update']"));

						Thread.sleep(2000);
						Bfun.captureScreenshot(driver, "Login failed" + i);

						Main.test.log(Status.PASS, "Commodity Group Updated successfully: ");
						ExcelUtils.writecomgrpResult(i, "Pass", "");
						passCount++;

					}
					else
					{
						Main.test.log(Status.PASS, "Execution Status is"+execu);
						Bfun.click(driver, By.xpath("//button[normalize-space()='Cancel']"));

						System.out.println("Here i am");
					}
					
					

					break;

				}

			} catch (Exception e) {
				Main.test.log(Status.FAIL, "asd Exception occurred: " + e.getMessage());
				String f=e.getMessage();
				ExcelUtils.writecomgrpResult(i, "Fail",f );
				failCount++;
			}
		}
		return new int[] { passCount, failCount };
	}
}
