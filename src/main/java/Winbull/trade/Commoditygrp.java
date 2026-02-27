package Winbull.trade;

import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
//import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;

public class Commoditygrp {

	public static int[] execute(WebDriver driver) {
		List<String> commodityList = commaster.dname;

		for (int i = 0; i < commodityList.size(); i++) {
			String commodity = commodityList.get(i);
			System.out.println(commodity);
		}
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

				for (int j = 0; j < uiRows.size(); j++) {

					WebElement uiRow = uiRows.get(j);

					String coname = uiRow.findElement(By.xpath("./td[1]")).getText().trim();
					System.out.println(coname);

					String excelCommodity = row.getCell(1).getStringCellValue();
					String buyValue = row.getCell(2).getStringCellValue();
					String sellValue = row.getCell(3).getStringCellValue();
					String diffTypeValue = row.getCell(4).getStringCellValue();
					String prebuy = formatter.formatCellValue(row.getCell(5));
					String presel = formatter.formatCellValue(row.getCell(6));
					String tradeBuyValue = row.getCell(7).getStringCellValue();
					String tradeSellValue = row.getCell(8).getStringCellValue();
					String delday = formatter.formatCellValue(row.getCell(9));

					System.out.println(buyValue);

					Bfun.setCommodityValues(driver, "com_table", excelCommodity, buyValue, sellValue, diffTypeValue, tradeBuyValue,
							tradeSellValue, prebuy, presel, delday);
					
					Bfun.click(driver, By.xpath("//button[normalize-space()='Update']"));

					Thread.sleep(2000);
					Main.test.log(Status.PASS, "Commodity Group Updated successfully: ");
					ExcelUtils.writecomgrpResult(i, "Pass", "");
					passCount++;

					break;

				}

			} catch (Exception e) {
				Main.test.log(Status.FAIL, "asd Exception occurred: " + e.getMessage());
				ExcelUtils.writecomgrpResult(i, "Fail", "");
				failCount++;
			}
		}
		return new int[] { passCount, failCount };
	}
}
