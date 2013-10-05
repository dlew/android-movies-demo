package com.mobiata.moviesdemo.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;

import com.mobiata.moviesdemo.R;

public class AboutDialogFragment extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		ContextThemeWrapper context = new ContextThemeWrapper(getActivity(),
				android.R.style.Theme_Holo_Dialog);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		builder.setMessage(R.string.about_msg);
		builder.setNeutralButton(android.R.string.ok, null);

		return builder.create();
	}
}
