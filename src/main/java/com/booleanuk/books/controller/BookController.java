package com.booleanuk.books.controller;

import com.booleanuk.books.model.Book;
import com.booleanuk.books.repository.BookRepository;
import com.booleanuk.books.response.BookListResponse;
import com.booleanuk.books.response.BookResponse;
import com.booleanuk.books.response.ErrorResponse;
import com.booleanuk.books.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public ResponseEntity<BookListResponse> getAllBooks() {
        BookListResponse bookListResponse = new BookListResponse();
        bookListResponse.set(this.bookRepository.findAll());
        return ResponseEntity.ok(bookListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getBookByID(@PathVariable int id) {
        Book book = this.bookRepository.findById(id).orElse(null);
        if (book == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        BookResponse bookResponse = new BookResponse();
        bookResponse.set(book);
        return ResponseEntity.ok(bookResponse);
    }
}
