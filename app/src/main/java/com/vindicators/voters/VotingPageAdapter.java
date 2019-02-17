package com.vindicators.voters;

/**
 * Created by delacez on 2/16/19.
 */

import android.content.Context;
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

public class VotingPageAdapter extends RecyclerView.Adapter<VotingPageAdapter.ViewHolder> {

    private List<User> filterList;
    private ArrayList<User> selectedUsers = new ArrayList<>();
    private Context context;

    public VotingPageAdapter(List<User> filterModelList, Context ctx) {
        filterList = filterModelList;
        context = ctx;
    }

    @Override
    public VotingPageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_brand_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User filterM = filterList.get(position);
        holder.brandName.setText(filterM.username);
        holder.user = filterM;
        //holder.productCount.setText("" + filterM.getProductCount());
        holder.selectionState.setChecked(false);
        for(int i = 0; i < selectedUsers.size(); i++){

            if(filterM.equals(selectedUsers.get(i))){
                holder.selectionState.setChecked(true);
                Log.d("USERS", filterM.username + " : true");

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
        public User user;

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
                        user.selected = true;
                        selectedUsers.add(user);
                    } else {
                        user.selected = false;
                        selectedUsers.remove(user);
                    }

                    Toast.makeText(VotingPageAdapter.this.context,
                            "User: " + user.selected  ,
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


    public void updateData(ArrayList<User> users){
        filterList = users;
        notifyDataSetChanged();
    }
}