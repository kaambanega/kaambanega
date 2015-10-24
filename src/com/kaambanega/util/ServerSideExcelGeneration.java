/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kaambanega.util;

import java.io.*;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.*;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author Fenil Jariwala
 */
public class ServerSideExcelGeneration
{

    private HSSFWorkbook wb;
    private HSSFSheet sheet;
    private HSSFRow contentRow;
    private HSSFCellStyle titleCellFmt;
    private HSSFCellStyle colHeadCellFmt;
    private HSSFCellStyle colFootCellFmt;
    private HSSFCellStyle rowCellLeftFmt;
    private HSSFCellStyle rowCellRightFmt;
    private HSSFCellStyle rowCellCenterFmt;
    private List<String> rowWidthList;
    private List<String> rowAlignList;
    private List<List<String>> headerColumnName;
    private List<List<String>> footerColumnName;
    private double[] stat_total;
    private List<String> rowColumnIds;
    private List<String> rowColumnTypes;
    private int rowCount = 0;
    private int splitCount = 50000;
    private boolean splitInSheet = true;
    private DecimalFormat formatter;
    private List colAlphaName;
    private OutputStream outstream;
    private String exportPath;
    private int sheetNum;
    private int headerRowSize = 0;
    private int titleRowSize = 0;
    private String noRecordFound = "No record found.";

    public void generateSSExcel(SqlRowSet resultSet, ExcelConfigurationBean excelObj) throws FileNotFoundException, IOException
    {
        generateExcel(resultSet, null, excelObj, null);
    }

    public void generateSSExcel(List dataList, ExcelConfigurationBean excelObj) throws FileNotFoundException, IOException
    {
        generateExcel(null, dataList, excelObj, null);
    }

    public void generateSSExcel(ResultSet resultSet, ExcelConfigurationBean excelObj) throws FileNotFoundException, IOException
    {
        generateExcel(null, null, excelObj, resultSet);
    }

    public void generateExcel(SqlRowSet resultSet, List dataList, ExcelConfigurationBean excelObj, ResultSet rs) throws FileNotFoundException, IOException
    {
        // Setting user parameter - export file name //
        String exportFilename = "YourReport";
        if (excelObj.getExportFileName() != null && !excelObj.getExportFileName().equals(""))
        {
            exportFilename = excelObj.getExportFileName();
            exportFilename = exportFilename.replaceAll(" ", "%20");
        }
        // Setting user parameter - split screen count //
        splitCount = excelObj.getSplitCount();
        if (splitCount > 60000)
        {
            splitCount = 60000;
        }
        // Setting user parameter - decimal format //
        String cDecimalFormat = "0.00";
        if (excelObj.getCustomDecimalFormat() != null)
        {
            cDecimalFormat = excelObj.getCustomDecimalFormat();
        }
        formatter = new DecimalFormat(cDecimalFormat);

        rowColumnIds = Arrays.asList(excelObj.getColumnsId().split(","));

        rowColumnTypes = excelObj.getColumnType().equalsIgnoreCase("") ? new ArrayList<String>() : Arrays.asList(excelObj.getColumnType().split(","));
        //noofColumns = rowColumnIds.size();

        //*****changes****//
//        Debug.appendLogFile("rowcoumn ids >.......... " + rowColumnIds);
        stat_total = new double[rowColumnIds.size() + 1];
//        Debug.appendLogFile("stat toatal__ :: " + stat_total.length);
        // Getting attributes of records in array
        if (excelObj.getRowAlign() != null)
        {
            rowAlignList = Arrays.asList(excelObj.getRowAlign().split(",")); // Making list of alignment of records cell //
        }
        if (excelObj.getRowWidth() != null)
        {
            rowWidthList = Arrays.asList(excelObj.getRowWidth().split(",")); // Making list of width of column cell //
        }
        // Generating a list of cell for header columns //
        if (excelObj.getAttachColumns() != null)
        {
            headerColumnName = new ArrayList<List<String>>();
            for (int k = 0; k < excelObj.getAttachColumns().size(); k++)
            {
                List<String> myList = Arrays.asList(excelObj.getAttachColumns().get(k).replaceAll(",", ", ").split(", "));
                headerColumnName.add(myList);
            }
            headerRowSize = headerColumnName.size();
//            Debug.appendLogFile("header row size ....." + headerRowSize);
//            Debug.appendLogFile("header row name ....." + headerColumnName);
        }
        // Generating a list of cell for footer columns //
        if (excelObj.getAttachFooters() != null)
        {
            footerColumnName = new ArrayList<List<String>>();
            for (int k = 0; k < excelObj.getAttachFooters().size(); k++)
            {
                List<String> myList = Arrays.asList(excelObj.getAttachFooters().get(k).replaceAll(",", ", ").split(","));
//                Debug.appendLogFile(" my list ................" + myList);
                footerColumnName.add(myList);
            }
        }

        // Retrieving HttpServletResponse response object //
        HttpServletResponse resp = excelObj.getResponse();

        // Export path //
        exportPath = excelObj.getExportPath();

        // Custom message when no record //
        if (excelObj.getNoRecordFound() != null)
        {
            noRecordFound = excelObj.getNoRecordFound();
        }

        colAlphaName = getColumnAlphabet();

        wb = new HSSFWorkbook();

        sheet = wb.createSheet("Sheet1");

        /* Setting style for header and footer columns */
        Font headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // Excel title style //
        titleCellFmt = wb.createCellStyle();
        titleCellFmt.setFont(headerFont);
        titleCellFmt.setFillForegroundColor(setColor((byte) 240, (byte) 240, (byte) 240));
        titleCellFmt.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        titleCellFmt.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleCellFmt.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        titleCellFmt.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);

