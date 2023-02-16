package com.smartcontactmanager.controllers;

import com.smartcontactmanager.entities.User;
import com.smartcontactmanager.helper.Message;
import com.smartcontactmanager.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserService userService;

    // home handler
    @GetMapping("/")
    public String homeHandler(Model model){
        model.addAttribute("title","Home - Smart Contact Manager");
        return "home";
    }

    //about handler
    @GetMapping("/about")
    public String aboutHandler(Model model){
        model.addAttribute("title","About - Smart Contact Manager");
        return "about";
    }
    // signup handler
    @GetMapping("/signup")
    public String signupHandler(Model model){
        model.addAttribute("title","Signup - Smart Contact Manager");
        model.addAttribute("user",new User());
        return "signup";
    }

    //user registration handler
    @PostMapping("/do_register")
    public String registerHandler(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, @RequestParam(value = "agreement",defaultValue = "false") boolean agreement, HttpSession session, Model model){
        try {
            if(!agreement){
                throw new Exception("you have not agreed term an condition");
            }
            if(bindingResult.hasErrors()){
                return "signup";
            }
            user.setRole("ROLE_USER");
            user.setEnabled(true);
            user.setImageURL("default.png");
            if(user.getPassword().equals(user.getConfirm_password())) {
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                User u = this.userService.save(user);
                model.addAttribute("user", new User());
                session.setAttribute("message",new Message("Successfully Registered !!","alert-success"));
                return "signup";
            }
            else {
                throw new Exception("Password doesn't match");
            }
        }catch (Exception e) {
            session.setAttribute("message", new Message("Something went wrong !! " + e.getMessage(), "alert-danger"));
            return "signup";
        }

    }

    @GetMapping("/signin")
    public String singinHandler(Model model){
        model.addAttribute("title","SignIn - Smart Contact Manager");
        return "login";
    }
}
