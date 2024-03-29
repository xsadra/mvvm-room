package at.sadra.apps.mvvmroom.app;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import static android.widget.Toast.makeText;

public class App {

    public static void toast(Context context, String message) {
        makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void log(String message) {
        Log.e(Tag.LOG_TAG, message);
    }

    public static class Value {
        public static final int PRIORITY_DEFAULT = 1;
        public static final int INVALID_ID = -1;
        public static final String ADD_NOTE_TITLE = "Add Note";
        public static final String EDIT_NOTE_TITLE = "Edit Note";
        public static final String APP_TITLE = "Note";
    }

    public static class Tag {
        public static final String LOG_TAG = "NOTE: ";
        public static final int ADD_NOTE_REQUEST = 1;
        public static final int EDIT_NOTE_REQUEST = 2;

        public static final String EXTRA_ID = "at.sadra.apps.mvvmroom.activity.EXTRA_ID";
        public static final String EXTRA_TITLE = "at.sadra.apps.mvvmroom.activity.EXTRA_TITLE";
        public static final String EXTRA_DESCRIPTION = "at.sadra.apps.mvvmroom.activity.EXTRA_DESCRIPTION";
        public static final String EXTRA_PRIORITY = "at.sadra.apps.mvvmroom.activity.EXTRA_PRIORITY";

    }

    public static class Message {
        public static final String ON_CHANGED = "onChanged";
        public static final String NOTE_SAVED = "Note saved!";
        public static final String NOTE_DELETED = "Note Deleted!";
        public static final String ALL_NOTES_DELETED = "All Notes Deleted!";
        public static final String NOTE_UPDATED = "Note Updated";
    }
    public static class Error {
        public static final String INSERT_TITLE_DESCRIPTION = "Insert a title and a description!";
        public static final String INVALID_INPUT = "invalid Input!";
        public static final String NOTE_NOT_SAVED = "Note not Saved!";
        public static final String NOTE_CANNOT_BE_UPDATED = "Note can't be Updated!";
    }
}
