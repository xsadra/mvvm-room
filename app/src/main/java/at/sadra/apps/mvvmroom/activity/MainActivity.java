package at.sadra.apps.mvvmroom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import at.sadra.apps.mvvmroom.R;
import at.sadra.apps.mvvmroom.adapter.NoteAdapter;
import at.sadra.apps.mvvmroom.app.App;
import at.sadra.apps.mvvmroom.model.Note;
import at.sadra.apps.mvvmroom.viewmodel.NoteViewModel;

import static at.sadra.apps.mvvmroom.app.App.Error;
import static at.sadra.apps.mvvmroom.app.App.Message;
import static at.sadra.apps.mvvmroom.app.App.Tag.ADD_NOTE_REQUEST;
import static at.sadra.apps.mvvmroom.app.App.Tag.EDIT_NOTE_REQUEST;
import static at.sadra.apps.mvvmroom.app.App.Tag.EXTRA_DESCRIPTION;
import static at.sadra.apps.mvvmroom.app.App.Tag.EXTRA_ID;
import static at.sadra.apps.mvvmroom.app.App.Tag.EXTRA_PRIORITY;
import static at.sadra.apps.mvvmroom.app.App.Tag.EXTRA_TITLE;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(App.Value.APP_TITLE);

        initializeFab();

        initializeRecyclerView();
    }

    private void initializeFab() {
        FloatingActionButton fabAddNote = findViewById(R.id.fab_add_note);
        fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {
            App.toast(this, Error.NOTE_NOT_SAVED);
            return;
        }
        String title = data.getStringExtra(EXTRA_TITLE);
        String description = data.getStringExtra(EXTRA_DESCRIPTION);
        int priority = data.getIntExtra(EXTRA_PRIORITY, 1);
        Note note = new Note(title, description, priority);

        if (requestCode == ADD_NOTE_REQUEST) {
            noteViewModel.insert(note);
            App.toast(this, Message.NOTE_SAVED);
        }

        if (requestCode == EDIT_NOTE_REQUEST) {
            int id = data.getIntExtra(EXTRA_ID, App.Value.INVALID_ID);
            if (id == App.Value.INVALID_ID) {
                App.toast(this, Error.NOTE_CANNOT_BE_UPDATED);
                return;
            }
            note.setId(id);
            noteViewModel.update(note);
            App.toast(this, Message.NOTE_UPDATED);
        }
    }

    private void initializeRecyclerView() {
        RecyclerView noteRecyclerView = findViewById(R.id.note_recycler_view);
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteRecyclerView.setHasFixedSize(true);

        final NoteAdapter adapter = new NoteAdapter();
        noteRecyclerView.setAdapter(adapter);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNote().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.submitList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Note currentNote = adapter.getNoteAt(position);
                noteViewModel.delete(currentNote);
                App.toast(MainActivity.this, Message.NOTE_DELETED);
            }
        }).attachToRecyclerView(noteRecyclerView);

        adapter.setOnItemClickListener(new NoteAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(EXTRA_ID, note.getId());
                intent.putExtra(EXTRA_TITLE, note.getTitle());
                intent.putExtra(EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(EXTRA_PRIORITY, note.getPriority());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete_all_notes:
                noteViewModel.deleteAllNotes();
                App.toast(this, Message.ALL_NOTES_DELETED);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
