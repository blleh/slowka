package slowka;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.Bindable;
import org.apache.pivot.collections.Map;
import org.apache.pivot.util.Resources;
import org.apache.pivot.wtk.CardPane;
import org.apache.pivot.wtk.Window;

import java.net.URL;

public class MainWindow extends Window implements Bindable {

	@BXML private CardPane cardPane;

	@Override
	public void initialize(Map<String, Object> strings, URL url, Resources strings2) {
	}

	public void openTestPanel(LearningActivity learningActivity) {
		cardPane.setSelectedIndex(learningActivity.cardIndex);
	}

	public enum LearningActivity {
		WORDS(1), SINGLE_CHOICE_TEST(2);
		private final int cardIndex;

		LearningActivity(int cardIndex) {
			this.cardIndex = cardIndex;
		}
	}
}
