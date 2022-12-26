package com.rest.books.services;

import java.util.List;
//import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.books.dao.BookRepository;
import com.rest.books.entities.Books;

@Service
public class BookService {

	@Autowired
	private BookRepository bookrepo;
	
//	private static List<Books> li=new ArrayList<>();
//	
//	static {
//		li.add(new Books(1,"Java complete","Richerd"));
//		li.add(new Books(12,"Python complete","Andrew"));
//		li.add(new Books(23,"Zero complete","Aryabhat"));
//		li.add(new Books(34,"Personality complete","Bill"));
//		}

	//Get All The Books	
	public List<Books> getAllBooks(){
		 List<Books> list=(List<Books>)this.bookrepo.findAll();
		 return list;
	}
	
	public Books getBookById(int id) {
		Books book=null;
		try {
//		book=li.stream().filter(e->e.getId()==id).findFirst().get();
			book=this.bookrepo.findById(id);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return book;
	}
	
	public Books addBook(Books book) {
		Books result=this.bookrepo.save(book);
		return result;
	}
	
	public void deleteBook(int id) {
//		li=li.stream().filter(e->e.getId()!=id).collect(Collectors.toList());
		this.bookrepo.deleteById(id);
	}
	
	public void updateBook(Books book,int id) {
//		li=li.stream().map(b->{
//			if(b.getId()==id)
//			{
//				b.setTitle(book.getTitle());
//				b.setAuthor(book.getAuthor());
//			}
//			return b;
//		}).collect(Collectors.toList());
		book.setId(id);
		this.bookrepo.save(book);
	}
}
