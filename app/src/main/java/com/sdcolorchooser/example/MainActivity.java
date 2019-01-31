/**
 * Created by Shashank Degloorkar on 20-Jan-2019
 */
package com.sdcolorchooser.example;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sdcircleimageview.SDCircleImageView;
import com.sdcolorchooser.ColorSelectionListener;
import com.sdcolorchooser.CustomGridLayoutManager;
import com.sdcolorchooser.SDColorChooser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SDColorChooser sdColorChooser;
    SDCircleImageView sdCircleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        sdColorChooser = findViewById(R.id.sdColorChooser);
        sdColorChooser.setHighlightColor(Color.BLUE);

        Resources resources = getResources();
        Bitmap icon_1 = BitmapFactory.decodeResource(resources, R.drawable.image_dog_1);
        Bitmap icon_2 = BitmapFactory.decodeResource(resources, R.drawable.image_dog_2);
        Bitmap icon_3 = BitmapFactory.decodeResource(resources, R.drawable.image_dog_3);
        Bitmap icon_4 = BitmapFactory.decodeResource(resources, R.drawable.image_dog_4);
        Bitmap icon_5 = BitmapFactory.decodeResource(resources, R.drawable.image_dog_5);
        Bitmap icon_6 = BitmapFactory.decodeResource(resources, R.drawable.image_dog_6);
        Bitmap icon_7 = BitmapFactory.decodeResource(resources, R.drawable.image_dog_7);
        Bitmap icon_8 = BitmapFactory.decodeResource(resources, R.drawable.image_dog_8);

        sdCircleImageView = findViewById(R.id.sdCircleImageView);
        sdCircleImageView.setInnerBitmapImage(icon_1);

        final List<Drawable> drawableList = new ArrayList<>();
        drawableList.add(new BitmapDrawable(icon_1));
        drawableList.add(new BitmapDrawable(icon_2));
        drawableList.add(new BitmapDrawable(icon_3));
        drawableList.add(new BitmapDrawable(icon_4));
        drawableList.add(new BitmapDrawable(icon_5));
        drawableList.add(new BitmapDrawable(icon_6));
        drawableList.add(new BitmapDrawable(icon_7));
        drawableList.add(new BitmapDrawable(icon_8));
        drawableList.add(new BitmapDrawable(icon_1));
        drawableList.add(new BitmapDrawable(icon_2));
        drawableList.add(new BitmapDrawable(icon_3));
        drawableList.add(new BitmapDrawable(icon_4));
        drawableList.add(new BitmapDrawable(icon_5));
        drawableList.add(new BitmapDrawable(icon_6));
        drawableList.add(new BitmapDrawable(icon_7));
        drawableList.add(new BitmapDrawable(icon_8));
        drawableList.add(new BitmapDrawable(icon_1));
        drawableList.add(new BitmapDrawable(icon_2));
        drawableList.add(new BitmapDrawable(icon_3));
        drawableList.add(new BitmapDrawable(icon_4));
        drawableList.add(new BitmapDrawable(icon_5));
        drawableList.add(new BitmapDrawable(icon_6));
        drawableList.add(new BitmapDrawable(icon_7));
        drawableList.add(new BitmapDrawable(icon_8));

        drawableList.add(new ColorDrawable(Color.RED));
        drawableList.add(new ColorDrawable(Color.GREEN));
        drawableList.add(new ColorDrawable(Color.BLACK));
        drawableList.add(new ColorDrawable(Color.MAGENTA));
        drawableList.add(new ColorDrawable(Color.WHITE));
        drawableList.add(new ColorDrawable(Color.RED));
        drawableList.add(new ColorDrawable(Color.GREEN));
        drawableList.add(new ColorDrawable(Color.BLACK));
        drawableList.add(new ColorDrawable(Color.MAGENTA));
        drawableList.add(new ColorDrawable(Color.WHITE));
        drawableList.add(new ColorDrawable(Color.RED));
        drawableList.add(new ColorDrawable(Color.GREEN));
        drawableList.add(new ColorDrawable(Color.BLACK));
        drawableList.add(new ColorDrawable(Color.MAGENTA));
        drawableList.add(new ColorDrawable(Color.WHITE));
        drawableList.add(new ColorDrawable(Color.RED));
        drawableList.add(new ColorDrawable(Color.GREEN));
        drawableList.add(new ColorDrawable(Color.BLACK));
        drawableList.add(new ColorDrawable(Color.MAGENTA));
        drawableList.add(new ColorDrawable(Color.WHITE));
        drawableList.add(new ColorDrawable(Color.RED));
        drawableList.add(new ColorDrawable(Color.GREEN));
        drawableList.add(new ColorDrawable(Color.BLACK));
        drawableList.add(new ColorDrawable(Color.MAGENTA));
        drawableList.add(new ColorDrawable(Color.WHITE));

        int width = resources.getDimensionPixelSize(R.dimen.circleview_width);
        int spacing = resources.getDimensionPixelSize(R.dimen.divider_width) + resources.getDimensionPixelSize(R.dimen.ring_width);
        width = width + spacing;

        sdColorChooser.setColorSelectionListener(new ColorSelectionListener() {
            @Override
            public void onColorSelected(int selectedIndex) {
                Drawable drawable = drawableList.get(selectedIndex);
                if (drawable instanceof ColorDrawable) {
                    ColorDrawable colorDrawable = (ColorDrawable) drawable;
                    int color = colorDrawable.getColor();
                    sdCircleImageView.setInnerColor(color);
                    sdCircleImageView.setInnerBitmapImage(null);
                } else {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    sdCircleImageView.setInnerBitmapImage(bitmapDrawable.getBitmap());
                }
            }
        });

        // Choosing grid for displaying SDCircleImageViews
        sdColorChooser.setLayoutManager(new CustomGridLayoutManager(this, width));

        // Choosing horizontal linear layout manager for displaying SDCircleImageViews
        // sdColorChooser.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        //sdColorChooser.setDrawableList(drawableList);
    }
}
