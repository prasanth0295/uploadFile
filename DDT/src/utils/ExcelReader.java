package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

import config.Constants;

public class ExcelReader {

	XSSFWorkbook workbook;
	public XSSFSheet sheet;
	XSSFCell cell;

	File src = new File(Constants.Path_TestData);
	File src2 = new File("G:\\Result.xlsx");

	public void setSheet(int Sheet) throws IOException {
		FileInputStream fis = new FileInputStream(src);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheetAt(Sheet);

	}

	public  String getSheetName(int i) throws IOException {

		String sheetName = sheet.getSheetName();
		//System.out.println(sheetName);

		return sheetName;
	}

	
	public String getEmail(int i) throws IOException {

		cell = sheet.getRow(i).getCell(0);
		System.out.println(cell.getStringCellValue());

		return cell.getStringCellValue();
	}

	public String getPassword(int i) throws IOException {

		cell = sheet.getRow(i).getCell(1);
		System.out.println(cell.getStringCellValue());
		return cell.getStringCellValue();

	}

	public void setResult(int i, String result) throws IOException {
		System.out.println(i + " " + result);
		sheet.getRow(i).createCell(2).setCellValue(result);
		FileOutputStream fileOut = new FileOutputStream(src);
		workbook.write(fileOut);
		fileOut.close();
	}
}
