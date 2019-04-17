package at.sadra.apps.mvvmroom.activity;

import android.os.Bundle;

import java.util.List;

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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState );
        setContentView(R.layout.activity_main);
        initialize();

    }

    private void initialize() {

        RecyclerView noteRecyclerView = findViewById(R.id.note_recycler_view);
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteRecyclerView.setHasFixedSize(true);

        final NoteAdapter adapter = new NoteAdapter();
        noteRecyclerView.setAdapter(adapter);

        NoteViewModel noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNote().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setNotes(notes);
            }
        });
    }
}
