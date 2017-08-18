package widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

    Paint linePaint;
    Paint textPaint;

    public ValidCodeView(Context context) {
        super(context);
    }

    public ValidCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;this.setBackgroundColor(Color.TRANSPARENT);
        initPaint();
        this.setCursorVisible(false);
        this.setSelected(false);
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxCount)});
    }

    private void initPaint() {
        linePaint = new Paint();
        linePaint.setColor(Color.GRAY);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(2);
        textPaint = new Paint();
        textPaint.setColor(Color.BLUE);
        textPaint.setTextSize(getResources().getDimension(R.dimen.text_size_normal));
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        bottomLineWidth = width / 8;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(int i = 0; i < 6; i++) {
            canvas.drawLine(
                    (i * 8 + 1) * bottomLineWidth / 6, height,
                    (i * 8 + 7) * bottomLineWidth / 6, height,
                    linePaint
            );
        }
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        for(int i = 0; i < text.length(); i++) {
            canvas.drawText(
                    text.charAt(i) + "",
                    (2 + 4 * i) * bottomLineWidth / 3, 7 * height / 8,
                    textPaint
            );
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if(null != text) {
            this.text = text.toString();
            currentTextLength = text.length();
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
