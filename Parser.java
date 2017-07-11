package js_parser;

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

import net.sourceforge.htmlunit.cyberneko.HTMLElements.Element;

public class Parser {
	private final String firedriver = "webdriver.firefox.bin";
	private final String fireAddress = "D:\\firefox\\firefox.exe";//firefox position

	Parser() throws Exception {
		System.setProperty(firedriver, fireAddress);
	}

	public static void Start() throws Exception {
		Parser t = new Parser();
	}

	public String parser() throws Exception {
		WebDriver driver = new HtmlUnitDriver(BrowserVersion.FIREFOX_45, true);
		driver.manage().window().maximize();
		// WebDriver driver = new FirefoxDriver();
		// ((HtmlUnitDriver) driver).setJavascriptEnabled(false);
		log();

		String web = WebSiteManager.getWebSite();
		driver.get(web);

		String temp = driver.findElement(By.id("inquiry-zone")).getText() + "\n"
				+ driver.findElement(By.id("discuss-zone")).getText();
		
		driver.quit();
		
		return web+" "+temp;
	}

	private static void log() {
		Logger logger = Logger.getLogger("");
		logger.setLevel(Level.OFF);
	}
}
