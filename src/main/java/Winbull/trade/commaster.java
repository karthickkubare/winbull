package Winbull.trade;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;

public class commaster {
	
	public static List<String> dname = new ArrayList<>();    


	public static int[] execute(WebDriver driver) {
		
		dname.clear();

		int passCount = 0;
		int failCount = 0;
		By masters = By.xpath("//span[text()='Masters']");
		Bfun.click(driver, masters);

		By commas = By.xpath("//span[text()='Commodity Master']");
		Bfun.click(driver, commas);

		int rows = ExcelUtils.commaster.getLastRowNum();

		for (int i = 1; i <= rows; i++) {

			Row row = ExcelUtils.commaster.getRow(i);

			if (row == null)
				continue;

			try {
				DataFormatter formatter = new DataFormatter();
				String com_name = row.getCell(1).getStringCellValue();
				dname.add(com_name);

				String pur_status = row.getCell(2).getStringCellValue();
				String dispur = formatter.formatCellValue(row.getCell(3));

				String weight = formatter.formatCellValue(row.getCell(4));
				String ocharge = formatter.formatCellValue(row.getCell(5));
				String seq_num = formatter.formatCellValue(row.getCell(6));

				String drate = formatter.formatCellValue(row.getCell(7));
				String dqty = formatter.formatCellValue(row.getCell(8));
				String isbar = row.getCell(9).getStringCellValue();
				String barqty = formatter.formatCellValue(row.getCell(10));
				String qty = formatter.formatCellValue(row.getCell(11));

				String active = row.getCell(12).getStringCellValue();
				String trasta = row.getCell(13).getStringCellValue();
				String sellsta = row.getCell(14).getStringCellValue();
				String buysta = row.getCell(15).getStringCellValue();
				String edit = row.getCell(17).getStringCellValue();
				String delete = row.getCell(18).getStringCellValue();

				Bfun.click(driver, By.xpath("//*[@id=\"content\"]/div[2]/div/div/div/div/div/h4/a/i"));

				Bfun.type(driver, By.id("com_name"), com_name);

				if (pur_status.equalsIgnoreCase("on")) {
					System.out.println(pur_status);

					Bfun.setRadioButton(driver,
							By.xpath("//*[@id=\\\"commodity_entry\\\"]/div[1]/div[2]/div/div/div/label[1]"));
				} else if (pur_status.equalsIgnoreCase("off")) {
					Bfun.setRadioButton(driver,
							By.xpath("//*[@id=\"commodity_entry\"]/div[1]/div[2]/div/div/div/label[2]"));
					Bfun.type(driver, By.id("com_display_purity"), dispur);
				}
				Bfun.type(driver, By.id("com_weight"), weight);
				Bfun.type(driver, By.id("com_other_charges"), ocharge);
				Bfun.type(driver, By.id("com_order_number"), seq_num);
				Bfun.scrollToBottom(driver, By.xpath("//button[normalize-space()='Save']"));
				Bfun.type(driver, By.id("com_roundoff"), drate);
				Bfun.type(driver, By.id("allowed_decimals"), dqty);

				if (isbar.equalsIgnoreCase("yes")) {

					Bfun.setRadioButton(driver,
							By.xpath("//*[@id=\"commodity_entry\"]/div[8]/div[1]/div/div/div/label[1]"));

					Bfun.type(driver, By.id("com_bar_no"), barqty);
				} else if (isbar.equalsIgnoreCase("no")) {
					Bfun.setRadioButton(driver,
							By.xpath("//*[@id=\"commodity_entry\"]/div[8]/div[1]/div/div/div/label[2]"));

				}
				Bfun.type(driver, By.id("com_bar_quantity"), qty);
				if (active.equalsIgnoreCase("yes")) {

					Bfun.setRadioButton(driver,
							By.xpath("//*[@id=\"commodity_entry\"]/div[11]/div[1]/div/div/div/label[1]"));

				} else if (active.equalsIgnoreCase("no")) {
					Bfun.setRadioButton(driver,
							By.xpath("//*[@id=\"commodity_entry\"]/div[11]/div[1]/div/div/div/label[2]"));

				}
				if (trasta.equalsIgnoreCase("yes")) {

					Bfun.setRadioButton(driver,
							By.xpath("//*[@id=\"commodity_entry\"]/div[11]/div[2]/div/div/div/label[1]"));
					if (sellsta.equalsIgnoreCase("Yes")) {
						Bfun.setRadioButton(driver,
								By.xpath("//*[@id=\"commodity_entry\"]/div[12]/div/div[1]/div/div/div/label[1]"));

					} else if (sellsta.equalsIgnoreCase("No")) {
						Bfun.setRadioButton(driver,
								By.xpath("//*[@id=\"commodity_entry\"]/div[12]/div/div[1]/div/div/div/label[2]"));
					}

					if (buysta.equalsIgnoreCase("Yes")) {
						Bfun.setRadioButton(driver,
								By.xpath("//*[@id=\"commodity_entry\"]/div[12]/div/div[2]/div/div/div/label[1]"));

					} else if (buysta.equalsIgnoreCase("No")) {
						Bfun.setRadioButton(driver,
								By.xpath("//*[@id=\"commodity_entry\"]/div[12]/div/div[2]/div/div/div/label[2]"));
					}
				}

				else if (trasta.equalsIgnoreCase("no")) {
					Bfun.setRadioButton(driver,
							By.xpath("//*[@id=\"commodity_entry\"]/div[11]/div[2]/div/div/div/label[2]"));

				}
				System.out.println(Bfun.getText(driver,
						By.xpath("//*[@id=\"commodity_entry\"]/div[12]/div/div[2]/div/div/div/label[2]")));
				Bfun.click(driver, By.xpath("//button[normalize-space()='Save']"));

				Main.test.log(Status.PASS, "Commodity Created successfully: ");
				passCount++;

				Bfun.click(driver, By.xpath("//input[@type='search']"));
				Bfun.type(driver, By.xpath("//input[@type='search']"), com_name);

				List<WebElement> rowsList = driver.findElements(By.xpath("//table//tbody//tr"));

				boolean recordFound = false;

				for (int j = 0; j < rowsList.size(); j++) {
					String commoname = rowsList.get(j).findElement(By.xpath("./td[2]")).getText();

					if (com_name.equalsIgnoreCase(commoname)) {
						recordFound = true;
						Main.test.log(Status.PASS, "Commodity created successfully: " + commoname);

						if (edit.equalsIgnoreCase("yes")) {
							Bfun.click(driver, By.xpath("//table/tbody/tr[1]/td[9]/a[1]"));
							Bfun.type(driver, By.id("com_name"), commoname);
							Bfun.scrollToBottom(driver, By.xpath("//button[normalize-space()='Update']"));

							Bfun.click(driver, By.xpath("//button[normalize-space()='Update']"));
						}

						if (delete.equalsIgnoreCase("yes")) {
							Bfun.click(driver, By.xpath("//table/tbody/tr[1]/td[9]/a[2]"));
							Bfun.click(driver, By.id("commonConfirmBtn"));
							Thread.sleep(2000);
							Main.test.log(Status.PASS, "Commodity deleted successfully: ");
						}

					}
				}

			} catch (Exception e) {

				Main.test.log(Status.FAIL, "Exception occurred: " + e.getMessage());
				ExcelUtils.writecommastResult(i, "Fail", "");
				failCount++;
			}
		}

		return new int[] { passCount, failCount };

	}
}