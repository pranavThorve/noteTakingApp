package com.apps.notesapp.database;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {

    @TypeConverter
    public Long toTimeStamp(Date date){
        return date == null ?  null : date.getTime();
    }

    @TypeConverter
    public  Date toDate(Long timeStamp){
        return timeStamp == null ? null : new Date(timeStamp);
    }
}
