package slowka.gui;

import org.apache.pivot.collections.Map;
import org.apache.pivot.util.ListenerList;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.PushButton;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import slowka.gui.listeners.SingleChoiceTestPanelSelectionListener;
import slowka.gui.listeners.WordsLearningPanelSelectionListener;

import java.net.URL;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WelcomePanelTest {

	@Mock private PushButton wordsButton;
	@Mock private PushButton singleChoiceTestButton;
	@Mock private ListenerList<ButtonPressListener> wordsButtonListners;
	@Mock private ListenerList<ButtonPressListener> singleChoiceTestButtonListeners;
	private Map<String, Object> parameters;
	private URL url;
	private Resources resources;
	@InjectMocks WelcomePanel welcomePanel;

	@Test
	public void shouldInitialize() {
		given(wordsButton.getButtonPressListeners()).willReturn(wordsButtonListners);
		given(singleChoiceTestButton.getButtonPressListeners()).willReturn(singleChoiceTestButtonListeners);

		welcomePanel.initialize(parameters, url, resources);

		verify(wordsButton).getButtonPressListeners();
		verify(singleChoiceTestButton).getButtonPressListeners();
		verify(wordsButtonListners).add(any(WordsLearningPanelSelectionListener.class));
		verify(singleChoiceTestButtonListeners).add(any(SingleChoiceTestPanelSelectionListener.class));
	}
}
