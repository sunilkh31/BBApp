package com.sunikita.bbapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by StarLord on 2016-11-13.
 */

public class AddLogDialogFragment extends AppCompatDialogFragment {

    public interface NoticeDialogListener {
        void onDialogPositiveClick(Pair<Float, Integer> recordToBeAdded);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_add_entry_dialog, null);
        setupListeners(view);
        builder.setView(view)
                .setMessage(R.string.add_entry_message)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String reps = ((EditText) view.findViewById(R.id.repetitions)).getText().toString();
                        String weight = ((EditText) view.findViewById(R.id.weightInLbs)).getText().toString();
                        if(reps.isEmpty() && weight.isEmpty()) return;
                        Float wgLbs = weight.isEmpty()?0f:Float.parseFloat(weight);
                        Integer repetitions = reps.isEmpty()?0:Integer.parseInt(reps);

                        NoticeDialogListener listener = (NoticeDialogListener) getActivity();
                        listener.onDialogPositiveClick(new Pair<>(wgLbs, repetitions));
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        return builder.create();
    }

    private void setupListeners(View view) {
        final ImageButton rIncrementButton = (ImageButton) view.findViewById(R.id.incrementRepetitions);
        final ImageButton rDecrementButton = (ImageButton) view.findViewById(R.id.decrementRepetitions);
        final ImageButton wIncrementButton = (ImageButton) view.findViewById(R.id.incrementWeight);
        final ImageButton wDecrementButton = (ImageButton) view.findViewById(R.id.decrementWeight);
        final EditText rView = (EditText) view.findViewById(R.id.repetitions);
        final EditText wView = (EditText) view.findViewById(R.id.weightInLbs);

        rIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = rView.getText().toString();
                text = text.isEmpty()?"0":text;
                Integer currValue = Integer.parseInt(text);
                currValue++;
                rView.setText(currValue.toString());
            }
        });

        rDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = rView.getText().toString();
                text = text.isEmpty()?"0":text;
                Integer currValue = Integer.parseInt(text);
                currValue = currValue<=0?0:currValue-1;
                rView.setText(currValue.toString());
            }
        });

        wIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = wView.getText().toString();
                text = text.isEmpty()?"0":text;
                Float currValue = Float.parseFloat(text);
                currValue+= 2.5f;
                wView.setText(currValue.toString());
            }
        });

        wDecrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = wView.getText().toString();
                text = text.isEmpty()?"0":text;
                Float currValue = Float.parseFloat(text);
                currValue = currValue<=0.0f?0.0f:currValue-2.5f;
                wView.setText(currValue.toString());
            }
        });
    }
}
