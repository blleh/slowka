package slowka.gui;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.BoxPane;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.PushButton;
import slowka.gui.listeners.SingleChoiceTestPanelSelectionListener;
import slowka.gui.listeners.WordsLearningPanelSelectionListener;

import java.net.URL;

import static slowka.gui.MainWindow.LearningActivity.SINGLE_CHOICE_TEST;
import static slowka.gui.MainWindow.LearningActivity.WORDS;

public class WelcomePanel extends BoxPane implements Bindable {

	@BXML private PushButton wordsButton;
	@BXML private PushButton singleChoiceTestButton;

	@Override
	public void initialize(Map<String, Object> strings, URL url, Resources strings2) {
		wordsButton.getButtonPressListeners().add(new WordsLearningPanelSelectionListener(this));
		singleChoiceTestButton.getButtonPressListeners().add(new SingleChoiceTestPanelSelectionListener(this));
	}
}
