package com.study.servlet_study.Service;

import com.study.servlet_study.entity.Book;
import com.study.servlet_study.repository.BookRepository;
import com.study.servlet_study.repository.ProductRepository;

public class BookService {
	
	private static BookService instance;
	private BookRepository bookRepository;
	
	private BookService() {
		bookRepository = BookRepository.getinstance();
	}
	
	public static BookService getInstance() {
		if(instance == null) {
			instance = new BookService();
		}
		return instance;
	}
	
	public boolean addBook(Book book) {
		return bookRepository.saveBook(book) > 0;
	}
	
	public Book getBook(int bookId) {
		return bookRepository.findBookByBookId(bookId);
	}

}
