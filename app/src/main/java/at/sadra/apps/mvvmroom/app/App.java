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
        Log.e(Strings.TAG, message);
    }

    public static class Strings {
        public static final String TAG = "NOTE";
    }

    public static class Message {
        public static final String ON_CHANGED = "onChanged";
    }

    public static class Error {
        public static final String INVALID_INPUT = "invalid Input!";
    }
}
