package in.mockito.mutarg;

public class Driver{
	public static void main(String[] args) {
		System.out.println("Start processing...");
		Caller caller = new Caller();
		caller.callService(new Service());
		System.out.println("End processing...");
	}
}