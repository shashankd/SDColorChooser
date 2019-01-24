/**
 * Created by Shashank Degloorkar on 20-Jan-2019
 */
package com.custom.color.chooser;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SDColorChooser sdColorChooser;
    SDCircleView sdCircleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        sdColorChooser = findViewById(R.id.sdColorChooser);
        sdColorChooser.setHighlightColor(Color.BLUE);

        Bitmap icon_1 = BitmapFactory.decodeResource(getResources(), R.drawable.image_dog_1);
        Bitmap icon_2 = BitmapFactory.decodeResource(getResources(), R.drawable.image_dog_2);
        Bitmap icon_3 = BitmapFactory.decodeResource(getResources(), R.drawable.image_dog_3);
        Bitmap icon_4 = BitmapFactory.decodeResource(getResources(), R.drawable.image_dog_4);
        Bitmap icon_5 = BitmapFactory.decodeResource(getResources(), R.drawable.image_dog_5);
        Bitmap icon_6 = BitmapFactory.decodeResource(getResources(), R.drawable.image_dog_6);
        Bitmap icon_7 = BitmapFactory.decodeResource(getResources(), R.drawable.image_dog_7);
        Bitmap icon_8 = BitmapFactory.decodeResource(getResources(), R.drawable.image_dog_8);

        sdCircleView = findViewById(R.id.sdCircleView);
        sdCircleView.setInnerBitmapImage(icon_1);

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

        sdColorChooser.setColorSelectionListener(new ColorSelectionListener() {
            @Override
            public void onColorSelected(int selectedIndex) {
                Drawable drawable = drawableList.get(selectedIndex);
                if (drawable instanceof ColorDrawable) {
                    ColorDrawable colorDrawable = (ColorDrawable) drawable;
                    int color = colorDrawable.getColor();
                    sdCircleView.setInnerColor(color);
                    sdCircleView.setInnerBitmapImage(null);
                } else {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                    sdCircleView.setInnerBitmapImage(bitmapDrawable.getBitmap());
                }
            }
        });

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

        int width = getResources().getDimensionPixelSize(R.dimen.circleview_width);
        int spacing = getResources().getDimensionPixelSize(R.dimen.divider_width) + getResources().getDimensionPixelSize(R.dimen.ring_width);
        width = width + spacing;

        // Choosing grid for displaying SDCircleViews
        sdColorChooser.setLayoutManager(new CustomGridLayoutManager(this, width));

        // Choosing horizontal linear layout manager for displaying SDCircleViews
        // sdColorChooser.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        sdColorChooser.setColorList(drawableList);

    }
}
