package com.gmail.previati.edgardo.currencyconverter.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.gmail.previati.edgardo.currencyconverter.Const;
import com.gmail.previati.edgardo.currencyconverter.R;
import com.gmail.previati.edgardo.currencyconverter.adapter.CurrencyAdapter;
import com.gmail.previati.edgardo.currencyconverter.data.Currencies;
import com.gmail.previati.edgardo.currencyconverter.data.Currency;
import com.gmail.previati.edgardo.currencyconverter.helper.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Updated by Edgardo on 26/09/2018.
 */
public class PreferenceActivity extends BaseActivity {

    private CheckBox mCheckBoxAutoUpdate;
    private RadioGroup mRadioGroupTimeAutoUpdate;
    private List<RadioButton> mRadioButtons;

    private Spinner mSpinnerDefaultCurrencyFrom;
    private Spinner mSpinnerDefaultCurrencyTo;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        initComponents();
        initAutoUpdateComponents();
        initSpinnerAction();
    }

    protected void initComponents() {
        initActionBar();
        enableSetDisplayHomeAsUpEnabled();

        mSpinnerDefaultCurrencyFrom = (Spinner) findViewById(R.id.spinner_default_currency_from);
        mSpinnerDefaultCurrencyTo = (Spinner) findViewById(R.id.spinner_default_currency_to);

        mCheckBoxAutoUpdate = (CheckBox) findViewById(R.id.checkbox_auto_update);
        mRadioGroupTimeAutoUpdate = (RadioGroup) findViewById(R.id.radiogroup_auto_update);
        mRadioButtons = new ArrayList<RadioButton>();
        for (int i = 0; i < mRadioGroupTimeAutoUpdate.getChildCount(); i++) {
            View radioButton = mRadioGroupTimeAutoUpdate.getChildAt(i);
            if (radioButton instanceof RadioButton) {
                mRadioButtons.add((RadioButton) radioButton);
            }
        }
    }

    private void initAutoUpdateComponents() {
        boolean autoUpdate = Util.getPreferenceValue(
                mContext,
                Const.KEY_PREFERENCE_REQUEST_OF_AUTO_UPDATE,
                Boolean.class,
                true);

        mCheckBoxAutoUpdate.setChecked(autoUpdate);
        for (RadioButton radioButton : mRadioButtons) {
            radioButton.setEnabled(autoUpdate);
            radioButton.setClickable(autoUpdate);
        }

        int minutesSaved = Util.getPreferenceValue(
                mContext,
                Const.KEY_PREFERENCE_MINUTES_TO_UPDATE,
                Integer.class,
                Const.MINUTES_TO_UPDATE[0]);

        int i = 0;
        for (RadioButton radioButton : mRadioButtons) {
            int minutesToUpdate;
            try {
                minutesToUpdate = Const.MINUTES_TO_UPDATE[i];
            } catch (Exception e) {
                minutesToUpdate = Const.MINUTES_TO_UPDATE[0];
            }

            radioButton.setText(Util.concat(
                    String.valueOf(Util.getTimeByMinutes(minutesToUpdate)),
                    Const.SPACE,
                    Util.getTimeDescriptionByMinutes(mContext, minutesToUpdate)));

            if (Const.MINUTES_TO_UPDATE[i] == minutesSaved) {
                radioButton.setChecked(true);
            }

            i++;
        }

        mCheckBoxAutoUpdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (RadioButton radioButton : mRadioButtons) {
                    radioButton.setEnabled(isChecked);
                    radioButton.setClickable(isChecked);
                }

                Util.savePreferenceValue(
                        mContext,
                        Const.KEY_PREFERENCE_REQUEST_OF_AUTO_UPDATE,
                        isChecked);
            }
        });

        mRadioGroupTimeAutoUpdate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int i = 0;
                for (RadioButton radioButton : mRadioButtons) {
                    if (radioButton.getId() == checkedId) {
                        int minToUpdate;
                        try {
                            minToUpdate = Const.MINUTES_TO_UPDATE[i];
                        } catch (Exception e) {
                            minToUpdate = Const.MINUTES_TO_UPDATE[0];
                        }

                        Util.savePreferenceValue(
                                mContext,
                                Const.KEY_PREFERENCE_MINUTES_TO_UPDATE,
                                minToUpdate);

                        break;
                    }

                    i++;
                }
            }
        });
    }

    private void initSpinnerAction() {
        Currencies currencies = new Currencies();
        currencies.addInHead(new Currency(
                Util.getStringFromResource(
                        mContext,
                        R.string.spinner_currency_default_value),
                Util.getStringFromResource(
                        mContext,
                        R.string.spinner_currency_default_value_description)));

        mSpinnerDefaultCurrencyFrom.setAdapter(new CurrencyAdapter(
                mContext,
                currencies,
                Util.getStringFromResource(mContext, R.string.label_spinner_default_currency_from)));

        mSpinnerDefaultCurrencyTo.setAdapter(new CurrencyAdapter(
                mContext,
                currencies,
                Util.getStringFromResource(mContext, R.string.label_spinner_default_currency_to)));

        String savedPreferenceCurrencyFrom = Util.getSavedCurrencyFrom(mContext);
        String savedPreferenceCurrencyTo = Util.getSavedCurrencyTo(mContext);

        if (savedPreferenceCurrencyFrom != null
                && !savedPreferenceCurrencyFrom.equals(Const.EMPTY)) {
            mSpinnerDefaultCurrencyFrom.setSelection(
                    currencies.getIndex(savedPreferenceCurrencyFrom));
        }
        if (savedPreferenceCurrencyTo != null
                && !savedPreferenceCurrencyTo.equals(Const.EMPTY)) {
            mSpinnerDefaultCurrencyTo.setSelection(
                    currencies.getIndex(savedPreferenceCurrencyTo));
        }

        mSpinnerDefaultCurrencyFrom.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(final AdapterView<?> parent, final View view,
                                               final int position, final long id) {
                        String from = Const.EMPTY;
                        if (mSpinnerDefaultCurrencyFrom.getSelectedItemPosition() != 0) {
                            from = ((Currency) mSpinnerDefaultCurrencyFrom.getSelectedItem())
                                    .getCurrencyCode();
                        }

                        Util.savePreferenceValue(
                                mContext,
                                Const.KEY_PREFERENCE_DEFAULT_CURRENCY_FROM,
                                from);
                    }

                    @Override
                    public void onNothingSelected(final AdapterView<?> parent) {
                    }
                });

        mSpinnerDefaultCurrencyTo.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(final AdapterView<?> parent, final View view,
                                               final int position, final long id) {
                        String to = Const.EMPTY;
                        if (mSpinnerDefaultCurrencyTo.getSelectedItemPosition() != 0) {
                            to = ((Currency) mSpinnerDefaultCurrencyTo.getSelectedItem())
                                    .getCurrencyCode();
                        }

                        Util.savePreferenceValue(
                                mContext,
                                Const.KEY_PREFERENCE_DEFAULT_CURRENCY_TO,
                                to);
                    }

                    @Override
                    public void onNothingSelected(final AdapterView<?> parent) {
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
