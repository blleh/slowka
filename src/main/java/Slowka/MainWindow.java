package Slowka;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.Alert;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.ComponentKeyListener;
import org.apache.pivot.wtk.FileBrowserSheet;
import org.apache.pivot.wtk.Form.Section;
import org.apache.pivot.wtk.Keyboard;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.MessageType;
import org.apache.pivot.wtk.Prompt;
import org.apache.pivot.wtk.Sheet;
import org.apache.pivot.wtk.SheetCloseListener;
import org.apache.pivot.wtk.TextInput;
import org.apache.pivot.wtk.Window;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static java.util.Map.Entry;
import static org.apache.pivot.wtk.Alert.alert;
import static org.apache.pivot.wtk.FileBrowserSheet.Mode.OPEN;
import static org.apache.pivot.wtk.MessageType.ERROR;
import static org.apache.pivot.wtk.MessageType.INFO;

public class MainWindow extends Window implements Bindable {

	@BXML private Label polishWord;
	@BXML private TextInput englishWord;
	@BXML private Section formSection;
	@BXML private Button checkButton;
	@BXML private Button loadButton;
	@BXML private Label status;

	private Map<String, String> wordsMap = new HashMap<String, String>();
	private Iterator<Entry<String,String>> iterator;
	{
		wordsMap.put("kot", "cat");
		wordsMap.put("pies", "dog");
		wordsMap.put("krowa", "cow");
		wordsMap.put("kon", "horse");
		iterator = wordsMap.entrySet().iterator();
	}
	private Entry<String, String> currentEntry;
	private int correct;
	private int incorrect;
	private int index;

	@Override
	public void initialize(org.apache.pivot.collections.Map<String, Object> strings, URL url, Resources strings2) {
		loadInitial();
		checkButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				String input = englishWord.getText();
				if (currentEntry.getValue().equals(input)) {
					correct++;
					Prompt.prompt("Poprawnie!", MainWindow.this);
				} else {
					incorrect++;
					Prompt.prompt("Niepoprawnie!", MainWindow.this);
				}
				englishWord.setText("");
				updateLabels();
				loadWord();
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
				fileBrowserSheet.open(MainWindow.this, new SheetCloseListener() {
					@Override
					public void sheetClosed(Sheet sheet) {
						if (sheet.getResult()) {
							File file = fileBrowserSheet.getSelectedFile();
							loadNewFile(file);
						} else {
							Prompt.prompt(ERROR, "Nie wybrano pliku", MainWindow.this);
						}
					}
				});
			}
		});
	}

	private void loadNewFile(File file) {
		try {
			wordsMap.clear();
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			while((line = br.readLine()) != null) {
				wordsMap.put(line.split(",")[0].trim(), line.split(",")[1].trim());
			};
			iterator = wordsMap.entrySet().iterator();
			loadInitial();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void loadInitial() {
		index = correct = incorrect = 0;
		updateLabels();
		loadWord();
	}

	private void loadWord() {
		if (iterator.hasNext()) {
			currentEntry = iterator.next();
			polishWord.setText(currentEntry.getKey());
			index++;
			checkButton.setEnabled(true);
		} else {
			checkButton.setEnabled(false);
			alert(INFO, String.format("Zestaw ukonczony.\nPoprawne: %d\nNiepoprawne: %d\n", correct, incorrect), MainWindow.this);
		}
	}

	private void updateLabels() {
		formSection.setHeading(String.format("Slowko %d/%d", index+1, wordsMap.size()));
		status.setText(String.format("Poprawne: %d Niepoprawne: %d", correct, incorrect));
	}
}
