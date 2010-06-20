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
		/*
		 * Je n'ai pas la moindre idée quant à l'utilité du paramètre nb=999.
		 * J'ai simplement trouvé le lien tel quel dans les forums. Si quelqu'un
		 * a la réponse, je suis preneur ;-) .
		 */
		String query = formatWithDate("http://www.rollers-coquillages.org/getkml.php?date=%tY-%tm-%td&nb=999");

		String uri = "geo:0,0?q=" + query;

		showUri(uri);
	}

	private String formatWithDate(String format) {

		Calendar calendar = Calendar.getInstance();

		calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
		return String.format(format, calendar, calendar, calendar);
	}

	private void showUri(String uri) {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
		activity.startActivity(intent);
	}

	public void showPdf() {
		String uri = formatWithDate("http://www.rollers-coquillages.org/parcours/%tY%tm%td/feuillederoute.pdf");

		showUri(uri);
	}

}
