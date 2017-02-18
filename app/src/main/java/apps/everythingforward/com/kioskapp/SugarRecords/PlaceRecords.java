package apps.everythingforward.com.kioskapp.SugarRecords;

import com.orm.SugarRecord;

/**
 * Created by santh on 2/18/2017.
 */

public class PlaceRecords extends SugarRecord {

    String placeName,placePhone,placeAddress;



    public PlaceRecords()
    {

    }

    public PlaceRecords(String placeName, String placePhone, String placeAddress) {
        this.placeName = placeName;
        this.placePhone = placePhone;
        this.placeAddress = placeAddress;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }




}
