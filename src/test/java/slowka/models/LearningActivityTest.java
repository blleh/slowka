package slowka.models;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static slowka.models.LearningActivity.SINGLE_CHOICE_TEST;
import static slowka.models.LearningActivity.WORDS;

public class LearningActivityTest {

	@Test
	public void shouldGiveCorrectIndices() {
		assertThat(WORDS.getCardIndex(), is(1));
		assertThat(SINGLE_CHOICE_TEST.getCardIndex(), is(2));
	}
}
