/**
 * Created by Shashank Degloorkar on 20-Jan-2019
 */
package com.custom.color.chooser.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.custom.color.chooser.ColorSelectionListener;
import com.custom.color.chooser.R;
import com.custom.color.chooser.SDCircleView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = RecyclerAdapter.class.getSimpleName();
    private List<Drawable> drawablesList;
    private Context context;
    private LayoutInflater layoutInflater;
    private int selectedIndex;
    private int highlightColor;
    private ColorSelectionListener colorSelectionListener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        SDCircleView sdCircleView;

        public ViewHolder(View view) {
            super(view);
            sdCircleView = view.findViewById(R.id.sdCircleView);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerAdapter(Context context, List<Drawable> drawablesList) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.drawablesList = drawablesList;
    }

    private View inflateNewView(int viewType) {
        return layoutInflater.inflate(R.layout.color_item, null, false);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflateNewView(viewType);
        Log.i(TAG, "onCreateViewHolder");
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Drawable drawable = drawablesList.get(position);
        final ViewHolder viewHolder = (ViewHolder) holder;
        Log.i(TAG, "onBindViewHolder " + position);

        if (drawable instanceof ColorDrawable) {
            ColorDrawable colorDrawable = (ColorDrawable) drawable;
            int color = colorDrawable.getColor();
            viewHolder.sdCircleView.setInnerColor(color);
            viewHolder.sdCircleView.setInnerBitmapImage(null);
        } else {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            viewHolder.sdCircleView.setInnerBitmapImage(bitmapDrawable.getBitmap());
        }

        viewHolder.sdCircleView.showBorder((selectedIndex == position));
        viewHolder.sdCircleView.setBorderColor(highlightColor);
        viewHolder.sdCircleView.setTag(position);
        viewHolder.sdCircleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (colorSelectionListener != null) {
                    selectedIndex = (int) v.getTag();
                    colorSelectionListener.onColorSelected(selectedIndex);
                }
                notifyDataSetChanged();
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return drawablesList.size();
    }

    public ColorSelectionListener getColorSelectionListener() {
        return colorSelectionListener;
    }

    public void setColorSelectionListener(ColorSelectionListener colorSelectionListener) {
        this.colorSelectionListener = colorSelectionListener;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public int getHighlightColor() {
        return highlightColor;
    }

    public void setHighlightColor(int highlightColor) {
        this.highlightColor = highlightColor;
    }


}