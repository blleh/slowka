package Slowka;

import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Window;

public class App implements Application {

	private Window window = null;

	@Override
	public void startup(Display display, Map<String, String> properties) throws Exception {
		BXMLSerializer bxmlSerializer = new BXMLSerializer();
		window = (Window) bxmlSerializer.readObject(getClass().getResource("/bxml/main_window.bxml"));
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