package com.youzan.common.util;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

import com.youzan.common.bean.constant.Constants;

/**
 * excel 导出工具类
 * @author zhucy
 */
public class ExcelExportUtil {

	private String template_xls = null;
	private String out_xls = null;
	private InputStream input = null;
	private OutputStream output = null;
	private Workbook workBook = null;
	
	public ExcelExportUtil(String imp){
		this.template_xls = imp;
	}

	public ExcelExportUtil(String imp, String out) {
		this(imp);
		this.out_xls = out;
	}
	
	public ExcelExportUtil(InputStream input) throws InvalidFormatException, IOException {
		//读取模板
		if (null == workBook) {
			workBook = WorkbookFactory.create(input);
		}
	}

	private void initTemplate() throws InvalidFormatException, IOException {
		if (null == workBook) {
			if (null == input) {
				input = this.getClass().getClassLoader().getResourceAsStream(template_xls);
			}

			if (null == output && null != out_xls && !"".equals(out_xls)) {
				output = new FileOutputStream(out_xls);
			}
			// 读取模板
			workBook = WorkbookFactory.create(input);
		}
	}

	/**
	 * 写入指定列的数据
	 * 
	 * @param dataMap
	 * @param sheetIndex
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public void writeData(Map<String, Object> dataMap, int sheetIndex)
			throws InvalidFormatException, IOException {
		this.initTemplate();
		// 数据填充的sheet
		Sheet sheet = workBook.getSheetAt(sheetIndex);

		for (Entry<String, Object> entry : dataMap.entrySet()) {
			// 指定坐标赋值
			setCell(getCell(entry.getKey(), entry.getValue(), sheet), sheet);
		}

		// 设置生成excel中公式自动计算
		sheet.setForceFormulaRecalculation(true);
	}
	
	public void setSheetName(int sheet, String name) {
		workBook.setSheetName(sheet, name);
	}
	
	public void initSheet(int size) {
		int sheetSize = workBook.getNumberOfSheets();
		
		if(sheetSize != size) {
			for(int i=1;i<size;i++) {
				workBook.cloneSheet(0);
			}
		}
	}

	/**
	 * 写入list 数据
	 * 
	 * @param rows 数据行
	 * @param sheetIndex excel sheet index 从0开始
	 * @param rowIndex 开始行索引 从1 开始
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public void writeDataList(List<Map<Integer, Object>> rows, int sheetIndex,
			int rowIndex,boolean isShift) throws InvalidFormatException, IOException {
		this.initTemplate();

		// 数据填充的sheet
		Sheet sheet = workBook.getSheetAt(sheetIndex);

		Map<Integer, Object> rowMap = null;

		int size = rows.size();
		int endRow = rowIndex + size - 2;
		
		//如果存在大于开始行的row，移动到插入行数量后面去
		int maxRow = sheet.getLastRowNum();
		if(maxRow > rowIndex){
			sheet.shiftRows(rowIndex, maxRow, size);
		}

		copyRows(rowIndex, endRow, rowIndex, sheet);// 复制list 行
		//insertRows(rowIndex,rowIndex + 1,size,sheet);

		for (int i = 0; i < size; i++) {
			rowMap = rows.get(i);
			for (Entry<Integer, Object> entry : rowMap.entrySet()) {
				setCell(getCell(sheet, rowIndex + i - 1, entry.getKey() - 1,
						entry.getValue()), sheet);
			}
		}

		// 设置生成excel中公式自动计算
		sheet.setForceFormulaRecalculation(true);
	}
	

	/**
	 * sheet 合并
	 * 
	 * @param sheetIndex
	 *            sheet index
	 * @param firstRow
	 *            开始行索引
	 * @param lastRow
	 *            结束行索引
	 * @param firstCol
	 *            开始列索引
	 * @param lastCol
	 *            结束列索引
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public void merge(int sheetIndex, int firstRow, int lastRow, int firstCol,
			int lastCol) throws InvalidFormatException, IOException {
		this.merge(sheetIndex, firstRow, lastRow, firstCol, lastCol, "");
	}

	/**
	 * sheet 合并，并设置数据
	 * 
	 * @param sheetIndex
	 *            sheet index
	 * @param firstRow
	 *            开始行索引
	 * @param lastRow
	 *            结束行索引
	 * @param firstCol
	 *            开始列索引
	 * @param lastCol
	 *            结束列索引
	 * @param data
	 *            cell 数据
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public void merge(int sheetIndex, int firstRow, int lastRow, int firstCol,
			int lastCol, Object data) throws InvalidFormatException,
			IOException {
		this.initTemplate();

		// 数据填充的sheet
		Sheet sheet = workBook.getSheetAt(sheetIndex);

		CellRangeAddress rang = new CellRangeAddress(firstRow, lastRow,
				firstCol, lastCol);
		sheet.addMergedRegion(rang);

		setCell(getCell(sheet, firstRow, firstCol, data, null), sheet);
	}

	/**
	 * 设置cell 样式(使用默认样式)
	 * 
	 * @param sheetIndex
	 * @param rowIndex
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public void setCellStyle(int sheetIndex, int rowIndex)
			throws InvalidFormatException, IOException {
		this.setCellStyle(sheetIndex, rowIndex, this.createDefaultStyleCell());
	}

	/**
	 * 设置cell 字体粗体样式(使用默认样式)
	 * 
	 * @param sheetIndex
	 * @param rowIndex
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public void setBoldCellFontStyle(int sheetIndex, int rowIndex)
			throws InvalidFormatException, IOException {
		Font font = this.workBook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

		CellStyle style = this.createDefaultStyleCell();
		style.setFont(font);

		this.setCellStyle(sheetIndex, rowIndex, style);
	}

	/**
	 * 设置cell 字体粗体样式(使用默认样式)
	 * 
	 * @param sheetIndex
	 * @param rowIndex
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public void setBoldCellFontStyle(int sheetIndex, int rowIndex, int fontSize)
			throws InvalidFormatException, IOException {
		this.setBoldCellFontStyle(sheetIndex, rowIndex, fontSize, "宋体");
	}
	
	public void setBoldCellFontStyle(int sheetIndex, int rowIndex, int fontSize,CellStyle style)
			throws InvalidFormatException, IOException {
		this.setBoldCellFontStyle(sheetIndex, rowIndex, fontSize, "宋体",style);
	}
	
	public void setRowHeight(int sheetIndex, int rowIndex,short height){
		Sheet sheet = workBook.getSheetAt(sheetIndex);
		Row curRow = sheet.getRow(rowIndex);
		if(null != curRow){
			curRow.setHeight(height);
		}
	}
	
	public void setBoldCell(int sheetIndex,String cellStr) throws InvalidFormatException, IOException {
		Font font = this.workBook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		
		CellStyle style = this.createDefaultStyleCell();
		style.setFont(font);
		style.setWrapText(true);
		
		this.setCellStyle(sheetIndex, cellStr, style);
	}

	/**
	 * 设置cell 字体粗体样式(使用默认样式)
	 * 
	 * @param sheetIndex
	 * @param rowIndex
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public void setBoldCellFontStyle(int sheetIndex, int rowIndex,
			int fontSize, String fontName) throws InvalidFormatException,
			IOException {
		Font font = this.workBook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) fontSize);
		font.setFontName(fontName);

		CellStyle style = this.createDefaultStyleCell();
		style.setFont(font);
		style.setWrapText(true);

		this.setCellStyle(sheetIndex, rowIndex, style);
	}
	
	/**
	 * 支持
	 * @param sheetIndex
	 * @param rowIndex
	 * @param fontSize
	 * @param fontName
	 * @param isWrapText
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public void setBoldCellFontStyle(int sheetIndex, int rowIndex,int fontSize, String fontName,CellStyle style) throws InvalidFormatException,
			IOException {
		Font font = this.workBook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) fontSize);
		font.setFontName(fontName);

		this.setCellStyle(sheetIndex, rowIndex, style);
	}
	

	/**
	 * 设置cell 公式
	 * 
	 * @param sheetIndex
	 * @param rowIndex
	 * @param colIndex
	 * @param formula
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public void setCellForMula(int sheetIndex, int rowIndex, int colIndex,
			String formula) throws InvalidFormatException, IOException {
		this.initTemplate();
		Sheet sheet = workBook.getSheetAt(sheetIndex);

		// 设置生成excel中公式自动计算
		sheet.setForceFormulaRecalculation(true);

		Row row = sheet.getRow(rowIndex);

		Cell cell = row.getCell(colIndex);
		if (null == cell) {
			cell = row.createCell(colIndex);
		}

		setCellForMula(cell, formula);
	}
	
	public void setThousandsCellStyle(int sheetIndex, String cellStr,boolean isBlod,int fontSize) throws InvalidFormatException, IOException{
		CellStyle cellStyle = createDefaultStyleCell();
		
		Font font = this.workBook.createFont();
		if(isBlod){
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		}
		
		font.setFontHeightInPoints((short) fontSize);
		font.setFontName("宋体");
		
		cellStyle.setFont(font);
		
		cellStyle.setDataFormat(getDataFormat().getFormat("#,##0"));
		setCellStyle(sheetIndex, cellStr, cellStyle);
	}
	
	/**
	 * 设置千分位样式
	 * @param sheetIndex
	 * @param cellStr
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public void setThousandsCellStyle(int sheetIndex, String cellStr) throws InvalidFormatException, IOException{
		setThousandsCellStyle(sheetIndex,cellStr,false,12);
	}

	/**
	 * 设置样式
	 * @param sheetIndex
	 * @param cellStr
	 * @param cellStyle
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public void setCellStyle(int sheetIndex, String cellStr,CellStyle cellStyle) throws InvalidFormatException, IOException{
		this.initTemplate();
		Sheet sheet = workBook.getSheetAt(sheetIndex);
		
		TempCell tempCell = this.getCell(cellStr, null, sheet);

		Row rowIn = sheet.getRow(tempCell.getRow());
		if (rowIn == null) {
			copyRows(tempCell.getRow() - 1, tempCell.getRow() - 1,
					tempCell.getRow(), sheet);// 复制上一行
			rowIn = sheet.getRow(tempCell.getRow());
		}
		Cell cellIn = rowIn.getCell(tempCell.getColumn());
		if (cellIn == null) {
			cellIn = rowIn.createCell(tempCell.getColumn());
		}
		
		cellIn.setCellStyle(null == cellStyle ? this.createDefaultStyleCell() : cellStyle);
	}
	
	/**
	 * 根据指定列设置公式
	 * 
	 * @param sheetIndex
	 * @param cellStr
	 * @param formula
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public void setCellForMula(int sheetIndex, String cellStr, String formula)
			throws InvalidFormatException, IOException {
		this.initTemplate();
		Sheet sheet = workBook.getSheetAt(sheetIndex);

		// 设置生成excel中公式自动计算
		sheet.setForceFormulaRecalculation(true);

		TempCell tempCell = this.getCell(cellStr, null, sheet);

		Row rowIn = sheet.getRow(tempCell.getRow());
		if (rowIn == null) {
			copyRows(tempCell.getRow() - 1, tempCell.getRow() - 1,
					tempCell.getRow(), sheet);// 复制上一行
			rowIn = sheet.getRow(tempCell.getRow());
		}
		Cell cellIn = rowIn.getCell(tempCell.getColumn());
		if (cellIn == null) {
			cellIn = rowIn.createCell(tempCell.getColumn());
		}

		setCellForMula(cellIn, formula);
	}

	private void setCellForMula(Cell cell, String formula) {
		cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
		cell.setCellFormula(formula);
	}

	public void setCellStyle(int sheetIndex, int rowIndex, CellStyle style)
			throws InvalidFormatException, IOException {
		this.initTemplate();

		Sheet sheet = workBook.getSheetAt(sheetIndex);
		Row curRow = sheet.getRow(rowIndex);
		if(null == curRow) {
			curRow = sheet.createRow(rowIndex);
		}
		int pevRowCellNum = sheet.getRow(rowIndex - 1).getLastCellNum();

		Cell cell = null;

		for (int i = 0; i < pevRowCellNum; i++) {
			cell = curRow.getCell(i);
			if (null == cell) {
				cell = curRow.createCell(i);
			}
			cell.setCellStyle(style);
		}
	}
	
    /**
     * 设置打印区域
     * 
     * @author  Evan.zhu
     * @param sheetIndex 工作薄 下标0开始
     * @param startCell 起始列 下标0开始
     * @param endCell 终止列 下标0开始
     * @param startRow 起始行 下标0开始
     * @param endRow 终止行 下标0开始
     * @return void [Return type description]
     * @throws IOException 
     * @throws InvalidFormatException 
     * @see [Related classes#Related methods#Related properties]
     */
	public void setPrintArea(int sheetIndex,int startCell,int endCell,int startRow,int endRow) throws InvalidFormatException, IOException {
		this.initTemplate();
		workBook.setPrintArea(sheetIndex, startCell, endCell, startRow, endRow);
	}

