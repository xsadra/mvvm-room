package at.sadra.apps.mvvmroom.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import at.sadra.apps.mvvmroom.model.Note;
import at.sadra.apps.mvvmroom.repository.NoteRepository;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository repository;
    private LiveData<List<Note>> allNote;

    public NoteViewModel(@NonNull Application application) {
        super(application);
    }

    public void insert(Note note){
        repository.insert(note);
    }

    public void update(Note note){
        repository.update(note);
    }

    public void delete(Note note){
        repository.delete(note);
    }

    public void deleteAllNotes(){
        repository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNote() {
        return allNote;
    }
}
