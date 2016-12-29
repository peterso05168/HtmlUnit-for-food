package net.sourceforge.htmlunit.htmlunit;

import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

public class Shengen {
	public void getData() {
		final WebClient webClient = new WebClient();
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setRedirectEnabled(true);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setTimeout(20000);
		
		try {
			final HtmlPage page = webClient.getPage("http://www.shengen.com.hk/main/quote.asp");
			HtmlTable FxTable = page.querySelector("body > div.sbody > table");
			List<HtmlTableRow> FxTableRow = FxTable.getRows();
			for (int i = 1; i < FxTableRow.size(); i++) {
				List<DomNode> resultTD =  FxTableRow.get(i).querySelectorAll("td");
				int counter = resultTD.size();
				for (int j = 1; j < counter; j++) {
					System.out.println(resultTD.get(j).asText());
				}
			}
			webClient.close();
		} catch (Exception e) {
			webClient.close();
			System.out.println("Fail to get HundredYear Fx Data");
		}
	}
}
