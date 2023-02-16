package com.smartcontactmanager.controllers;

import com.smartcontactmanager.entities.Contact;
import com.smartcontactmanager.entities.User;
import com.smartcontactmanager.helper.Message;
import com.smartcontactmanager.service.ContactService;
import com.smartcontactmanager.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

import static com.smartcontactmanager.helper.Constants.DEFAULT_PAGE_SIZE;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // method for adding common data for response
    @ModelAttribute
    public void addCommonData(Model model, Principal principal){
        String name=principal.getName();
        User user=this.userService.getUserByName(name);
        model.addAttribute("user",user);
    }

    // implementation for dashboard
    @GetMapping("/home")
    public String dashboard(Model model){
        model.addAttribute("title","User Dashboard");
        return "normal/user_dashboard";
    }

    // implementation for adding contact
    @GetMapping("add-contact")
    public String openAddContactForm(Model model){
        model.addAttribute("title","Add Contact");
        model.addAttribute("contact",new Contact());
        return "normal/add_contact_form";
    }

    @PostMapping("/process-contact")
    public String handleContactSubmissionForm(@Valid @ModelAttribute Contact contact,
                                              BindingResult bindingResult,
                                              Principal principal,
                                              @RequestParam("profileImage")MultipartFile file,
                                              HttpSession session){

        if(bindingResult.hasErrors())
            return "normal/add_contact_form";
        try {
            String userName = principal.getName();
            User user = this.userService.getUserByName(userName);

            if (file.isEmpty()) {
                contact.setImage("contact.png");
            } else {
                long currentTimeMillis=System.currentTimeMillis();
                contact.setImage(currentTimeMillis+file.getOriginalFilename());
                File saveFile = new ClassPathResource("static/IMG").getFile();
                Path path= Paths.get(saveFile.getAbsolutePath()+File.separator+currentTimeMillis+file.getOriginalFilename());
                Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
            }
            contact.setUser(user);
            user.getContacts().add(contact);
            this.userService.save(user);
            session.setAttribute("message",new Message("contact is added successfully.. Add More !!","success"));
        }catch (Exception e){
            session.setAttribute("message",new Message("Something went wrong.. try again !! "+e.getMessage(),"danger"));
            e.printStackTrace();
        }
        return "normal/add_contact_form";
    }

    // show contacts
    @GetMapping("/show-contacts/{page}/{currentPageSize}")
    public String showContactHandler(@PathVariable("page") int page,@PathVariable("currentPageSize")int currentPageSize, Model model, Principal principal, @RequestParam(value = "pageSize",defaultValue = DEFAULT_PAGE_SIZE)int pageSize){
        if(pageSize!=Integer.parseInt(DEFAULT_PAGE_SIZE))
        {
            currentPageSize=pageSize;
            System.out.println(currentPageSize);
        }
        model.addAttribute("title","User Contacts");
        String userName=principal.getName();
        User user=this.userService.getUserByName(userName);
        // current page->page
        // total page-> 5
        Pageable pageable= PageRequest.of(page,currentPageSize);
        Page<Contact> contacts=this.contactService.getAllContactByUserId(user.getId(),pageable);
        model.addAttribute("contacts",contacts);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPage",contacts.getTotalPages());
        model.addAttribute("pageSize",currentPageSize);
        System.out.println(contacts);
        return "normal/show_contacts";
    }

    @GetMapping("/contact/{cId}")
    public String showContactDetail(@PathVariable("cId") int contactId,Model model,Principal principal){
        Optional<Contact>optionalContact= this.contactService.findById(contactId);
        Contact contact=optionalContact.get();
        String userName=principal.getName();
        User user=this.userService.getUserByName(userName);
        if(user.getId()==contact.getUser().getId()) {
            model.addAttribute("contact", contact);
            model.addAttribute("title",contact.getName());
        }
        return "normal/contact_details";
    }

    @GetMapping("/delete/{cId}")
    @Transactional
    public String deleteUser(@PathVariable("cId") int contactId,Principal principal,HttpSession session){
        Optional<Contact>optionalContact= this.contactService.findById(contactId);
        Contact contact=optionalContact.get();
        String userName=principal.getName();
        User user=this.userService.getUserByName(userName);
        if(user.getId()==contact.getUser().getId()) {
            contact.setUser(null);

//      delete profile as well if it's not default image
            if(!contact.getImage().equals("contact.png")) {
                try {
                    File saveFile = new ClassPathResource("static/IMG").getFile();
                    Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + contact.getImage());
                    Files.delete(path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            user.getContacts().remove(contact);
            this.userService.save(user);
            session.setAttribute("message",new Message("Contact deleted successfully...","success"));
        }else{
            session.setAttribute("message",new Message("You don't have permission to delete this contact..","danger"));
        }

        return "redirect:/user/show-contacts/0/"+DEFAULT_PAGE_SIZE;
    }

    @PostMapping("/update-contact/{cId}")
    public String updateContact(@PathVariable("cId") int contactId, Model model){
        Contact contact=this.contactService.findById(contactId).get();
        model.addAttribute("title","update contact");
        model.addAttribute("contact",contact);
        return "normal/update_contact";
    }

    @PostMapping("/process-update")
    public String processContact(@Valid @ModelAttribute Contact contact,BindingResult bindingResult,Principal principal,
                                 @RequestParam("profileImage")MultipartFile file,
                                 HttpSession session){

        if(bindingResult.hasErrors()) {
            return "normal/update_contact";
        }
        try{
            Contact oldContact=this.contactService.findById(contact.getcId()).get();
            if(!file.isEmpty()){

                // delete old photo
                File saveFile = new ClassPathResource("static/IMG").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + oldContact.getImage());
                Files.delete(path);

                // save new file
                long currentTimeMillis=System.currentTimeMillis();
                contact.setImage(currentTimeMillis+file.getOriginalFilename());
                 saveFile = new ClassPathResource("static/IMG").getFile();
                 path= Paths.get(saveFile.getAbsolutePath()+File.separator+currentTimeMillis+file.getOriginalFilename());
                Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
            }else{
                contact.setImage(oldContact.getImage());
            }

            User user=this.userService.getUserByName(principal.getName());
            contact.setUser(user);
            this.contactService.save(contact);
            session.setAttribute("message",new Message("contact is updated successfully !!","success"));
        }catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:/user/show-contacts/0/"+DEFAULT_PAGE_SIZE;
    }

    // profile page
    @GetMapping("/profile")
    public String profileImage(Model model){
        model.addAttribute("title","profile Page");
        return "normal/profile_page";
    }

    @GetMapping("/settings")
    public String openSettings() {
        return"normal/settings";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword,Principal principal,HttpSession session) {
        System.out.println("Old Password-"+oldPassword);
        System.out.println("New Password-"+newPassword);
        String userName=principal.getName();
        User currentUser= this.userService.getUserByName(userName);
        System.out.println(currentUser.getPassword());
        if(this.bCryptPasswordEncoder.matches(oldPassword, currentUser.getPassword()))
        {
            currentUser.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
            this.userService.save(currentUser);
            session.setAttribute("message", new Message("Password Changed", "success"));
        }
        else
        {
            session.setAttribute("message", new Message("Please enter Correct old Password", "danger"));
            return "redirect:/user/settings";
        }
        return"redirect:/user/home";

    }
}
