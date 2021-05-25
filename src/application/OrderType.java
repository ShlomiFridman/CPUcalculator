package application;

public enum OrderType {

	FCFS(false),SJF(false),SJR(true),RR(false);

	final boolean preemptive;
	private OrderType(boolean flag) {
		this.preemptive=flag;
	}
}
