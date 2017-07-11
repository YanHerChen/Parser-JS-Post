package js_parser;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

class Output extends Thread {
	protected Queue queue;
	protected PageManager pm;
	XSSFWorkbook workbook = new XSSFWorkbook();
	XSSFSheet sheet = workbook.createSheet("Demo");
	int nowRow = 0;
	int num;
	Row row;
	Cell cell;
	String path = "C:/Users/.../...";//position

	public Output(int N, Queue queue, PageManager pm) {
		this.queue = queue;
		this.pm = pm;
		this.num = N;
		this.path = path + N + ".xlsx";
	}

	public void run() {

		while (Continue()) {
			if (queue.size() > 0) {
				try {
					row = sheet.createRow(nowRow);
					cell = row.createCell(0);
					cell.setCellValue((String) queue.getObject());
					nowRow++;
				} catch (Exception e) {
				}
			}

			try {
				sleep(500);
			} catch (InterruptedException e) {
			}
		}

		Final();

		try {
			output();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void Final() {
		for (int i = 0; i < queue.size(); i++) {
			try {
				row = sheet.createRow(nowRow);
				cell = row.createCell(0);
				cell.setCellValue((String) queue.getObject());
				nowRow++;
			} catch (Exception e) {
			}
		}
	}

	private void output() throws IOException {
		FileOutputStream outputStream = new FileOutputStream(path);
		workbook.write(outputStream);
		outputStream.flush();
		outputStream.close();
	}

	private boolean Continue() {
		if (nowRow+1 >= num) {
			return false;
		}
		return true;
	}
}