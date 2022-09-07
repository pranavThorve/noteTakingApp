package com.apps.notesapp.util;

import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.apps.notesapp.database.NoteEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SampleData {

    private static String str1 = "Namaste";
    private static String str2 = "hello";
    private static String str3 = "Hiii";

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static Date getDate(int diff){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MILLISECOND,diff);
        return  calendar.getTime();
    };


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<NoteEntity> getSampleData(){

        List<NoteEntity> list = new ArrayList<NoteEntity>();

        list.add(new NoteEntity(str1,getDate(0)));
        list.add(new NoteEntity(str2,getDate(-1)));
        list.add(new NoteEntity(str3,getDate(-2)));

        Log.d("TAG", list.toString());

        return list;
    }

}
