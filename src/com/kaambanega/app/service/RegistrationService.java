package com.kaambanega.app.service;

import java.sql.SQLException;

import com.kaambanega.app.datamanager.RegistrationDataManager;
import com.kaambanega.app.formbean.RegisterFormBean;

public class RegistrationService {
	RegistrationDataManager dataManager = new RegistrationDataManager();
	public String checkAvailability(String username) throws ClassNotFoundException, SQLException{
		return dataManager.checkAvailability(username);
	}
	
	public String newUser(RegisterFormBean rfb) throws ClassNotFoundException, SQLException {
		if (dataManager.newUser(rfb) > 0) {
            return "Success";
        } else {
            return "Failure";
        }
	}

}
