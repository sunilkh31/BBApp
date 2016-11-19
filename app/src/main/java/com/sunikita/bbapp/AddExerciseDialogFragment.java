package com.sunikita.bbapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by StarLord on 2016-11-13.
 */

public class AddExerciseDialogFragment extends AppCompatDialogFragment {

    public interface AddExerciseDialogListener {
        void onDialogPositiveClick(String exerciseName);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_add_exercise, null);
        builder.setView(view)
                .setTitle(R.string.add_exercise)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText exEditText = (EditText) view.findViewById(R.id.newExerciseName);
                        String exName = exEditText.getText().toString().trim();
                        if(!exName.isEmpty()) {
                            AddExerciseDialogListener listener = (AddExerciseDialogListener) getActivity();
                            listener.onDialogPositiveClick(exName);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        return builder.create();
    }
}
