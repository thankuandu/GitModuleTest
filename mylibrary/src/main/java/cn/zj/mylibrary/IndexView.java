package cn.zj.mylibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * 作者： 张卓嘉  .
 * 日期： 2018/10/18
 * 版本： V1.0
 * 说明：
 */
public class IndexView extends View {

    private int GRAY = 0xFFe8e8e8;
    private int DEFAULT_TEXT_COLOR = 0xFF999999;
    private static final String[] WORDS = new String[]{
            "↑", "☆", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
            "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
            "X", "Y", "Z", "#"
    };

    private Context mContext;
    private TextView mShowTextDialog;

    private int mWordSize;
    private int mwordColor;

    private Paint mPaint;

    private int mWidth;
    private int mHeight;

    private int mCellHeight;
    private int mChoose = -1;// 选中


    public IndexView(Context context) {
        this(context, null);
    }

    public IndexView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public IndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        mWordSize = sp2px(mContext, 12);
        mwordColor = 0;
        initPaint();
    }

    public void setShowTextDialog(TextView textDialog) {
        this.mShowTextDialog = textDialog;
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(DEFAULT_TEXT_COLOR);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mWordSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        //字母的高度咯
        mCellHeight = mHeight / WORDS.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < WORDS.length; i++) {
            float xPos = mWidth / 2 - mPaint.measureText(WORDS[i]) / 2;
            float yPos = mCellHeight * i + mCellHeight;
            canvas.drawText(WORDS[i], xPos, yPos, mPaint);
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int oldC = mChoose;
        int action = event.getAction();
        float y = event.getY();
        int c = (int) (y / getHeight() * WORDS.length);

        System.out.println(action + "");
        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackgroundColor(0x00000000);
                mChoose = -1;//

                if (mShowTextDialog != null) {
                    mShowTextDialog.setVisibility(INVISIBLE);
                }
                invalidate();
                break;
            default:
                setBackgroundColor(GRAY);
                if (oldC != c) {
                    if (c >= 0 && c < WORDS.length) {
                        if (mOnTouchingLetterChangedListener != null) {
                            mOnTouchingLetterChangedListener.onTouchingLetterChanged(WORDS[c]);
                        }
                        if (mShowTextDialog != null) {
                            mShowTextDialog.setText(WORDS[c]);
                            mShowTextDialog.setVisibility(View.VISIBLE);
                        }
                    }
                    mChoose = c;
                    invalidate();
                }
                break;
        }

        return true;
    }

    private OnTouchingLetterChangedListener mOnTouchingLetterChangedListener;

    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener letterChangedListener) {
        mOnTouchingLetterChangedListener = letterChangedListener;
    }

    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String letter);
    }

    /**
     * 文字字体大小sp转换px
     *
     * @param context 上下文对象
     * @param spValue sp的值
     * @return 返回值
     */
    public int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }
}
