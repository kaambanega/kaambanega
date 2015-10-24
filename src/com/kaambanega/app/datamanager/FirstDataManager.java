/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kaambanega.app.datamanager;

import com.kaambanega.app.formbean.FirstFormBean;
import com.kaambanega.logForDebug.CommonMember;
import com.kaambanega.persistence.SQLUtility;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

/**
 *
 * @author Fenil Jariwala
 */
public class FirstDataManager {
    
    private SQLUtility su = new SQLUtility();
    
    public int insertData(FirstFormBean ffb) throws ClassNotFoundException, SQLException {
        
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO first_try(name, age) VALUES(:name, :age)");
        CommonMember.appendLogFile("I anm IN hhhh" + sb.toString());
        return su.persist("jobportal", sb.toString(), new BeanPropertySqlParameterSource(ffb));
        
        
    }
    
    public List getList() throws ClassNotFoundException, SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM first_try");
        return su.getList("jobportal", sb.toString());
    }
}
