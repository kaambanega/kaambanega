/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kaambanega.app.controller;

import com.kaambanega.app.formbean.FirstFormBean;
import com.kaambanega.app.service.FirstService;
import com.kaambanega.logForDebug.CommonMember;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Fenil Jariwala
 */
@Controller
public class FirstController {

    private FirstService fs = new FirstService();
    
    @RequestMapping("/index.htm")
    public ModelAndView index(){
    	ModelAndView mv = new ModelAndView("index");
    	return mv;
    }

    @RequestMapping(value="/first.htm/insertData1", method=RequestMethod.POST)
    public ModelAndView insertData1(@ModelAttribute FirstFormBean ffb) {
        CommonMember.appendLogFile("I anm IN hhhh");
        ModelAndView mv = new ModelAndView("ajax");
        try {
            mv.addObject("message", fs.insertData(ffb));
            mv.addObject("task", "insert");
        } catch (ClassNotFoundException ex) {
            CommonMember.appendLogFileException("ClassNotFoundException In FirstController ::", ex, false);
        } catch (SQLException ex) {
            CommonMember.appendLogFileException("SQLException In FirstController ::", ex, false);
        }

        return mv;
    }

    @RequestMapping(value="/first.htm/getList1", method=RequestMethod.POST)
    public ModelAndView getList1() {
        ModelAndView mv = new ModelAndView("ajax");
        CommonMember.appendLogFile("I anm IN hhhh");
        try {
            mv.addObject("list", fs.getList());
            mv.addObject("task", "display");
        } catch (ClassNotFoundException ex) {
            CommonMember.appendLogFileException("ClassNotFoundException In FirstController ::", ex, false);
        } catch (SQLException ex) {
            CommonMember.appendLogFileException("SQLException In FirstController ::", ex, false);
        }


        return mv;
    }
}
