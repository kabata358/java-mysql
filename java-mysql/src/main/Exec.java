package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Exec {

	public static void main(String[] args) {
		
		try {
			
			// データベースに接続
			Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost/sample?useSSL=false",
				"root",
				"root12345"
			);
			
			System.out.println("Connected");
			
			Scanner scanner = new Scanner(System.in);
			
			System.out.print("[追加：1　変更：2　削除：3　全件表示：9　終了：0]　->　");
			
			
			switch(scanner.nextInt()) {
			case 1 -> insertData(con);
			case 2 -> updateData(con);
			case 3 -> deleteData(con);
			case 9 -> showData(con);
			default -> System.out.println("終了します。");
			};
			
			
			
			
			
			
			scanner.close();
			
			
			// 後処理（リソースのクローズ）
			//rs.close();
			//pstmt.close();
			con.close();
			
			System.out.println("終了");
			
			
		} catch ( Exception e) {
			
			System.out.println(e);
			
		}
		
	}
	
	public static void insertData(Connection con) throws SQLException {
		
		String name, sex, today;
		int old;
		
		String sql = "INSERT INTO member (name, sex, old, enter) VALUES (?, ?, ?, ?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("追加する情報を入力してください。");
		
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
		today = sdf.format(cl.getTime());
		
		// 確認
		System.out.println("'" + name + ", " + sex + ", " + old + "'" + "　---　このデータを追加しますか？");
		System.out.print("[追加：1　取り消し：0]　->　");
		
		if(scanner.nextInt() == 1) {
			pstmt.setString(1, name);
			pstmt.setString(2, sex);
			pstmt.setInt(3, old);
			pstmt.setString(4, today);
			pstmt.executeUpdate();
			System.out.println("追加完了");
		}			
}
	
	public static void updateData(Connection con) throws SQLException {
		showData(con);
		
		int id;
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("更新するidを入力してください。");
		
		System.out.print("id：");
		id = scanner.nextInt();
		
		
		
		String name, sex, today;
		int old;
		
		String sql = "UPDATE member SET name = ?, sex = ?, old = ?, enter = ? WHERE id =" + id;
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		
		System.out.println("更新する情報を入力してください。");
		
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
		today = sdf.format(cl.getTime());
		
		// 確認
		System.out.println("'" + name + ", " + sex + ", " + old + "'" + "　---　このデータに更新しますか？");
		System.out.print("[更新：1　取り消し：0]　->　");
		
		if(scanner.nextInt() == 1) {
			pstmt.setString(1, name);
			pstmt.setString(2, sex);
			pstmt.setInt(3, old);
			pstmt.setString(4, today);
			pstmt.executeUpdate();
			System.out.println("更新完了");
		}
	}
	
	public static  void deleteData(Connection con) throws SQLException {
		
		showData(con);
		
		int id;
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("削除するidを入力してください。");
		
		System.out.print("id：");
		id = scanner.nextInt();
		
		
		
		String sql = "DELETE FROM member WHERE id =" + id;
		Statement smt = con.createStatement();
		int rowsCount = smt.executeUpdate(sql);
		
		System.out.print(rowsCount + "レコード削除しました。");
		
		smt.close();
		
	}
	
	public static void showData(Connection con) throws SQLException {
		String sql = "SELECT * FROM member";
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			System.out.printf("%d\t%s\t%s\t%d\t%s\t%s\n", rs.getInt("id"), rs.getString("name"), rs.getString("sex"), rs.getInt("old"), rs.getString("enter"), rs.getString("memo"));
		}
		
	}

}
