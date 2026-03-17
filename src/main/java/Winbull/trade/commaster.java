package Winbull.trade;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

public class commaster {

    public static List<String> dname = new ArrayList<>();

    // ===================== EXECUTE =====================
    public static int[] execute(WebDriver driver) {

        dname.clear();
        int passCount = 0;
        int failCount = 0;

     
        int rows = ExcelUtils.commaster.getLastRowNum();

        for (int i = 1; i <= rows; i++) {

            Row row = ExcelUtils.commaster.getRow(i);
            if (row == null)
                continue;

            String com_name = row.getCell(1).getStringCellValue();
            String edname = row.getCell(19).getStringCellValue();
            String edit = row.getCell(17).getStringCellValue();
            String delete = row.getCell(18).getStringCellValue();
            String add = row.getCell(2).getStringCellValue();

           

            if (add.equalsIgnoreCase("yes")) {
            	if (commaster.addCommodity(driver, row, i)) {
            		
            	    System.out.println("Commodity Added Successfully");
            	    passCount++;
            	   
            	} else {
            	    System.out.println("Commodity Add Failed");
            	
            		failCount++;
                }
            	
            }
            System.out.println(edit);
            if (edit.equalsIgnoreCase("yes")) {
                if (commaster.editCommodity(driver, com_name, edname, i)) {
                	 System.out.println("Commodity edit Successfully");
                	 
             	    passCount++;
             	   
             	} else {
             	    System.out.println("Commodity edit Failed");
             	
             		failCount++;
                     
                 }
             	
            }

            if (delete.equalsIgnoreCase("yes")) {
                if(commaster.deleteCommodity(driver, com_name , edit , edname))
                {
                	System.out.println("Commodity delete Successfully");
             	    passCount++;
            }else {
            	System.out.println("Commodity delete failed");
         	    failCount++;
            }
                }
            

            ExcelUtils.writecommastResult(i, "Pass", "");
        }

        return new int[] { passCount, failCount };
    }

    public static boolean addCommodity(WebDriver driver, Row row, int i) {

        try {

            DataFormatter formatter = new DataFormatter();

            String com_name = row.getCell(1).getStringCellValue();
            String pur_status = row.getCell(3).getStringCellValue();
            String dispur = formatter.formatCellValue(row.getCell(4));
            String weight = formatter.formatCellValue(row.getCell(5));
            String ocharge = formatter.formatCellValue(row.getCell(6));
            String seq_num = formatter.formatCellValue(row.getCell(7));
            String drate = formatter.formatCellValue(row.getCell(8));
            String dqty = formatter.formatCellValue(row.getCell(9));
            String isbar = row.getCell(10).getStringCellValue();
            String barqty = formatter.formatCellValue(row.getCell(11));
            String qty = formatter.formatCellValue(row.getCell(12));
            String active = row.getCell(13).getStringCellValue();
            String trasta = row.getCell(14).getStringCellValue();
            String sellsta = row.getCell(15).getStringCellValue();
            String buysta = row.getCell(16).getStringCellValue();
            Bfun.click(driver, By.xpath("//span[text()='Masters']"));
            Bfun.click(driver, By.xpath("//span[text()='Commodity Master']"));

            Bfun.click(driver, By.xpath("//*[@id=\"content\"]/div[2]/div/div/div/div/div/h4/a/i"));

        
            Bfun.type(driver, By.id("com_name"), com_name);
            Bfun.type(driver, By.id("com_weight"), weight);
            Bfun.type(driver, By.id("com_other_charges"), ocharge);
            Bfun.type(driver, By.id("com_order_number"), seq_num);
            Bfun.type(driver, By.id("com_roundoff"), drate);
            Bfun.type(driver, By.id("allowed_decimals"), dqty);

         
            if (pur_status.equalsIgnoreCase("on")) {
                Bfun.setRadioButton(driver,
                        By.xpath("//*[@id=\"commodity_entry\"]/div[1]/div[2]/div/div/div/label[1]"));
            } else {
                Bfun.setRadioButton(driver,
                        By.xpath("//*[@id=\"commodity_entry\"]/div[1]/div[2]/div/div/div/label[2]"));
                Bfun.type(driver, By.id("com_display_purity"), dispur);
            }

            if (isbar.equalsIgnoreCase("yes")) {
                Bfun.setRadioButton(driver,
                        By.xpath("//*[@id=\"commodity_entry\"]/div[8]/div[1]/div/div/div/label[1]"));
                Bfun.type(driver, By.id("com_bar_no"), barqty);
            } else {
                Bfun.setRadioButton(driver,
                        By.xpath("//*[@id=\"commodity_entry\"]/div[8]/div[1]/div/div/div/label[2]"));
            }
            Bfun.scrollToBottom(driver, By.xpath("//button[normalize-space()='Save']"));
            Thread.sleep(1000);

            Bfun.type(driver, By.id("com_bar_quantity"), qty);

            if (active.equalsIgnoreCase("yes")) {
                Bfun.setRadioButton(driver,
                        By.xpath("//*[@id=\"commodity_entry\"]/div[11]/div[1]/div/div/div/label[1]"));
            } else {
                Bfun.setRadioButton(driver,
                        By.xpath("//*[@id=\"commodity_entry\"]/div[11]/div[1]/div/div/div/label[2]"));
            }

            if (trasta.equalsIgnoreCase("yes")) {
                Bfun.setRadioButton(driver,
                        By.xpath("//*[@id=\"commodity_entry\"]/div[11]/div[2]/div/div/div/label[1]"));

                if (sellsta.equalsIgnoreCase("yes")) {
                    Bfun.setRadioButton(driver,
                            By.xpath("//*[@id=\"commodity_entry\"]/div[12]/div/div[1]/div/div/div/label[1]"));
                } else {
                    Bfun.setRadioButton(driver,
                            By.xpath("//*[@id=\"commodity_entry\"]/div[12]/div/div[1]/div/div/div/label[2]"));
                }

                if (buysta.equalsIgnoreCase("yes")) {
                    Bfun.setRadioButton(driver,
                            By.xpath("//*[@id=\"commodity_entry\"]/div[12]/div/div[2]/div/div/div/label[1]"));
                } else {
                    Bfun.setRadioButton(driver,
                            By.xpath("//*[@id=\"commodity_entry\"]/div[12]/div/div[2]/div/div/div/label[2]"));
                }
            } else {
                Bfun.setRadioButton(driver,
                        By.xpath("//*[@id=\"commodity_entry\"]/div[11]/div[2]/div/div/div/label[2]"));
            }
            Bfun.scrollToBottom(driver, By.xpath("//button[normalize-space()='Save']"));
            
            Bfun.click(driver, By.xpath("//button[normalize-space()='Save']"));
            Thread.sleep(1000);
            String toastMsg = getToastMessageIfPresent(driver);
            Thread.sleep(2000);
			Bfun.captureScreenshot(driver, "Created" + i);


            if (toastMsg.equalsIgnoreCase("Record added successfully")) {
                Main.test.log(Status.PASS, "Commodity Added Successfully: " + com_name);
                ExcelUtils.updatecomgrpname(i, com_name);
                
                
                return true;
            }


            Main.test.log(Status.FAIL, "Add Failed: " + toastMsg);
            ExcelUtils.writecommastResult(i, "Fail - " + toastMsg, "");
           
            return false;
            
        } catch (Exception e) {
            Main.test.log(Status.FAIL, "Add Exception: " + e.getMessage());
            return false;
            
        }
    }

