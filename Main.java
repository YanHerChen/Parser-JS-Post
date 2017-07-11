package js_parser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.omg.CORBA.Current;

public class Main {
	private final static int end = 50, first = 1;
	private final static int SemaphoreNumber = 3;
	
	Main() throws Exception {
		Queue queue = new Queue(100);
		PageManager pm = new PageManager(end, first);
		Parser t = new Parser();
		Start(queue, t);
		Output op = new Output(end, queue, pm);
		op.start();
	}

	public static void main(String[] args) throws Exception {
		SemaPhore SP = new SemaPhore();
	}

	private void Start(Queue queue, Parser t) {
		ExecutorService exec = Executors.newCachedThreadPool();
		final Semaphore semp = new Semaphore(SemaphoreNumber);
		for (int index = 0; index < end; index++) {
			final int NO = index;
			Runnable run = new Runnable() {
				public void run() {
					try {
						Thread.sleep((long) Math.random() * 1000);
						semp.acquire();
						String content = t.parser();
						System.out.println(content);
						queue.putObject(content);
						semp.release();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			exec.execute(run);
		}
		exec.shutdown();
	}
}