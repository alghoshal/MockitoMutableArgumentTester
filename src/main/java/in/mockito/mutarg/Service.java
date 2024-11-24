package in.mockito.mutarg;

import org.apache.commons.lang.mutable.MutableInt;

public class Service {
	
	public void serve(MutableInt value) {
		System.out.println("Service called with value: "+value);
	}

}
