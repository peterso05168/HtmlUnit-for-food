package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import constant.MysqlConst;

public class FoodDAO {
	public void getData() {
		
			try {
				Class.forName("com.mysql.cj.jdbc.Driver"); 
				Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/FOOD_RELATED?autoReconnect=true&useSSL=false&" + "user=" + MysqlConst.account + "&password=" + MysqlConst.passwd);    
			    // PreparedStatement pst = conn.prepareStatement("UPDATE FOOD SET buy_price = ?, sell_price = ? WHERE currency_id = ? AND company_id = ? ");

			} catch (Exception e) {
				e.printStackTrace();
			}
	       	       
	}
}
