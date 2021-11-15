package com.example.myapplicationapp.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText,nt;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Hol soy goku");
        nt=new MutableLiveData<>();
        nt.setValue("kavs");

    }

    public LiveData<String> getText() {
        return nt;
    }
}