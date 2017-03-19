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
			HtmlTable FxTable = OneHundredYear.querySelector("#post-body-2303306006605946796 > table");
			List<HtmlTableRow> FxTableRow = FxTable.getRows();
			Class.forName("com.mysql.jdbc.Driver");  // MySQL database connection
	        Connection conn = DriverManager.getConnection("jdbc:mysql://119.246.135.3:3306/FxExchange?" + "user=" + MysqlConst.account + "&password=" + MysqlConst.passwd);    
	        PreparedStatement pst = conn.prepareStatement("UPDATE FXDETAIL SET buy_price = ?, sell_price = ? WHERE currency_id = ? AND company_id = ? ");
	       
	        for (int i = 2; i < FxTableRow.size(); i++) {
				ArrayList<String> tempDetail = new ArrayList<>();
				String[] requiredDataField = FxTableRow.get(i).asText().split("\\s+");
				for (int j = 0; j < 4; j++) {
					 tempDetail.add(requiredDataField[j]);
				}
				
				if (tempDetail.get(0).contains("USD")) {
					pst.setDouble(1, Double.parseDouble(tempDetail.get(1)));
					pst.setDouble(2, Double.parseDouble(tempDetail.get(2)));
					pst.setInt(3, 1);
				}else if (tempDetail.get(0).contains("EUR")) {
					pst.setDouble(1, Double.parseDouble(tempDetail.get(1)));
					pst.setDouble(2, Double.parseDouble(tempDetail.get(2)));
					pst.setInt(3, 2);
				}else if (tempDetail.get(0).contains("GBP")) {
					pst.setDouble(1, Double.parseDouble(tempDetail.get(1)));
					pst.setDouble(2, Double.parseDouble(tempDetail.get(2)));
					pst.setInt(3, 3);
				}else {
					return;
				}
				
				pst.setInt(4, 1);
				
				int rs = pst.executeUpdate();
				if (rs == 0) {
					System.err.println("Failed to UPDATE: " + tempDetail.get(0));
				}else {
					System.out.println("UPDATED: " + tempDetail.get(0) + ", BUY: " + tempDetail.get(1) + ", SELL: " + tempDetail.get(2));
				}
			}			
			webClient.close();
		} catch (Exception e) {
			webClient.close();
			System.out.println(e.getMessage());
			//System.out.println("Fail to get HundredYear Fx Data");
		}
	}
}
