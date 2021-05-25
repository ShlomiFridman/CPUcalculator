package application;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class CPU {
	
	private boolean preemptive;
	private OrderType type;		// FCFS(0),SJF(1),SJR(2);
	private PriorityQueue<Process> waiting,toEnter;
	private LinkedList<Process> finished,original;
	private Process current;
	private int clock;

	public CPU(OrderType type) {
		setPreemptive(type.preemptive);
		setType(type);
		finished = new LinkedList<>();
		original = new LinkedList<>();
		waiting = new PriorityQueue<>((a,b)->{
			int res = (type!=OrderType.FCFS && type!=OrderType.RR)? a.getRemain()-b.getRemain() : a.getEnter()-b.getEnter();
			return res==0? a.getName().compareTo(b.getName()):res;
		});
		this.toEnter = new PriorityQueue<>(new Comparator<>() {

			@Override
			public int compare(Process o1, Process o2) {
				return o1.getEnter()-o2.getEnter();
			}
			
		});
	}
	
	public CPU(OrderType type,List<Process> toEnter) {
		this(type);
		this.addAll(toEnter);
//		Iterator<Process> it = toEnter.iterator();
//		while (it.hasNext())
//			this.add(it.next());
	}

	public CPU(OrderType type,Process[] toEnter) {
		this(type);
		for (Process tmp:toEnter)
			this.add(tmp);
//		Iterator<Process> it = toEnter.iterator();
//		while (it.hasNext())
//			this.add(it.next());
	}
	
	public void add(Process p) {
		this.original.add(p);
		toEnter.offer(p);
	}

	public void addAll(List<Process> list) {
		this.original.addAll(list);
		toEnter.addAll(list);
	}
	
	public double run() {
		if (this.isDone())
			this.reset();
		while (!isDone())
			tick();
		return this.getWaitAvg();
	}

	/**
	 * Advance the clock, and add plus one to the wait time in the waiting queue
	 */
	public boolean tick() {
		checkToEnter();
		clock++;
		if (current==null || (current.remainMinusOne() || (preemptive && !waiting.isEmpty() && waiting.peek().getRemain()-current.getRemain()<0)))
			contextSwitch();
		waitPlusOne();
		return isDone();
	}
	
	/**
	 * Enter a new process to the CPU, if the old one has not finished it re enters the waiting list
	 */
	public void contextSwitch() {
		if (current!=null) {
//			System.out.println("Exit "+clock+":\t"+current);
			if (current.getRemain()==0) {
				finished.add(current);
			}
			else {
				current.setEnter(clock);
				waiting.offer(current);
			}
		}
		current = waiting.poll();
	}
	
	/**
	 * Plus one to the wait time to all process in the waiting queue
	 */
	private void waitPlusOne() {
		Iterator<Process> it = waiting.iterator();
		while (it.hasNext())
			it.next().waitPlusOne();
	}
	
	private void checkToEnter() {
		if (toEnter.isEmpty()) return;
		Iterator<Process> it = toEnter.iterator();
		LinkedList<Process> toRemove = new LinkedList<>();
		Process tmp;
		while (it.hasNext()) {
			tmp = it.next();
			if (tmp.getEnter()<=clock) {
				waiting.offer(tmp);
				toRemove.add(tmp);
			}
		}
		toEnter.removeAll(toRemove);
	}

	/**
	 * Returns the current waiting average of the finished process
	 */
	public double getWaitAvg() {
		double cntr,sum;
		cntr = sum = 0;
		Iterator<Process> it = finished.iterator();
		while (it.hasNext()) {
			cntr++;
			sum += (double) it.next().getWait();
		}
		return (cntr!=0)? sum/cntr:0;
	}
	
	public void reset() {
		this.setClock(0);
		Iterator<Process> it;
		it = this.waiting.iterator();
		while (it.hasNext())
			this.toEnter.offer(it.next());
		it = this.finished.iterator();
		while (it.hasNext())
			this.toEnter.offer(it.next());
		if (this.current!=null) {
			this.toEnter.offer(current);
			current=null;
		}
		it = this.toEnter.iterator();
		while (it.hasNext())
			it.next().reset();
	}
	
	public boolean isDone() {
		return waiting.isEmpty() && toEnter.isEmpty() && (current==null || current.isDone());
	}

	public boolean isPreemptive() {
		return preemptive;
	}

	public void setPreemptive(boolean preemptive) {
		this.preemptive = preemptive;
	}

	public OrderType getType() {
		return type;
	}

	public void setType(OrderType type) {
		this.type = type;
	}

	public PriorityQueue<Process> getWaiting() {
		return waiting;
	}

	public void setWaiting(PriorityQueue<Process> waiting) {
		this.waiting = waiting;
	}

	public PriorityQueue<Process> getToEnter() {
		return toEnter;
	}

	public void setToEnter(PriorityQueue<Process> toEnter) {
		this.toEnter = toEnter;
	}

	public LinkedList<Process> getFinished() {
		return finished;
	}

	public void setFinished(LinkedList<Process> finished) {
		this.finished = finished;
	}

	public Process getCurrent() {
		return current;
	}

	public void setCurrent(Process current) {
		this.current = current;
	}

	public int getClock() {
		return clock;
	}

	public void setClock(int clock) {
		this.clock = clock;
	}

	public LinkedList<Process> getOriginal() {
		return original;
	}

	public void setOriginal(LinkedList<Process> original) {
		this.original = original;
	}
	
}
