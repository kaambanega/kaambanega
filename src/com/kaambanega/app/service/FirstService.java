/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kaambanega.app.service;

import com.kaambanega.app.datamanager.FirstDataManager;
import com.kaambanega.app.formbean.FirstFormBean;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Fenil Jariwala
 */
public class FirstService {

    private FirstDataManager fd = new FirstDataManager();

    public String insertData(FirstFormBean ffb) throws ClassNotFoundException, SQLException {

        if (fd.insertData(ffb) > 0) {
            return "Success";
        } else {
            return "Failure";
        }
    }

    public List getList() throws ClassNotFoundException, SQLException {
        return fd.getList();
    }
}
