package com.example.anhquoc.constraintlayout;

public class Profile {
    private String userId;
    private String avatar;
    private String displayName;

    public Profile(String userId, String avatar, String displayName) {
        this.userId = userId;
        this.avatar = avatar;
        this.displayName = displayName;
    }

    public String getUserId ()
    {
        return userId;
    }

    public void setUserId (String userId)
    {
        this.userId = userId;
    }

    public String getAvatar ()
    {
        return avatar;
    }

    public void setAvatar (String avatar)
    {
        this.avatar = avatar;
    }

    public String getDisplayName ()
    {
        return displayName;
    }

    public void setDisplayName (String displayName)
    {
        this.displayName = displayName;
    }

    @Override
    public String toString()
    {
        return "Profile [userId = "+userId+", avatar = "+avatar+", displayName = "+displayName+"]";
    }
}
