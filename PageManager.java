package js_parser;

public class PageManager {
	private static int nowpage;
	private static int end;
	
	PageManager(int end, int first){
		this.end = nowpage - end;
		this.nowpage = first - 1;
	}
	
	public static int getpage() {
		nowpage += 1;
		return nowpage;
	}
	
	public static int getnowpage(){
		return nowpage;
	}
}
