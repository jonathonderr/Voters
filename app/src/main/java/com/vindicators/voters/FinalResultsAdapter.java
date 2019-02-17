package com.vindicators.voters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FinalResultsAdapter extends RecyclerView.Adapter<FinalResultsAdapter.ViewHolder> {

    private List<RestaurantFirebase> filterList;
    public ArrayList<RestaurantFirebase> selectedRestaurants = new ArrayList<>();
    private Context context;

    public FinalResultsAdapter(List<RestaurantFirebase> filterModelList, Context ctx) {
        filterList = filterModelList;
        context = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

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
//            holder.selectionState.setChecked(false);
//            for(int i = 0; i < selectedUsers.size(); i++){
//
//                if(filterM.equals(selectedUsers.get(i))){
//                    holder.selectionState.setChecked(true);
//                    Log.d("USERS", filterM.username + " : true");
//
//                }
//
//            }

    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView brandName;
        public TextView brandCount;
        public CheckBox selectionState;
        public RestaurantFirebase restaurant;

        public ViewHolder(View view) {
            super(view);
            brandName = (TextView) view.findViewById(R.id.brand_name);
            brandCount = (TextView) view.findViewById(R.id.brand_count);

            //item click event listener
            //view.setOnClickListener(this);

            //checkbox click event handling
//            selectionState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView,
//                                             boolean isChecked) {
//                    if (isChecked) {
//                        user.selected = true;
//                        selectedUsers.add(user);
//                    } else {
//                        user.selected = false;
//                        selectedUsers.remove(user);
//                    }
//
//                    Toast.makeText(CreateGroupAdapter.this.context,
//                            "User: " + user.selected  ,
//                            Toast.LENGTH_LONG).show();
//
//                }
//            });

        }
//        @Override
//        public void onClick(View v) {
//            TextView brandName = (TextView) v.findViewById(R.id.brand_name);
//            //show more information about brand
//        }
    }


    public void updateData(ArrayList<RestaurantFirebase> restaurants){
        filterList = restaurants;
        notifyDataSetChanged();
    }
}
