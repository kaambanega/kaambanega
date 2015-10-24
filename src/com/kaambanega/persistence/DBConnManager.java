/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kaambanega.persistence;

import com.kaambanega.logForDebug.CommonMember;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Fenil Jariwala
 */
public class DBConnManager {

//    static final Map<String, String> mp = new HashMap();
//
//    static {
//        mp.put("jobportal", "jdbc:mysql://localhost/jobportal?user=root&password=mysql");
//    }

    public Connection getMySQLConnection(String strAlias) throws ClassNotFoundException, SQLException {
        CommonMember.appendLogFile("IN load");
        Class.forName("com.mysql.jdbc.Driver");
        CommonMember.appendLogFile("after load");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/kaambanega", "root", "");
    }

    public void releaseMySQLConnection(Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}
