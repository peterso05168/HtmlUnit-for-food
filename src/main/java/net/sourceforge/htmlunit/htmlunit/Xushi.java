package net.sourceforge.htmlunit.htmlunit;

import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

public class Xushi {
	public void getData() {
		final WebClient webClient = new WebClient();
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setRedirectEnabled(true);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setTimeout(20000);
		
		try {
			final HtmlPage page = webClient.getPage("http://www.xushi-exchange.com/exchange.aspx");
			HtmlTable FxTable = page.querySelector("body > section > div > div.exchange_con > div.col-md-9.exchange_con_right > div.table-responsive > table");
			List<HtmlTableRow> FxTableRow = FxTable.getRows();
			for (int i = 2; i < FxTableRow.size(); i++) {
				String[] requiredDataField = FxTableRow.get(i).asText().split("\\s+");
				for (int j = 0; j < 4; j++) {
					System.out.println(requiredDataField[j]);				
				}
			}			
			webClient.close();
		} catch (Exception e) {
			webClient.close();
			System.out.println("Fail to get HundredYear Fx Data");
		}
	}
}
