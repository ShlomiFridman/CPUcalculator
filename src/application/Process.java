package application;

public class Process {
	
	private String name;
	private int enter,runtime,remain,wait,tq,tqClock;

	public Process(String name,int enter,int time,int tq) {
		setName(name);
		setEnter(enter);
		setRuntime(time);
		setRemain(time);
		setTQ(tq);
	}
	
	public void reset() {
		this.setWait(0);
		this.setRemain(this.getRuntime());
	}
	
	public boolean remainMinusOne() {
		if (this.remain<1)
			throw new RuntimeException("Process is aready done, can't change remaining time");
		this.remain--;
		this.tqClock--;
		return isDone();
	}
	
	public void waitPlusOne() {
		if (isDone())
			throw new RuntimeException("Process is aready done, can't change waiting time");
		this.wait++;
	}
	
	public boolean isDone() {
		if (tqClock==0) {
			tqClock=tq;
			return true;
		}
		return remain==0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEnter() {
		return enter;
	}

	public void setEnter(int enter) {
		if (enter<0)
			throw new IllegalArgumentException("Invalid enter time, can not be below zero");
		this.enter = enter;
	}

	public int getRuntime() {
		return runtime;
	}

	public void setRuntime(int time) {
		if (time<0)
			throw new IllegalArgumentException("Invalid running time, can not be below zero");
		this.runtime = time;
	}

	public int getRemain() {
		return remain;
	}

	public void setRemain(int remain) {
		this.remain = remain;
	}

	public int getWait() {
		return wait;
	}

	public void setWait(int wait) {
		this.wait = wait;
	}

	@Override
	public String toString() {
		return String.format("%s Process: Entered at '%d' | Runtime '%d' ('%d' Remains) | Waited '%d' | TQ %d(%d)", name,enter,runtime,remain,wait,tq,tqClock);
	}
	
	public void setTQ(int tq) {
		this.tq = tq;
		this.tqClock=tq;
	}
	public int getTQ() {
		return this.tq;
	}

}
