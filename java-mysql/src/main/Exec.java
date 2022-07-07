package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

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
			/*
			while (rs.next()) {
				System.out.println(rs.getString("name"));
				System.out.println(rs.getString("sex"));
			}
			*/
			
			System.out.println("Connected");
			
			
			
			Scanner scanner = new Scanner(System.in);
			
			System.out.print("[情報追加：1　終了：0]　->　");
			
			while(scanner.nextInt() == 1) {
				
				String name, sex;
				int old;
				
				// 情報入力
				System.out.print("名前：");
				name = scanner.next();
				System.out.print("性別：");
				sex = scanner.next();
				System.out.print("年齢：");
				old = scanner.nextInt();

				
				// 日付を取得
				Calendar cl = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String today = sdf.format(cl.getTime());
				
				
				System.out.println(name + ", " + sex + ", " + old);
				System.out.print("データを追加しますか？　[追加：1　取り消し：0]　->　");
				
				if(scanner.nextInt() == 1) {
					String sql = "INSERT INTO member (name, sex, old, enter) VALUES ('" + name + "', '" + sex + "', '" + old + "', '" + today + "');";
					pstmt.executeUpdate(sql);
					System.out.println("追加完了");
				}
				
				System.out.print("[情報追加：1　終了：0]　->　");
				
			}
			
			
			scanner.close();
			
			
			// 後処理（リソースのクローズ）
			rs.close();
			pstmt.close();
			con.close();
			
			System.out.println("終了");
			
			
		} catch ( Exception e) {
			
			System.out.println(e);
			
		}
		
	}

}
