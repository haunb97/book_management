package com.codegym.controller;

import com.codegym.model.Book;
import com.codegym.model.Category;
import com.codegym.service.CategoryService;
import com.codegym.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories(){
        return categoryService.findAll();
    }

    @GetMapping("/books")
    public ModelAndView listPhones(@RequestParam("s")Optional<String> s , @PageableDefault(size = 10 ,sort = "price")Pageable pageable){
        Page<Book> books ;
        if(s.isPresent()){
            books= bookService.findAllByNameContaining(s.get(), pageable);
        }else {
            books = bookService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/book/list");
        modelAndView.addObject("books",books);
        return modelAndView;
    }
    @GetMapping("/create-book")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/book/create");
        modelAndView.addObject("book",new Book());
        return modelAndView;

    }
    @PostMapping("/create-book")
    public ModelAndView savePhone(@ModelAttribute("book") Book book){
        bookService.save(book);
        ModelAndView modelAndView = new ModelAndView("/book/create");
        modelAndView.addObject("book",new Book());
        modelAndView.addObject("message","đã thêm sách thành công");
        return modelAndView;

    }
    @GetMapping("/edit-book/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Book book = bookService.findById(id);
        if(book != null){
            ModelAndView modelAndView = new ModelAndView("/book/edit");
            modelAndView.addObject("book", book);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }
    @PostMapping("/edit-book")
    public ModelAndView updatePhone(@ModelAttribute("book") Book book){
        bookService.save(book);
        ModelAndView modelAndView = new ModelAndView("/book/edit");
        modelAndView.addObject("book", book);
        modelAndView.addObject("message", "cập nhật sách thành công");
        return modelAndView;

    }
    @GetMapping("/delete-book/{id}")
    public ModelAndView showDeletePhone(@PathVariable Long id){
        Book book = bookService.findById(id);
        if(book != null){
            ModelAndView modelAndView = new ModelAndView("/book/delete");
            modelAndView.addObject("book", book);
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }
    @PostMapping("/delete-book")
    public String deletePhone(@ModelAttribute("book") Book book){
        bookService.remove(book.getId());
        return "redirect:books";
    }
    @GetMapping("/searchByCategory")
    public ModelAndView searchBookBYCategory(@RequestParam("search") Long categoryId, Pageable pageable){
        Page<Book> books;
        if(categoryId == -1){
            books = bookService.findAll(pageable);
        }else {
            Category category = categoryService.findById(categoryId);
            books = (Page<Book>) bookService.findAllByCategory(category , pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/book/list");
        modelAndView.addObject("books", books);
        modelAndView.addObject("search", categoryId);
        return modelAndView;
    }
    @GetMapping("/sort")
    public ModelAndView sortByDate(Pageable pageable){
        ModelAndView modelAndView = new ModelAndView("/book/list");
        modelAndView.addObject("books",bookService.findAllByOrderByDateOfPurchaseAscPriceDesc(pageable));
        return modelAndView;
    }
    @GetMapping("/searchbyCategory/{id}")
    public ModelAndView searchBookByCategoryCss(@PathVariable("id") Long id, Pageable pageable){
        Page<Book> books;
        if(id == -1){
            books = bookService.findAll(pageable);
        }else {
            Category category = categoryService.findById(id);
            books = (Page<Book>) bookService.findAllByCategory(category , pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/book/list");
        modelAndView.addObject("books", books);
        modelAndView.addObject("search", id);
        return modelAndView;

    }


}
