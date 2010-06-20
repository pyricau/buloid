package info.piwai.buloid;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.DatePicker;

public class SelectorManager {

	private final Activity		activity;

	private final DatePicker	datePicker;

	public SelectorManager(Activity activity, DatePicker datePicker) {
		assert activity != null;
		assert datePicker != null;

		this.activity = activity;
		this.datePicker = datePicker;
	}

	public void showOnGoogleMaps() {
		String query = formatWithDate("http://www.rollers-coquillages.org/getkml.php?date=%tY-%tm-%td&nb=999");

		String uri = "geo:0,0?q=" + query;

		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

		activity.startActivity(intent);
	}

	private String formatWithDate(String format) {

		Calendar calendar = Calendar.getInstance();

		calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
		return String.format(format, calendar, calendar, calendar);
	}

	public void showPdf() {
		String uri = formatWithDate("http://www.rollers-coquillages.org/parcours/%tY%tm%td/feuillederoute.pdf");

		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

		activity.startActivity(intent);
	}

}