    // ===================== EDIT =====================
    public static boolean editCommodity(WebDriver driver, String com_name, String edname, int i) {

        try {
        	Thread.sleep(1000);
        	 Bfun.click(driver, By.xpath("//span[text()='Masters']"));
             Bfun.click(driver, By.xpath("//span[text()='Commodity Master']"));

            Bfun.type(driver, By.xpath("//input[@type='search']"), com_name);
            Bfun.click(driver, By.xpath("//table/tbody/tr[1]/td[9]/a[1]"));

            Bfun.type(driver, By.id("com_name"), edname);
            Bfun.click(driver, By.xpath("//button[normalize-space()='Update']"));
            Thread.sleep(2000);
			Bfun.captureScreenshot(driver, "Updated" + i);

            String toastMsg = getToastMessageIfPresent(driver);

            if (!toastMsg.isEmpty() && !toastMsg.toLowerCase().contains("success")) {
                Main.test.log(Status.FAIL, "Edit Failed: " + toastMsg);
                return false;
            }

            Main.test.log(Status.PASS, "Commodity Edited Successfully: " + edname);
            ExcelUtils.updatecomgrpedme(i, edname);

            return true;

        } catch (Exception e) {
            Main.test.log(Status.FAIL, "Edit Exception: " + e.getMessage());
            return false;
        }
    }

    // ===================== DELETE =====================
    public static boolean deleteCommodity(WebDriver driver, String com_name, String edit , String edname) {

        try {
        	
        	Thread.sleep(1000);
        	 Bfun.click(driver, By.xpath("//span[text()='Masters']"));
             Bfun.click(driver, By.xpath("//span[text()='Commodity Master']"));
             if (edit.equalsIgnoreCase("yes")) {
                
              	
            Bfun.type(driver, By.xpath("//input[@type='search']"), edname);
            Bfun.click(driver, By.xpath("//table/tbody/tr[1]/td[9]/a[2]"));
            Bfun.click(driver, By.id("commonConfirmBtn"));
            Thread.sleep(2000);
			Bfun.captureScreenshot(driver, "Deleted");


            Main.test.log(Status.PASS, "Commodity Deleted Successfully: " + edname);
            ExcelUtils.deletecomgrprow(edname);
            return true;
            
             }
             else
             {
            	 Bfun.type(driver, By.xpath("//input[@type='search']"), com_name);
                 Bfun.click(driver, By.xpath("//table/tbody/tr[1]/td[9]/a[2]"));
                 Bfun.click(driver, By.id("commonConfirmBtn"));
                 Thread.sleep(2000);
					Bfun.captureScreenshot(driver, "Deleted");

                 Main.test.log(Status.PASS, "Commodity Deleted Successfully: " + com_name);
                 ExcelUtils.deletecomgrprow(edname);
                 return true;

             }

        } catch (Exception e) {
			Bfun.captureScreenshot(driver, "Deleted");

            Main.test.log(Status.FAIL, "Delete Exception: " + e.getMessage());
            return false;
        }
    }

    // ===================== TOAST =====================
    public static String getToastMessageIfPresent(WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(@class,'text-break')]")));
            return msg.getText();
        } 
        catch (TimeoutException e)
        {
            return "";
        }
    }
}