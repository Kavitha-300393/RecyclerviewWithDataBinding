package kavi.com.retrofitwitharray;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Random;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> implements Filterable {
  RawDatabinding rawDatabinding;
    public DataAdapter(ArrayList<AndroidVersion> android, Context context) {
        this.android = android;
        this.mFilteredList=android;
        this.context = context;
    }

    private ArrayList<AndroidVersion> android;
    private ArrayList<AndroidVersion> mFilteredList;

    Context context;

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        rawDatabinding=DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.row, viewGroup, false);
        rawDatabinding.setAndroidversion(rawDatabinding.getAndroidversion());
        return new ViewHolder(rawDatabinding);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        holder.rawDatabinding.tvName.setText(mFilteredList.get(position).getChangePercent24Hr());
        holder.rawDatabinding.tvPrice.setText(mFilteredList.get(position).getName());
        holder.rawDatabinding.tvApiLevel.setText("$."+mFilteredList.get(position).getSymbol());
        holder.rawDatabinding.tvVersion.setText(mFilteredList.get(position).getPriceUsd());
        holder.rawDatabinding.number.setText(String.valueOf(position+1));

        Random r = new Random();
        int red=r.nextInt(255 - 0 + 1)+0;
        int green=r.nextInt(255 - 0 + 1)+0;
        int blue=r.nextInt(255 - 0 + 1)+0;

        GradientDrawable draw = new GradientDrawable();
        draw.setShape(GradientDrawable.OVAL);
        draw.setColor(Color.rgb(red,green,blue));
        holder.rawDatabinding.tvApiLevel.setBackground(draw);

    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = android;
                } else {

                    ArrayList<AndroidVersion> filteredList = new ArrayList<>();

                    for (AndroidVersion androidVersion : android) {

                        if (androidVersion.getName().toLowerCase().contains(charString) ||androidVersion.getPriceUsd().toLowerCase().contains(charString) || androidVersion.getChangePercent24Hr().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<AndroidVersion>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RawDatabinding rawDatabinding;

        public ViewHolder(RawDatabinding itemView) {
            super(itemView.getRoot());
            this.rawDatabinding=itemView;

        }
    }
}
