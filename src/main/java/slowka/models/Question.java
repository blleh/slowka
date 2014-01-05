package slowka.models;

import java.util.ArrayList;
import java.util.List;

public class Question {
	private final List<String> answers = new ArrayList<String>();
	private final String question;
	private int correctAnswerIndex = -1;

	public Question(String question) {
		this.question = question;
	}

	public void addAnswer(String answer) {
		answers.add(answer);
	}

	public void addCorrectAnswer(String answer) {
		addAnswer(answer);
		if (correctAnswerIndex < 0) {
			this.correctAnswerIndex = answers.size() - 1;
		} else {
			throw new RuntimeException("More than one correct answer in single answer question");
		}
	}

	public String getQuestion() {
		return question;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public boolean isCorrectAnswer(String toBeChecked) {
		if (correctAnswerIndex < 0) {
			throw new RuntimeException("No correct answer given");
		}
		return answers.get(correctAnswerIndex).equals(toBeChecked);
	}
}
