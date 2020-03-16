package com.csci412.bigbrainvocabbuilder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class TestLogic {

    private DatabaseManager dbManager = null;

    public TestLogic(Context context) {
        dbManager = new DatabaseManager(context);
    }

    public String[] createQuestion() {
        String[] testItems = new String[4];
        String[] mainWordDef = dbManager.getRandomWord(-1);

        testItems[0] = mainWordDef[0];
        testItems[1] = mainWordDef[1];

        testItems[2] = dbManager.getRandomWord(-1)[1];
        testItems[3] = dbManager.getRandomWord(-1)[1];

        return testItems;
    }

}
