package com.study.servlet_study.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import com.study.servlet_study.config.DBConnectionMgr;
import com.study.servlet_study.entity.Author;
import com.study.servlet_study.entity.Book;
import com.study.servlet_study.entity.Publisher;

public class BookInsertMain {
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		String bookName = null;
		String authorName = null;
		String publisherName = null;
		
		System.out.print("도서명 >>>");
		bookName = scanner.nextLine();
		System.out.print("저자명 >>>");
		authorName = scanner.nextLine();
		System.out.print("출판사명 >>>");
		publisherName = scanner.nextLine();
		
		Book book = Book.builder()
				.bookName(bookName)
				.author(Author.builder().authorName(authorName).build())
				.publisher(Publisher.builder().publisherName(publisherName).build())
				.build();
		
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		
		Connection con = null;
		PreparedStatement pstmt = null; 
		int count = 0;
		
		// author에 값 넣기
		try {
			con = pool.getConnection();
			String sql = "insert into author_tb values (0, ?)";
			// Statement 는 <<< java.sql >>> 꺼 / insert할때만 넣어주는것 RETURN_GENERATED_KEYS -> 넣어주는 이유는 author_tb에 insert 넣어줄 때 AI 에 자동 증가된 숫자를 가지고 올수있도록해줌
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			// sql 에 있는 ? 값 가져오기
			pstmt.setString(1, book.getAuthor().getAuthorName());
			// executeQueray 는 
			// executUpdate 는 
			pstmt.executeUpdate();
			// 자동 증가된 AI 키 값을 가져오기
			ResultSet rs = pstmt.getGeneratedKeys();
			// book 클래스 -> Author 클래스 -> AuthorId 에 getGeneratedKeys 값을 넣기
			if(rs.next()) {
				book.getAuthor().setAuthorId(rs.getInt(1));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		
		// publisher 에 값 넣기
		try {
			con = pool.getConnection();
			String sql = "insert into publisher_tb values (0, ?)";
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, book.getPublisher().getPublisherName());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				book.getPublisher().setPublisherId(rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		// book 에 값 넣기
		try {
			con = pool.getConnection();
			String sql = "insert into book_tb values (0, ?, ?, ?)";
			pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, book.getBookName());
			pstmt.setInt(2, book.getAuthor().getAuthorId());
			pstmt.setInt(3, book.getPublisher().getPublisherId());
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				book.setBookId(rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		
		System.out.println("추가된 도서 정보");
		System.out.println(book);
	}
}
