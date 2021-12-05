package sad.fit2021.bookstoreproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sad.fit2021.bookstoreproject.model.composite.BookRankId;
import sad.fit2021.bookstoreproject.model.dto.BookDto;
import sad.fit2021.bookstoreproject.model.entity.Book;
import sad.fit2021.bookstoreproject.model.entity.BookRank;
import sad.fit2021.bookstoreproject.model.entity.Category;
import sad.fit2021.bookstoreproject.model.entity.User;
import sad.fit2021.bookstoreproject.repository.BookRankRepository;
import sad.fit2021.bookstoreproject.repository.BookRepository;
import sad.fit2021.bookstoreproject.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookRankRepository bookRankRepository;

    public List<Book> getBookByTitle(String title) {
        return bookRepository.findAllByTitleContaining(title);
    }

    public Book getBookById(Integer id) {
        return bookRepository.findBookByBookId(id);
    }
    public List<Book> get4Book(){
        return bookRepository.findWithPageable(PageRequest.of(0, 4, Sort.Direction.DESC, "price"));
    }
    public Page<BookDto> getPaginatedBookByTitle(String title, int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        Page<Book> page;
        if (title.equals("")) {
            page = this.bookRepository.findAll(pageable);
        } else {
            page = this.bookRepository.findAllByTitleContaining(title, pageable);
        }
        Page<BookDto> dtoPage = page.map(book -> new BookDto(book));

        return dtoPage;
    }

    public List<BookDto> getTop10Rate() {
        Page<BookRank> page = bookRankRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "rate")));
        List<BookRank> bookRanks = page.getContent();
        List<BookDto> bookList = new ArrayList<>();
        for (BookRank br : bookRanks) {
            bookList.add(new BookDto(bookRepository.findBookByBookId(br.getId().getBookId())));
        }
        return bookList;
    }

    public boolean isEnough(Integer bookId) {
        return bookRepository.findBookByBookId(bookId).getInStock() > 1;
    }

    public List<Book> get4BookByCategory(List<Category> category) {
        return bookRepository.findWithPageable(PageRequest.of(0, 4,Sort.Direction.DESC, "title"));

    }

    public List<BookRank> getFeedback(Integer bookId) {
        return bookRankRepository.findAllByBookBookId(bookId)
                .map(List::of)
                .orElse(Collections.emptyList());
    }

    public void saveFeedback(Integer id,int rate, String message, String name) {
        User user = userRepository.findUserByUsername(name);
        BookRank br = bookRankRepository.findAllById(new BookRankId(id, user.getUserId()));
        if(br != null){
            br.setFeedback(message);
            br.setRate(rate);
        }else{
            br = new BookRank();
            br.setFeedback(message);
            br.setRate(rate);
            br.setUser(user);
            br.setBook(bookRepository.findBookByBookId(id));
            br.setId(new BookRankId(id, user.getUserId()));
        }
        bookRankRepository.save(br);
    }
}
