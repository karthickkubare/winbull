package Winbull.trade;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.*;

public class ExcelUtils {

    public static Workbook workbook;
    public static Sheet masterSheet;
    public static Sheet loginSheet;
    public static Sheet RcomtypeSheet;
    public static Sheet commaster;
    public static Sheet comgrp;
    public static Sheet traderSheet;
    public static Sheet premiumSheet;
    public static Sheet cusgrpSheet;
    public static Sheet cusmarginSheet;
    public static Sheet rpanelsetSheet;
    public static Sheet marqueeSheet;
    public static Sheet popupSheet;


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
        cusgrpSheet = workbook.getSheet("Customergrp");
        cusmarginSheet = workbook.getSheet("Cusmargin");
        rpanelsetSheet = workbook.getSheet("rpanelset");
        marqueeSheet = workbook.getSheet("marquee");
        popupSheet = workbook.getSheet("popup");


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
            if (row == null) {
                continue;
            }

            Cell functionCell = row.getCell(1);
            Cell executeCell = row.getCell(2);

            if (functionCell == null || executeCell == null) {
                continue;
            }

            String functionName = functionCell.toString().trim();
            String executeFlag = executeCell.toString().trim();

            functions.add(functionName);
            execution.add(executeFlag);
        }
    }

    public static void writeLoginResult(int rowNum, String status, String message) {

        Row row = loginSheet.getRow(rowNum);
        if (row == null) {
            row = loginSheet.createRow(rowNum);
        }

        row.createCell(3).setCellValue(status);
        row.createCell(4).setCellValue(message);
    }

    public static void writetraderResult(int rowNum, String status, String message) {

        Row row = traderSheet.getRow(rowNum);
        if (row == null) {
            row = traderSheet.createRow(rowNum);
        }

        row.createCell(17).setCellValue(status);

    }

    public static void writerctypeResult(int rowNum, String status, String message) {

        Row row = RcomtypeSheet.getRow(rowNum);
        if (row == null) {
            row = RcomtypeSheet.createRow(rowNum);
        }

        row.createCell(13).setCellValue(status);

    }

    public static void writecommastResult(int rowNum, String status, String message) {

        Row row = commaster.getRow(rowNum);
        if (row == null) {
            row = commaster.createRow(rowNum);
        }

        row.createCell(20).setCellValue(status);

    }

    public static void writecomgrpResult(int rowNum, String status, String message) {

        Row row = comgrp.getRow(rowNum);
        if (row == null) {
            row = comgrp.createRow(rowNum);
        }

        row.createCell(10).setCellValue(status);

    }

    public static void writecomgrpedit(int rowNum, String newname, String message) {

        Row row = comgrp.getRow(rowNum);
        if (row == null) {
            row = comgrp.createRow(rowNum);
        }
        row.createCell(1).setCellValue(newname);

    }
   public static void writemarginResult(int rowNum, String status, String message) {

        Row row = cusmarginSheet.getRow(rowNum);
        if (row == null) {
            row = cusmarginSheet.createRow(rowNum);
        }

        row.createCell(5).setCellValue(status);

    }

    public static void writepremResult(int rowNum, String status, String message) {

        Row row = premiumSheet.getRow(rowNum);
        if (row == null) {
            row = premiumSheet.createRow(rowNum);
        }

        row.createCell(11).setCellValue(status);

    }

    public static void updateMaster(int rowIndex, int pass, int fail) {

        Row row = masterSheet.getRow(rowIndex);

        if (row == null) {
            row = masterSheet.createRow(rowIndex);
        }

        String result = "PASS - " + pass + " FAIL - " + fail;

        row.createCell(3).setCellValue(result);
    }

    public static void updatecomgrpname(int rowIndex, String edname) {

        Row row = comgrp.getRow(rowIndex);

        if (row == null) {
            row = comgrp.createRow(rowIndex);
        }

        String name = edname;
        System.out.println(name);
        row.createCell(1).setCellValue(name);
    }

    public static void updatecommasedme(int rowIndex, String edname) {

        Row row = commaster.getRow(rowIndex);

        if (row == null) {
            row = commaster.createRow(rowIndex);
        }

        String name = edname;

        row.createCell(1).setCellValue(name);
    }

    public static void updatecomgrpedme(int rowIndex, String edname) {

        Row row = comgrp.getRow(rowIndex);

        if (row == null) {
            row = comgrp.createRow(rowIndex);
        }

        String name = edname;

        row.createCell(1).setCellValue(name);
    }

    public static void deletecomgrprow(String name) {

        int lastRowNum = comgrp.getLastRowNum();

        for (int i = 0; i <= lastRowNum; i++) {

            Row row = comgrp.getRow(i);

            if (row == null) {
                continue;
            }

            Cell cell = row.getCell(1);
            String execu = row.getCell(2).getStringCellValue();// Column index 1 (same as your update)

            if (cell != null && cell.getCellType() == CellType.STRING) {

                if (cell.getStringCellValue().equalsIgnoreCase(name)) {
                    if (execu.equalsIgnoreCase("yes")) {
                        comgrp.removeRow(row);
                    }

                    break;
                }
            }
        }
    }

    public static void writecusgrpResult(int rowNum, String status, String message) {

        Row row = cusgrpSheet.getRow(rowNum);
        if (row == null) {
            row = cusgrpSheet.createRow(rowNum);
        }

        row.createCell(10).setCellValue(status);

    }

    public static void writerpanelsetResult(int rowNum, String status, String message) {

        Row row = rpanelsetSheet.getRow(rowNum);
        if (row == null) {
            row = rpanelsetSheet.createRow(rowNum);
        }

        row.createCell(6).setCellValue(status);

    }

    public static void writemarqueeResult(int rowNum, String status, String message) {

        Row row = marqueeSheet.getRow(rowNum);
        if (row == null) {
            row = marqueeSheet.createRow(rowNum);
        }

        row.createCell(8).setCellValue(status);

    }

    public static void writepopupResult(int rowNum, String status, String message) {

        Row row = popupSheet.getRow(rowNum);
        if (row == null) {
            row = popupSheet.createRow(rowNum);
        }

        row.createCell(10).setCellValue(status);

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
