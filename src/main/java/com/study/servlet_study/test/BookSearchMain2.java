package com.study.servlet_study.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.study.servlet_study.config.DBConnectionMgr;
import com.study.servlet_study.entity.Author;
import com.study.servlet_study.entity.Book;
import com.study.servlet_study.entity.Publisher;

public class BookSearchMain2 {
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		String searchValue = null;
		
		System.out.print("검색할 도서명을 입력하세요 >>> ");
		searchValue = scanner.nextLine();
		
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//List로 업케스팅
		List<Book> bookList = new ArrayList<>();
		
		try {
			con = pool.getConnection();
			String sql = "select\r\n"
					+ "	bt.book_id,\r\n"
					+ "    bt.book_name,\r\n"
					+ "    bt.author_id,\r\n"
					+ "	at.author_name,\r\n"
					+ "    bt.publisher_id,\r\n"
					+ "    pt.publisher_name\r\n"
					+ "from\r\n"
					+ "	book_tb bt\r\n"
					+ "    left outer join author_tb at on(bt.author_id = at.author_id)\r\n"
					+ "    left outer join publisher_tb pt on(bt.publisher_id = pt.publisher_id)\r\n"
					+ "where\r\n"
					+ "	bt.book_name like ?;";
			
			pstmt = con.prepareStatement(sql);
			// setString(몇 번째 물음표, 입력값)
			pstmt.setString(1, "%" + searchValue + "%"); // '가나다'
			//executeQuery -> 실행
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				Author author = Author.builder()
						.authorId(rs.getInt(3))
						.authorName(rs.getString(4))
						.build();
				
				Publisher publisher = Publisher.builder()
						.publisherId(rs.getInt(5))
						.publisherName(rs.getString(6))
						.build();
				
				Book book = Book.builder()
						.bookId(rs.getInt(1))
						.bookName(rs.getString(2))
						.author(author)
						.publisher(publisher)
						.build();
						
				bookList.add(book);
			}
			
		} catch (Exception e) {

			e.printStackTrace();
		}finally {
			pool.freeConnection(con, pstmt, rs);
		}
		System.out.println("도서명 / 저자명 / 출판사명");
		
		for(Book book : bookList) {
			System.out.println("[도서명]: " + book.getBookName()
								+ "\n[저자명]: " +book.getAuthor().getAuthorName()
								+ "\n[출판사명]: " +book.getPublisher().getPublisherName()
								+ "\n");
		}
		
	}

}
