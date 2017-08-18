package jonbyn.com.jonbyncustomview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.util.AttributeSet;

import jonbyn.com.jonbyncustomview.R;

/**
 * Created by Jonbyn on 2017/8/17.
 */

public class ValidCodeView extends AppCompatEditText {

    private Context mContext;

    private int width;
    private int height;

    private int bottomLineWidth;
    private int maxCount = 6;
    private String text = "";

    private int lineColor;
    private int textColor;
    private int dotColor;

    private int type;
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_PSW = 1;
    private float textSize;

    Paint linePaint;
    Paint textPaint;
    Paint dotPaint;

    public ValidCodeView(Context context) {
        super(context);
    }

    public ValidCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;this.setBackgroundColor(Color.TRANSPARENT);
        initAttrs(attrs);
        initPaint();
        this.setCursorVisible(false);
        this.setSelected(false);
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxCount)});
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.ValidCodeView);
        maxCount = typedArray.getInt(R.styleable.ValidCodeView_valid_count, 6);
        lineColor = typedArray.getColor(R.styleable.ValidCodeView_line_color, ContextCompat.getColor(mContext, R.color.def_line_color));
        textColor = typedArray.getColor(R.styleable.ValidCodeView_text_color, ContextCompat.getColor(mContext, R.color.colorAccent));
        dotColor = typedArray.getColor(R.styleable.ValidCodeView_dot_color, ContextCompat.getColor(mContext, R.color.colorAccent));
        textSize = typedArray.getDimension(R.styleable.ValidCodeView_text_size, 20);
        type = typedArray.getInt(R.styleable.ValidCodeView_input_type, 0);
    }

    private void initPaint() {
        linePaint = new Paint();
        linePaint.setColor(lineColor);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(2);
        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(getResources().getDimension(R.dimen.text_size_normal));
        textPaint.setTextAlign(Paint.Align.CENTER);
        dotPaint = new Paint();
        dotPaint.setColor(dotColor);
        dotPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        bottomLineWidth = width * 3 / (maxCount * 4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(int i = 0; i < maxCount; i++) {
            canvas.drawLine(
                    (i * 8 + 1) * bottomLineWidth / 6,
                    height,
                    (i * 8 + 7) * bottomLineWidth / 6,
                    height,
                    linePaint
            );
        }
        switch(type) {
            case TYPE_NORMAL:
                drawText(canvas);
                break;
            case TYPE_PSW:
                drawDot(canvas);
                break;
        }
    }

    private void drawText(Canvas canvas) {
        for(int i = 0; i < text.length(); i++) {
            canvas.drawText(
                    text.charAt(i) + "",
                    (2 + 4 * i) * bottomLineWidth / 3,
                    7 * height / 8,
                    textPaint
            );
        }
    }

    private void drawDot(Canvas canvas) {
        for(int i = 0; i < text.length(); i++) {
            canvas.drawCircle(
                    (2 + 4 * i) * bottomLineWidth / 3,
                    5 * height / 8,
                    10,
                    dotPaint
            );
        }
    }

    public void setType(int type) {
        this.type = type;
        invalidate();
    }

    public int getMaxCount() {
        return maxCount;
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if(null != text) {
            this.text = text.toString();
            invalidate();
        }
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if(selStart == selEnd) {
            setSelection(getText().length());
        }
    }
}
