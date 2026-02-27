package Winbull.trade;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ExcelUtils {

	public static Workbook workbook;
	public static Sheet masterSheet;
	public static Sheet loginSheet;
	public static Sheet RcomtypeSheet;
	public static Sheet commaster;
	public static Sheet comgrp;
	public static Sheet traderSheet;
	public static Sheet premiumSheet;
	
	

	public static void openExcel(String path) throws Exception {
		
		Runtime.getRuntime().exec("taskkill /F /IM excel.exe");
		FileInputStream fis = new FileInputStream(path);
		workbook = new XSSFWorkbook(fis);
		

		masterSheet = workbook.getSheet("Master");
		loginSheet = workbook.getSheet("Login");
		traderSheet = workbook.getSheet("tradersheet");
		premiumSheet = workbook.getSheet("Premium");
		RcomtypeSheet = workbook.getSheet("RcommodityType");
		commaster = workbook.getSheet("commoditymaster");
		comgrp = workbook.getSheet("commoditygrp");
		
		
		fis.close();
		loadMasterData();
	}

	public static List<String> functions = new ArrayList<>();
	public static List<String> execution = new ArrayList<>();

	private static void loadMasterData() {

	    int rows = masterSheet.getLastRowNum();
	    System.out.println(rows);
	    functions.clear();
	    execution.clear();

	    for (int i = 1; i <= rows; i++) {

	        Row row = masterSheet.getRow(i);
	        if (row == null)
	            continue;

	        Cell functionCell = row.getCell(1);
	        Cell executeCell = row.getCell(2);

	        if (functionCell == null || executeCell == null)
	            continue;

	        String functionName = functionCell.toString().trim();
	        String executeFlag = executeCell.toString().trim();

	        functions.add(functionName);
	        execution.add(executeFlag);
	    }
	}


	public static void writeLoginResult(int rowNum, String status, String message) {

		Row row = loginSheet.getRow(rowNum);
		if (row == null)
			row = loginSheet.createRow(rowNum);

		row.createCell(3).setCellValue(status);
		row.createCell(4).setCellValue(message);
	}

	public static void writetraderResult(int rowNum, String status, String message) {

		Row row = traderSheet.getRow(rowNum);
		if (row == null)
			row = traderSheet.createRow(rowNum);

		row.createCell(17).setCellValue(status);
		
	}
	public static void writerctypeResult(int rowNum, String status, String message) {

		Row row = RcomtypeSheet.getRow(rowNum);
		if (row == null)
			row = RcomtypeSheet.createRow(rowNum);

		row.createCell(13).setCellValue(status);
		
	}
	public static void writecommastResult(int rowNum, String status, String message) {

		Row row = commaster.getRow(rowNum);
		if (row == null)
			row = commaster.createRow(rowNum);

		row.createCell(16).setCellValue(status);
		
	}
	public static void writecomgrpResult(int rowNum, String status, String message) {

		Row row = comgrp.getRow(rowNum);
		if (row == null)
			row = comgrp.createRow(rowNum);

		row.createCell(16).setCellValue(status);
		
	}
	public static void updateMaster(int rowIndex, int pass, int fail) {

		Row row = masterSheet.getRow(rowIndex);

		if (row == null)
			row = masterSheet.createRow(rowIndex);

		String result = "PASS - " + pass + " FAIL - " + fail;

		row.createCell(3).setCellValue(result);
	}

	public static void saveExcel(String path) throws Exception {

		FileOutputStream fos = new FileOutputStream(path);
		workbook.write(fos);
		fos.close();
	}

	public static void closeExcel() throws Exception {
		workbook.close();
	}

}
