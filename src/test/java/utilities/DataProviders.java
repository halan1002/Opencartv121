package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	//Data Provider 1
	@DataProvider(name="LoginData")
	public String [][] getData() throws IOException
	{
		String path=".\\testData\\OpenCart_LoginData.xlsx";//taking excel file from testData
		ExcelUtility excelUtil=new ExcelUtility(path);//create an object ExcelUtility
		int totalRows=excelUtil.getRowCount("Sheet1");
		int totalCols=excelUtil.getRCellCount("Sheet1", 1);
		
		String loginData[][]=new String[totalRows][totalCols];//created for 2 two dimension array which can store info from excel sheet
		for(int i=1;i<=totalRows;i++)//read the data from excel storing in 2 dimension array
		{
			for(int j=0;j<totalCols;j++)
			{
				loginData[i-1][j]=excelUtil.getCellData("Sheet1", i, j);
			}
		}
		return loginData;//returning 2 dimension array
	}
	
	//Data Provider 2
	
	//Data Provider 3
}
