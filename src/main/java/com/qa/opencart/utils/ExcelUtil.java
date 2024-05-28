package com.qa.opencart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

//import only org.apache.poi.ss.usermodel .....
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {

	private static String TEST_DATA_SHEET_PATH = "./src/test/resources/testdata/OpenCartTestData.xlsx";     // ./filepath
	
	private static Workbook book;
	private static Sheet sheet;
	
	//method to read data from excel file
	public static Object[][] getTestData(String sheetName) {
		
		System.out.println("Reading test data from sheet: "+sheetName);
		
		Object data[][]=null;
		
		try {
			FileInputStream ip = new FileInputStream(TEST_DATA_SHEET_PATH);   //to establish a connection between java 
			                                                                  //code and data file
			book = WorkbookFactory.create(ip); //WorkbookFactory Class for creating a copy of this excel file in java memory 
											   //Comes from apache poi
			sheet = book.getSheet(sheetName);
			
			data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
			
			for(int i=0; i<sheet.getLastRowNum(); i++) {
				for(int j=0; j<sheet.getRow(0).getLastCellNum(); j++) {
					data[i][j]= sheet.getRow(i+1).getCell(j).toString(); 
					System.out.println("**"+data[i][j]+"**");
				//  data[0][0]= sheet.getRow(1).getCell(0)     -- since row(0) has only labels in the sheet
				}
			}	
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
		
	}
	
}
