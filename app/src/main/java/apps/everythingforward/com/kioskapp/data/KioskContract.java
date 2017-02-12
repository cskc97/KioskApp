package apps.everythingforward.com.kioskapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by admin on 2/11/2017.
 */

public final class KioskContract {

    public static final String CONTENT_AUTHORITY = "apps.everythingforward.com.kioskapp.data";
    public static final Uri BASE_CONTENT = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PLACES = "Places";

    public static class KioskEntry implements BaseColumns{
        private KioskEntry(){}

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT, PATH_PLACES);

        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String TABLE_NAME = "Places";
        public static final String COLUMN_PLACE = "Place";
        public static final String COLUMN_DESCRIPTION = "Description";
        public static final String COLUMN_ADDRESS = "Address";

    }
}
