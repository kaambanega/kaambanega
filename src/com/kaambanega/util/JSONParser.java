/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kaambanega.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 * @author Fenil Jariwala
 */
public class JSONParser {

    public String parse(SqlRowSet rs, String cellIdColName) {

        StringBuilder json = new StringBuilder("");

        int pageNo, totalPages;


        int totalRecords = 0;
        while (rs.next()) {
            totalRecords++;
        }

        if (totalRecords == 0) {
            pageNo = totalPages = 0;
        } else {
            pageNo = totalPages = 1;
        }

        json.append("{\"page\":\"").append(pageNo).append("\",\"total\":");
        json.append(totalPages).append(",\"records\":\"");
        json.append(totalRecords).append("\",\"rows\":[ ");

        int columncount = rs.getMetaData().getColumnCount();

        rs.beforeFirst();

        while (rs.next()) {
            json.append("{\"id\":\"");
            json.append(rs.getString(cellIdColName));
            json.append("\",\"cell\":[\"");

            for (int j = 0; j < columncount; j++) {
                json.append(rs.getString(j + 1));
                if (columncount != (j + 1)) {
                    json.append("\",\"");
                }
            }

            json.append("\"]},");
        }
        return json.toString().substring(0, json.length() - 1) + "]}";
    }

    public String parse(SqlRowSet rs, String cellIdColName, int pageNo, int rowsPerPage) {

        StringBuilder json = new StringBuilder("");


        int totalRecords = 0;
        while (rs.next()) {
            totalRecords++;
        }


        int totalPages = totalRecords / rowsPerPage;
        if ((totalPages % rowsPerPage) > 0 || (totalPages == 0 && totalRecords > 0)) {
            totalPages++;
        } else if (totalRecords % rowsPerPage == 0 && totalRecords > 0 && totalRecords <= rowsPerPage) {
            totalPages = 1;
        }
        if (totalRecords == 0) {
            pageNo = totalPages = 0;
        }

        json.append("{\"page\":\"").append(pageNo).append("\",\"total\":");
        json.append(totalPages).append(",\"records\":\"");
        json.append(totalRecords).append("\",\"rows\":[ ");

        int columncount = rs.getMetaData().getColumnCount();

        rs.beforeFirst();

        while (rs.next()) {
            json.append("{\"id\":\"");
            json.append(rs.getString(cellIdColName));
            json.append("\",\"cell\":[\"");

            for (int j = 0; j < columncount; j++) {
                json.append(rs.getString(j + 1));
                if (columncount != (j + 1)) {
                    json.append("\",\"");
                }
            }

            json.append("\"]},");
        }
        return json.toString().substring(0, json.length() - 1) + "]}";

    }

    public String parse(SqlRowSet rs, String cellIdColName, int totalRecords, int pageNo, int rowsPerPage) {

        StringBuilder json = new StringBuilder("");



        int totalPages = totalRecords / rowsPerPage;
        if ((totalPages % rowsPerPage) > 0 || (totalPages == 0 && totalRecords > 0)) {
            totalPages++;
        } else if (totalRecords % rowsPerPage == 0 && totalRecords > 0 && totalRecords <= rowsPerPage) {
            totalPages = 1;
        }

        if (totalRecords == 0) {
            pageNo = totalPages = 0;
        }

        json.append("{\"page\":\"").append(pageNo).append("\",\"total\":");
        json.append(totalPages).append(",\"records\":\"");
        json.append(totalRecords).append("\",\"rows\":[ ");

        int columncount = rs.getMetaData().getColumnCount();

        rs.beforeFirst();

        while (rs.next()) {
            json.append("{\"id\":\"");
            json.append(rs.getString(cellIdColName));
            json.append("\",\"cell\":[\"");

            for (int j = 0; j < columncount; j++) {
                json.append(rs.getString(j + 1));
                if (columncount != (j + 1)) {
                    json.append("\",\"");
                }
            }

            json.append("\"]},");
        }
        return json.toString().substring(0, json.length() - 1) + "]}";

    }

    public String parse(List ls, String cellIDColName) {

        StringBuilder json = new StringBuilder();
        int columncount = 0;
        Object cols[] = null;

        json.append("{\"page\":1,\"total\":1,\"records\":\"");
        json.append(ls.size());
        json.append("\",\"rows\":[ ");

        if (ls.size() > 0) {
            Map rowT = (Map) ls.get(0);
            columncount = rowT.size();
            Set set = rowT.keySet();
            cols = set.toArray();
            for (int i = 0; i < ls.size(); i++) {
                Map row = (Map) ls.get(i);
                json.append("{\"id\":\"");
                json.append(row.get(cellIDColName));
                json.append("\",\"cell\":[\"");
                for (int j = 0; j < columncount; j++) {
                    //String data = row.get(cols[j].toString());
                    String data = null;
                    try {
                        data = (String) row.get(cols[j]);
                    } catch (ClassCastException e) {
                        data = row.get(cols[j]).toString();
                    }
                    json.append(data);
                    if (columncount != (j + 1)) {
                        json.append("\",\"");
                    }
                }
                json.append("\"]},");
            }

        }
        return json.toString().substring(0, json.length() - 1) + "]}";

    }

