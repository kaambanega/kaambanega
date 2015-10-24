/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kaambanega.persistence;


/**
 *
 * @author Fenil Jariwala
 */
public class ProcedureParam
{
    private int paramDataType;
    private String paramName;
    private String paramInOutType;

    public int getParamDataType()
    {
        return paramDataType;
    }

    public void setParamDataType(int paramDataType)
    {
        this.paramDataType = paramDataType;
    }

    public String getParamInOutType()
    {
        return paramInOutType;
    }

    public void setParamInOutType(String paramInOutType)
    {
        this.paramInOutType = paramInOutType;
    }

    public String getParamName()
    {
        return paramName;
    }

    public void setParamName(String paramName)
    {
        this.paramName = paramName;
    }
}