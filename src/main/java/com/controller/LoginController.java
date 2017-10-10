package com.controller;

import com.model.User;
import com.service.UserService;
import com.sun.org.apache.xpath.internal.operations.String;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping(value={"/login", "/"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }
   /* @RequestMapping(value="/login", method = RequestMethod.POST)
    public ModelAndView processConfirmationForm(ModelAndView modelAndView, @RequestParam Map requestParams){
        User user = userService.findUserByEmail((java.lang.String) requestParams.get("email"));
        bCryptPasswordEncoder.matches((CharSequence) requestParams.get("password"), user.getPassword());
        modelAndView.addObject("message", "Username or Password is wrong!!");
        return modelAndView;
    }*/


    @RequestMapping(value="/admin/home", method = RequestMethod.GET)
    public ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available for Admin");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

}
