package slowka.gui.listeners;

import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import slowka.gui.MainWindow;
import slowka.gui.WelcomePanel;

import static slowka.models.LearningActivity.SINGLE_CHOICE_TEST;

public class SingleChoiceTestPanelSelectionListener implements ButtonPressListener {
	private final WelcomePanel welcomePanel;

	public SingleChoiceTestPanelSelectionListener(WelcomePanel welcomePanel) {
		this.welcomePanel = welcomePanel;
	}

	@Override
	public void buttonPressed(Button button) {
		MainWindow activeWindow = (MainWindow) welcomePanel.getAncestor(MainWindow.class);
		activeWindow.openTestPanel(SINGLE_CHOICE_TEST);
	}
}