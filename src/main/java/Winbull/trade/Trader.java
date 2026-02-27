package Winbull.trade;

import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.*;
import com.aventstack.extentreports.Status;

public class Trader  {

	

	public static int[] execute(WebDriver driver) {

		List<String> commodityList = commaster.dname;

		for (String commodity : commodityList) {
		    System.out.println(commodity);
		}

		int passCount = 0;
		int failCount = 0;
		By masters = By.xpath("//span[text()='Masters']");
		Bfun.click(driver, masters);

		By trader = By.xpath("//span[text()='Trader (Customer & Supplier)']");
		Bfun.click(driver, trader);

		int rows = ExcelUtils.traderSheet.getLastRowNum();
		

		for (int i = 1; i <= rows; i++) {
			
			Row row = ExcelUtils.traderSheet.getRow(i);
			
			if (row == null)
				continue;

			try {
				
				
				DataFormatter formatter = new DataFormatter();
				String name = row.getCell(1).getStringCellValue();
				String aname = row.getCell(2).getStringCellValue();
				String c_name = row.getCell(3).getStringCellValue();
				String mob = formatter.formatCellValue(row.getCell(4));
				String w_num = row.getCell(5).toString();
				String address = row.getCell(12).getStringCellValue();
				String city = row.getCell(13).getStringCellValue();
				String email = row.getCell(6).getStringCellValue();
				String gstno = row.getCell(7).getStringCellValue();
				String panno = row.getCell(8).getStringCellValue();
				String passw = row.getCell(9).getStringCellValue();
				String lifetime = row.getCell(10).getStringCellValue();
				System.out.println(lifetime);
				//String date = formatter.formatCellValue(row.getCell(18));
				String edit = row.getCell(14).getStringCellValue();
				String delete = row.getCell(15).getStringCellValue();
				String active= row.getCell(16).getStringCellValue();
//				String buy = row.getCell(17).getStringCellValue();
//				String sell = row.getCell(18).getStringCellValue();
//				String amount = row.getCell(19).getStringCellValue();
//				
			//	System.out.println(date);
				Bfun.click(driver, By.xpath("//h4/a"));
				Main.test.log(Status.INFO, "Creating Trader: " + name);

			

				Bfun.type(driver, By.name("fv[cus_name]"), name);
				Bfun.type(driver, By.id("cus_alise_name"), aname);
				Bfun.type(driver, By.id("cus_company_name"), c_name);
				Bfun.type(driver, By.id("cus_mobile"), mob);
				Bfun.type(driver, By.id("cus_email"), email);
				Bfun.type(driver, By.id("cus_whatsapp"), w_num);

				Bfun.type(driver, By.id("cus_address"), address);
				Bfun.type(driver, By.id("cus_city"), city);

				Bfun.type(driver, By.id("cus_gstno"), gstno);
				Bfun.type(driver, By.id("cus_panno"), panno);
				
				 
				Bfun.scrollToBottom(driver, By.xpath("//button[normalize-space()='Save']"));
 				 
				Bfun.scrollToElement(driver, By.xpath("//button[normalize-space()='Save']"));
				Bfun.scrollToBottom(driver, By.xpath("//button[normalize-space()='Save']"));

				Bfun.type(driver, By.id("cus_login_password"), passw);
				Bfun.type(driver, By.id("cus_login_con_password"), passw);
				Bfun.scrollToElement(driver, By.xpath("//button[normalize-space()='Save']"));

				Bfun.scrollToBottom(driver, By.xpath("//button[normalize-space()='Save']"));
				
				Bfun.click(driver, By.xpath("//button[normalize-space()='Save']"));
				Thread.sleep(2000);
				Bfun.click(driver, By.xpath("//input[@type='search']"));
				Bfun.type(driver, By.xpath("//input[@type='search']"), name);
				Thread.sleep(1000);
				String searchname = Bfun.getText(driver, By.xpath("//input[@type='search']"));
				System.out.println(searchname);

				Thread.sleep(1000);
			
				String goldmin = formatter.formatCellValue(row.getCell(18));
				String goldmax = formatter.formatCellValue(row.getCell(19));
 				String goldallot = formatter.formatCellValue(row.getCell(20));
 				String silvermin = formatter.formatCellValue(row.getCell(21));
 				String silvermax = formatter.formatCellValue(row.getCell(22));
 				String silverallot = formatter.formatCellValue(row.getCell(23));
			

				List<WebElement> rowsList = driver.findElements(By.xpath("//table//tbody//tr"));

				boolean recordFound = false;

				for (int j = 0; j < rowsList.size(); ) {

					String traderName = rowsList.get(j).findElement(By.xpath("./td[2]")).getText();
					
					if (traderName.equalsIgnoreCase(searchname)) {
						recordFound = true;
						Main.test.log(Status.PASS, "Trader created successfully: " + name);
						
						if(active.equalsIgnoreCase("yes"))
						{
							Bfun.click(driver,By.xpath("//table/tbody/tr/td[7]"));
							if (lifetime.equalsIgnoreCase("yes")) 
							{
								Bfun.setRadioButton(driver, By.xpath("//*[@id=\"iframeForm\"]/div[3]/div[1]/div/div/div/label[1]"));
							}
							else
							{
								Bfun.setRadioButton(driver, By.xpath("//*[@id=\"iframeForm\"]/div[3]/div[1]/div/div/div/label[1]"));
							}
 							Bfun.setCheckboxWithExcelValue(driver, By.id("has_gminqty"), By.id("gold_min_qty"), goldmin);
 							
 							Bfun.setCheckboxWithExcelValue(driver, By.id("has_gmaxqty"), By.id("gold_max_qty"), goldmax);
 							Bfun.setCheckboxWithExcelValue(driver, By.id("has_gallot_qty"), By.id("gold_allot_qty"), goldallot);
 							Bfun.setCheckboxWithExcelValue(driver, By.id("has_sminqty"), By.id("silver_min_qty"), silvermin);
 							Bfun.setCheckboxWithExcelValue(driver, By.id("has_smaxqty"), By.id("silver_max_qty"), silvermax);
 							Bfun.setCheckboxWithExcelValue(driver, By.id("has_sallot_qty"), By.id("silver_allot_qty"), silverallot);
 						
							
							if (lifetime.equalsIgnoreCase("yes")) 
							  {
								  Bfun.setCheckbox(driver, By.id("cus_is_life_time"), lifetime);
							   }
							int mcs = commodityList.size();
							
							for (int k = 0; k < mcs; k++) {

							    String commodity = commodityList.get(k);

							    String tname = Bfun.getText(driver,
							            By.xpath("//td[normalize-space()='" + commodity + "']"));

							    if (tname.equalsIgnoreCase(commodity)) {

//							        if (buy.equalsIgnoreCase("yes")) {
//							            Bfun.enableCheckbox(driver,
//							                    By.name("cdItems[cus_com_status_buy][" + k + "]"));
//							        }
//
//							        if (sell.equalsIgnoreCase("yes")) {
//							            Bfun.enableCheckbox(driver,
//							                    By.name("cdItems[cus_com_status_sell][" + k + "]"));
//							        }
//
//							        if (amount.equalsIgnoreCase("yes")) {
//							            Bfun.enableCheckbox(driver,
//							                    By.name("cdItems[cus_com_amountpurch][" + k + "]"));
//							        }
							    }
							}							 Bfun.scrollToElement(driver, By.xpath("//button[text()='Update']"));
							 Bfun.scrollToBottom(driver, By.xpath("//button[text()='Update']"));
							 Bfun.scrollToElement(driver, By.xpath("//button[text()='Update']"));
							 Bfun.scrollToElement(driver, By.xpath("//button[text()='Update']"));

							 Bfun.click(driver, By.xpath("//button[text()='Update']"));
							 Main.test.log(Status.PASS, "Trader Activated successfully: ");
							
						}
						if (edit.equalsIgnoreCase("yes")) {
							Bfun.click(driver, By.xpath("//table/tbody/tr/td[8]/a[1]"));

							Bfun.scrollToBottom(driver, By.xpath("//button[text()='Update']"));
							Bfun.type(driver, By.id("cus_login_password"), passw);
							Bfun.scrollToBottom(driver, By.xpath("//button[text()='Update']"));

							Bfun.click(driver, By.xpath("//button[text()='Update']"));
							Main.test.log(Status.PASS, "Trader Updated Successfully: ");
						}
						
						
						if (delete.equalsIgnoreCase("yes")) {
							Bfun.click(driver, By.xpath("//table/tbody/tr/td[8]/a[2]"));
							Bfun.click(driver, By.id("commonConfirmBtn"));
							Main.test.log(Status.PASS, "Trader deleted successfully: ");
						}
						
						
					}
					if (recordFound) {
						Main.test.log(Status.PASS, "Trader Runned successfully: ");
						System.out.println("Trader Created successfully");
						row.createCell(17).setCellValue("PASS");
						passCount++;
					} else {
						Main.test.log(Status.FAIL, "Trader creation failed: " + name);
						System.out.println("Trader Created failed");
						row.createCell(17).setCellValue("FAIL");
						failCount++;
					}
					Bfun.captureScreenshot(driver, "Trader failed" + i);
					break;
				}

				
			} catch (Exception e) {
				
				Main.test.log(Status.FAIL, "Exception occurred: " + e.getMessage());
				ExcelUtils.writetraderResult(i, "Fail", "");
				failCount++;
			}

		}

		return new int[] { passCount, failCount };
	}
}
