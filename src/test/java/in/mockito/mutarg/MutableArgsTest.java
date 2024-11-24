package in.mockito.mutarg;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.mutable.MutableInt;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import in.mockito.mutarg.Caller;
import in.mockito.mutarg.Service;
import junit.framework.TestCase;

public class MutableArgsTest extends TestCase {

	List<MutableInt> multiValuesWritten;

	@Mock
	Service service;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		MockitoAnnotations.initMocks(this);
		multiValuesWritten = new ArrayList<MutableInt>();
	}

	/**
	 * Failure with ArgumentCaptor
	 */
	public void testMutableArgsWithArgCaptorFail() {
		Caller caller = new Caller();
		ArgumentCaptor<MutableInt> valueCaptor = ArgumentCaptor.forClass(MutableInt.class);

		caller.callService(service);
		verify(service, times(2)).serve(valueCaptor.capture());

		/*
		 * Fails:
		 * "junit.framework.AssertionFailedError: expected:<[1, 2]> but was:<[2, 2]>"
		 * Caller reused the MutableInt object & updated it before the 2nd call to
		 * serve(). So the referenced argument obj in ArgumentCaptor, has the last/
		 * latest updated value.
		 */
		assertEquals(Arrays.asList(new MutableInt(1), new MutableInt(2)), valueCaptor.getAllValues());
	}

	/**
	 * Success with Answer
	 */
	public void testMutableArgsWithDoAnswer() {
		Caller caller = new Caller();
		doAnswer(new CaptureArgumentsWrittenAsMutableInt<Void>()).when(service).serve(any(MutableInt.class));

		caller.callService(service);
		verify(service, times(2)).serve(any(MutableInt.class));

		// Works!
		assertEquals(new MutableInt(1), multiValuesWritten.get(0));
		assertEquals(new MutableInt(2), multiValuesWritten.get(1));
	}

	/**
	 * Captures Arguments to the Service.serve() method: 
	 * - Multiple calls to serve()happen from the same caller 
	 * - Along with reuse of MutableInt argument objects by the caller, 
	 * - Argument value is copied to a new MutableInt object & that's captured
	 * 
	 * @param <Void>
	 */

	public class CaptureArgumentsWrittenAsMutableInt<Void> implements Answer<Void> {
		public Void answer(InvocationOnMock invocation) {
			Object[] args = invocation.getArguments();
			System.out.println("called with arguments: " + Arrays.toString(args));
			multiValuesWritten.add(new MutableInt(args[0].toString()));
			return null;
		}
	}
}
