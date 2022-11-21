package com.amazein.library.helper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.core.content.res.ResourcesCompat;

import com.iapps.library.R;


/**
 * sub class of {@link AutoCompleteTextView} that includes a clear (dismiss / close) button with
 * a OnClearListener to handle the event of clicking the button
 * @author Pasca Maulana
 *
 */
public class ClearableAutoCompleteTextViewAsDropDown extends AppCompatAutoCompleteTextView {

	boolean isClearBtnHide = true;
	private int color = Color.GRAY;
	private boolean enoughToFilter = true;
	private boolean canClear = false;

	public void setColor(int color) {
		this.color = color;
	}

	public void setCanClear(boolean canClear) {
		this.canClear = canClear;
	}

	public void setEnoughToFilter(boolean enoughToFilter) {
		this.enoughToFilter = enoughToFilter;
	}

	private OnTextListener onTextListener;
	private OnTextListener2 onTextListener2;

	public void setOnTextListener(OnTextListener onTextListener) {
		this.onTextListener = onTextListener;
	}

	public void setOnTextListener2(OnTextListener2 onTextListener2) {
		this.onTextListener2 = onTextListener2;
	}

	public interface OnTextListener {
		void ontextChange(String s);
	}

	public interface OnTextListener2 {
		void ontextChange(View view, String s);
	}

	// if not set otherwise, the default clear listener clears the text in the
	// text view
	private OnClearListener defaultClearListener = new OnClearListener() {

		@Override
		public void onClear() {
			ClearableAutoCompleteTextViewAsDropDown et = ClearableAutoCompleteTextViewAsDropDown.this;

			if(canClear)
			et.setText("");
		}
	};

	@Override
	public boolean enoughToFilter() {
		return enoughToFilter;
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction,
								  Rect previouslyFocusedRect) {
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
		try {
			if (focused) {
                performFiltering(getText(), 0);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void forceFilterEmpty(){
		performFiltering("", 0);
	}

	private OnClearListener onClearListener = defaultClearListener;

	// The image we defined for the spinner state
	public Drawable imgSpinner = ResourcesCompat.getDrawable(getResources(),
			R.drawable.abc_spinner_mtrl_am_alpha, null);


	public void setImgSpinner(int imgSpinner) {
		this.imgSpinner = ResourcesCompat.getDrawable(getResources(), imgSpinner, null);
	}

	public interface OnClearListener {
		void onClear();
	}

	/* Required methods, not used in this implementation */
	public ClearableAutoCompleteTextViewAsDropDown(Context context) {
		super(context);
		init();
	}

	/* Required methods, not used in this implementation */
	public ClearableAutoCompleteTextViewAsDropDown(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	/* Required methods, not used in this implementation */
	public ClearableAutoCompleteTextViewAsDropDown(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	void init() {

		clearState();
		showSpinner();

		// if the clear button is pressed, fire up the handler. Otherwise do nothing
		this.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				checkOriginalOntouch(v, event);
				return false;
			}
		});

		this.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() > 0) {
					clearState();
					showClearButton();


					if(onTextListener!=null){
						onTextListener.ontextChange(s.toString());
					}

					if(onTextListener2!=null){
						onTextListener2.ontextChange(ClearableAutoCompleteTextViewAsDropDown.this, s.toString());
					}

				} else {
					clearState();
					showSpinner();
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	public void checkOriginalOntouch(View v, MotionEvent event){
		ClearableAutoCompleteTextViewAsDropDown et = ClearableAutoCompleteTextViewAsDropDown.this;

		if (et.getCompoundDrawables()[2] == null)
			return;

		if (event.getAction() != MotionEvent.ACTION_UP) {
			onClearListener.onClear();
			return;
		}

		if (event.getX() > et.getWidth() - et.getPaddingRight() - imgSpinner.getIntrinsicWidth()) {
			if(canClear) {
				if (!isClearBtnHide)
					onClearListener.onClear();
			}else{
				onClearListener.onClear();
			}
		}
	}

	public void setImgClearButton(Drawable imgClearButton) {
		this.imgSpinner = imgClearButton;
	}

	public void setImgClearButton(Drawable imgClearButton, int color) {
		this.imgSpinner = imgClearButton;
		this.color = color;
	}

	public void setOnClearListener(final OnClearListener clearListener) {
		this.onClearListener = clearListener;
	}

	public void clearState() {
		isClearBtnHide = true;
		this.setCompoundDrawables(null, null, null, null);
	}

	public void showClearButton() {
		isClearBtnHide = false;
		this.setCompoundDrawablesWithIntrinsicBounds(null, null, makeCustomColor(color, imgSpinner), null);
	}

	public void showSpinner() {
		isClearBtnHide = false;
		this.setCompoundDrawablesWithIntrinsicBounds(null, null, makeCustomColor(color, imgSpinner), null);
	}

	public static Drawable makeCustomColor(int color, Drawable drble){
		drble.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
		return drble;
	}

}