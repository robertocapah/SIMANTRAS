package com.kalbenutritionals.simantra.Database.Common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ClsTNotifikasi {
    @DatabaseField(id = true)
    private String notifId;
    @DatabaseField
    private String userId;
    @DatabaseField
    private String title;
    @DatabaseField
    private String content;
    @DatabaseField
    private String imageUrl;
    @DatabaseField
    private boolean bitSend;
    @DatabaseField
    private boolean bitRead;

    public String getNotifId() {
        return notifId;
    }

    public void setNotifId(String notifId) {
        this.notifId = notifId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isBitSend() {
        return bitSend;
    }

    public void setBitSend(boolean bitSend) {
        this.bitSend = bitSend;
    }

    public boolean isBitRead() {
        return bitRead;
    }

    public void setBitRead(boolean bitRead) {
        this.bitRead = bitRead;
    }
}
