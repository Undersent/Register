package com.controller;

import com.model.User;
import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import com.service.EmailService;
import com.service.EmailServiceImpl;
import com.service.EmailServiceSMM;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;;

    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(HttpServletRequest request, @Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if(userExists != null){
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a registered with that email");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            createUser(modelAndView, user,request);

        }
        return modelAndView;
    }

    private void createUser(ModelAndView modelAndView, User user,
                            HttpServletRequest request) {
        user.setEnabled(false);
        user.setConfirmationEmailToken(UUID.randomUUID().toString());
        userService.saveUser(user);
        modelAndView.addObject("successMessage", "User has been registered :)" +
                ", confirmation e-mail has been sent to: " + user.getEmail());
        sendEmailToUser(user,request );
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("registration");
    }

    private void sendEmailToUser(User user, HttpServletRequest request) {
        String confirmationUrl = request.getScheme() + "://" + request.getServerName();
        SimpleMailMessage registrationEmail = new SimpleMailMessage();
        registrationEmail.setTo(user.getEmail());
        registrationEmail.setText("To confirm your e-mail click link below:\n" +
                confirmationUrl + "/confirm?token=" + user.getConfirmationEmailToken());
        emailService.sendEmail(registrationEmail);
    }

    @RequestMapping(value="/confirm", method = RequestMethod.GET)
    public ModelAndView showCOnfrimationPage(ModelAndView modelAndView,
                                             @RequestParam("token") String token){
        User user = userService.findByConfirmationEmailToken(token);
        if(user == null){
            modelAndView.addObject("invalidToken", "Wrong confirmation link");
        }else{
            modelAndView.addObject("confirmedToken", user.getConfirmationEmailToken());
        }
        modelAndView.setViewName("confirm");
        return modelAndView;
    }

    @RequestMapping(value="/confirm", method = RequestMethod.POST)
    public ModelAndView processConfirmationForm(ModelAndView modelAndView, BindingResult bindingResult,
                                                @RequestParam Map requestParams, RedirectAttributes redir){
        modelAndView.setViewName("confirm");
        Zxcvbn passwordCheck = new Zxcvbn();
        Strength strength = passwordCheck.measure((String)requestParams.get("password"));
        if(strength.getScore() < 3){
            bindingResult.reject("password");
            redir.addFlashAttribute("errorMessage", "Your password is to week");
            return modelAndView;
        }
        User user = userService.findByConfirmationEmailToken((String)requestParams.get("token"));
        user.setPassword(bCryptPasswordEncoder.encode((String)requestParams.get("password")));
        user.setEnabled(true);
        userService.saveUser(user);
        modelAndView.addObject("successMessage", "Your password has been set!");
        return modelAndView;
    }

}
