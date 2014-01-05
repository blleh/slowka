package slowka;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.BoxPane;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonGroup;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.FileBrowserSheet;
import org.apache.pivot.wtk.Form;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.Prompt;
import org.apache.pivot.wtk.RadioButton;
import org.apache.pivot.wtk.Sheet;
import org.apache.pivot.wtk.SheetCloseListener;

import java.io.File;
import java.net.URL;
import java.util.Iterator;

import static org.apache.pivot.wtk.FileBrowserSheet.Mode.OPEN;
import static org.apache.pivot.wtk.MessageType.ERROR;

public class SingleAnswerTestPanel extends BoxPane implements Bindable {

	@BXML private ButtonGroup answers;
	@BXML private BoxPane answersPane;
	@BXML private Form.Section formSection;
	@BXML private Button checkButton;
	@BXML private Button loadButton;
	@BXML private Label status;
	@BXML private Button restartButton;

	private Iterator<java.util.Map.Entry<String,String>> iterator;
	private java.util.Map.Entry<String, String> currentEntry;
	private int correct;
	private int incorrect;
	private int index;
	private RadioButton button1;
	private RadioButton button2;

	@Override
	public void initialize(org.apache.pivot.collections.Map<String, Object> strings, URL url, Resources strings2) {
		finishOldSuite();

		answersPane.add(button1);
		answersPane.add(button2);

		answers.add(button1);
		answers.add(button2);

		checkButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				String input = "not implemented yet";
				final MainWindow activeWindow = (MainWindow) SingleAnswerTestPanel.this.getAncestor(MainWindow.class);
				if (currentEntry.getValue().equals(input)) {
					correct++;
					Prompt.prompt("Poprawnie!", activeWindow);
				} else {
					incorrect++;
					Prompt.prompt(ERROR, String.format("%s", currentEntry.getValue()), activeWindow);
				}
				updateLabels();
				loadQuestion();
			}
		});

		restartButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				startNewSuite();
			}
		});

		loadButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				final FileBrowserSheet fileBrowserSheet = new FileBrowserSheet();
				final MainWindow activeWindow = (MainWindow) SingleAnswerTestPanel.this.getAncestor(MainWindow.class);
				fileBrowserSheet.setMode(OPEN);
				fileBrowserSheet.open(activeWindow, new SheetCloseListener() {
					@Override
					public void sheetClosed(Sheet sheet) {
						Prompt.prompt(ERROR, "Not implemented yet!", activeWindow);
					}
				});
			}
		});
	}

	private void loadNewSuite(File file) {
	}

	private void startNewSuite() {
		index = correct = incorrect = 0;
		checkButton.setEnabled(true);
		updateLabels();
		loadQuestion();
	}

	private void loadQuestion() {
		if (iterator.hasNext()) {
			currentEntry = iterator.next();
			index++;
		} else {
			finishOldSuite();
		}
	}

	private void updateLabels() {
		formSection.setHeading(String.format("Pytanie %d/%d", index+1, 1000));
		status.setText(String.format("Poprawne: %d Niepoprawne: %d", correct, incorrect));
	}

	private void finishOldSuite() {
		formSection.setHeading("Wybierz nowy zestaw");
		checkButton.setEnabled(false);
	}
}