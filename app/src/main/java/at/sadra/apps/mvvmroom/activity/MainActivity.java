package at.sadra.apps.mvvmroom.activity;

import android.os.Bundle;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import at.sadra.apps.mvvmroom.R;
import at.sadra.apps.mvvmroom.app.App;
import at.sadra.apps.mvvmroom.model.Note;
import at.sadra.apps.mvvmroom.viewmodel.NoteViewModel;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNote().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                // TODO: update RecyclerView
                App.toast(MainActivity.this, App.Message.ON_CHANGED);
            }
        });
    }
}
