/**
 * @author UmaMaheswararao
 */

package com.util;

import java.util.ArrayList;
import java.util.Random;

public class Container {
	static ExcelUtility reader;
	public static ArrayList<Object[]> getEmpAnalyseDataFromExcel() {
		ArrayList<Object[]> myData = new ArrayList<Object[]>();
		try {
			reader = new ExcelUtility("./src/main/java/com/testdata/NewiQaptureData.xlsx");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int rowNum = 2; rowNum <= reader.getRowCount("capture"); rowNum++) {
			String searchItem = reader.getCellData("capture", "searchItem", rowNum);
			String empNo = reader.getCellData("capture", "empNo", rowNum);
			String empName = reader.getCellData("capture", "empName", rowNum);
			String cityNo = reader.getCellData("capture", "cityNo", rowNum);
			String empFee = reader.getCellData("capture", "empFee", rowNum);
			String empFormFee = reader.getCellData("capture", "empFormFee", rowNum);
			String empRegFee = reader.getCellData("capture", "empRegFee", rowNum);
			String sscPercentage = reader.getCellData("capture", "sscPercentage", rowNum);
			String interPercentage = reader.getCellData("capture", "interPercentage", rowNum);
			String graduationPercentage = reader.getCellData("capture", "graduationPercentage", rowNum);
			String mastersPercentage = reader.getCellData("capture", "mastersPercentage", rowNum);
			String qualification = reader.getCellData("capture", "qualification", rowNum);

			String joiningYear = reader.getCellData("capture", "joiningYear", rowNum);
			String joiningMonth = reader.getCellData("capture", "joiningMonth", rowNum);
			String joiningDate = reader.getCellData("capture", "joiningDate", rowNum);
			String exitYear = reader.getCellData("capture", "exitYear", rowNum);
			String exitMonth = reader.getCellData("capture", "exitMonth", rowNum);
			String exitDate = reader.getCellData("capture", "exitDate", rowNum);

			String companyEnvironment = reader.getCellData("capture", "companyEnvironment", rowNum);
			String salaryIncrement = reader.getCellData("capture", "salaryIncrement", rowNum);
			String overAll = reader.getCellData("capture", "overAll", rowNum);
			String ddSelect = reader.getCellData("capture", "ddSelect", rowNum);
			String yesNoNa = reader.getCellData("capture", "yesNoNa", rowNum);
			String checkBox1 = reader.getCellData("capture", "checkBox1", rowNum);
			String checkBox2 = reader.getCellData("capture", "checkBox2", rowNum);
			String checkBoxQ2 = reader.getCellData("capture", "checkBoxQ2", rowNum);

			Object ob[] = {searchItem, empNo, empName, cityNo, empFee, empFormFee, empRegFee, 
					sscPercentage, interPercentage, graduationPercentage, mastersPercentage, qualification,
					joiningYear, joiningMonth, joiningDate, exitYear, exitMonth, exitDate,
					companyEnvironment, salaryIncrement, overAll, ddSelect, yesNoNa, checkBox1, checkBox2, checkBoxQ2};
			myData.add(ob);
		}
		return myData;
	}
	public static ArrayList<Object[]> getLoginDataFromExcel() {
		ArrayList<Object[]> loginData = new ArrayList<Object[]>();
		try {
			reader = new ExcelUtility("./src/main/java/com/testdata/NewiQaptureData.xlsx");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(int rowNum = 2; rowNum <= reader.getRowCount("Login"); rowNum++) {
			String username = reader.getCellData("Login", "username", rowNum);
			String password = reader.getCellData("Login", "password", rowNum);

			Object ob[] = {username, password};
			loginData.add(ob);
		}
		return loginData;
	}
	
	public static void getRandomData() {
		Random r = new Random();
		try {
			reader = new ExcelUtility("./src/main/java/com/testdata/NewiQaptureData.xlsx");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String data = reader.getCellData("Analyse", 0, 1);
		String newData = data+r.nextInt();
		
		
		
		
	}



}
