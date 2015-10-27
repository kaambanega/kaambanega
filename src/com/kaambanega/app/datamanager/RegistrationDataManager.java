package com.kaambanega.app.datamanager;

import java.sql.SQLException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.kaambanega.app.formbean.RegisterFormBean;
import com.kaambanega.logForDebug.CommonMember;
import com.kaambanega.persistence.SQLUtility;

public class RegistrationDataManager implements MessageSourceAware {
	
	MessageSource messageSource;
	
	SQLUtility su = new SQLUtility();
	
	public String checkAvailability(String username) throws ClassNotFoundException, SQLException{
		return su.getString("kaambanega", "SELECT user_id FROM user_mst WHERE user_id=:user_id", new MapSqlParameterSource("user_id", username));
	}
	
	public int newUser(RegisterFormBean rfb) throws ClassNotFoundException, SQLException{
		StringBuilder sb = new StringBuilder();
		WebApplicationContext webAppContext = ContextLoader.getCurrentWebApplicationContext();
		messageSource = (MessageSource) webAppContext.getBean("messageSource");
		String salt = messageSource.getMessage("password.salt", null, Locale.US);
		CommonMember.appendLogFile("salt value is: " + salt);
        sb.append("INSERT INTO user_mst(user_id, password, user_typ, email_id, is_actv) VALUES(:user_id, MD5(CONCAT('"+salt+"', ':password')), :user_typ, :email_id, :is_actv)");
        CommonMember.appendLogFile("I anm IN hhhh" + sb.toString());
        return su.persist("kaambanega", sb.toString(), new BeanPropertySqlParameterSource(rfb));
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
