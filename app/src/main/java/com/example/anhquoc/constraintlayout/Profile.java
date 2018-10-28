package com.example.anhquoc.constraintlayout;

public class Profile {
    private String userId;
    private String displayName;
    private String avatarURL;

    public Profile(String userid, String displayname, String avatarurl) {
       this.userId = userid;
       this.displayName = displayname;
       this.avatarURL = avatarurl;
    }

    public String getProfileId() {
        return userId;
    }

    public void setProfileId(String userid) {
        this.userId = userid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAvatar() {
        return avatarURL;
    }

    public void setAvatar(String avatarurl) {
        this.avatarURL = avatarurl;
    }
}
