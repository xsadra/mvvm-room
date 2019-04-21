package at.sadra.apps.mvvmroom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.picker.DaysHeaderAdapter;

import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import at.sadra.apps.mvvmroom.R;
import at.sadra.apps.mvvmroom.adapter.NoteAdapter;
import at.sadra.apps.mvvmroom.app.App;
import at.sadra.apps.mvvmroom.model.Note;
import at.sadra.apps.mvvmroom.viewmodel.NoteViewModel;

import static at.sadra.apps.mvvmroom.app.App.Tag.*;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState );
        setContentView(R.layout.activity_main);
        setTitle(App.Strings.APP_TITLE);
        initialize();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != App.Tag.ADD_NOTE_REQUEST || resultCode != RESULT_OK){
            App.toast(this, App.Message.NOTE_NOT_SAVED);
            return;
        }
        String title = data.getStringExtra(EXTRA_TITLE);
        String description = data.getStringExtra(EXTRA_DESCRIPTION);
        int priority = data.getIntExtra(EXTRA_PRIORITY, 1);
        Note note = new Note(title, description, priority);
        noteViewModel.insert(note);

        App.toast(this, App.Message.NOTE_SAVED);

    }

    private void initialize() {

        FloatingActionButton fabAddNote = findViewById(R.id.fab_add_note);
        fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, App.Tag.ADD_NOTE_REQUEST);
            }
        });

        RecyclerView noteRecyclerView = findViewById(R.id.note_recycler_view);
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteRecyclerView.setHasFixedSize(true);

        final NoteAdapter adapter = new NoteAdapter();
        noteRecyclerView.setAdapter(adapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNote().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setNotes(notes);
            }
        });
    }
}
