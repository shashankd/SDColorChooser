/**
 * Created by Shashank Degloorkar on 20-Jan-2019
 */

package com.custom.color.chooser;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.custom.color.chooser.adapter.RecyclerAdapter;

import java.util.List;

public class SDColorChooser extends LinearLayout {
    private final static String TAG = SDColorChooser.class.getSimpleName();
    private Paint mPaint;
    private Paint mPaintShadow;
    private RectF mRect;
    private RectF mRectShadow;
    private int shadowWidth;
    private int pivotTriangleHeight;
    private int pivotTrianglePosition;

    private int mSquareColor;
    private int mPadding = 0;
    private ScrollView scrollView;
    private int backgroundColor;
    private int shadowColor;

    private View parentView;
    private Context context;
    private int selectedIndex = -1;
    private int highlightColor = Color.BLUE;

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private ColorSelectionListener colorSelectionListener;

    public SDColorChooser(Context context) {
        super(context);
        init(context, null);
    }

    public SDColorChooser(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SDColorChooser(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        int curWidth, curHeight, curLeft, curTop, maxHeight;

        //get the available size of child view
        final int childLeft = this.getPaddingLeft();
        final int childTop = this.getPaddingTop();
        final int childRight = this.getMeasuredWidth() - this.getPaddingRight();
        final int childBottom = this.getMeasuredHeight() - this.getPaddingBottom();
        final int childWidth = childRight - childLeft;
        final int childHeight = childBottom - childTop;

        maxHeight = 0;
        curLeft = childLeft;
        curTop = childTop;

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);

            if (child.getVisibility() == GONE)
                return;

            //Get the maximum size of the child
            child.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.AT_MOST));
            curWidth = child.getMeasuredWidth();
            curHeight = child.getMeasuredHeight();
            //wrap is reach to the end
            if (curLeft + curWidth >= childRight) {
                curLeft = childLeft;
                curTop += maxHeight;
                maxHeight = 0;
            }
            //do the layout
            child.layout(curLeft, curTop, curLeft + curWidth, curTop + curHeight);
            //store the max height
            if (maxHeight < curHeight)
                maxHeight = curHeight;
            curLeft += curWidth;
        }
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        this.context = context;

        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER_VERTICAL);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SDColorChooser);
        String backgroundColorStr = typedArray.getString(R.styleable.SDColorChooser_backgroundColor);
        backgroundColor = (backgroundColorStr == null) ? Color.parseColor("#3AD1CC") : Color.parseColor(backgroundColorStr);

        String shadowColorStr = typedArray.getString(R.styleable.SDColorChooser_shadowColor);
        shadowColor = (shadowColorStr == null) ? Color.parseColor("#3AD1CC") : Color.parseColor(shadowColorStr);

        shadowWidth = (int) typedArray.getDimension(R.styleable.SDColorChooser_shadowWidth, 10);
        pivotTriangleHeight = (int) typedArray.getDimension(R.styleable.SDColorChooser_pivotHeight, 10);

        typedArray.recycle();

        parentView = inflate(context, R.layout.view, this);

        recyclerView = parentView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        if (mLayoutManager == null) {
            mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        }

        recyclerView.setPadding(0, pivotTriangleHeight + 10, shadowWidth + 10, shadowWidth);

        recyclerView.setLayoutManager(mLayoutManager);

        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint = new Paint();
        mPaintShadow = new Paint();
        mRect = new RectF();
        mRectShadow = new RectF();

        mPaint.setColor(backgroundColor);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);

        mPaintShadow.setColor(shadowColor);
        mPaintShadow.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintShadow.setAntiAlias(true);

        // Set shadow
        mRect.left = mPadding;
        mRect.right = getWidth() - mPadding - shadowWidth;
        mRect.top = mPadding + pivotTriangleHeight;
        mRect.bottom = getHeight() - mPadding - shadowWidth;

        mRectShadow.left = mRect.left + shadowWidth;
        mRectShadow.right = getWidth();
        mRectShadow.top = mRect.top + shadowWidth;
        mRectShadow.bottom = getHeight();

        // Create triangle path
        Path trianglePath = new Path();
        trianglePath.moveTo(((mRect.right - mRect.left) / 2) - (pivotTriangleHeight / 2), pivotTriangleHeight);
        trianglePath.lineTo(((mRect.right - mRect.left) / 2), 0);
        trianglePath.lineTo(((mRect.right - mRect.left) / 2) + (pivotTriangleHeight / 2), pivotTriangleHeight);
        trianglePath.lineTo(((mRect.right - mRect.left) / 2) - (pivotTriangleHeight / 2), pivotTriangleHeight);

        canvas.drawPath(trianglePath, mPaint);
        canvas.drawRoundRect(mRectShadow, 50, 50, mPaintShadow);
        canvas.drawRoundRect(mRect, 50, 50, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setColorList(List<Drawable> drawableList) {
        recyclerAdapter = new RecyclerAdapter(context, drawableList);
        recyclerAdapter.setSelectedIndex(selectedIndex);
        recyclerAdapter.setHighlightColor(highlightColor);
        recyclerAdapter.setColorSelectionListener(new ColorSelectionListener() {
            @Override
            public void onColorSelected(int selectedIndex) {
                if (colorSelectionListener != null) {
                    colorSelectionListener.onColorSelected(selectedIndex);
                }
                SDColorChooser.this.selectedIndex = selectedIndex;
                Log.i(TAG, "selectedIndex = " + selectedIndex);
            }
        });

        recyclerView.setAdapter(recyclerAdapter);
    }

    public Drawable getSelectedColor() {
        return null;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    private boolean hideOnSelect;

    public void setHideOnSelect(boolean hideOnSelect) {
        this.hideOnSelect = hideOnSelect;
    }

    public void setHighlightColor(int color) {
        highlightColor = color;
    }

    public void setColorSelectionListener(ColorSelectionListener colorSelectionListener) {
        this.colorSelectionListener = colorSelectionListener;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return mLayoutManager;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        recyclerView.setLayoutManager(mLayoutManager);
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColorRes(int backgroundResId) {
        this.backgroundColor = ContextCompat.getColor(context, backgroundResId);
    }

    public void setShadowColor(int shadowColorId) {
        this.shadowColor = ContextCompat.getColor(context, shadowColorId);
    }

    public int getShadowWidth() {
        return shadowWidth;
    }

    public void setShadowWidth(int shadowWidth) {
        this.shadowWidth = shadowWidth;
    }

    public int getShadowColor() {
        return shadowColor;
    }

}