	private TempCell getCell(Sheet sheet, int rowIndex, int colIndex,
			Object data, CellStyle style) {
		TempCell tempCell = new TempCell();

		Row row = sheet.getRow(rowIndex);
		if (null == row) {
			row = sheet.createRow(rowIndex);
		}

		Cell cell = row.getCell(colIndex);
		if (null == cell) {
			cell = row.createCell(colIndex);
		}

		tempCell.setData(data);
		tempCell.setRow(rowIndex);
		tempCell.setColumn(colIndex);
		tempCell.setCellStyle(null == style ? cell.getCellStyle() : style);

		return tempCell;
	}

	private TempCell getCell(Sheet sheet, int rowIndex, int colIndex,
			Object data) {
		return getCell(sheet, rowIndex, colIndex, data, null);
	}

	/**
	 * 功能:合并单元格
	 */
	private CellRangeAddress mergeRegion(Sheet sheet, int firstRow,
			int lastRow, int firstCol, int lastCol) {
		CellRangeAddress rang = new CellRangeAddress(firstRow, lastRow,
				firstCol, lastCol);
		sheet.addMergedRegion(rang);
		return rang;
	}

	public CellStyle createDefaultStyleCell() throws InvalidFormatException,
			IOException {
		CellStyle style = createStyleCell(HSSFCellStyle.ALIGN_CENTER,
				HSSFCellStyle.VERTICAL_CENTER);

		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);

