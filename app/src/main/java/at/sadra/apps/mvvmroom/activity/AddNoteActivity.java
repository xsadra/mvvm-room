package at.sadra.apps.mvvmroom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import at.sadra.apps.mvvmroom.R;
import at.sadra.apps.mvvmroom.app.App;

import static at.sadra.apps.mvvmroom.app.App.Tag.*;

public class AddNoteActivity extends AppCompatActivity {

    private TextView addNoteTitle;
    private TextView addNoteDescription;
    private NumberPicker addNotePriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        initialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String title = addNoteTitle.getText().toString();
        String description = addNoteDescription.getText().toString();
        int priority = addNotePriority.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()){
            App.toast(AddNoteActivity.this,"Insert a title and a description!");
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_PRIORITY, priority);

        setResult(RESULT_OK, data);
        finish();
    }

    private void initialize() {
        addNoteTitle = findViewById(R.id.add_note_title);
        addNoteDescription = findViewById(R.id.add_note_description);
        addNotePriority = findViewById(R.id.add_note_priority);

        addNotePriority.setMinValue(1);
        addNotePriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle(App.Strings.ADD_NOTE_TITLE);
    }
}
