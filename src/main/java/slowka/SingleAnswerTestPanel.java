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
import org.apache.pivot.wtk.TextArea;
import slowka.models.Question;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.apache.pivot.wtk.FileBrowserSheet.Mode.OPEN;
import static org.apache.pivot.wtk.MessageType.ERROR;

public class SingleAnswerTestPanel extends BoxPane implements Bindable {

	@BXML private ButtonGroup answersButtonGroup;
	@BXML private BoxPane answersPane;
	@BXML private Form.Section formSection;
	@BXML private Button checkButton;
	@BXML private Button loadButton;
	@BXML private Label status;
	@BXML private Button restartButton;
	@BXML private TextArea questionLabel;

	private Iterator<Question> iterator;
	private Question currentQuestion;
	private int correct;
	private int incorrect;
	private int index;
	private List<Question> questions = new ArrayList<Question>();

	@Override
	public void initialize(org.apache.pivot.collections.Map<String, Object> strings, URL url, Resources strings2) {
		finishOldSuite();
		checkButton.getButtonPressListeners().add(new ButtonPressListener() {
			@Override
			public void buttonPressed(Button button) {
				String input = (String) answersButtonGroup.getSelection().getButtonData();
				final MainWindow activeWindow = (MainWindow) SingleAnswerTestPanel.this.getAncestor(MainWindow.class);
				if (currentQuestion.isCorrectAnswer(input)) {
					correct++;
					Prompt.prompt("Poprawnie!", activeWindow);
				} else {
					incorrect++;
					Prompt.prompt(ERROR, String.format("Niepoprawnie"), activeWindow);
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
						if (sheet.getResult()) {
							File file = fileBrowserSheet.getSelectedFile();
							loadNewSuite(file);
							startNewSuite();
						} else {
							Prompt.prompt(ERROR, "Nie wybrano pliku", activeWindow);
						}
					}
				});
			}
		});
	}

	private void loadNewSuite(File file) {
		questions.clear();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			Question currentQuestion = null;
			while ((line = br.readLine()) != null) {
				if (!line.isEmpty()) {
					if (currentQuestion == null) {
						currentQuestion = new Question(line);
					} else {
						if (line.startsWith("*")) {
							currentQuestion.addCorrectAnswer(line);
						} else {
							currentQuestion.addAnswer(line);
						}
					}
				} else {
					if (currentQuestion != null) {
						questions.add(currentQuestion);
						currentQuestion = null;
					}
				}
			}
			if (currentQuestion != null) {
				questions.add(currentQuestion);
			}
		} catch (Exception e) {
			throw new RuntimeException("error in loading suite", e);
		}
	}

	private void startNewSuite() {
		index = correct = incorrect = 0;
		iterator = questions.iterator();
		checkButton.setEnabled(true);
		updateLabels();
		loadQuestion();
	}

	private void loadQuestion() {
		if (iterator.hasNext()) {
			currentQuestion = iterator.next();
			displayQuestion();
			index++;
		} else {
			finishOldSuite();
		}
	}

	private void displayQuestion() {
		answersPane.removeAll();
		questionLabel.setText(currentQuestion.getQuestion());
		for (String answer : currentQuestion.getAnswers()) {
			RadioButton answerButon = new RadioButton(answersButtonGroup, answer);
			answersPane.add(answerButon);
		}

	}

	private void updateLabels() {
		formSection.setHeading(String.format("Pytanie %d/%d", index+1, questions.size()));
		status.setText(String.format("Poprawne: %d Niepoprawne: %d", correct, incorrect));
	}

	private void finishOldSuite() {
		questionLabel.setText("");
		answersPane.removeAll();
		formSection.setHeading("Wybierz nowy zestaw");
		checkButton.setEnabled(false);
	}
}
