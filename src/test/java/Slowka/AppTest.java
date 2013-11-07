package Slowka;


import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Window;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.InputStream;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class AppTest {

	private Display display;
	@Mock private Window window;
	@Mock private BXMLSerializer bxmlSerializer;
	@Mock private Map<String, String> map;
	@InjectMocks private App testee;

	@Test
	public void shouldCreateWindow() throws Exception {
		given(bxmlSerializer.readObject(any(InputStream.class))).willReturn(window);
		testee.startup(display, map);
		verifyZeroInteractions(map);
		verify(window).open(display);
	}

	@Test
	public void shouldCloseWindowOnShutDown() throws Exception {
		testee.shutdown(true);
		verify(window).close();
	}

	@Test
	public void shouldDoNothingForSuspendAndResume() throws Exception {
		testee.suspend();
		testee.resume();
	}
}