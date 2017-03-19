package net.sourceforge.htmlunit.htmlunit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import constant.MysqlConst;

public class HundredYear {
	public void getData() {
		final WebClient webClient = new WebClient();
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setRedirectEnabled(true);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setTimeout(20000);
		
		try {
			final HtmlPage page = webClient.getPage("http://bcel1985.blogspot.hk/");
			HtmlElement firstLink = page.querySelector("#Blog1 > div.blog-posts.hfeed > div:nth-child(1) > div > div > div > h3 > a");
			HtmlPage OneHundredYear = firstLink.click();
			HtmlTable FxTable = OneHundredYear.querySelector("table");
			List<HtmlTableRow> FxTableRow = FxTable.getRows();
			Class.forName("com.mysql.jdbc.Driver");  // MySQL database connection
	        Connection conn = DriverManager.getConnection("jdbc:mysql://119.246.135.3:3306/FxExchange?" + "user=" + MysqlConst.account + "&password=" + MysqlConst.passwd);    
	        PreparedStatement pst = conn.prepareStatement("UPDATE FXDETAIL SET buy_price = ?, sell_price = ? WHERE currency_id = ? AND company_id = ? ");
	       
	        for (int i = 2; i < FxTableRow.size(); i++) {
				ArrayList<String> tempDetail = new ArrayList<>();
				int rs = 0;
				String[] requiredDataField = FxTableRow.get(i).asText().split("\\s+");
				for (int j = 0; j < 4; j++) {
					 tempDetail.add(requiredDataField[j]);
				}
				
				if (tempDetail.get(0).contains("USD")) {
					pst.setInt(3, 1);
				}else if (tempDetail.get(0).contains("EUR")) {
					pst.setInt(3, 2);
				}else if (tempDetail.get(0).contains("GBP")) {
					pst.setInt(3, 3);
				}else if (tempDetail.get(0).contains("JPY")) {
					pst.setInt(3, 4);
				}else if (tempDetail.get(0).contains("SGD")) {
					//pst.setInt(3, 5);
				}else if (tempDetail.get(0).contains("AUD")) {
					pst.setInt(3, 6);
				}else if (tempDetail.get(0).contains("NZD")) {
					pst.setInt(3, 7);
				}else if (tempDetail.get(0).contains("CHF")) {
					pst.setInt(3, 8);
				}else if (tempDetail.get(0).contains("CAD")) {
					pst.setInt(3, 9);
				}else if (tempDetail.get(0).contains("MYR")) {
					pst.setInt(3, 10);
				}else if (tempDetail.get(0).contains("TWD")) {
					//pst.setInt(3, 11);
				}else if (tempDetail.get(0).contains("CNY")) {
					pst.setInt(3, 12);
				}else if (tempDetail.get(0).contains("PHP")) {
					pst.setInt(3, 13);
				}else if (tempDetail.get(0).contains("THB")) {
					pst.setInt(3, 14);
				}else if (tempDetail.get(0).contains("INR")) {
					pst.setInt(3, 15);
				}else if (tempDetail.get(0).contains("KRW")) {
					pst.setInt(3, 16);
				}else if (tempDetail.get(0).contains("IDR")) {
					pst.setInt(3, 17);
				}else {
					return;
				}
				
				try {
					pst.setDouble(1, Double.parseDouble(tempDetail.get(1)));
					pst.setDouble(2, Double.parseDouble(tempDetail.get(2)));
					pst.setInt(4, 1);
					rs = pst.executeUpdate();
				}catch (Exception e) {}
				
				if (rs == 0) {
					System.err.println("Failed to UPDATE: " + tempDetail.get(0));
				}else {
					System.out.println("UPDATED: " + tempDetail.get(0) + ", BUY: " + tempDetail.get(1) + ", SELL: " + tempDetail.get(2));
				}
			}			
			webClient.close();
		} catch (Exception e) {
			webClient.close();
			e.printStackTrace();
			//System.out.println("Fail to get HundredYear Fx Data");
		}
	}
}
