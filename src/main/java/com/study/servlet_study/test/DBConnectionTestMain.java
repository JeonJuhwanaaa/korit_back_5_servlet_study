package com.study.servlet_study.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.study.servlet_study.config.DBConnectionMgr;
import com.study.servlet_study.entity.Author;

public class DBConnectionTestMain {
	
	public static void main(String[] args) {
		
		// 비스듬히 누워있는 글자는 static
		DBConnectionMgr pool = DBConnectionMgr.getInstance();
		// connection 자바랑 DB 연결해주는 역할
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = pool.getConnection();
			
			System.out.println(con.getSchema());
			String sql = "select * from author_tb";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			List<Author> authorList = new ArrayList<>();
		
//			System.out.println("id: " + rs.getInt(1)); // 컬럼1 (author_id) 가지고 오기
//			System.out.println("name: " + rs.getString(2)); // 컬럼2(author_name) 가지고 오기
			// rs.next는 커서 이동
			while(rs.next()) {
				authorList.add(Author.builder()
						.authorId(rs.getInt(1))
						.authorName(rs.getString(2))
						.build());
				
			}
			authorList.forEach(author -> System.out.println(author));

			for(Author author : authorList) {
				System.out.println(author);
			}

			for(int i = 0; i < authorList.size(); i++) {
				Author author = authorList.get(i);
				System.out.println(author);		
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
	}
}