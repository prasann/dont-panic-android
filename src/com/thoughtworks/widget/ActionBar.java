package com.thoughtworks.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.thoughtworks.R;

public class ActionBar extends RelativeLayout {

    private LayoutInflater mInflater;
    private ImageView mLogoView;
    private TextView mTitleView;
    private ProgressBar mProgress;
    private LinearLayout mActionIconContainer;

    public ActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        RelativeLayout barView = (RelativeLayout) mInflater.inflate(R.layout.actionbar, null);
        addView(barView);

        mLogoView = (ImageView) barView.findViewById(R.id.actionbar_home_logo);
        mProgress = (ProgressBar) barView.findViewById(R.id.actionbar_progress);
        mTitleView = (TextView) barView.findViewById(R.id.actionbar_title);
        mActionIconContainer = (LinearLayout) barView.findViewById(R.id.actionbar_actionIcons);
    }

    public void setHomeLogo(int resId) {
        setHomeLogo(resId, null);
    }

    public void setHomeLogo(int resId, OnClickListener onClickListener) {
        mLogoView.setImageResource(resId);
        mLogoView.setVisibility(View.VISIBLE);
        mLogoView.setOnClickListener(onClickListener);
        if (onClickListener != null) {
        }
    }

    public void setTitle(CharSequence title) {
        mTitleView.setText(title);
    }

    public void setTitle(int resid) {
        mTitleView.setText(resid);
    }

    public void addActionIcon(int iconResourceId, OnClickListener onClickListener) {
        View view = mInflater.inflate(R.layout.actionbar_icon, mActionIconContainer, false);
        ImageButton imgButton = (ImageButton) view.findViewById(R.id.actionbar_item);
        imgButton.setImageResource(iconResourceId);
        imgButton.setOnClickListener(onClickListener);

        mActionIconContainer.addView(view, mActionIconContainer.getChildCount());
    }

    public void addActionButton(String text, OnClickListener onClickListener) {
        View view = mInflater.inflate(R.layout.actionbar_button, mActionIconContainer, false);
        Button button = (Button) view.findViewById(R.id.actionbar_btn_item);
        button.setText(text);
        button.setOnClickListener(onClickListener);
        mActionIconContainer.addView(view, mActionIconContainer.getChildCount());
    }

    public boolean isProgressBarVisible() {
        return mProgress.getVisibility() == View.VISIBLE;
    }

    public void setProgressBarVisibility(int visibility) {
        mProgress.setVisibility(visibility);
    }
}

