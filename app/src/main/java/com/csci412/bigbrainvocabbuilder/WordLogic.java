package com.csci412.bigbrainvocabbuilder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class WordLogic {
    private DatabaseManager dbManager = null;

    public WordLogic(Context context) {
        dbManager = new DatabaseManager(context);
    }

    public String[] createLearnDef() {
        String[] mainWordDef = dbManager.getRandomWord(0);
        return mainWordDef;
    }
}
