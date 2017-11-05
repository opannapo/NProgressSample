package napodev.nprogresslib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by opannapo on 11/5/17.
 */

public class NProgressLib extends View {
    private static final String TAG = "NProgressLib";

    //KEBUTUHAN SAVED INSTANCE
    private static final String INSTANCE_STATE = "INSTANCE_STATE";
    private static final String INSTANCE_STROKE_WIDTH = "INSTANCE_STROKE_WIDTH";
    private static final String INSTANCE_TEXT = "INSTANCE_TEXT";
    private static final String INSTANCE_TEXT_SIZE = "INSTANCE_TEXT_SIZE";
    private static final String INSTANCE_TEXT_COLOR = "INSTANCE_TEXT_COLOR";
    private static final String INSTANCE_PROGRESS = "INSTANCE_PROGRESS";
    private static final String INSTANCE_MAX = "INSTANCE_MAX";
    private static final String INSTANCE_CIRCLE_BASE_COLOR = "INSTANCE_CIRCLE_BASE_COLOR";
    private static final String INSTANCE_CIRCLE_PROGRESS_COLOR = "INSTANCE_CIRCLE_PROGRESS_COLOR";
    private static final String INSTANCE_BLOCK_BACKGROUND_COLOR = "INSTANCE_BLOCK_BACKGROUND_COLOR";
    private static final String INSTANCE_ANIMATION_HAS_LOADED = "INSTANCE_ANIMATION_HAS_LOADED";
    private static final String INSTANCE_PROGRESS_VISIBLE = "INSTANCE_PROGRESS_VISIBLE";
    private static final String INSTANCE_CAP_ROUND = "INSTANCE_CAP_ROUND";

    private Paint paint;
    protected Paint textPaint;

    private RectF rectFLingkaran = new RectF();

    private float strokeWidth;
    private String text;
    private float textSize;
    private int textColor;
    private int progress = 0;
    private int max;
    private int circleProgressColor;
    private int circleBaseColor;
    private int blockBackgroundColor;

    private final int defaultCircleProgressColor = Color.WHITE;
    private final int defaultCircleBaseColor = Color.rgb(200, 200, 200);
    private final int defaultTextColor = Color.rgb(66, 145, 241);
    private final int defaultBlockBackgroundColor = Color.WHITE;
    private final float defaultStrokeWidth = UtilsPixel.dp2px(getResources(), 5);
    private final int defaultMax = 100;
    private final float defaultTextSize = UtilsPixel.dp2px(getResources(), 12);
    private final float defaultTextPadding = UtilsPixel.dp2px(getResources(), 5);

    private final int minSize = (int) UtilsPixel.dp2px(getResources(), 80);
    private boolean progressValueVisibility = false;
    private boolean progressCapRound = true;

    private float progressTmpAnimationProgress;
    private boolean animationHasLoaded = false;
    private float maxTextWitdh;
    private int textPadding;

    public NProgressLib(Context context) {
        this(context, null);
    }

    public NProgressLib(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NProgressLib(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.NProgressLib, defStyleAttr, 0);
        initByAttributes(attributes);
        attributes.recycle();

        initPainters();
    }

    protected void initByAttributes(TypedArray attributes) {
        setCircleBaseColor(attributes.getColor(R.styleable.NProgressLib_circle_base_color, defaultCircleBaseColor));
        setCircleProgressColor(attributes.getColor(R.styleable.NProgressLib_circle_progress_color, defaultCircleProgressColor));
        setTextColor(attributes.getColor(R.styleable.NProgressLib_text_color, defaultTextColor));
        setTextSize(attributes.getDimension(R.styleable.NProgressLib_text_size, defaultTextSize));
        setMax(attributes.getInt(R.styleable.NProgressLib_max, defaultMax));
        setProgress(attributes.getInt(R.styleable.NProgressLib_progress, 0));
        setStrokeWidth(attributes.getDimension(R.styleable.NProgressLib_stroke_width, defaultStrokeWidth));
        setText(attributes.getString(R.styleable.NProgressLib_text));
        setProgressValueVisibility(attributes.getBoolean(R.styleable.NProgressLib_progressValueVisibility, false));
        setProgressCapRound(attributes.getBoolean(R.styleable.NProgressLib_progressCapRound, true));
        setBlockBackgroundColor(attributes.getColor(R.styleable.NProgressLib_block_color, defaultBlockBackgroundColor));
        setTextPadding((int) attributes.getDimension(R.styleable.NProgressLib_text_padding, defaultTextPadding));
    }

