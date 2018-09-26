package com.gmail.previati.edgardo.currencyconverter.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.previati.edgardo.currencyconverter.Const;
import com.gmail.previati.edgardo.currencyconverter.R;
import com.gmail.previati.edgardo.currencyconverter.action.GetExchangeQuote;
import com.gmail.previati.edgardo.currencyconverter.adapter.CurrencyAdapter;
import com.gmail.previati.edgardo.currencyconverter.asynctask.handler.GetExchangeQuoteHandler;
import com.gmail.previati.edgardo.currencyconverter.data.Conversion;
import com.gmail.previati.edgardo.currencyconverter.data.Currencies;
import com.gmail.previati.edgardo.currencyconverter.data.Currency;
import com.gmail.previati.edgardo.currencyconverter.helper.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Updated by Edgardo on 26/09/2018.
 */
public class MainActivity extends BaseActivity {

    protected static final String TAG = MainActivity.class.getName();

    private Spinner mSpinnerCurrencyFrom;
    private Spinner mSpinnerCurrencyTo;

    private ImageView mImageViewSwitch;
    private TextView mTextViewDetails;

    private EditText mEditTextAmountFrom;
    private EditText mEditTextAmountTo;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!initDisclaimer()) {
            initSpinnerAction();
            convert();
            initSwitchAction();
            initAmountFromListener();
        }
    }

    private boolean initDisclaimer() {
        boolean accepted = Util.getPreferenceValue(
                mContext,
                Const.KEY_PREFERENCE_DISCLAIMERR_ACCEPTED,
                Boolean.class,
                false);

        String acceptedAtDate = Util.getPreferenceValue(
                mContext,
                Const.KEY_PREFERENCE_DATE_DISCLAIMERR_ACCEPTED,
                String.class,
                null);

        boolean outOfDate = true;
        if (acceptedAtDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(Const.DATE_FORMAT, Locale.getDefault());
            Date updateAt = new Date();
            try {
                updateAt = dateFormat.parse(acceptedAtDate);
                updateAt = Util.addTime(
                        updateAt,
                        Calendar.HOUR,
                        Const.ONE_DAY_IN_HOURS * Const.VALIDITY_DAYS);
            } catch (ParseException e) {
                Log.wtf(TAG, e.getMessage());
            }

            outOfDate = new Date().after(updateAt);
        }

        if (!accepted || acceptedAtDate == null || outOfDate) {
            openNewActivity(DisclaimerActivity.class, false, true);
            return true;
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_action_bar_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_preferences:
                openNewActivity(PreferenceActivity.class, true, false);
                return true;

            case R.id.action_update:
                convert(true);
                return true;

            case R.id.action_info:
                try {
                    PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                    Toast.makeText(
                            mContext,
                            Util.concat(
                                    Util.getStringFromResource(mContext, R.string.app_name),
                                    Const.NEW_LINE,
                                    Util.getStringFromResource(mContext, R.string.version),
                                    Const.SPACE,
                                    packageInfo.versionName
                            ),
                            Toast.LENGTH_SHORT).show();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void initComponents() {
        initActionBar();

        mSpinnerCurrencyFrom = findViewById(R.id.spinner_currency_from);
        mSpinnerCurrencyTo = findViewById(R.id.spinner_currency_to);

        mImageViewSwitch = findViewById(R.id.image_view_switch);
        mTextViewDetails = findViewById(R.id.text_view_details);

        mEditTextAmountFrom = findViewById(R.id.edit_text_amount_source);
        mEditTextAmountTo = findViewById(R.id.edit_text_amount_destination);
    }

    private void initSpinnerAction() {
        Currencies currencies = new Currencies();

        mSpinnerCurrencyFrom.setAdapter(new CurrencyAdapter(
                mContext,
                currencies,
                Util.getStringFromResource(mContext, R.string.label_spinner_currency_from)));

        mSpinnerCurrencyTo.setAdapter(new CurrencyAdapter(
                mContext,
                currencies,
                Util.getStringFromResource(mContext, R.string.label_spinner_currency_to)));

        mSpinnerCurrencyFrom.setSelection(
                currencies.getIndex(Util.getDefaultCurrencyFrom(mContext)));
        mSpinnerCurrencyTo.setSelection(
                currencies.getIndex(Util.getDefaultCurrencyTo(mContext)));

        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, final View view,
                                       final int position, final long id) {
                String from = ((Currency) mSpinnerCurrencyFrom.getSelectedItem()).getCurrencyCode();
                String to = ((Currency) mSpinnerCurrencyTo.getSelectedItem()).getCurrencyCode();

                if (!from.equals(to)) {
                    convert();
                }
            }

            @Override
            public void onNothingSelected(final AdapterView<?> parent) {
            }
        };

        mSpinnerCurrencyFrom.setOnItemSelectedListener(listener);
        mSpinnerCurrencyTo.setOnItemSelectedListener(listener);
    }

    private void initSwitchAction() {
        mImageViewSwitch.setOnClickListener(new View.OnClickListener() {
            private static final int DELAY = 300;

            @Override
            public void onClick(View v) {
                mImageViewSwitch.setClickable(false);
                final int pos1 = mSpinnerCurrencyFrom.getSelectedItemPosition();
                final int pos2 = mSpinnerCurrencyTo.getSelectedItemPosition();

                Animator anim = AnimatorInflater.loadAnimator(
                        getApplicationContext(),
                        R.animator.flipping);
                anim.setTarget(mImageViewSwitch);
                anim.setDuration(DELAY * 2);
                anim.start();

                TransitionDrawable transition =
                        (TransitionDrawable) mSpinnerCurrencyFrom.getBackground();
                transition.startTransition(DELAY);
                transition.reverseTransition(DELAY);

                mSpinnerCurrencyFrom.setSelection(pos2);
                mSpinnerCurrencyFrom.invalidate();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        TransitionDrawable transition =
                                (TransitionDrawable) mSpinnerCurrencyTo.getBackground();
                        transition.startTransition(DELAY);
                        transition.reverseTransition(DELAY);

                        mSpinnerCurrencyTo.setSelection(pos1);
                        mSpinnerCurrencyTo.invalidate();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mImageViewSwitch.setClickable(true);
                                convert();
                            }
                        }, DELAY);
                    }
                }, DELAY);
            }
        });
    }

    private void initAmountFromListener() {
        mEditTextAmountFrom.requestFocus();
        mEditTextAmountFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String value = mEditTextAmountFrom.getText().toString();
                if (value.equals(Const.EMPTY)) {
                    mEditTextAmountTo.getText().clear();
                    return;
                }

                // TODO: replace with reg expression
                if (value.length() > 1) {
                    while (value.charAt(0) == '0') {
                        value = value.substring(1);
                        if (value.equals(Const.EMPTY)) {
                            break;
                        }
                    }
                }

                if (value.startsWith(".")) {
                    value = Util.concat("0", value);
                }

                mEditTextAmountFrom.removeTextChangedListener(this);
                mEditTextAmountFrom.setText(value);
                mEditTextAmountFrom.addTextChangedListener(this);

                mEditTextAmountFrom.setSelection(value.length());

                convert();
            }

            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count,
                                          final int after) {
            }

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before,
                                      final int count) {
            }
        });
    }

    private void convert() {
        convert(false);
    }

    private void convert(boolean forceUpdate) {
        Conversion conversion = new Conversion(
                (Currency) mSpinnerCurrencyFrom.getSelectedItem(),
                (Currency) mSpinnerCurrencyTo.getSelectedItem());

        if (forceUpdate || Util.isExchangeQuoteOutOfDate(mContext, conversion)) {
            new GetExchangeQuote(
                    new GetExchangeQuoteHandler(
                            mContext,
                            mTextViewDetails,
                            conversion.getCurrencyFrom(),
                            conversion.getCurrencyTo()
                    )).execute(conversion);

            mEditTextAmountTo.getText().clear();
            mEditTextAmountFrom.getText().clear();
            Toast.makeText(
                    mContext,
                    R.string.toast_updating,
                    Toast.LENGTH_SHORT).show();
        } else {
            String exchangeQuote = Util.getPreferenceValue(
                    mContext,
                    Util.concat(
                            conversion.getCurrencyFrom(),
                            conversion.getCurrencyTo(),
                            Const.KEY_PREFERENCE_EXCHANGE_QUOTE),
                    String.class,
                    "1");

            mEditTextAmountTo.setText(Util.convertAmount(
                    mEditTextAmountFrom.getText().toString(),
                    exchangeQuote));

            mTextViewDetails.setText(Util.concat(
                    Util.getStringFromResource(mContext, R.string.label_info_exchange_quote),
                    Const.SPACE,
                    exchangeQuote,
                    Const.NEW_LINE,
                    Const.NEW_LINE,
                    Util.getStringFromResource(mContext, R.string.label_info_updated_at),
                    Const.SPACE,
                    Util.getPreferenceValue(
                            mContext,
                            Util.concat(
                                    conversion.getCurrencyFrom(),
                                    conversion.getCurrencyTo(),
                                    Const.KEY_PREFERENCE_UPDATE_AT),
                            String.class
                    )));
        }
    }
}
