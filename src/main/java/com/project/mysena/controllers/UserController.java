package com.project.mysena.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.project.mysena.models.User;
import com.project.mysena.service.UserService;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.web.servlet.ModelAndView;

   import java.util.List;
   import java.util.Optional;
import javax.servlet.http.HttpSession;

   @RestController
   @RequestMapping("/users")
   public class UserController {

       @Autowired
       private UserService userService;

   @PostMapping("/register")
    public ModelAndView createUser(@ModelAttribute User user) {
        Optional<User> existingUser = userService.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("register_error");
            modelAndView.addObject("error", "El correo electrónico ya existe");
            return modelAndView;
        }
        userService.createUser(user);
        ModelAndView modelAndView = new ModelAndView("success");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

       @GetMapping("/{id}")
       public Optional<User> getUserById(@PathVariable Long id) {
           return userService.getUserById(id);
       }

       @GetMapping
       public List<User> getAllUsers() {
           return userService.getAllUsers();
       }

       @PutMapping("/{id}")
       public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
           return userService.updateUser(id, userDetails);
       }

       @DeleteMapping("/{id}")
       public void deleteUser(@PathVariable Long id) {
           userService.deleteUser(id);
       }

  @PostMapping("/login")
       public ModelAndView loginUser(@RequestParam String email, @RequestParam String password) {
           try {
               User user = userService.authenticateUser(email, password);
               ModelAndView modelAndView = new ModelAndView("welcome");
               modelAndView.addObject("user", user);
               return modelAndView;
           } catch (Exception e) {
               ModelAndView modelAndView = new ModelAndView("login_error");
               modelAndView.addObject("error", "Invalid email or password");
               return modelAndView;
           }
       }

       @GetMapping("/register")
       public ModelAndView showRegisterPage() {
           return new ModelAndView("register");
       }
   }