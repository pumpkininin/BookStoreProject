package sad.fit2021.bookstoreproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import sad.fit2021.bookstoreproject.exception.DuplicatedEmailException;
import sad.fit2021.bookstoreproject.exception.DuplicatedUsernameException;
import sad.fit2021.bookstoreproject.model.dto.UserDto;
import sad.fit2021.bookstoreproject.model.entity.User;
import sad.fit2021.bookstoreproject.model.entity.VerificationToken;
import sad.fit2021.bookstoreproject.service.AmazonService;
import sad.fit2021.bookstoreproject.service.EmailService;
import sad.fit2021.bookstoreproject.service.UserService;
import sad.fit2021.bookstoreproject.service.VerificationTokenService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Calendar;
import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private AmazonService amazonService;

    @GetMapping(value = "/forgot-form")
    public String forgotForm(){
        return "forgot-form";
    }
    @PostMapping(value = "/reset-request")
    public String sendResetEmail(@RequestParam(value = "username") String username, Model model) throws MessagingException {
        if(userService.existUsername(username)) {
            String token = UUID.randomUUID().toString();
            verificationTokenService.saveToken(userService.getUserByUsername(username),token);
            emailService.sendVerificationEmail(userService.getUserByUsername(username), "reset-password");
            model.addAttribute("sent", "A verification email has been sent to you email. Please check you email to reset your password");
            return "reset-form";
        }else{
            return "forgot-form";
        }
    }
    @GetMapping(value = "/reset-password")
    public String resetPasswordForm(@RequestParam(value = "token") String token, Model model){
        VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            model.addAttribute("message", "auth.message.expired");
            model.addAttribute("expired", true);
            model.addAttribute("token", token);
            return "badUser";
        }
        model.addAttribute("token", token);
        return "reset-password-form";
    }
    @PostMapping(value = "/new-password")
    public String resetPassword(HttpServletRequest request, @RequestParam(value = "token") String token, Model model){
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        if(password.equalsIgnoreCase(confirmPassword)){
            VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
            User user = verificationToken.getUser();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.updateUser(user);
            return "reset-success";
        }else{
            model.addAttribute("error", "Two password is not match");
            model.addAttribute("token", token);
            return "reset-password-form";
        }
    }
    @GetMapping(value = "/register-form")
    public String registerForm(@ModelAttribute UserDto userDto, Model model) {
        model.addAttribute("userDto", userDto);
        return "register";
    }

    @PostMapping(value = "/register")
    public String register(@Valid UserDto userDto, BindingResult bindingResult,
                           Model model, @RequestParam("file") MultipartFile multipartFile,
                           HttpServletRequest request)
                        throws DuplicatedEmailException, DuplicatedUsernameException{
        if (bindingResult.hasErrors()) {
            return "register";
        }
        if (!multipartFile.getOriginalFilename().equals("")) {
            String avtPath = amazonService.uploadFile(multipartFile, "avt");
            userDto.setAvt(avtPath);
        }else{
            userDto.setAvt("https://bookstorev1.s3.us-east-2.amazonaws.com/avt/default.jpg");
        }

        try {
            String scheme = request.getScheme();
            String serverName = request.getServerName();
            int serverPort = request.getServerPort();
            String appUrl = scheme + "://" + serverName + ":" + serverPort;
            emailService.setAppUrl(appUrl);
            userService.register(userDto);
        } catch (DuplicatedEmailException e) {
            model.addAttribute("message", "An account for that email already exists.");
            return "register";
        } catch (DuplicatedUsernameException e) {
            model.addAttribute("message", "An account for that username already exists.");
            return "register";
        }
        model.addAttribute("message", "");
        return "registerSuccess";

    }

    @GetMapping("/activation")
    public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {
        System.out.println(token);
        VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
        if (verificationToken == null) {
            System.out.println("null");
            String mess = "Invalid token.";
            model.addAttribute("message", mess);
            return "badUser";
        }
        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            String messageValue = "Your registration token has expired. Please register again.";
            model.addAttribute("message", messageValue);
            model.addAttribute("token", token);
            return "badUser";
        }
        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        return "verifySuccess";

    }

    @GetMapping("/resendVerificationToken")
    public String resendVerificationToken(
            @RequestParam("token") String existingToken, Model model, HttpServletRequest request) throws MessagingException {
        VerificationToken newToken =  verificationTokenService.generateNewToken(existingToken);
        User user = userService.getUser(newToken.getToken());
        try {
            String scheme = request.getScheme();
            String serverName = request.getServerName();
            int serverPort = request.getServerPort();
            String appUrl = scheme + "://" + serverName + ":" + serverPort;
            emailService.sendVerificationEmail(user, "activation");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        model.addAttribute("message", "We will send an email with a new registration token to your email account");
        return "resendMail";
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(Model model, @RequestParam("token") String token) {
        VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
        if (verificationToken == null) {
            model.addAttribute("meassage", "auth.message.invalidToken");
            return "badUser";
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            model.addAttribute("message", "auth.message.expired");
            model.addAttribute("expired", true);
            model.addAttribute("token", token);
            return "badUser";
        }

        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        model.addAttribute("message", "message.accountVerified");
        return "index";
    }
    @GetMapping(value = "/user/profile")
    public String getProfile(Model model, Principal principal){
        UserDto userDto = new UserDto(userService.getUserByUsername("hieunk"));
        model.addAttribute("userDto", userDto);
        return "profile";

    }
    @PostMapping(value = "/user/update-profile")
    public String updateProfile(Model model, @ModelAttribute UserDto userDto, Principal principal){
        User user = userService.getUserByUsername("hieunk");
        user.setAddress(userDto.getAddress());
        user.setAvt(userDto.getAvt());
        user.setDob(userDto.getDob());
        user.setFullname(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        return "profile";
    }
}
