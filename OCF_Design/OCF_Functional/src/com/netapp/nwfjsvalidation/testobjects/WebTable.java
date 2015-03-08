package com.netapp.nwfjsvalidation.testobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.netapp.nwfjsvalidation.lib.utils.ExtLogger;

public class WebTable {

	private static ExtLogger logger = ExtLogger.getLogger(WebTable.class);

	private WebElement webTable;
	private Hashtable<String, Integer> colNames = new Hashtable<String, Integer>();
	private List<WebElement> rows = new ArrayList<WebElement>();
	private List<String> selectedRowData = new ArrayList<String>();
	private int selectedRow = -1;

	public WebTable(WebElement table) {
		this.webTable = table;
		List<WebElement> tableCol = this.webTable.findElements(By
				.cssSelector("thead tr th"));
		if (tableCol.size() == 0) {
			logger.error(" No Data Available ");
		} else {
			for (int col = 0; col < tableCol.size() - 1; col++) {
				colNames.put(tableCol.get(col).getText(), col);
			}
			refreshTable(this.webTable, false);
		}
	}

	public int getRows() {
		return this.rows.size();
	}

	public List<WebElement> getRowData(WebElement row) {
		return row.findElements(By.tagName("td"));
	}

	public void refreshTable(WebElement tableElement) {
		this.refreshTable(tableElement, true);
	}

	public void refreshTable(WebElement tableElement, boolean isClearedTable) {
		clear();
		this.webTable = tableElement;
		List<WebElement> tableRows = this.webTable.findElements(By
				.tagName("tr"));
		if (tableRows != null && tableRows.size() > 1) {
			rows = tableRows.subList(1, tableRows.size());
		}
	}

	public int getColumnIndex(String column) {
		return this.colNames.get(column);
	}

	public int getSelectedRow() {
		return this.selectedRow;
	}

	public String getValue(int rowIndex, int colIndex) {
		String value = "";
		WebElement row = rows.get(rowIndex);
		List<WebElement> rowValues = getRowData(row);
		value = rowValues.get(colIndex).getText();
		return value;
	}

	public void clickSingleCell(int rowIndex, int colIndex) {
		WebElement row = rows.get(rowIndex);
		List<WebElement> rowValues = getRowData(row);
		rowValues.get(colIndex).click();
	}

	public void clickSingleCell(int rowIndex, String column) {
		this.clickSingleCell(rowIndex, getColumnIndex(column));
	}

	public void clear() {
		this.rows.clear();
		this.selectedRowData.clear();
		this.selectedRow = -1;
	}

	public String getValueFromSelectedRow(String column) {
		return selectedRowData.get(getColumnIndex(column));
	}

	private boolean isTableRowExist(WebElement row, HashMap<String, ?> expected) {
		boolean isFound = false;
		List<WebElement> colValues = getRowData(row);
		for (String key : expected.keySet()) {
			int colIndex = colNames.get(key);
			if (colIndex >= 0) {
				isFound = colValues.get(colIndex).getText()
						.contains(expected.get(key).toString());
				if (!isFound)
					return isFound;
			}
		}
		for (int col = 0; col < colValues.size(); col++) {
			selectedRowData.add(colValues.get(col).getText());
		}
		return isFound;
	}

	private WebElement findData(List<WebElement> tableRows,
			HashMap<String, ?> expected) {
		WebElement rowFound = null;
		for (int row = 0; row < tableRows.size(); row++) {
			if (isTableRowExist(tableRows.get(row), expected)) {
				this.selectedRow = row + 1;
				return tableRows.get(row);
			}
		}
		return rowFound;
	}

	public void selectRow(HashMap<String, ?> expected) {
		WebElement rowFound = findRow(expected);
		if (rowFound != null) {
			rowFound.click();
		}
	}

	public WebElement findRow(HashMap<String, ?> expected) {
		WebElement rowFound = findData(rows, expected);
		if (rowFound != null) {
			logger.info("Selected row : " + rowFound.getText());
		}
		return rowFound;
	}
}
