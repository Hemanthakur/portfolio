package com.rest.books.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rest.books.entities.Books;
import com.rest.books.services.BookService;

@RestController
public class BookTestController {

	@Autowired
	private BookService bookService;
	
	@GetMapping("/books")
	public ResponseEntity<List<Books>> getBooks(){
		List<Books> bl=bookService.getAllBooks();
			if(bl.size()<=0) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			}
			return ResponseEntity.status(HttpStatus.CREATED).body(bl);
		}
	
	@RequestMapping(value="/books/{id}",method=RequestMethod.GET)
	public ResponseEntity<Books> getBook(@PathVariable("id") int id) 
	{
		Books book=bookService.getBookById(id);
		if(book==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(book);
	}
	
	@PostMapping("/addBook")
	public ResponseEntity<Books> addBooks(@RequestBody Books book) {
		Books b=null;
		try {
			b=this.bookService.addBook(book);
			System.out.println(book);
			return ResponseEntity.of(Optional.of(b)); 
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@DeleteMapping("/books/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable("id") int id) {
		try {
			this.bookService.deleteBook(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		catch(Exception e){
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PutMapping("/books/{id}")
	public ResponseEntity<Books> updateBook(@RequestBody Books book,@PathVariable("id") int id){
		try {
			this.bookService.updateBook(book, id);
			return ResponseEntity.ok().body(book);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
}
