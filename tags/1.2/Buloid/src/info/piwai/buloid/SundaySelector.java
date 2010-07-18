package info.piwai.buloid;

import java.util.Calendar;

public class SundaySelector {

	private final Calendar	sundayCalendar;

	public SundaySelector() {
		sundayCalendar = Calendar.getInstance();

		int dayOfWeek = sundayCalendar.get(Calendar.DAY_OF_WEEK);

		if (dayOfWeek != Calendar.SUNDAY) {
			rollToNextSunday(dayOfWeek);
		}
	}

	private void rollToNextSunday(int dayOfWeek) {
		int daysToAdd = 8 - dayOfWeek;
		rollDays(daysToAdd);
	}

	private void rollDays(int daysToRoll) {
		sundayCalendar.add(Calendar.DAY_OF_WEEK, daysToRoll);
	}

	public void nextSunday() {
		rollDays(7);
	}

	public void previousSunday() {
		rollDays(-7);
	}

	public Calendar getCalendar() {
		return (Calendar) sundayCalendar.clone();
	}

}
