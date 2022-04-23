package com.lab4.demo.book;

import com.lab4.demo.book.model.dto.BookDTO;
import com.lab4.demo.book.model.dto.BookRequestDTO;
import com.lab4.demo.book.model.dto.BookResponseDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(BOOKS)
@RequiredArgsConstructor
@AllArgsConstructor
public class BookController {
    private BookService bookService;

    @GetMapping
    public List<BookDTO> findAll() {
        return bookService.findAll();
    }

    /*@GetMapping(BOOKS + SEARCH)
    public Page<BookDTO> findAllPaged(@PathVariable String search, @PageableDefault(sort = "title") Pageable pageable) {
        return bookService.findAllByTitleLikeOrAuthorLikeOrGenreLike(search,search,search, pageable);
    }*/

    @GetMapping(SEARCH)
    public List<BookDTO> searchList(@PathVariable String search) {
        return bookService.findAllByTitleLikeOrAuthorLikeOrGenreLike(search,search,search);
    }

    @PostMapping
    public BookDTO create(@RequestBody BookDTO bookDTO) {
        return bookService.create(bookDTO);
    }

    @DeleteMapping(BOOKS_ID)
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    @PutMapping(BOOKS_ID)
    public BookDTO update(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        return bookService.update(id, bookDTO);
    }

//    @PatchMapping(BOOKS_ID)
//    public BookDTO rename(@PathVariable Long id, @RequestBody String newTitle) {
//        return bookService.rename(id, newTitle);
//    }

    @PatchMapping(BOOKS_ID)
    public BookDTO updateQuantity(@PathVariable Long id, @RequestBody Integer quantity) {
        return bookService.sell(id, quantity);
    }
}
