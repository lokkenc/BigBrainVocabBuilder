package com.csci412.bigbrainvocabbuilder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class LearnLogic {
    private DatabaseManager dbManager = null;

    public LearnLogic(Context context) {
        dbManager = new DatabaseManager(context);
    }

    public String[] createLearnDef() {
        String[] mainWordDef = dbManager.getRandomWord(1);
        return mainWordDef;
    }
}
