package com.sdcircleview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class SDCircleView extends ImageView {
    private Bitmap.Config bitmapConfig;
    private static final String TAG = SDCircleView.class.getSimpleName();
    private static final ImageView.ScaleType SCALE_TYPE = ImageView.ScaleType.CENTER_CROP;

    private Rect mRect = new Rect();
    private Rect mBorderRect = new Rect();

    private Paint paint = new Paint();
    private Paint borderPaint = new Paint();
    private Context context;
    private float innerCircleRadius;

    private int mPadding = 0;
    private int borderColor;
    private float borderWidth;
    private int innerColor;
    private Bitmap innerBitmapImage;

    private boolean showBorder;
    private AttributeSet attrs;

    private boolean initDone = false;
    private boolean setupDone = false;
    private Matrix matrix;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setup();
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        setup();
    }

    @Override
    public void setPaddingRelative(int start, int top, int end, int bottom) {
        super.setPaddingRelative(start, top, end, bottom);
        setup();
    }

    public SDCircleView(Context context) {
        super(context);
        init(context, null);
    }

    public SDCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SDCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        super.setScaleType(SCALE_TYPE);
        this.context = context;
        this.attrs = attrs;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SDCircleView);
        String borderColorStr = typedArray.getString(R.styleable.SDCircleView_borderColor);
        borderColor = (borderColorStr == null) ? Color.parseColor("#00574B") : Color.parseColor(borderColorStr);
        String innerColorStr = typedArray.getString(R.styleable.SDCircleView_innerColor);
        innerColor = (innerColorStr == null) ? Color.parseColor("#3AD1CC") : Color.parseColor(innerColorStr);
        int innerBitmapResId = typedArray.getResourceId(R.styleable.SDCircleView_innerBitmap, -1);
        if (innerBitmapResId > 0) {
            innerBitmapImage = BitmapFactory.decodeResource(context.getResources(), innerBitmapResId);
        }

        borderWidth = typedArray.getDimension(R.styleable.SDCircleView_borderWidth, 2);
        showBorder = typedArray.getBoolean(R.styleable.SDCircleView_showBorder, false);

        typedArray.recycle();

        initDone = true;
        setup();
    }

    private void setup() {
        setScaleType(ScaleType.CENTER_CROP);

        paint.setColor(innerColor);
        paint.setStyle(Paint.Style.FILL);
        mRect.left = mPadding + (int) borderWidth;
        mRect.right = getWidth() - mPadding - (int) borderWidth;
        mRect.top = mPadding + (int) borderWidth;
        mRect.bottom = getHeight() - mPadding - (int) borderWidth;

        innerCircleRadius = Math.min((mRect.height() / 2.0f), (mRect.width() / 2.0f));

        borderPaint.setColor(borderColor);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);
        borderPaint.setAntiAlias(true);
        borderPaint.setDither(true);

        mBorderRect.left = mPadding;
        mBorderRect.right = getWidth() - mPadding;
        mBorderRect.top = mPadding;
        mBorderRect.bottom = getHeight() - mPadding;

        setupDone = true;
        invalidate();
    }

    public Bitmap getBitmapClippedCircle(Bitmap bitmap, int width, int height) {
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_4444;
        }
        final Bitmap outputBitmap = Bitmap.createBitmap(width, height, bitmapConfig);

        final Path path = new Path();
        path.addCircle(
                (float) (width / 2)
                , (float) (height / 2)
                , (float) Math.min(width / 2, (height / 2))
                , Path.Direction.CCW);

        final Canvas canvas = new Canvas(outputBitmap);
        canvas.clipPath(path);

        int dimen = Math.min(width, height);
        if (!bitmap.isRecycled()) {
            bitmap = Bitmap.createScaledBitmap(bitmap, dimen, dimen, true);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setDither(true);
            canvas.drawBitmap(bitmap, 0, 0, paint);
        }

        bitmap.recycle();
        return outputBitmap;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);

        paint.setColor(innerColor);

        if (innerBitmapImage != null) {
            canvas.drawBitmap(getBitmapClippedCircle(innerBitmapImage, mRect.width(), mRect.height()), borderWidth, borderWidth, paint);
        } else {
            //paint.setColor(innerColor);
            canvas.drawCircle(mRect.centerX(), mRect.centerY(), innerCircleRadius, paint);
        }

        if (showBorder) {
            float mBorderRadius = Math.min(((mBorderRect.height() - borderWidth) / 2.0f), ((mBorderRect.height() - borderWidth) / 2.0f));
            canvas.drawCircle(mBorderRect.centerX(), mBorderRect.centerY(), mBorderRadius, borderPaint);
        }
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        invalidate();
    }

    public void setInnerColor(int innerColor) {
        this.innerColor = innerColor;
        invalidate();
    }

    public void setInnerBitmapImage(Bitmap innerBitmapImage) {
        this.innerBitmapImage = innerBitmapImage;
        invalidate();
    }

    public void showBorder(boolean show) {
        this.showBorder = show;
        invalidate();
    }

    public void setBitmapConfig(Bitmap.Config bitmapConfig) {
        this.bitmapConfig = bitmapConfig;
    }
}
