package sad.fit2021.bookstoreproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sad.fit2021.bookstoreproject.model.dto.BookDto;
import sad.fit2021.bookstoreproject.model.entity.Book;
import sad.fit2021.bookstoreproject.model.entity.BookRank;
import sad.fit2021.bookstoreproject.service.BookService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping(value = "/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping(value = "/detail/{id}")
    public String getBookById(@PathVariable(value = "id") Integer bookId, Model model){
        Book book = bookService.getBookById(bookId);
        List<Book> bookList = bookService.get4BookByCategory(book.getCategory());
        model.addAttribute("book", book);
        model.addAttribute("bookList",bookList);
        List<BookRank> bookRanks = bookService.getFeedback(book.getBookId());
        model.addAttribute("feedbacks", bookRanks);
        float rank = 0;
        for(BookRank b : bookRanks){
            rank+=b.getRate();
        }
        rank /= bookRanks.size();
        model.addAttribute("rate", rank);
        return "book-detail";
    }
    @GetMapping("/page")
    public String viewHomePage(Model model) {
        Page<BookDto> page = bookService.getPaginatedBookByTitle("", 1, 8, "title", "asc");
        List<BookDto> bookList = page.getContent();
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("bookList", bookList);
        model.addAttribute("sortField", "price");
        model.addAttribute("sortDir", "asc");
        List<Book> books = bookService.get4Book();
        model.addAttribute("books", books);

        model.addAttribute("disabled", true);
        return "bookList";

    }

    @GetMapping("/page/{pageNo}")
    public String getPaginated(@PathVariable(value = "pageNo") int pageNo,
                               @RequestParam("sortField") String sortField,
                               @RequestParam("sortDir") String sortDir, Model model) {
        int pageSize = 8;
        Page<BookDto> page = bookService.getPaginatedBookByTitle("", pageNo, pageSize, sortField, sortDir);
        List<BookDto> bookList = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("bookList", bookList);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        List<Book> books = bookService.get4Book();
        model.addAttribute("books", books);
        model.addAttribute("disabled", pageNo==1);
        return "bookList";
    }

    @PostMapping(value = "/post-feedback/{id}")
    public String postFeedback(@PathVariable(value = "id") Integer id,
                               @RequestParam(value = "rate") Integer rate,
                               @RequestParam(value = "message") String message,
                               Model model,
                               Principal principal){
        bookService.saveFeedback(id,rate,  message, "hieunk");
        Book book = bookService.getBookById(id);
        List<Book> bookList = bookService.get4BookByCategory(book.getCategory());
        model.addAttribute("book", book);
        model.addAttribute("bookList",bookList);
        List<BookRank> bookRanks = bookService.getFeedback(book.getBookId());
        model.addAttribute("feedbacks", bookRanks);
        float rank = 0;
        for(BookRank b : bookRanks){
            rank+=b.getRate();
        }
        rank /= bookRanks.size();
        model.addAttribute("rate", rank);
        return "book-detail";
    }
    @GetMapping(value = "/search")
    public String search(@RequestParam(value = "keyword") String keyword, Model model){
        Page<BookDto> page = bookService.getPaginatedBookByTitle(keyword, 1, 8, "title", "asc");
        List<BookDto> bookList = page.getContent();
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("bookList", bookList);
        model.addAttribute("sortField", "price");
        model.addAttribute("sortDir", "asc");
        List<Book> books = bookService.get4Book();
        model.addAttribute("books", books);

        model.addAttribute("disabled", true);
        return "bookList";
    }
}