    protected void initPainters() {
        //PAINT TEXT
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);

        //GENERAL PAINT
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getCircleBaseColor());
        paint.setAntiAlias(true);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(isProgressCapRound() ? Paint.Cap.ROUND : Paint.Cap.BUTT);
        paint.setAntiAlias(true);
    }

    @Override
    public void invalidate() {
        initPainters();
        super.invalidate();
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return minSize;
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return minSize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        Log.d(TAG, "onMeasure " + widthMeasureSpec + "::" + widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setMaxTextWitdh(canvas.getWidth() / 2);
        String textString;
        String[] texts = getText().split(" ");
        textString = texts[0];

        Rect bounds = findOptimalLabelSize(canvas, textString);
        int bw = bounds.width();
        int bh = bounds.height();


        float blockRight = (canvas.getWidth() / 2) + (bw / 2) + getTextPadding();
        float blockWidth = (((blockRight - (canvas.getWidth() / 2))) * 2) + getStrokeWidth();
        float blockHeight = bh + getStrokeWidth() + (getTextPadding() * 2);

        canvas.drawText(textString, (getWidth() - bw) / 2.0f,
                Math.abs(textPaint.ascent()) + Math.abs(textPaint.descent() + (getStrokeWidth() / 2) + getTextPadding()),
                textPaint);

        if (isProgressValueVisibility()) {
            String textProgress = String.valueOf(getProgress() + "%");
            Rect boundsTextCenter = new Rect();
            textPaint.setTextSize(UtilsPixel.px2dp(getResources(), (getWidth() / 2) - getTextSize()));
            textPaint.setColor(getCircleProgressColor());
            textPaint.getTextBounds(textProgress, 0, textProgress.length(), boundsTextCenter);
            int bwTextCenter = boundsTextCenter.width();
            int bhTextCenter = boundsTextCenter.height();
            canvas.drawText(textProgress, (getWidth() / 2) - (bwTextCenter / 2),
                    (getHeight() / 2) + (bhTextCenter / 2), textPaint);
        }

        float l = blockWidth;
        float r = (canvas.getWidth() / 2) - getStrokeWidth();
        float radianToDregree = (float) (180 / Math.PI);//57.2958f;
        float x = (float) (Math.asin(l / (r * 2)) * radianToDregree + (getTextPadding()));
        float startPoint = (-90) + x;
        float endPoint = 360 - (x * 2);
        rectFLingkaran.set((blockHeight / 2) + getTextPadding(),
                (blockHeight / 2) + getTextPadding(),
                (canvas.getWidth()) - (blockHeight / 2) - getTextPadding(),
                (canvas.getHeight()) - (blockHeight / 2) - getTextPadding());

        //DRAW FULL Progress for layer1
        paint.setColor(getCircleBaseColor());
        canvas.drawArc(rectFLingkaran, startPoint, endPoint, false, paint);

        //DRAW Progress for layer2 by % values
        if (!getAnimationHasLoaded()) {
            float limit = endPoint / 100;
            progressTmpAnimationProgress += limit;
            Log.d(TAG, "canvas w:h " + canvas.getWidth() + ":" + canvas.getHeight());
            Log.d(TAG, "progressTmpAnimationProgress:endPoint => " + progressTmpAnimationProgress + ":" + endPoint + " LIMIT " + limit);
            if (progressTmpAnimationProgress >= (endPoint - limit)) {
                paint.setColor(getCircleProgressColor());
                canvas.drawArc(rectFLingkaran, startPoint, progressTmpAnimationProgress * getProgress() / 100,
                        false, paint);
                setAnimationHasLoaded(true);
                return;
            } else {
                paint.setColor(getCircleProgressColor());
                canvas.drawArc(rectFLingkaran, startPoint, progressTmpAnimationProgress * getProgress() / 100,
                        false, paint);
            }
            postInvalidateDelayed((long) (1 / (progressTmpAnimationProgress - endPoint)));
        } else {
            paint.setColor(getCircleProgressColor());
            canvas.drawArc(rectFLingkaran, startPoint, endPoint * getProgress() / 100,
                    false, paint);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putFloat(INSTANCE_STROKE_WIDTH, getStrokeWidth());
        bundle.putString(INSTANCE_TEXT, getText());
        bundle.putFloat(INSTANCE_TEXT_SIZE, getTextSize());
        bundle.putInt(INSTANCE_TEXT_COLOR, getTextColor());
        bundle.putInt(INSTANCE_PROGRESS, getProgress());
        bundle.putInt(INSTANCE_MAX, getMax());
        bundle.putInt(INSTANCE_CIRCLE_BASE_COLOR, getCircleBaseColor());
        bundle.putInt(INSTANCE_CIRCLE_PROGRESS_COLOR, getCircleProgressColor());
        bundle.putInt(INSTANCE_BLOCK_BACKGROUND_COLOR, getBlockBackgroundColor());
        bundle.putBoolean(INSTANCE_ANIMATION_HAS_LOADED, getAnimationHasLoaded());
        bundle.putBoolean(INSTANCE_CAP_ROUND, isProgressCapRound());

        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            final Bundle bundle = (Bundle) state;
            setStrokeWidth(bundle.getFloat(INSTANCE_STROKE_WIDTH));
            setTextSize(bundle.getFloat(INSTANCE_TEXT_SIZE));
            setText(bundle.getString(INSTANCE_TEXT));
            setTextColor(bundle.getInt(INSTANCE_TEXT_COLOR));
            setMax(bundle.getInt(INSTANCE_MAX));
            setProgress(bundle.getInt(INSTANCE_PROGRESS));
            setCircleBaseColor(bundle.getInt(INSTANCE_CIRCLE_BASE_COLOR));
            setCircleProgressColor(bundle.getInt(INSTANCE_CIRCLE_PROGRESS_COLOR));
            setBlockBackgroundColor(bundle.getInt(INSTANCE_BLOCK_BACKGROUND_COLOR));
            setAnimationHasLoaded(bundle.getBoolean(INSTANCE_ANIMATION_HAS_LOADED));
            setProgressValueVisibility(bundle.getBoolean(INSTANCE_PROGRESS_VISIBLE));
            setProgressCapRound(bundle.getBoolean(INSTANCE_CAP_ROUND));

            initPainters();

            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        this.invalidate();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        this.invalidate();
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        if (this.progress > getMax()) {
            this.progress %= getMax();
        }
        invalidate();
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        if (max > 0) {
            this.max = max;
            invalidate();
        }
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        this.invalidate();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        this.invalidate();
    }

    public int getCircleProgressColor() {
        return circleProgressColor;
    }

    public void setCircleProgressColor(int circleProgressColor) {
        this.circleProgressColor = circleProgressColor;
        this.invalidate();
    }

    public int getCircleBaseColor() {
        return circleBaseColor;
    }

    public void setCircleBaseColor(int circleBaseColor) {
        this.circleBaseColor = circleBaseColor;
        this.invalidate();
    }

    public int getBlockBackgroundColor() {
        return blockBackgroundColor;
    }

    public void setBlockBackgroundColor(int val) {
        this.blockBackgroundColor = val;
        this.invalidate();
    }

    public boolean getAnimationHasLoaded() {
        return animationHasLoaded;
    }

    public void setAnimationHasLoaded(boolean val) {
        this.animationHasLoaded = val;
    }

    public float getMaxTextWitdh() {
        return this.maxTextWitdh;
    }

    public void setMaxTextWitdh(float maxTextWitdh) {
        this.maxTextWitdh = maxTextWitdh;
    }

    public int getTextPadding() {
        return this.textPadding;
    }

    public void setTextPadding(int textPadding) {
        this.textPadding = textPadding;
    }

    public boolean isProgressValueVisibility() {
        return progressValueVisibility;
    }

    public void setProgressValueVisibility(boolean progressValueVisibility) {
        this.progressValueVisibility = progressValueVisibility;
    }

    private Rect findOptimalLabelSize(Canvas canvas, String textString) {
        Rect bounds = new Rect();
        textPaint.setTextSize(getTextSize());
        textPaint.getTextBounds(textString, 0, textString.length(), bounds);

        if (bounds.width() > getMaxTextWitdh()) {
            setTextSize(getTextSize() - getStrokeWidth());
            textPaint.setTextSize(getTextSize());
            textPaint.getTextBounds(textString, 0, textString.length(), bounds);
        }

        Log.d(TAG, "findOptimalLabelSize " + bounds.width() + ":" + bounds.height() + " getTextSize():" + getTextSize());
        return bounds;
    }

    public boolean isProgressCapRound() {
        return progressCapRound;
    }

    public void setProgressCapRound(boolean progressCapRound) {
        this.progressCapRound = progressCapRound;
    }
}