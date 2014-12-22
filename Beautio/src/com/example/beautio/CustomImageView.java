package com.example.beautio;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.parse.ParseImageView;

public class CustomImageView extends ParseImageView{

	public CustomImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	Drawable drawable = getDrawable();
        if (drawable != null)
        {
            int width =  MeasureSpec.getSize(widthMeasureSpec);
            int diw = drawable.getIntrinsicWidth();
            if (diw > 0)
            {
                int height = width * drawable.getIntrinsicHeight() / diw;
                setMeasuredDimension(width, height);
            }
            else
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
        else
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    

}
