package apps.everythingforward.com.kioskapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by admin on 2/11/2017.
 */

public class KioskDbHelper extends SQLiteOpenHelper {

    public static  final int DATABASE_VERSION = 1;
    public static final String DATABSE_NAME = "Kiosk.db";

    public KioskDbHelper(Context context){
        super(context, DATABSE_NAME, null , DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

final String TEXT_TYPE = " TEXT";
        final String COMMA_SEP = ",";
        String CREATE_SQL_ENTRIES = " CREATE TABLE " + KioskContract.KioskEntry.TABLE_NAME + "( " +
                                    KioskContract.KioskEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
                                    COMMA_SEP + KioskContract.KioskEntry.COLUMN_PLACE + TEXT_TYPE + COMMA_SEP
                                    + KioskContract.KioskEntry.COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP
                                    + KioskContract.KioskEntry.COLUMN_ADDRESS + TEXT_TYPE + " )";
        db.execSQL(CREATE_SQL_ENTRIES);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
