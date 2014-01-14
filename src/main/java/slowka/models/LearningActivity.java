package slowka.models;

public enum LearningActivity {
	WORDS(1), SINGLE_CHOICE_TEST(2);
	private final int cardIndex;

	LearningActivity(int cardIndex) {
		this.cardIndex = cardIndex;
	}

	public int getCardIndex() {
		return cardIndex;
	}
}