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

public class LauncherManager {

	private final Activity			activity;

	private final SundaySelector	sundaySelector;

	public LauncherManager(Activity activity, SundaySelector sundaySelector) {
		if (activity == null) {
			throw new IllegalArgumentException("activity should not be null");
		}
		if (sundaySelector == null) {
			throw new IllegalArgumentException("sundaySelector should not be null");
		}

		this.activity = activity;
		this.sundaySelector = sundaySelector;
	}

	public void showOnGoogleMaps() {

		/*
		 * nb represents the last "nb" most recent points to download
		 * 
		 * Cf http://www.rollers-coquillages.org/Ou-est-Raoul.html
		 */

		launchUriWithDayFormat("geo:0,0?q=http://www.rollers-coquillages.org/getkml.php?date=%tY-%tm-%td&nb=10");

	}

	private void launchUriWithDayFormat(String format) {

		Calendar day = sundaySelector.getCalendar();
		String uri = formatWithDate(format, day);
		launchUri(uri);
	}

	private String formatWithDate(String format, Calendar calendar) {
		return String.format(format, calendar, calendar, calendar);
	}

	private void launchUri(String uri) {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
		activity.startActivity(intent);
	}

	public void showPdf() {
		launchUriWithDayFormat("http://www.rollers-coquillages.org/parcours/%tY%tm%td/feuillederoute.pdf");
	}

}
