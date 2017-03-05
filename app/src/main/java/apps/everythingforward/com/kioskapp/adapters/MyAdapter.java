package apps.everythingforward.com.kioskapp.adapters;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import apps.everythingforward.com.kioskapp.R;
import apps.everythingforward.com.kioskapp.SugarRecords.PlaceRecords;

/**
 * Created by santh on 2/19/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    //private String[] Titles = {"Card 1", "Card 2", "Card 3", "Card 4", "Card 5"};
    String name, address, phNo;
    private List<PlaceRecords> places = PlaceRecords.listAll(PlaceRecords.class);
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
       TextView placeNametv, placeAddresstv, placePhNotv;


        public ViewHolder(final View itemView) {
            super(itemView);
            placeNametv = (TextView)itemView.findViewById(R.id.place_name);
            placeAddresstv = (TextView)itemView.findViewById(R.id.place_address);
            placePhNotv = (TextView)itemView.findViewById(R.id.place_phone_no);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int positon = getAdapterPosition();
                    Snackbar.make(view, "Item clicked at position " + positon, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int positon = getAdapterPosition();

                    PlaceRecords placeRecords = PlaceRecords.findById(PlaceRecords.class, positon+1);
                    placeRecords.delete();
                    Snackbar.make(view, "Item deleted at position " + positon, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return true;
                }
            });
        }
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int i) {
        PlaceRecords placeRecords = places.get(i);
        name = placeRecords.getPlaceName();
        address = placeRecords.getPlaceAddress();
        phNo = placeRecords.getPlacePhoneNumber();
        holder.placeNametv.setText(name);
        holder.placeAddresstv.setText(address);
        holder.placePhNotv.setText(phNo);

    }

    @Override
    public int getItemCount() {
        return places.size();
    }
}
