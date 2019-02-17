package com.vindicators.voters;

/**
 * Created by delacez on 2/16/19.
 */

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VotingPageAdapter extends RecyclerView.Adapter<VotingPageAdapter.ViewHolder> {


    private List<RestaurantFirebase> filterList;
    public ArrayList<RestaurantFirebase> selectedUsers = new ArrayList<>();
    private Context context;

    public VotingPageAdapter(List<RestaurantFirebase> filterModelList, Context ctx) {
        filterList = filterModelList;
        context = ctx;
    }

    @Override
    public VotingPageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_brand_item, parent, false);

        VotingPageAdapter.ViewHolder viewHolder = new VotingPageAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VotingPageAdapter.ViewHolder holder, int position) {
        RestaurantFirebase filterM = filterList.get(position);
        holder.brandName.setText(filterM.name);
        holder.restaurantFirebase = filterM;
        //holder.productCount.setText("" + filterM.getProductCount());
        holder.selectionState.setChecked(false);
        for(int i = 0; i < selectedUsers.size(); i++){

            if(filterM.equals(selectedUsers.get(i))){
                holder.selectionState.setChecked(true);
                Log.d("USERS", filterM.name + " : true");

            }

        }

    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView brandName;
        public TextView productCount;
        public CheckBox selectionState;
        public RestaurantFirebase restaurantFirebase;

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

                        final FirebaseServices fHelper = new FirebaseServices();
                        fHelper.USERS_REF.child(fHelper.mAuth.getCurrentUser().getUid()).child("current").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final String vid = (String) dataSnapshot.getValue();
                                fHelper.addVotesRestaurant(vid, restaurantFirebase.id, restaurantFirebase.name, fHelper.mAuth.getCurrentUser().getUid());
                                Intent intent = new Intent(context, FinalResults.class);
                                context.startActivity(intent);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

//                        fHelper.addVotesRestaurant();

                    } else {

                    }

                }
            });
        }

        @Override
        public void onClick(View v) {
            TextView brandName = (TextView) v.findViewById(R.id.brand_name);
            //show more information about brand
            Log.d("Clicked", "Vote");
        }
    }


    public void updateData(ArrayList<RestaurantFirebase> users){
        filterList = users;
        notifyDataSetChanged();
    }
}