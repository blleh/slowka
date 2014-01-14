package slowka.gui.listeners;

import org.apache.pivot.wtk.Button;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import slowka.gui.MainWindow;
import slowka.gui.WelcomePanel;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static slowka.models.LearningActivity.SINGLE_CHOICE_TEST;

@RunWith(MockitoJUnitRunner.class)
public class SingleChoiceTestPanelSelectionListenerTest {

	@Mock WelcomePanel welcomePanel;
	@Mock Button button;
	@Mock private MainWindow mainWindow;

	@Test
	public void shouldOpenSingletestChoicePanelInMainWindow() {
		SingleChoiceTestPanelSelectionListener listener = new SingleChoiceTestPanelSelectionListener(welcomePanel);
		given(welcomePanel.getAncestor(MainWindow.class)).willReturn(mainWindow);
		listener.buttonPressed(button);
		verify(mainWindow).openTestPanel(SINGLE_CHOICE_TEST);
	}
}
