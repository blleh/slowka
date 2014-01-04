package slowka;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.BoxPane;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.CardPane;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.ComponentKeyListener;
import org.apache.pivot.wtk.FileBrowserSheet;
import org.apache.pivot.wtk.Form;
import org.apache.pivot.wtk.Keyboard;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.Prompt;
import org.apache.pivot.wtk.Sheet;
import org.apache.pivot.wtk.SheetCloseListener;
import org.apache.pivot.wtk.TextInput;
import org.apache.pivot.wtk.skin.CardPaneSkin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import static org.apache.pivot.wtk.FileBrowserSheet.Mode.OPEN;
import static org.apache.pivot.wtk.MessageType.ERROR;

public class WordsLearningPanel extends BoxPane implements Bindable {

	@BXML private Label polishWord;
	@BXML private TextInput englishWord;
	@BXML private Form.Section formSection;
	@BXML private Button checkButton;
	@BXML private Button loadButton;
	@BXML private Label status;
	@BXML private Button restartButton;
	@BXML private CardPane cardPanis;

	private java.util.Map<String, String> wordsMap = new HashMap<String, String>();
	private Iterator<java.util.Map.Entry<String,String>> iterator;
	private java.util.Map.Entry<String, String> currentEntry;
	private int correct;
	private int incorrect;
	private int index;

	@Override
	public void initialize(org.apache.pivot.collections.Map<String, Object> strings, URL url, Resources strings2) {
		cardPanis.getStyles().put("selectionChangeEffect", CardPaneSkin.SelectionChangeEffect.CROSSFADE);

		finishOldSuite();
		checkButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				String input = englishWord.getText();
				final MainWindow activeWindow = (MainWindow) WordsLearningPanel.this.getParent().getParent();

				if (currentEntry.getValue().equals(input)) {
					correct++;
					Prompt.prompt("Poprawnie!", activeWindow);
				} else {
					incorrect++;
					Prompt.prompt(ERROR, String.format("%s", currentEntry.getValue()), activeWindow);
				}
				englishWord.setText("");
				updateLabels();
				loadWord();
			}
		});

		restartButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				startNewSuite();
			}
		});

		englishWord.getComponentKeyListeners().add(new ComponentKeyListener() {
			@Override
			public boolean keyTyped(Component component, char c) {
				if (c == '\n') {
					checkButton.press();
				}
				return false;
			}

			@Override public boolean keyPressed(Component component, int i, Keyboard.KeyLocation keyLocation) {return false;}
			@Override public boolean keyReleased(Component component, int i, Keyboard.KeyLocation keyLocation) {return false;}
		});

		loadButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				final FileBrowserSheet fileBrowserSheet = new FileBrowserSheet();
				fileBrowserSheet.setMode(OPEN);
				final MainWindow activeWindow = (MainWindow) WordsLearningPanel.this.getParent().getParent();
				fileBrowserSheet.open(activeWindow, new SheetCloseListener() {
					@Override
					public void sheetClosed(Sheet sheet) {
						if (sheet.getResult()) {
							File file = fileBrowserSheet.getSelectedFile();
							loadNewSuite(file);
						} else {
							Prompt.prompt(ERROR, "Nie wybrano pliku", activeWindow);
						}
					}
				});
			}
		});
	}

	private void loadNewSuite(File file) {
		wordsMap.clear();
		String line;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			while((line = br.readLine()) != null) {
				wordsMap.put(line.split(",")[0].trim(), line.split(",")[1].trim());
			};
			startNewSuite();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void startNewSuite() {
		index = correct = incorrect = 0;
		checkButton.setEnabled(true);
		englishWord.setEnabled(true);
		iterator = wordsMap.entrySet().iterator();
		updateLabels();
		loadWord();
	}

	private void loadWord() {
		if (iterator.hasNext()) {
			currentEntry = iterator.next();
			polishWord.setText(currentEntry.getKey());
			index++;
		} else {
			finishOldSuite();
		}
	}

	private void updateLabels() {
		formSection.setHeading(String.format("Slowko %d/%d", index+1, wordsMap.size()));
		status.setText(String.format("Poprawne: %d Niepoprawne: %d", correct, incorrect));
	}

	private void finishOldSuite() {
		formSection.setHeading("Wybierz nowy zestaw");
		checkButton.setEnabled(false);
		englishWord.setEnabled(false);
	}
}