        titleCellFmt.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        titleCellFmt.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);

        titleCellFmt.setBorderRight(HSSFCellStyle.BORDER_THIN);
        titleCellFmt.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);

        titleCellFmt.setBorderTop(HSSFCellStyle.BORDER_THIN);
        titleCellFmt.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);


        // Header column style //
        colHeadCellFmt = wb.createCellStyle();

        colHeadCellFmt.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        colHeadCellFmt.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);

        colHeadCellFmt.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        colHeadCellFmt.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);

        colHeadCellFmt.setBorderRight(HSSFCellStyle.BORDER_THIN);
        colHeadCellFmt.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);

        colHeadCellFmt.setBorderTop(HSSFCellStyle.BORDER_THIN);
        colHeadCellFmt.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);

        colHeadCellFmt.setFont(headerFont);
        colHeadCellFmt.setFillForegroundColor(setColor((byte) 240, (byte) 240, (byte) 240));
        colHeadCellFmt.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        colHeadCellFmt.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);


        // Footer column style //
        colFootCellFmt = wb.createCellStyle();
        colFootCellFmt.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        colFootCellFmt.setLeftBorderColor(HSSFColor.GREY_25_PERCENT.index);

        colFootCellFmt.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        colFootCellFmt.setBottomBorderColor(HSSFColor.GREY_25_PERCENT.index);

        colFootCellFmt.setBorderRight(HSSFCellStyle.BORDER_THIN);
        colFootCellFmt.setRightBorderColor(HSSFColor.GREY_25_PERCENT.index);

        colFootCellFmt.setBorderTop(HSSFCellStyle.BORDER_THIN);
        colFootCellFmt.setTopBorderColor(HSSFColor.GREY_25_PERCENT.index);

        colFootCellFmt.setFont(headerFont);
        colFootCellFmt.setFillForegroundColor(setColor((byte) 240, (byte) 240, (byte) 240));
        colFootCellFmt.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        colFootCellFmt.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        /* Setting style for records */
        rowCellLeftFmt = wb.createCellStyle();

        rowCellLeftFmt.setAlignment(HSSFCellStyle.ALIGN_LEFT);

        rowCellCenterFmt = wb.createCellStyle();
        rowCellCenterFmt.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        rowCellRightFmt = wb.createCellStyle();
        rowCellRightFmt.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

        // Generate the title of the excel //
        if (excelObj.getExcelTitle() != null)
        {
            titleRowSize = excelObj.getExcelTitle().size();
            printTitle(excelObj);
        }

        // Generating Header //
        if (excelObj.getAttachColumns() != null)
        {
            printColumn();
        }

        // Generating Records //
        printRows(resultSet, dataList, excelObj, rs);

        // Generating Footer //
        if (excelObj.getAttachFooters() != null)
        {
            printFooter();
        }
        // Printing note bellow footer //
        if (excelObj.getFooterNote() != null)
        {
            printFooterNote(excelObj);
        }

        outputExcel(resp, exportFilename);
    }

    /*----------------------- Function to generate title of excel ----------------------*/
    private void printTitle(ExcelConfigurationBean excelObj)
    {
        for (int i = 0; i < titleRowSize; i++)
        {
            generateCustomMsg(i, excelObj.getExcelTitle().get(i), colAlphaName.get(0).toString() + (i + 1) + ":" + colAlphaName.get(rowColumnIds.size()).toString() + (i + 1), titleCellFmt);
        }
        rowCount += titleRowSize;
    }

    /*----------------------- Function to generate header columns ----------------------*/
    private void printColumn()
    {
        HSSFRow headerRow;
        for (int x = 0; x < headerRowSize; x++)
        {
            headerRow = sheet.createRow(x + titleRowSize);
            int col1 = 0;
            int row1 = 0;
            int col2 = 0;
            int row2 = 0;
            String colName = "";
            int colwidth = 0;
            boolean firstColumn = true;
            for (int y = 0; y < headerColumnName.get(x).size(); y++)
            {
//                Debug.appendLogFile("headerColumnName ...." + x + "...." + headerColumnName.get(x));
//                Debug.appendLogFile("headerColumnName ...." + x + "." + y + "..." + headerColumnName.get(x).get(y));
//                Debug.appendLogFile("clumn names........" + colName);
                if (headerColumnName.get(x).get(y).indexOf("#cspan") > -1)
                {
                    col2 = y;
                    row2 = x + titleRowSize;
                }
                else if (headerColumnName.get(x).get(y).indexOf("#cspan") < 0 && headerColumnName.get(x).get(y).indexOf("#rspan") < 0)
                {
                    if (y == 0 || firstColumn)
                    {
                        colName = headerColumnName.get(x).get(y);
                        colwidth = Integer.parseInt(rowWidthList.get(y)) * 40;
                        firstColumn = false;
                        col1 = y;
                        col2 = y;
                        row1 = x + titleRowSize;
                        row2 = x + titleRowSize;
                    }
                    else
                    {
                        for (int l = x + 1; l < headerRowSize; l++)
                        {
                            if (headerColumnName.get(l).get(y - 1).indexOf("#rspan") > -1)
                            {
                                row2 += 1;
                            }
                            else
                            {
                                break;
                            }
                        }
                        generateHeadFootCell(headerRow, colHeadCellFmt, col1, row1 + 1, col2, row2 + 1, colName, colwidth);

//                        Debug.appendLogFile("get(x) ...." + x + "... get (y)..." + y);
                        colName = headerColumnName.get(x).get(y);
                        colwidth = Integer.parseInt(rowWidthList.get(y)) * 40;
                        firstColumn = false;
                        col1 = y;
                        col2 = y;
                        row1 = x + titleRowSize;
                        row2 = x + titleRowSize;
                    }
                }
            }
            generateHeadFootCell(headerRow, colHeadCellFmt, col1, row1 + 1, col2, row2 + 1, colName, colwidth);
        }
        rowCount += headerRowSize;
    }

    /*----------------------- Function to generate records ----------------------*/
    private void printRows(SqlRowSet resultSet, List dataList, ExcelConfigurationBean excelObj, ResultSet rs) throws IOException
    {
        sheetNum = 2;
        int cIndex = 0;
        if (resultSet != null)
        {

            if (resultSet.next())
            {
                do
                {
                    cIndex = 0;
                    // Spliting into sheet and updating rowCount //
                    splitInSheet(excelObj);

                    contentRow = sheet.createRow(rowCount);

                    // Setting the sr no. column //
                    cIndex = generateSrNo(excelObj, cIndex);
                    for (int k = 0; k < rowColumnIds.size(); k++, cIndex++)
                    {
                        if (rowColumnTypes.isEmpty())
                        {
                            generateRowCell(cIndex, resultSet.getString(rowColumnIds.get(k)), null);
                        }
                        else
                        {
                            generateRowCell(cIndex, resultSet.getString(rowColumnIds.get(k)), rowColumnTypes.get(k).toString());
                        }
                        // Recording the grand total of particular column //
                        if (excelObj.getAttachFooters() != null)
                        {
                            for (int i = 0; i < footerColumnName.size(); i++)
                            {
                                if (excelObj.isSrNo() && footerColumnName.get(i).get(k) != null && footerColumnName.get(i).get(k).indexOf("#stat_total") > -1)
                                {
                                    stat_total[k] += resultSet.getDouble(rowColumnIds.get(k - 1));
                                    break;
                                }
                                else if (footerColumnName.get(i).get(k) != null && footerColumnName.get(i).get(k).indexOf("#stat_total") > -1)
                                {
                                    stat_total[k] += resultSet.getDouble(rowColumnIds.get(k));
                                    break;
                                }
                            }
                        }
                    }
                    if (footerColumnName != null)
                    {
                        for (int i = 0; i < footerColumnName.size(); i++)
                        {
                            if (excelObj.isSrNo() && footerColumnName.get(i).get(footerColumnName.get(i).size() - 1) != null && footerColumnName.get(i).get(footerColumnName.get(i).size() - 1).indexOf("#stat_total") > -1)
                            {
                                stat_total[footerColumnName.get(i).size() - 1] += resultSet.getDouble(rowColumnIds.get(footerColumnName.get(i).size() - 2));
                                break;
                            }
                        }
                    }
                    rowCount++;
                }
                while (resultSet.next());
            }
            else
            {
                // Message for no record found //
                generateCustomMsg(rowCount, noRecordFound, colAlphaName.get(0).toString() + (rowCount + 1) + ":" + colAlphaName.get(rowColumnIds.size()).toString() + (rowCount + 1), rowCellCenterFmt);
                rowCount++;
            }
        }
        else if (rs != null)
        {

            try
            {
                if (rs.next())
                {
                    do
                    {
                        cIndex = 0;
                        // Spliting into sheet and updating rowCount //
                        splitInSheet(excelObj);

                        contentRow = sheet.createRow(rowCount);

                        // Setting the sr no. column //
                        cIndex = generateSrNo(excelObj, cIndex);
                        for (int k = 0; k < rowColumnIds.size(); k++, cIndex++)
                        {
                            if (rowColumnTypes.isEmpty())
                            {
                                generateRowCell(cIndex, rs.getString(rowColumnIds.get(k)), null);
                            }
                            else
                            {
                                generateRowCell(cIndex, rs.getString(rowColumnIds.get(k)), rowColumnTypes.get(k).toString());
                            }
                            // Recording the grand total of particular column //
                            if (excelObj.getAttachFooters() != null)
                            {
                                for (int i = 0; i < footerColumnName.size(); i++)
                                {
                                    if (excelObj.isSrNo() && footerColumnName.get(i).get(k) != null && footerColumnName.get(i).get(k).indexOf("#stat_total") > -1)
                                    {
                                        stat_total[k] += rs.getDouble(rowColumnIds.get(k - 1));
                                        break;
                                    }
                                    else if (footerColumnName.get(i).get(k) != null && footerColumnName.get(i).get(k).indexOf("#stat_total") > -1)
                                    {
                                        stat_total[k] += rs.getDouble(rowColumnIds.get(k));
                                        break;
                                    }
                                }
                            }
                        }
                        if (footerColumnName != null)
                        {
                            for (int i = 0; i < footerColumnName.size(); i++)
                            {
                                if (excelObj.isSrNo() && footerColumnName.get(i).get(footerColumnName.get(i).size() - 1) != null && footerColumnName.get(i).get(footerColumnName.get(i).size() - 1).indexOf("#stat_total") > -1)
                                {
                                    stat_total[footerColumnName.get(i).size() - 1] += rs.getDouble(rowColumnIds.get(footerColumnName.get(i).size() - 2));
                                    break;
                                }
                            }
                        }
                        rowCount++;
                    }
                    while (rs.next());
                }
                else
                {
                    // Message for no record found //
                    generateCustomMsg(rowCount, noRecordFound, colAlphaName.get(0).toString() + (rowCount + 1) + ":" + colAlphaName.get(rowColumnIds.size()).toString() + (rowCount + 1), rowCellCenterFmt);
                    rowCount++;
                }
            }
            catch (Exception e)
            {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);

                String myException = sw.toString();
                myException = "Error Start \nDATE = " + Calendar.getInstance().getTime().toString() + "\n" + myException + "\nError End";
                if (myException.length() > 4000)
                {
                    myException = myException.substring(0, 3999);
                }
                sw.close();
                pw.close();

                throw new IOException(myException);
            }
        }
        else
        {
            if (dataList.isEmpty())
            {
                // Message for no record found //
                generateCustomMsg(rowCount, noRecordFound, colAlphaName.get(0).toString() + (rowCount + 1) + ":" + colAlphaName.get(rowColumnIds.size()).toString() + (rowCount + 1), rowCellCenterFmt);
                rowCount++;
            }
            else
            {
                Map recMap;
                for (int j = 0; j < dataList.size(); j++)
                {
                    recMap = (Map) dataList.get(j);
                    cIndex = 0;

                    // Spliting into sheet and updating rowCount //
                    splitInSheet(excelObj);

                    contentRow = sheet.createRow(rowCount);

                    // Setting the sr no. column //
                    cIndex = generateSrNo(excelObj, cIndex);

                    //*****changes****//
                    for (int k = 0; k < rowColumnIds.size(); k++, cIndex++)
                    {

//                        Debug.appendLogFile("recMap.get(rowColumnIds.get(k)).toString()..." + k + "..." + recMap.get(rowColumnIds.get(k)).toString());

                        if (rowColumnTypes.isEmpty())
                        {
                            generateRowCell(cIndex, recMap.get(rowColumnIds.get(k)).toString(), null);
                        }
                        else
                        {
                            generateRowCell(cIndex, recMap.get(rowColumnIds.get(k)).toString(), rowColumnTypes.get(k).toString());
                        }

                    }

                    for (int k = 0; k <= rowColumnIds.size(); k++, cIndex++)
                    {
//                         Debug.appendLogFile("recMap.get(rowColumnIds.get(k)).toString()..."+k+"..."+recMap.get(rowColumnIds.get(k)).toString());
//                       
//                        generateRowCell(cIndex, recMap.get(rowColumnIds.get(k)).toString());
                        // Recording the grand total of particular column //
                        if (excelObj.getAttachFooters() != null)
                        {
                            for (int i = 0; i < footerColumnName.size(); i++)
                            {
//                                Debug.appendLogFile("footerColumnName..." + i + ".." + k + ".." + footerColumnName.get(i).get(k).toString());
//                                Debug.appendLogFile("footerColumnName.get(i).get(k).indexOf(\"#stat_total\")...." + footerColumnName.get(i).get(k).indexOf("#stat_total"));

                                if (excelObj.isSrNo() && footerColumnName.get(i).get(k) != null && footerColumnName.get(i).get(k).indexOf("#stat_total") > -1)
                                {
//                                    Debug.appendLogFile("...recMap.get(rowColumnIds.get(k - 1)).toString().." + k + "..." + recMap.get(rowColumnIds.get(k - 1)).toString());
                                    stat_total[k] += Double.parseDouble(recMap.get(rowColumnIds.get(k - 1)).toString());
                                    break;
                                }
                                else if (footerColumnName.get(i).get(k) != null && footerColumnName.get(i).get(k).indexOf("#stat_total") > -1)
                                {
//                                    Debug.appendLogFile("...recMap.get(rowColumnIds.get(k - 1)).toString().." + k + "..." + recMap.get(rowColumnIds.get(k - 1)).toString());
                                    stat_total[k] += Double.parseDouble(recMap.get(rowColumnIds.get(k)).toString());
                                    break;
                                }
                            }
                        }
                    }
                    if (footerColumnName != null)
                    {
                        for (int i = 0; i < footerColumnName.size(); i++)
                        {
                            if (excelObj.isSrNo() && footerColumnName.get(i).get(footerColumnName.get(i).size() - 1) != null && footerColumnName.get(i).get(footerColumnName.get(i).size() - 1).indexOf("#stat_total") > -1)
                            {
                                stat_total[footerColumnName.get(i).size() - 1] += Double.parseDouble(recMap.get(rowColumnIds.get(footerColumnName.get(i).size() - 2)).toString());
                                break;
                            }
                        }
                    }
                    rowCount++;
                }
            }
        }
    }

    /*----------------------- Function to generate footer rows ----------------------*/
    private void printFooter()
    {
        HSSFRow footerRow;
        for (int x = 0; x < footerColumnName.size(); x++)
        {
            footerRow = sheet.createRow(x + rowCount);
            int col1 = 0;
            int row1 = 0;
            int col2 = 0;
            int row2 = 0;
            String colName = "";
            boolean firstColumn = true;
            for (int y = 0; y < footerColumnName.get(x).size(); y++)
            {
//                Debug.appendLogFile("headerColumnName ...." + x + "." + y + "..." + headerColumnName.get(x).get(y));
//                Debug.appendLogFile("clumn names........" + colName);
                if (footerColumnName.get(x).get(y).indexOf("#cspan") > -1)
                {
                    col2 = y;
                    row2 = x + rowCount;
                }
                else if (footerColumnName.get(x).get(y).indexOf("#cspan") < 0 && footerColumnName.get(x).get(y).indexOf("#rspan") < 0)
                {
                    if (y == 0 || firstColumn)
                    {
                        colName = footerColumnName.get(x).get(y);
                        firstColumn = false;
                        col1 = y;
                        col2 = y;
                        row1 = x + rowCount;
                        row2 = x + rowCount;
                    }
                    else
                    {
                        for (int l = x + 1; l < footerColumnName.size(); l++)
                        {
                            if (footerColumnName.get(l).get(y - 1).indexOf("#rspan") > -1)
                            {
                                row2 = row2 + 1;
                            }
                            else
                            {
                                break;
                            }
                        }

                        // Replacing '#stat_total' with grand total amount of that particular column //
                        if (colName.indexOf("#stat_total") > -1)
                        {
                            colName = formatter.format(stat_total[col1]);
                        }

                        generateHeadFootCell(footerRow, colFootCellFmt, col1, row1 + 1, col2, row2 + 1, colName);
//                        Debug.appendLogFile("get(x) ...." + x + "... get (y)..." + y);
                        colName = footerColumnName.get(x).get(y);
                        firstColumn = false;
                        col1 = y;
                        col2 = y;
                        row1 = x + rowCount;
                        row2 = x + rowCount;
                    }
                }
            }
            if (colName.indexOf("#stat_total") > -1)
            {
                colName = formatter.format(stat_total[col1]);
            }
            generateHeadFootCell(footerRow, colFootCellFmt, col1, row1 + 1, col2, row2 + 1, colName);
        }
        rowCount += footerColumnName.size();
    }
    /*----------------------- Function to generate title of excel ----------------------*/

    private void printFooterNote(ExcelConfigurationBean excelObj)
    {
        for (int i = 0; i < excelObj.getFooterNote().size(); i++)
        {
            generateCustomMsg(rowCount, excelObj.getFooterNote().get(i), colAlphaName.get(0).toString() + (rowCount + 1) + ":" + colAlphaName.get(rowColumnIds.size()).toString() + (rowCount + 1), titleCellFmt);
            rowCount++;
        }
    }

    private void splitInSheet(ExcelConfigurationBean excelObj)
    {
        if (rowCount == (splitCount + headerRowSize + titleRowSize) && splitInSheet)
        {
            sheet = wb.createSheet("Sheet" + sheetNum);
            if (excelObj.getExcelTitle() != null)
            {
                printTitle(excelObj);
            }
            if (excelObj.getAttachColumns() != null)
            {
                printColumn();
            }
            rowCount = headerRowSize + titleRowSize;
            sheetNum++;
        }
    }

    private void generateCustomMsg(int rowCount, String msg, String cordinates, HSSFCellStyle hssfcellfmt)
    {
        contentRow = sheet.createRow(rowCount);
        HSSFCell contentCell = contentRow.createCell(0);
        sheet.addMergedRegion(org.apache.poi.ss.util.CellRangeAddress.valueOf(cordinates));

        contentCell.setCellValue(msg);
        contentCell.setCellStyle(hssfcellfmt);

    }

    private int generateSrNo(ExcelConfigurationBean excelObj, int cIndex)
    {
        if (excelObj.isSrNo())
        {
            HSSFCell contentCell = contentRow.createCell(cIndex);
            contentCell.setCellValue((rowCount - headerRowSize - titleRowSize + 1));
            contentCell.setCellStyle(setExcelAlignment(rowAlignList.get(cIndex)));
            return cIndex + 1;
        }
        else
        {
            return cIndex;
        }
    }

