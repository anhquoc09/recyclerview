package com.example.anhquoc.constraintlayout;

import java.util.ArrayList;

public class ProfileList {
    public ArrayList<Profile> mProfileList;

    public ProfileList() {
        this.mProfileList = new ArrayList<>();
    }

    public ProfileList(ArrayList<Profile> mProfileList) {
        this.mProfileList = mProfileList;
    }

    public ArrayList<Profile> getProfileList() {
        return mProfileList;
    }

    public void setProfileList(ArrayList<Profile> mProfileList) {
        this.mProfileList = mProfileList;
    }
}
