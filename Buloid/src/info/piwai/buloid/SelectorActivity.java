package info.piwai.buloid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ScrollView;
import android.widget.TextView;

public class SelectorActivity extends Activity {

	private static final int	ABOUT_DIALOG	= 1;

	private static final int	MANUAL_DIALOG	= ABOUT_DIALOG + 1;

	private DatePicker			datePicker;

	private Button				showOnGMaps;

	private Button				downloadPdf;

	private SelectorManager		selectorManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		init();

		bind();

	}

	private void init() {
		setContentView(R.layout.selector);

		datePicker = (DatePicker) findViewById(R.id.date_picker);

		showOnGMaps = (Button) findViewById(R.id.show_on_gmaps);

		downloadPdf = (Button) findViewById(R.id.download_pdf);

		selectorManager = new SelectorManager(this, datePicker);
	}

	private void bind() {
		showOnGMaps.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				selectorManager.showOnGoogleMaps();
			}
		});
		downloadPdf.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				selectorManager.showPdf();
			}
		});
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
