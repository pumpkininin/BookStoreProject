package sad.fit2021.bookstoreproject.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;


import java.util.Calendar;
import java.util.List;


import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import sad.fit2021.bookstoreproject.exception.DuplicatedEmailException;
import sad.fit2021.bookstoreproject.exception.DuplicatedUsernameException;
import sad.fit2021.bookstoreproject.model.dto.BookDto;
import sad.fit2021.bookstoreproject.model.dto.UserDto;
import sad.fit2021.bookstoreproject.model.entity.Book;
import sad.fit2021.bookstoreproject.model.entity.User;
import sad.fit2021.bookstoreproject.model.entity.VerificationToken;
import sad.fit2021.bookstoreproject.service.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class MainController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AmazonService amazonService;
    @Autowired
    private BookService bookService;
    @Autowired
    private VerificationTokenService verificationTokenService;
    @GetMapping(value = {"/"})
    public String index(Model model) {
        List<Book> books = bookService.get4Book();
        model.addAttribute("books", books);
        return "index";
    }
    @GetMapping(value = { "/login"})
    public String loginPage(Model model) {
        return "login";
    }

    @GetMapping(value = "/homepage")
    public ModelAndView homepage() {
        ModelAndView mav = new ModelAndView("homepage");
        List<BookDto> books = bookService.getTop10Rate();
        mav.addObject("topRate", books);
        return mav;
    }


//    @GetMapping("/book/{title}")
//    public List<Book> getBookbyTitle(@PathVariable("title") String title){
//        return bookService.getBookByName(title);
//    }


}
