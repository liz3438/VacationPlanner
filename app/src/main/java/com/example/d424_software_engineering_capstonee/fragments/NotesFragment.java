package com.example.d424_software_engineering_capstonee.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.d424_software_engineering_capstonee.R;

public class NotesFragment extends Fragment {
    private EditText noteEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        noteEdit = view.findViewById(R.id.edit_text_note);
        Button saveButton = view.findViewById(R.id.button_save_note);
        Button cancelButton = view.findViewById(R.id.button_cancel);
        saveButton.setOnClickListener(v->{
            String note = noteEdit.getText().toString();
            if(!note.isEmpty()){
                String title = noteEdit.getText().toString().trim();
                String content = noteEdit.getText().toString().trim();
                String noteText = "Title: " + title + "\n\n" + content;

                ClipboardManager clipboard = (ClipboardManager) requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Trip Note", noteText);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), "Note saved to clipboard!", Toast.LENGTH_SHORT).show();
                noteEdit.setText("");
            } else {
                Toast.makeText(getContext(), "Please enter a note", Toast.LENGTH_SHORT).show();
            }
        });
        cancelButton.setOnClickListener(v->{
            clearFields();
            navigateToTripList();
            if(getActivity() != null) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
            Toast.makeText(getContext(), "Note cancelled", Toast.LENGTH_LONG).show();
        });
        return view;
    }
    private void navigateToTripList(){
        if(getActivity() != null){
            getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            TripsFragment tripsFragment = new TripsFragment();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, tripsFragment)
                    .commit();
        }
    }
    private void clearFields() {
        noteEdit.setText("");
    }
}
