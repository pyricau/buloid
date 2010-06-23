/*
 * Copyright 2010 Pierre-Yves Ricau (py.ricau+buloid@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package info.piwai.buloid;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.Toast;

public class SelectorManager {

	private final Activity		activity;

	private final DatePicker	datePicker;

	public SelectorManager(Activity activity, DatePicker datePicker) {
		if (activity == null) {
			throw new IllegalArgumentException("activity should not be null");
		}
		if (datePicker == null) {
			throw new IllegalArgumentException("datePicker should not be null");
		}

		this.activity = activity;
		this.datePicker = datePicker;
	}

	public void showOnGoogleMaps() {

		/*
		 * nb représente les X derniers points les plus récents à récupérer.
		 * 
		 * Cf http://www.rollers-coquillages.org/Ou-est-Raoul.html
		 */

		showUriWithDayFormat("geo:0,0?q=http://www.rollers-coquillages.org/getkml.php?date=%tY-%tm-%td&nb=10");

	}

	private void showUriWithDayFormat(String format) {

		Calendar day = extractDay();

		if (isSunday(day)) {
			String uri = formatWithDate(format, day);
			showUri(uri);
		} else {
			Toast.makeText(activity, "Erreur : la date sélectionnée n'est pas un dimanche, mais un " + dayName(day) + ".",
					Toast.LENGTH_LONG).show();
		}
	}

	private Calendar extractDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
		return calendar;
	}

	private boolean isSunday(Calendar day) {
		return day.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
	}

	private String formatWithDate(String format, Calendar calendar) {
		return String.format(format, calendar, calendar, calendar);
	}

	private void showUri(String uri) {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
		activity.startActivity(intent);
	}

	private String dayName(Calendar day) {
		return (String) DateFormat.format("EEEE", day);
	}

	public void showPdf() {
		showUriWithDayFormat("http://www.rollers-coquillages.org/parcours/%tY%tm%td/feuillederoute.pdf");
	}

}
