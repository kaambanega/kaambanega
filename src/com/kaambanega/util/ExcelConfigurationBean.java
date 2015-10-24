/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kaambanega.util;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 *    
 * @author Fenil jariwala
 */
public class ExcelConfigurationBean
{
    private String columnsId;
    private String columnType = "";
    private List<String> attachColumns;
    private List<String> attachFooters;
    private String rowAlign;
    private String colAlign;
    private String footAlign;
    private String rowWidth;
    private String hideColumn;
    private boolean srNo = false;
    private String exportFileName;
    private String exportPath;
    private HttpServletResponse response;
    private int splitCount = 50000;
    private boolean splitInSheet = true;
    private String customDecimalFormat = "0.00";
    private String noRecordFound;
    private List<String> excelTitle;
    private List<String> footerNote;

    public String getColumnType()
    {
        return columnType;
    }

    public void setColumnType(String columnType)
    {
        this.columnType = columnType;
    }

    public String getColumnsId()
    {
        return columnsId;
    }

    public void setColumnsId(String columnsId)
    {
        this.columnsId = columnsId;
    }

    public List<String> getAttachColumns()
    {
        return attachColumns;
    }

    public void setAttachColumns(List<String> attachColumns)
    {
        this.attachColumns = attachColumns;
    }

    public String getRowAlign()
    {
        return rowAlign;
    }

    public void setRowAlign(String rowAlign)
    {
        this.rowAlign = rowAlign;
    }

    public String getRowWidth()
    {
        return rowWidth;
    }

    public String getColAlign()
    {
        return colAlign;
    }

    public void setColAlign(String colAlign)
    {
        this.colAlign = colAlign;
    }

    public String getFootAlign()
    {
        return footAlign;
    }

    public void setFootAlign(String footAlign)
    {
        this.footAlign = footAlign;
    }

    public void setRowWidth(String rowWidth)
    {
        this.rowWidth = rowWidth;
    }

    public boolean isSrNo()
    {
        return srNo;
    }

    public void setSrNo(boolean srNo)
    {
        this.srNo = srNo;
    }

    public String getExportFileName()
    {
        return exportFileName;
    }

    public void setExportFileName(String exportFileName)
    {
        this.exportFileName = exportFileName;
    }

    public String getExportPath()
    {
        return exportPath;
    }

    public void setExportPath(String exportPath)
    {
        this.exportPath = exportPath;
    }

    public HttpServletResponse getResponse()
    {
        return response;
    }

    public void setResponse(HttpServletResponse response)
    {
        this.response = response;
    }

    public List<String> getAttachFooters()
    {
        return attachFooters;
    }

    public void setAttachFooters(List<String> attachFooters)
    {
        this.attachFooters = attachFooters;
    }
    public List<String> getFooterNote()
    {
        return footerNote;
    }

    public void setFooterNote(List<String> footerNote)
    {
        this.footerNote = footerNote;
    }

    public int getSplitCount()
    {
        return splitCount;
    }

    public void setSplitCount(int splitCount)
    {
        this.splitCount = splitCount;
    }

    public boolean isSplitInSheet()
    {
        return splitInSheet;
    }

    public void setSplitInSheet(boolean splitInSheet)
    {
        this.splitInSheet = splitInSheet;
    }

    public String getCustomDecimalFormat()
    {
        return customDecimalFormat;
    }

    public void setCustomDecimalFormat(String customDecimalFormat)
    {
        this.customDecimalFormat = customDecimalFormat;
    }

    public String getHideColumn()
    {
        return hideColumn;
    }

    public void setHideColumn(String hideColumn)
    {
        this.hideColumn = hideColumn;
    }

    public String getNoRecordFound()
    {
        return noRecordFound;
    }

    public void setNoRecordFound(String noRecordFound)
    {
        this.noRecordFound = noRecordFound;
    }

    public List<String> getExcelTitle()
    {
        return excelTitle;
    }

    public void setExcelTitle(List<String> excelTitle)
    {
        this.excelTitle = excelTitle;
    }
    
}