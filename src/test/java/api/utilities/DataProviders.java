package api.utilities;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	@DataProvider(name = "AllData")
	public String[][] allDataProvider() {
		String fileName = System.getProperty("user.dir") + "//TestData//TestData.xlsx";
		
		int ttlRowsCount = ReadExcelFile.getRowCount(fileName, "Sheet1");
		int ttlColCount = ReadExcelFile.getColCount(fileName, "Sheet1");
		
		String userData[][] = new String[ttlRowsCount-1][ttlColCount];
		
		for (int rowNo = 1; rowNo < ttlRowsCount; rowNo++) 
		{
			for (int colNo = 0; colNo < ttlColCount; colNo++) 
			{
				userData[rowNo-1][colNo] = ReadExcelFile.getCellValue(fileName, "Sheet1", rowNo, colNo);
			}
		}
		return userData;
	}
	
	@DataProvider(name = "UserNamesData")
	public String[] userNamesProvider() {
		String fileName = System.getProperty("user.dir") + "//TestData//TestData.xlsx";
		
		int ttlRowsCount = ReadExcelFile.getRowCount(fileName, "Sheet1");
		//int ttlColCount = ReadExcelFile.getColCount(fileName, "Sheet1");
		
		String userNamesData[] = new String[ttlRowsCount-1];
		
		for (int rowNo = 1; rowNo < ttlRowsCount; rowNo++) 
		{
			userNamesData[rowNo-1] = ReadExcelFile.getCellValue(fileName, "Sheet1", rowNo, 1);
		}
		return userNamesData;
	}
}
