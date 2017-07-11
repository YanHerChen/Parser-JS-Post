package example;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.SilentCssErrorHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;

import js_parser.PageManager;
import net.sourceforge.htmlunit.cyberneko.HTMLElements.Element;

public class MyExample {
	private final String firedriver = "webdriver.firefox.bin";
	private final String fireAddress = "D:\\firefox\\firefox.exe";//firefox position
	private final String web="https://www.kingnet.com.tw/knNew/inquiry/consult.html?inquiryId=";
	
	MyExample() throws Exception {
		System.setProperty(firedriver, fireAddress);
	}

	public static void main(String[] args) throws Exception {
		MyExample example = new MyExample();
		example.parser();
	}

	public void parser() throws Exception {
		WebDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_45, true);
		
		log();
		
		int i=1;
		while(i<10){
			String website=web+i;
			driver.get(website);
			String st = driver.findElement(By.id("inquiry-zone")).getText() + "\n"
				+ driver.findElement(By.id("discuss-zone")).getText();
			System.out.println(website+"\n"+st);
			i++;
		}
		
		driver.quit();
	}

	private static void log() {
		Logger logger = Logger.getLogger("");
		logger.setLevel(Level.OFF);
	}
}
