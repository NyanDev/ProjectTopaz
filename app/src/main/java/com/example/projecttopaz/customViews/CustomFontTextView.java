package com.example.projecttopaz.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.example.projecttopaz.R;

/**
 * Created by xuan- on 19/09/2017.
 */

public class CustomFontTextView extends android.support.v7.widget.AppCompatTextView{
    public CustomFontTextView(Context context) {
        super(context);
        init(null);
    }

    public CustomFontTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomFontTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        if (attrs!=null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CustomFontTextView);
            String fontName = ta.getString(R.styleable.CustomFontTextView_fontName);
            if (fontName!=null) {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" +fontName);
                setTypeface(myTypeface);
            }
            ta.recycle();
        }
    }
}
