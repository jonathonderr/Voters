package com.vindicators.voters;

/**
 * Created by delacez on 2/16/19.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private List<RestaurantFirebase> filterList;
    private Context context;

    public RestaurantAdapter(List<RestaurantFirebase> filterModelList, Context ctx) {
        filterList = filterModelList;
        context = ctx;
    }

    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_brand_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RestaurantFirebase filterM = filterList.get(position);
        holder.brandName.setText(filterM.name);
        holder.restaurant = filterM;
        //holder.productCount.setText("" + filterM.getProductCount());
        holder.selectionState.setChecked(filterM.selected);

    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView brandName;
        public TextView productCount;
        public CheckBox selectionState;
        public RestaurantFirebase restaurant;

        public ViewHolder(View view) {
            super(view);
            brandName = (TextView) view.findViewById(R.id.brand_name);
            //productCount = (TextView) view.findViewById(R.id.product_count);
            selectionState = (CheckBox) view.findViewById(R.id.brand_select);

            //item click event listener
            view.setOnClickListener(this);

            //checkbox click event handling
            selectionState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    if (isChecked) {
                        restaurant.selected = true;
                    } else {
                        restaurant.selected = false;
                    }

                    Toast.makeText(RestaurantAdapter.this.context,
                            "Restaurant: " + restaurant.name  ,
                            Toast.LENGTH_LONG).show();

                }
            });
        }

        @Override
        public void onClick(View v) {
            TextView brandName = (TextView) v.findViewById(R.id.brand_name);
            //show more information about brand
        }
    }

    public void updateData(ArrayList<RestaurantFirebase> restaurants){
        filterList = restaurants;
        notifyDataSetChanged();
    }
}