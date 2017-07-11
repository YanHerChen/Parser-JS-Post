package js_parser;

public class Queue {
	int read_pointer = 0;
	int write_pointer = 0;
	boolean is_locked = false;
	boolean is_closed = false;
	Object[] queue_items = null;

	public Queue(int size) {
		queue_items = new Object[size + 1];
		for (int i = 0; i < queue_items.length; i++)
			queue_items[i] = null;
	}

	public synchronized Object getObject() throws Exception {
		lockQueue('r');
		Object obj = queue_items[read_pointer];
		queue_items[read_pointer] = null;
		read_pointer = (read_pointer + 1) % queue_items.length;
		unlockQueue();
		return obj;
	}

	public synchronized void putObject(Object obj) throws Exception {
		lockQueue('w');
		queue_items[write_pointer] = obj;
		write_pointer = (write_pointer + 1) % queue_items.length;
		unlockQueue();
	}

	public void close() {
		try {
			lockQueue('c');
			is_closed = true;
			unlockQueue();
			queue_items = null;
			System.gc();
		} catch (Exception e) {
		}
	}

	private void lockQueue(char task) throws Exception {
		while (true) {
			if (is_closed) {
				Exception e = new Exception("佇列已關閉");
				throw e;
			}
			synchronized (this) {
				if (is_locked) {
					try {
						wait();
					} catch (InterruptedException e) {
					}
				} else {
					boolean ok = false;
					if (task == 'r')
						if (getReadSize() > 0)
							ok = true;
					if (task == 'w')
						if (getWriteSize() > 0)
							ok = true;
					if (task == 'c')
						ok = true;
					if (ok) {
						is_locked = true;
						return;
					}
				}
			}
		}
	}

	private void unlockQueue() {
		is_locked = false;
		synchronized (this) {
			notifyAll();
		}
	}

	private int getReadSize() {
		int size = write_pointer - read_pointer;
		if (size < 0)
			size += queue_items.length;
		return size;
	}
	
	private int getWriteSize() {
		int size = read_pointer - write_pointer - 1;
		if (size < 0)
			size += queue_items.length;
		return size;
	}

	public boolean chkQueue() {
		if (getReadSize() == 0)
			return true;
		return false;
	}

	public int size() {
		return getReadSize();
	}
}