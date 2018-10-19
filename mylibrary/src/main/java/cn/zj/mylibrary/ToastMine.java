package cn.zj.mylibrary;

import android.content.Context;
import android.content.MutableContextWrapper;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.NinePatchDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 作者： 张卓嘉  .
 * 日期： 2018/10/19
 * 版本： V1.0
 * 说明：
 */
public class ToastMine {

    public static Toast mToast;
    private static View toastLayout;
    private static ImageView toastIcon;
    private static TextView toastTextView;

    public static void show(Context context, String msg) {
        if (toastLayout == null) {
            toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(R.layout.toast_layout, null);
            toastIcon = (ImageView) toastLayout.findViewById(R.id.toast_icon);
            toastTextView = (TextView) toastLayout.findViewById(R.id.toast_text);
            toastIcon.setVisibility(View.GONE);
            NinePatchDrawable toastDrawable = (NinePatchDrawable) context.getResources().getDrawable(R.drawable.toast_frame);
            toastDrawable.setColorFilter(context.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
            toastLayout.setBackground(toastDrawable);
            toastTextView.setTextColor(context.getResources().getColor(R.color.white));
            toastTextView.setText(msg);
            toastTextView.setTypeface(Typeface.create("sans-serif-condensed", Typeface.NORMAL));
            toastTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        }
        if (mToast == null) {
            mToast = new Toast(context);
        }
        toastTextView.setText(msg);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setView(toastLayout);
        mToast.show();
    }


}
