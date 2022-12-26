package com.rest.books.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.books.entities.Books;

public interface BookRepository extends JpaRepository<Books,Integer>{
	public Books findById(int id);
}