//    private void generateRowCell(int cIndex, String valStr)
//    {
//        HSSFCell contentCell = contentRow.createCell(cIndex);
//        try
//        {
//            contentCell.setCellValue(Double.parseDouble(valStr));
//        }
//        catch (Exception e)
//        {
//            contentCell.setCellValue(valStr);
//        }
//        if (rowAlignList != null)
//        {
//            contentCell.setCellStyle(setExcelAlignment(rowAlignList.get(cIndex)));
//        }
//        else
//        {
//            contentCell.setCellStyle(rowCellLeftFmt);
//        }
//    }
    private void generateRowCell(int cIndex, String valStr, String colType)
    {
        HSSFCell contentCell = contentRow.createCell(cIndex);
        try
        {
            if (colType.equalsIgnoreCase("N"))
            {
                contentCell.setCellValue(Double.parseDouble(valStr));
            }
            else
            {
                contentCell.setCellValue(valStr);
            }
        }
        catch (Exception e)
        {
            try
            {
                contentCell.setCellValue(Double.parseDouble(valStr));
            }
            catch (Exception ex)
            {
                contentCell.setCellValue(valStr);
            }

        }
        if (rowAlignList != null)
        {
            contentCell.setCellStyle(setExcelAlignment(rowAlignList.get(cIndex)));
        }
        else
        {
            contentCell.setCellStyle(rowCellLeftFmt);
        }
    }

    private void generateHeadFootCell(HSSFRow hssRow, HSSFCellStyle hssfcellfmt, int col1, int row1, int col2, int row2, String colName, int colwidth)
    {
        generateHeadFootCell(hssRow, hssfcellfmt, col1, row1, col2, row2, colName);
        sheet.setColumnWidth(col1, colwidth);
    }

    private void generateHeadFootCell(HSSFRow hssRow, HSSFCellStyle hssfcellfmt, int col1, int row1, int col2, int row2, String colName)
    {
        sheet.addMergedRegion(org.apache.poi.ss.util.CellRangeAddress.valueOf(colAlphaName.get(col1).toString() + (row1) + ":" + colAlphaName.get(col2).toString() + (row2)));
        HSSFCell colCell = hssRow.createCell(col1);
        try
        {
//            Debug.appendLogFile("generateHeadFootCell......"+colName);
            colCell.setCellValue(Double.parseDouble(colName));
        }
        catch (Exception e)
        {
            colCell.setCellValue(colName);
        }
        colCell.setCellStyle(hssfcellfmt);
    }

    private void outputExcel(HttpServletResponse resp, String exportFilename) throws FileNotFoundException, IOException
    {
        if (exportPath != null)
        {
            FileOutputStream fileOut = new FileOutputStream(exportPath + "/" + exportFilename + ".xls");
            wb.write(fileOut);
            fileOut.close();
        }
        else
        {
            outstream = resp.getOutputStream();
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/ms-excel");
            resp.setHeader("Content-disposition", "attachment; filename=\"" + exportFilename + ".xls\"");
            resp.setHeader("Cache-Control", "max-age=0");
            wb.write(outstream);
            outstream.flush();
            outstream.close();
        }
    }

    // Other Functions //
    public short setColor(byte red, byte green, byte blue)
    {
        HSSFPalette palette = wb.getCustomPalette();

        HSSFColor hssfColor = null;

        hssfColor = palette.findColor(red, green, blue);
        if (hssfColor == null)
        {
            palette.setColorAtIndex(HSSFColor.LAVENDER.index, red, green, blue);
            hssfColor = palette.getColor(HSSFColor.LAVENDER.index);
        }


        return hssfColor.getIndex();
    }

    private HSSFCellStyle setExcelAlignment(String alignment)
    {
        if (alignment.equalsIgnoreCase("center"))
        {
            return rowCellCenterFmt;
        }
        else if (alignment.equalsIgnoreCase("right"))
        {
            return rowCellRightFmt;
        }
        else
        {
            return rowCellLeftFmt;
        }
    }

    private List getColumnAlphabet()
    {

        List testList = new ArrayList();

        testList.add(0, "A");
        testList.add(1, "B");
        testList.add(2, "C");
        testList.add(3, "D");
        testList.add(4, "E");
        testList.add(5, "F");
        testList.add(6, "G");
        testList.add(7, "H");
        testList.add(8, "I");
        testList.add(9, "J");
        testList.add(10, "K");
        testList.add(11, "L");
        testList.add(12, "M");
        testList.add(13, "N");
        testList.add(14, "O");
        testList.add(15, "P");
        testList.add(16, "Q");
        testList.add(17, "R");
        testList.add(18, "S");
        testList.add(19, "T");
        testList.add(20, "U");
        testList.add(21, "V");
        testList.add(22, "W");
        testList.add(23, "X");
        testList.add(24, "Y");
        testList.add(25, "Z");
        int alpha = 0;
        int firstList = 0;
        for (int i = 26; i < 255; i++, alpha++)
        {
            if (alpha == 26)
            {
                alpha = 0;
                firstList++;
            }
            testList.add(i, testList.get(firstList).toString() + testList.get(alpha).toString());
        }
        return testList;
    }
}