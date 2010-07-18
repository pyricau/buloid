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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class LauncherActivity extends Activity {

	private static final String	DAY_FORMAT		= "dd MMMM";

	private static final int	ABOUT_DIALOG	= 1;

	private static final int	MANUAL_DIALOG	= ABOUT_DIALOG + 1;

	private Button				previousSunday;

	private Button				nextSunday;

	private TextView			sundayView;

	private Button				showOnGMaps;

	private Button				downloadPdf;

	private LauncherManager		launcherManager;

	private SundaySelector		sundaySelector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		init();
		bind();
		updateSundayView();
	}

	private void init() {
		setContentView(R.layout.launcher);

		sundaySelector = new SundaySelector();

		previousSunday = (Button) findViewById(R.id.previous_sunday);
		nextSunday = (Button) findViewById(R.id.next_sunday);
		showOnGMaps = (Button) findViewById(R.id.show_on_gmaps);
		downloadPdf = (Button) findViewById(R.id.download_pdf);
		sundayView = (TextView) findViewById(R.id.sunday_view);

		launcherManager = new LauncherManager(this, sundaySelector);
	}

	private void bind() {

		previousSunday.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sundaySelector.previousSunday();
				updateSundayView();
			}
		});

		nextSunday.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sundaySelector.nextSunday();
				updateSundayView();
			}
		});

		showOnGMaps.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				launcherManager.showOnGoogleMaps();
			}
		});
		downloadPdf.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				launcherManager.showPdf();
			}
		});
	}

	private void updateSundayView() {
		CharSequence formatedSunsay = DateFormat.format(DAY_FORMAT, sundaySelector.getCalendar());
		sundayView.setText(formatedSunsay);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add("Manuel").setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				showDialog(MANUAL_DIALOG);
				return true;
			}
		});

		menu.add("A propos").setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				showDialog(ABOUT_DIALOG);
				return true;
			}
		});

		return true;
	}

	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
			case ABOUT_DIALOG:
				return createAboutDialog();
			case MANUAL_DIALOG:
				return createManualDialog();
			default:
				throw new IllegalArgumentException("Unknown dialog id. Shouldn't happen.");
		}
	}

	private Dialog createAboutDialog() {
		return createInfoDialog("A propos", R.string.about);
	}

	private Dialog createInfoDialog(String title, int messageId) {
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

		dialogBuilder //
				.setTitle(title) //
				.setView(linkifyText(messageId)) //
				.setPositiveButton(android.R.string.ok, //
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});

		return dialogBuilder.create();
	}

	private ScrollView linkifyText(int messageId) {

		String message = getString(messageId);

		ScrollView scrollView = new ScrollView(this);
		TextView textView = new TextView(this);

		SpannableString spannableString = new SpannableString(message);

		Linkify.addLinks(spannableString, Linkify.ALL);
		textView.setText(spannableString);
		textView.setMovementMethod(LinkMovementMethod.getInstance());

		scrollView.setPadding(14, 2, 10, 12);
		scrollView.addView(textView);

		return scrollView;
	}

	private Dialog createManualDialog() {
		return createInfoDialog("Manuel", R.string.manual);
	}
}
