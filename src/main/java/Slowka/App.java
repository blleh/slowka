package slowka;

import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Window;

public class App implements Application {

	public static final String MAIN_WINDOW_BXML = "/bxml/main_window.bxml";
	private Window window = null;
	private BXMLSerializer bxmlSerializer = new BXMLSerializer();

	@Override
	public void startup(Display display, Map<String, String> properties) throws Exception {
		window = (Window) bxmlSerializer.readObject(getClass().getResource(MAIN_WINDOW_BXML));
		window.open(display);
	}

	@Override
	public boolean shutdown(boolean b) throws Exception {
		if (window != null) {
			window.close();
		}
		return false;
	}

	@Override
	public void suspend() throws Exception {
	}

	@Override
	public void resume() throws Exception {
	}

	public static void main(String... args) {
		DesktopApplicationContext.main(App.class, args);
	}
}