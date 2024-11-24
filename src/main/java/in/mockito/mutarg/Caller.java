package in.mockito.mutarg;

import org.apache.commons.lang.mutable.MutableInt;

public class Caller {
	public void callService(Service service) {
		MutableInt value = new MutableInt();
		value.setValue(1);
		service.serve(value);

		value.setValue(2);
		service.serve(value);
	}
}