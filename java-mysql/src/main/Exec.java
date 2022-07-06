package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Exec {

	public static void main(String[] args) {
		
		try {
			
			// データベースに接続
			Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost/sample?useSSL=false",
				"",
				""
			);
			
			// SQL文の実行
			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM member");
			ResultSet rs = pstmt.executeQuery();
			
			// 検索結果を表示
			while (rs.next()) {
				System.out.println(rs.getString("name"));
				System.out.println(rs.getString("sex"));
			}
			
			System.out.println("Connected");
			
			
			String sql = "INSERT INTO member (name, sex, old, enter) VALUES ('大宮太一', '男', '32', '2022-06-27');";
			pstmt.executeUpdate(sql);
			
			
			// 後処理（リソースのクローズ）
			rs.close();
			pstmt.close();
			con.close();
			
		} catch ( Exception e) {
			
			System.out.println(e);
			
		}
		
	}

}
