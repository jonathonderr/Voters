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

import java.util.List;

public class ChooseFriendsActivityAdapter extends RecyclerView.Adapter<ChooseFriendsActivityAdapter.ViewHolder> {

    private List<FilterModel> filterList;
    private Context context;

    public ChooseFriendsActivityAdapter(List<FilterModel> filterModelList
            , Context ctx) {
        filterList = filterModelList;
        context = ctx;
    }

    @Override
    public ChooseFriendsActivityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_brand_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User filterM = filterList.get(position);
        holder.brandName.setText(filterM.getName());
        holder.productCount.setText("" + filterM.getProductCount());
        if (filterM.isSelected()) {
            holder.selectionState.setChecked(true);
        } else {
            holder.selectionState.setChecked(false);
        }

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView brandName;
        public TextView productCount;
        public CheckBox selectionState;

        public ViewHolder(View view) {
            super(view);
            brandName = (TextView) view.findViewById(R.id.brand_name);
            productCount = (TextView) view.findViewById(R.id.product_count);
            selectionState = (CheckBox) view.findViewById(R.id.brand_select);

            //item click event listener
            view.setOnClickListener(this);

            //checkbox click event handling
            selectionState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    if (isChecked) {
                        Toast.makeText(ChooseFriendsActivityAdapter.this.context,
                                "selected brand is " + brandName.getText(),
                                Toast.LENGTH_LONG).show();
                    } else {

                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            TextView brndName = (TextView) v.findViewById(R.id.brand_name);
            //show more information about brand
        }
    }
}