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

public class BookSearchMain {
	
	public static void main(String[] args) {
		
		// 검색할 도서명을 입력하세요 >>> 글
		// 도서명 / 저자명 / 출판사
		
		//싱글톤
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		
		// sql이랑 연결
		Connection con = null;
		// sql 입력 값 연결
		PreparedStatement pstmt = null;

		ResultSet rs = null;
		
		Scanner scanner = new Scanner(System.in);
		
		// sql 이랑 연결하면서 예외처리 해주기 
		try {
			con = pool.getConnection();
			System.out.print("이름 입력 >> ");
			String word = scanner.nextLine();
			// sql 넣을 때 "" 두개 먼저 넣고 붙여넣기
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
					+ "    left outer join publisher_tb pt on(bt.publisher_id = pt.publisher_id)"
					+ "where\r\n"
					+ "	bt.book_name like " + "\'%" + word + "%\'";
			// sql 에서 '%글%' 을 표현
			// " \' %" -> %를 문자로 변환해준다 
			// prepareStatement(sql) -> 웬만하면 한 세트 
			pstmt = con.prepareStatement(sql);
			// executeQuery -> 작성된 sql 실행하게 해줌
			rs = pstmt.executeQuery();
			
			List<Book> bookList = new ArrayList<>();
			
			while(rs.next()) {
				bookList.add(Book.builder()
						.bookId(rs.getInt(1))
						.bookName(rs.getString(2))
						.author(Author.builder().authorId(rs.getInt(3)).authorName(rs.getString(4)).build())
						.publisher(Publisher.builder().publisherId(rs.getInt(5)).publisherName(rs.getString(6)).build())
						.build());
				}
			
			// 클래스 호출하고 타고 타고 가는걸 이해하기
			// 1번) 향상된 for문 사용
			for(Book book : bookList) {
				System.out.println(book.getBookName() + book.getAuthor().getAuthorName() + book.getPublisher().getPublisherName());
			}
			// 2번) forEach문 사용
			bookList.forEach(book -> System.out.println(book.getBookName() + book.getAuthor().getAuthorName() + book.getPublisher().getPublisherName()));
			// 3번) for문 사용
			for(int i = 0; i < bookList.size(); i++) {
				System.out.println(bookList.get(i).getBookName() + bookList.get(i).getAuthor().getAuthorName() + bookList.get(i).getPublisher().getPublisherName());
			}
			
			} catch (Exception e) {
				e.printStackTrace();
		}
		
	}

}