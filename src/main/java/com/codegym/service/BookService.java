package com.codegym.service;

import com.codegym.model.Book;
import com.codegym.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Page<Book> findAll(Pageable pageable);
    Book findById(Long id);
    void save (Book book);
    void remove(Long id);
    Iterable<Book> findAllByCategory(Category category, Pageable pageable);
    Page<Book> findAllByNameContaining(String name, Pageable pageable);
    Page<Book>findAllByOrderByDateOfPurchaseAscPriceDesc(Pageable pageable);
}
