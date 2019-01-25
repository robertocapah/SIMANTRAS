package com.kalbe.mobiledevknlibs.PermissionChecker;

/**
 * Created by Robert on 05/01/2018.
 */

public enum enumPermissionChecker {
    CALENDAR (1),
    CAMERA (2),
    CONTACTS (3),
    LOCATION (4),
    MICROPHONE (5),
    PHONE (6),
    SENSORS (7),
    SMS (8),
    STORAGE (9)
    ;
    enumPermissionChecker(int idPermission){
        this.idPermision = idPermission;
    }
    public int getIdPermision(){return this.idPermision;}
    private final int idPermision;
}
