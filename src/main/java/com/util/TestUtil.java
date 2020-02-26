/**
 * @author UmaMaheswararao
 */

package com.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class TestUtil {

	public static String TESTDATA_SHEET_PATH = "./src/main/java/com/testdata/NewiQaptureData.xlsx";

	static Workbook book;
	static Sheet sheet;

	// Read particular cell data from Excel
	public static String readCellData(String sheet, int row, int cell){
		String v="";
		try {
			FileInputStream fis=new FileInputStream(TESTDATA_SHEET_PATH);
			Workbook wb=WorkbookFactory.create(fis);
			v=wb.getSheet(sheet).getRow(row).getCell(cell).toString();
		} 
		catch (Exception e) {	
			e.printStackTrace();
		}
		return v;
	}

	// Write data to cell
	public static void writeCellData(String sheet, int row, int cell, String value){
		try {
			FileInputStream fis=new FileInputStream(TESTDATA_SHEET_PATH);
			Workbook wb=WorkbookFactory.create(fis);
			wb.getSheet(sheet).getRow(row).createCell(cell).setCellValue(value);
			FileOutputStream fos = new FileOutputStream(TESTDATA_SHEET_PATH);
			wb.write(fos);
		} 
		catch (Exception e) {		
			e.printStackTrace();
		}
	}


	// Read set of data from Excel
	public static Object[][] getTestData(String sheetName) {
		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		System.out.println("No.of Rows: "+sheet.getLastRowNum() + " & " + "No.of columns: "+sheet.getRow(0).getLastCellNum());
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
				// System.out.println(data[i][k]);
			}
		}
		return data;
	}

	// Get data from a given cell, generates random data and writes it to the same cell
	public static String getRandomData(String sheet, int rowNum, int colNum) {
		String newData = null;
		String old = TestUtil.readCellData(sheet, rowNum, colNum);
		for (int i = 0; i < 1000; i++) {
			// Removes numerics from the cell data and append a random number
			newData = old.replaceAll("\\d","")+new Random().nextInt(1000);
			if (!newData.equals(old)) {
				break;
			} 
		}
		TestUtil.writeCellData(sheet, rowNum, colNum, newData);
		return newData;
	}

	// Get Data from a given cell, increments integer part and writes it to the same cell
	public static String getOrderedRandomData(String sheet, int rowNum, int colNum) {
		String oldData = TestUtil.readCellData(sheet, rowNum, colNum);
		String txt = oldData.replaceAll("\\d","");
		int num = Integer.parseInt(oldData.replaceAll("[^0-9]",""));
		String newData = txt+(++num);
		TestUtil.writeCellData(sheet, rowNum, colNum, newData);
		return newData;
	}


}
