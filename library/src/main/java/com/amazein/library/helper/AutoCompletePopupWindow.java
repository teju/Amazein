package com.amazein.library.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.amazein.library.helper.adapter.GenericDDAdapterNoFilter;
import com.amazein.library.helper.adapter.SimpleAdapter;
import com.google.android.material.textfield.TextInputLayout;
import com.iapps.library.R;


/**
 * Created by chanpyaeaung on 21/3/17.
 *
 * @Contributor chanpyaeaung
 */


public class AutoCompletePopupWindow extends PopupWindow {

    //region LAYOUT_RESOURCE Declaration
    private static final int LAYOUT_RESOURCE = R.layout.autocomplete_popup_windown;
    //endregion

    //region Constants Declaration
    public static final int SIMPLE_ADAPTER = 11001, GENERIC_DD_NOFILTER_ADAPTER = 11002, GENERIC_DD_ADAPTER = 11003,
            BANK_ADAPTER = 11004, ADDRESS_ADAPTER = 11005, MAP_ADAPTER = 11006,
            BANK_COUNTRY_ADAPTER = 11007, INVEST_AMOUNT_ADAPTER = 11008,BANK_NUMBER_ADAPTER = 11009;
    //endregion

    //region View Variables Declaration
    private View popupView;
    private View anchor;
    private Toolbar toolbar;
    private ListView lv;
    private ClearableEditText edtSearch;
    private TextInputLayout tiSearch;
    private QuickScroll quickScroll;
    //endregion

    //region Variable Declaration
    private Context context;
    private int type = SIMPLE_ADAPTER;
    private String title;
    private BaseAdapter adapter;
    private Scrollable scrollable;
    private String value;
    private ViewClickListener viewClickListener;
    private boolean haltPopup = false;
    //endregion

    //region Parameters Initialization
    public void setValue(String value) {
        this.value = value;
    }

    public void setHaltPopup(boolean haltPopup) {
        this.haltPopup = haltPopup;
    }
//endregion


    //region Constructor
    public AutoCompletePopupWindow(final Context context, String title, String value, final int type, View anchor, final BaseAdapter adapter, Scrollable scrollable, final AdapterView.OnItemClickListener listener) {
        super(context);
        this.context = context;
        this.title = title;
        this.type = type;
        this.adapter = adapter;
        this.anchor = anchor;

        this.scrollable = scrollable;

        popupView = LayoutInflater.from(context).inflate(R.layout.autocomplete_popup_windown, null);
        setContentView(popupView);

        toolbar = (Toolbar) popupView.findViewById(R.id.toolbar);
        Helper.colorizeToolbar(toolbar, context.getResources().getColor(R.color.primary_header_color), (Activity)context) ;
        toolbar.setNavigationIcon(Helper.getArrowBackButtonCompat((Activity) context));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ((TextView) popupView.findViewById(R.id.tvTitle)).setText(title);
        tiSearch = (TextInputLayout) popupView.findViewById(R.id.tiSearch);
        edtSearch = (ClearableEditText) popupView.findViewById(R.id.etSearch);
        edtSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        tiSearch.setHint(title);
        if (!TextUtils.isEmpty(value)) {
            edtSearch.setText(value);
        }
        /*if(type == MAP_ADAPTER) {
            edtSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.places_ic_search, 0, 0, 0);
        }*/
        lv = (ListView) popupView.findViewById(R.id.lv);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = "";
                if(type == SIMPLE_ADAPTER) {
                    name  = ((SimpleAdapter) adapter).getItem(position).getName();
                } else {
                    name = adapter.getItem(position).toString();
                }
                edtSearch.setText(name);
                dismiss();
                listener.onItemClick(parent, view, position, id);

            }
        });
        quickScroll = (QuickScroll) popupView.findViewById(R.id.quickScroll);
        quickScroll.init(QuickScroll.TYPE_INDICATOR_WITH_HANDLE, lv, scrollable, QuickScroll.STYLE_HOLO);
        quickScroll.setFixedSize(1);
        quickScroll.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 48);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);

        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());


        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                switch (type) {
                    case SIMPLE_ADAPTER:
                        ((SimpleAdapter) adapter).getFilter().filter(s.toString());
                        break;
                    case GENERIC_DD_NOFILTER_ADAPTER:
                        ((GenericDDAdapterNoFilter) adapter).getFilter().filter(s.toString());
                        break;

                }
            }
        });

    }
    //endregion

    //region Main Function
    public void show() {

        if(haltPopup)return;

        if(!TextUtils.isEmpty(value)) {
            edtSearch.setText(value);
        }
        showAtLocation(anchor, Gravity.CENTER, 0, 0);
    }
    //endregion


}