		return style;
	}
	
	/**
	 * 创建cell style
	 * @param halign
	 * @param bdBottom
	 * @param bdLeft
	 * @param bdRight
	 * @param bdTop
	 * @param fontSize
	 * @param fontName
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public CellStyle createCellStyle(int halign,int bdBottom,int bdLeft,int bdRight,int bdTop,int fontSize,String fontName) throws InvalidFormatException, IOException{
		CellStyle cellStyle = this.createStyleCell((short)halign,HSSFCellStyle.VERTICAL_CENTER);
		
		cellStyle.setBorderBottom((short)bdBottom);
		cellStyle.setBorderLeft((short)bdLeft);
		cellStyle.setBorderRight((short)bdRight);
		cellStyle.setBorderTop((short)bdTop);
		
		Font font = this.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);//设置粗体
		
		font.setFontHeightInPoints((short) fontSize);
		font.setFontName(StringUtils.isNotEmpty(fontName) ? fontName : "宋体");
		
		cellStyle.setFont(font);
		cellStyle.setDataFormat(this.getDataFormat().getFormat("#,##0"));//千分位格式化
		
		return cellStyle; 
	}
	
	public CellStyle createNormalCellStyle(int halign,int bdBottom,int bdLeft,int bdRight,int bdTop,int fontSize,String fontName) throws InvalidFormatException, IOException{
		CellStyle cellStyle = this.createStyleCell((short)halign,HSSFCellStyle.VERTICAL_CENTER);
		
		cellStyle.setBorderBottom((short)bdBottom);
		cellStyle.setBorderLeft((short)bdLeft);
		cellStyle.setBorderRight((short)bdRight);
		cellStyle.setBorderTop((short)bdTop);
		
		Font font = this.createFont();
		
		font.setFontHeightInPoints((short) fontSize);
		font.setFontName(StringUtils.isNotEmpty(fontName) ? fontName : "宋体");
		
		cellStyle.setFont(font);
		
		return cellStyle; 
	}
	
	
	public DataFormat getDataFormat() throws InvalidFormatException, IOException{
		this.initTemplate();
		return workBook.createDataFormat();
	}

	public CellStyle createStyleCell(short halign, short valign)
			throws InvalidFormatException, IOException {
		this.initTemplate();

		CellStyle cellStyle = this.workBook.createCellStyle();
		cellStyle.setAlignment(halign); // 设置单元格水平方向对其方式
		cellStyle.setVerticalAlignment(valign); // 设置单元格垂直方向对其方式

		return cellStyle;
	}

	/**
	 * 创建一个字体
	 * 
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public Font createFont() throws InvalidFormatException, IOException {
		this.initTemplate();
		return this.workBook.createFont();
	}

	/**
	 * 功能:设置合并单元格样式
	 */
	private void setRegionStyle(CellStyle cs, CellRangeAddress region,
			Sheet sheet) {
		for (int i = region.getFirstRow(); i <= region.getLastRow(); i++) {
			Row row = sheet.getRow(i);
			if (row == null)
				row = sheet.createRow(i);
			for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) {
				Cell cell = row.getCell(j);
				if (cell == null) {
					cell = row.createCell(j);
					cell.setCellValue("");
				}
				cell.setCellStyle(cs);
			}
		}
	}

	/**
	 * 功能:给指定坐标单元格赋值
	 */
	private void setCell(TempCell tempCell, Sheet sheet) {
		if (tempCell.getColumnSize() > -1) {
			CellRangeAddress rangeAddress = mergeRegion(sheet,
					tempCell.getRow(), tempCell.getRow(), tempCell.getColumn(),
					tempCell.getColumn() + tempCell.getColumnSize());
			setRegionStyle(tempCell.getCellStyle(), rangeAddress, sheet);
		}

		Row rowIn = sheet.getRow(tempCell.getRow());
		if (rowIn == null) {
			copyRows(tempCell.getRow() - 1, tempCell.getRow() - 1,
					tempCell.getRow(), sheet);// 复制上一行
			rowIn = sheet.getRow(tempCell.getRow());
		}
		Cell cellIn = rowIn.getCell(tempCell.getColumn());
		if (cellIn == null) {
			cellIn = rowIn.createCell(tempCell.getColumn());
		}
		// 根据data类型给cell赋值
		if (tempCell.getData() instanceof String) {
			cellIn.setCellValue((String) tempCell.getData());
		} else if (tempCell.getData() instanceof Integer) {
			cellIn.setCellValue((int) tempCell.getData());
		} else if (tempCell.getData() instanceof Double) {
			cellIn.setCellValue((double) tempCell.getData());
		} else if (tempCell.getData() instanceof BigDecimal) {
			cellIn.setCellValue(((BigDecimal) tempCell.getData()).doubleValue());
		} else if(tempCell.getData() instanceof Long){
			cellIn.setCellValue((Long)tempCell.getData());
		} else {
			cellIn.setCellValue(null == tempCell.getData() ? Constants.EMPTY : tempCell.getData().toString());
		}
		// 样式
		if (tempCell.getCellStyle() != null && tempCell.getColumnSize() == -1) {
			cellIn.setCellStyle(tempCell.getCellStyle());
		}
	}

	/**
	 * 功能:copy rows
	 */
	private void copyRows(int startRow, int endRow, int pPosition, Sheet sheet) {
		int pStartRow = startRow - 1;
		int pEndRow = endRow - 1;
		int targetRowFrom;
		int targetRowTo;
		int columnCount;
		CellRangeAddress region = null;
		int i;
		int j;
		if (pStartRow == -1 || pEndRow == -1) {
			return;
		}
		// 拷贝合并的单元格
		for (i = 0; i < sheet.getNumMergedRegions(); i++) {
			region = sheet.getMergedRegion(i);
			if ((region.getFirstRow() >= pStartRow)
					&& (region.getLastRow() <= pEndRow)) {
				targetRowFrom = region.getFirstRow() - pStartRow + pPosition;
				targetRowTo = region.getLastRow() - pStartRow + pPosition;
				CellRangeAddress newRegion = region.copy();
				newRegion.setFirstRow(targetRowFrom);
				newRegion.setFirstColumn(region.getFirstColumn());
				newRegion.setLastRow(targetRowTo);
				newRegion.setLastColumn(region.getLastColumn());
				sheet.addMergedRegion(newRegion);
			}
		}
		// 设置列宽
		for (i = pStartRow; i <= pEndRow; i++) {
			Row sourceRow = sheet.getRow(i);
			columnCount = sourceRow.getLastCellNum();
			if (sourceRow != null) {
				Row newRow = sheet.createRow(pPosition - pStartRow + i);
				// Row newRow = sheet.createRow(++i);
				newRow.setHeight(sourceRow.getHeight());
				for (j = 0; j < columnCount; j++) {
					Cell templateCell = sourceRow.getCell(j);
					if (templateCell != null) {
						Cell newCell = newRow.createCell(j);
						copyCell(templateCell, newCell);
					}
				}
			}
		}
	}

	/**
	 * 功能:copy cell,不copy值
	 */
	private void copyCell(Cell srcCell, Cell distCell) {
		distCell.setCellStyle(srcCell.getCellStyle());
		if (srcCell.getCellComment() != null) {
			distCell.setCellComment(srcCell.getCellComment());
		}
		int srcCellType = srcCell.getCellType();
		distCell.setCellType(srcCellType);
	}

	/**
	 * 功能:获取单元格数据,样式(根据坐标:B3)
	 */
	private TempCell getCell(String key, Object value, Sheet sheet) {
		TempCell tempCell = new TempCell();

		// 得到列 字母
		String lineStr = "";
		String reg = "[A-Z]+";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(key);
		while (m.find()) {
			lineStr = m.group();
		}
		// 将列字母转成列号 根据ascii转换
		char[] ch = lineStr.toCharArray();
		int column = 0;
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			int post = ch.length - i - 1;
			int r = (int) Math.pow(10, post);
			column = column + r * ((int) c - 65);
		}
		tempCell.setColumn(column);

		// 得到行号
		reg = "[0-9]+";
		p = Pattern.compile(reg);
		m = p.matcher(key);
		while (m.find()) {
			tempCell.setRow((Integer.parseInt(m.group()) - 1));
		}

		// 获取模板指定单元格样式,设置到tempCell(写列表数据的时候用)
		Row rowIn = sheet.getRow(tempCell.getRow());
		if (rowIn == null) {
			rowIn = sheet.createRow(tempCell.getRow());
		}
		Cell cellIn = rowIn.getCell(tempCell.getColumn());
		if (cellIn == null) {
			cellIn = rowIn.createCell(tempCell.getColumn());
		}
		tempCell.setCellStyle(cellIn.getCellStyle());
		tempCell.setData(value);
		return tempCell;
	}

	/**
	 * 输出到byte 字节数组
	 * 
	 * @return
	 * @throws IOException
	 */
	public byte[] writeBytes() throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		workBook.write(output);

		output.flush();
		byte[] b = output.toByteArray();
		output.close();

		return b;
	}

	/**
	 * 写入到输出流，并返回
	 * 
	 * @return
	 * @throws IOException
	 */
	public OutputStream writeOutputStream() throws IOException {
		if (null != output) {
			workBook.write(output);
		}
		if (null != input) {
			input.close();
		}
		return output;
	}

	/**
	 * 写入输出流
	 * 
	 * @throws IOException
	 */
	public void writeFile() throws IOException {
		if (null != output) {
			workBook.write(output);
		}
		this.close();
	}

	private void close() throws IOException {
		if (null != input) {
			input.close();
		}
		if (null != output) {
			output.flush();
			output.close();
		}
	}

	/**
	 * 描述:临时单元格数据
	 */
	class TempCell {
		private int row;
		private int column;
		private CellStyle cellStyle;
		private Object data;
		// 用于列表合并,表示几列合并
		private int columnSize = -1;

		public int getColumn() {
			return column;
		}

		public void setColumn(int column) {
			this.column = column;
		}

		public int getRow() {
			return row;
		}

		public void setRow(int row) {
			this.row = row;
		}

		public CellStyle getCellStyle() {
			return cellStyle;
		}

		public void setCellStyle(CellStyle cellStyle) {
			this.cellStyle = cellStyle;
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}

		public int getColumnSize() {
			return columnSize;
		}

		public void setColumnSize(int columnSize) {
			this.columnSize = columnSize;
		}
	}

}
