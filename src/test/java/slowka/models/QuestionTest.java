package slowka.models;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class QuestionTest {

	Question question = new Question("test");

	@Test
	public void shouldInitialiyeWithQuestion() {
		assertThat(question.getQuestion(), is("test"));
	}

	@Test
	public void shouldAddAnswers() {
		question.addAnswer("answer1");
		question.addAnswer("answer2");
		assertThat(question.getAnswers().size(), is(2));
		assertThat(question.getAnswers().get(0), is("answer1"));
		assertThat(question.getAnswers().get(1), is("answer2"));
	}

	@Test
	public void shouldAddCorrectAnswer() {
		question.addCorrectAnswer("correct answer");
		assertThat(question.getAnswers().size(), is(1));
		assertThat(question.isCorrectAnswer("correct ANSWER"), is(false));
		assertThat(question.isCorrectAnswer("correct answer"), is(true));
	}

	@Test(expected = RuntimeException.class)
	public void shouldThrowRuntimeExceptionWhenNoCorrectAnswerWasSetBeforeChecking() {
		question.addAnswer("not correct answer");
		question.isCorrectAnswer("correct answer");
	}
}