    public String parse(List ls, String cellIDColName, int pageNo, int rowsPerPage) {

        StringBuilder json = new StringBuilder();
        int totalRecords = ls.size();
        int count = 0;
        int columncount = 0;
        int totalPages = 0;
        int vPageNo = 0;
        if (totalRecords == 0) {
            vPageNo = 0;
        } else {
            vPageNo = pageNo;
        }
        Object ColumnArray[] = null;
        totalPages = totalRecords / rowsPerPage;
        if (totalRecords % rowsPerPage > 0) {
            totalPages++;
        } else if (totalRecords % rowsPerPage == 0 && totalRecords > 0 && totalRecords <= rowsPerPage) {
            totalPages = 1;
        }
        json.append("{\"page\":\"").append(vPageNo).append("\",\"total\":").append(totalPages).append(",\"records\":\"").append(totalRecords).append("\",\"rows\":[ ");
        if (ls.size() > 0) {
            count = ls.size();
            Map RecMapCount = (Map) ls.get(0);
            columncount = RecMapCount.size();
            Set set = RecMapCount.keySet();
            ColumnArray = set.toArray();
            for (int i = 0; i < count; i++) {
                Map RecMap = (Map) ls.get(i);
                json.append("{\"id\":\"");
                json.append(RecMap.get(cellIDColName));
                json.append("\",\"cell\":[\"");
                for (int j = 0; j < columncount; j++) {

                    String data = null;
                    try {
                        data = (String) RecMap.get(ColumnArray[j]);
                    } catch (ClassCastException e) {
                        data = RecMap.get(ColumnArray[j]).toString();
                    }

                    json.append(data);
                    if (columncount == j + 1) {
                    } else {
                        json.append("\",\"");
                    }
                }
                json.append("\"]},");
            }

        }

        return json.toString().substring(0, json.length() - 1) + "]}";

    }

    public String parse(List lst, String cellIDColName, boolean srNo, boolean emptySrNo) {
        StringBuilder json = new StringBuilder();
        int totalRecords = lst.size();
        int count = 0;
        int columncount = 0;
        String parseStr = "";

        Object columnArray[] = null;
        json.append("data = {total_count:" + totalRecords + ",rows: [");
        if (lst.size() > 0) {
            count = lst.size();
            Map recMapCount = (Map) lst.get(0);
            columncount = recMapCount.size();
            Set set = recMapCount.keySet();
            columnArray = set.toArray();
            for (int i = 0; i < count; i++) {
                Map recMap = (Map) lst.get(i);
                json.append("{\"id\":\"");
                json.append(recMap.get(cellIDColName));
                int cnt = i + 1;
                if (srNo) {
                    if (emptySrNo) {
                        json.append("\",\"data\":[\"\",\"");
                    } else {
                        json.append("\",\"data\":[\"" + cnt + "\",\"");
                    }
                } else {
                    json.append("\",\"data\":[\"");
                }
                for (int j = 0; j < columncount; j++) {
                    String data = null;
                    try {
                        data = recMap.get(columnArray[j]).toString().replaceAll("\\\\", "\\\\\\\\").replaceAll("\n", "<br/>").replaceAll("\"", "&quot;");
                    } catch (ClassCastException e) {
                        data = recMap.get(columnArray[j]).toString();
                    }

                    json.append(data);
                    if (columncount == j + 1) {
                    } else {
                        json.append("\",\"");
                    }
                }
                json.append("\"]},");
            }
            parseStr = json.toString().substring(0, json.length() - 1) + "]}";
        } else {
            parseStr = json.toString() + "]}";
        }
        return parseStr;
    }

    public String parse(SqlRowSet rs, String cellIDColName, boolean srNo, boolean emptySrNo) {
        StringBuilder json = new StringBuilder();
        int totalRecords = 0;
        int count = 0;
        int columncount = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            totalRecords++;
            json.append("{\"id\":\"");
            json.append(rs.getString(cellIDColName));
            count = count + 1;
            if (srNo) {
                if (emptySrNo) {
                    json.append("\",\"data\":[\"\",\"");
                } else {
                    json.append("\",\"data\":[\"" + count + "\",\"");
                }
            } else {
                json.append("\",\"data\":[\"");
            }
            for (int j = 0; j < columncount; j++) {
                String data = null;
                try {
                    data = rs.getString(j + 1).toString().replaceAll("\\\\", "\\\\\\\\").replaceAll("\n", "<br/>").replaceAll("\"", "&quot;");
                } catch (ClassCastException e) {
                    data = rs.getString(j + 1).toString();
                }

                json.append(data);
                if (columncount == j + 1) {
                } else {
                    json.append("\",\"");
                }
            }
            json.append("\"]},");
        }
        return "data = {total_count:" + totalRecords + ",rows: [" + json.toString() + "]}";
    }
}
