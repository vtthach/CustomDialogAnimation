package cbsa.consumer.kiosk.accountmanagement.ui.account.statement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cbsa.consumer.kiosk.accountmanagement.application.AccMgtConstants;
import cbsa.consumer.kiosk.accountmanagement.widget.dialog.RightDialogFragment;
import consumer.kiosk.R;

public class AccMgtEnterEmailDialogFragment extends RightDialogFragment {

    private static final String STATEMENT_INFO = "STATEMENT_INFO";
    public static final String TAG = AccMgtEnterEmailDialogFragment.class.getSimpleName();

    @Bind(R.id.tvSelectedTitle)
    TextView tvSelectedTitle;
    @Bind(R.id.tvSelectSubTitle)
    TextView tvSelectSubTitle;
    @Bind(R.id.edEmailInProfile)
    EditText edEmailInProfile;
    @Bind(R.id.btnChange)
    TextView btnChange;
    @Bind(R.id.edEmailCustom1)
    EditText edEmailCustom1;
    @Bind(R.id.edEmailCustom2)
    EditText edEmailCustom2;
    @Bind(R.id.btnRemove1)
    TextView btnRemove1;
    @Bind(R.id.btnAddRecipient)
    TextView btnAddRecipient;
    @Bind(R.id.labelNotice)
    TextView labelNotice;
    @Bind(R.id.btnCancel)
    View btnCancel;
    @Bind(R.id.btnSubmit)
    View btnSubmit;
    @Bind(R.id.rowRecipientInProfile)
    RelativeLayout rowRecipientInProfile;
    @Bind(R.id.rowRecipientCustom1)
    RelativeLayout rowRecipientCustom1;
    @Bind(R.id.rowRecipientCustom2)
    RelativeLayout rowRecipientCustom2;
    @Bind(R.id.tvHeading)
    TextView tvHeading;

    AccountStatementInfo accountStatementInfo;

    @Parcel
    public static class AccountStatementInfo {
        @SerializedName("statementType")
        public int statementType;
        @SerializedName("isConfirmEmailInfo")
        public boolean isConfirmEmailInfo;
        @SerializedName("emailInProfile")
        public String emailInProfile;
        @SerializedName("emailSaved1")
        public String emailSaved1;
        @SerializedName("emailSaved2")
        public String emailSaved2;
    }

    public static AccMgtEnterEmailDialogFragment newInstance(AccountStatementInfo accountStatementInfo) {
        AccMgtEnterEmailDialogFragment fragment = new AccMgtEnterEmailDialogFragment();
        Bundle data = new Bundle();
        data.putParcelable(STATEMENT_INFO, Parcels.wrap(accountStatementInfo));
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arg = getArguments();
        if (arg != null) {
            accountStatementInfo = Parcels.unwrap(arg.getParcelable(STATEMENT_INFO));
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.account_statement_enter_email_dialog_fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpView(accountStatementInfo);
    }

    private void setUpView(AccountStatementInfo accountStatementInfo) {
        setUpHeading(accountStatementInfo.isConfirmEmailInfo);
        setUpTitle(accountStatementInfo.statementType);
        setUpExistEmail(accountStatementInfo);
    }

    private void setUpHeading(boolean isConfirm) {
        if (isConfirm) {
            tvHeading.setText(R.string.account_statement_confirm_heading);
        } else {
            tvHeading.setText(R.string.account_statement_enter_email_heading);
        }
    }

    private void setUpExistEmail(AccountStatementInfo accountStatementInfo) {
        // Setup email in profile
        if (accountStatementInfo.emailInProfile != null) {
            // Show profile email
            rowRecipientInProfile.setVisibility(View.VISIBLE);
            edEmailInProfile.setEnabled(false);
            btnChange.setText(getString(R.string.account_statement_enter_email_change));
        } else {
            // Hide profile email
            rowRecipientInProfile.setVisibility(View.GONE);
        }

        // Setup email custom 1
        setUpRowCustom(accountStatementInfo.emailSaved1, edEmailCustom1, rowRecipientCustom1);

        // Setup email custom 2
        setUpRowCustom(accountStatementInfo.emailSaved2, edEmailCustom2, rowRecipientCustom2);

        // Show hide button add
        if (accountStatementInfo.emailSaved1 == null) {
            btnAddRecipient.setVisibility(View.VISIBLE);
        } else {
            btnAddRecipient.setVisibility(View.GONE);
        }
    }

    private void setUpRowCustom(String emailCustom, EditText edEmail, View rowRecipient) {
        if (emailCustom != null) {
            edEmail.setText(emailCustom);
            edEmail.setEnabled(false);
            rowRecipient.setVisibility(View.VISIBLE);
        } else {
            edEmail.setEnabled(true);
            rowRecipient.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btnChange)
    void onBtnChangeClicked() {
        if (edEmailInProfile.isEnabled()) {
            if (isValidEmail(edEmailInProfile.getText().toString())) {
                edEmailInProfile.setEnabled(false);
                checkToShowButtonAddOtherRecipient();
            }
        } else {
            edEmailInProfile.setEnabled(true);
        }
    }

    private void checkToShowButtonAddOtherRecipient() {
        if (btnAddRecipient.getVisibility() != View.VISIBLE) {
            btnAddRecipient.setVisibility(View.VISIBLE);
        }
    }


    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    @OnClick(R.id.btnRemove1)
    void onBtnRemoveClicked() {
        edEmailCustom1.setText("");
        rowRecipientCustom1.setVisibility(View.GONE);
        btnAddRecipient.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.btnAddRecipient)
    void onBtnAddRecipientClicked() {
        edEmailCustom1.setEnabled(true);
        rowRecipientCustom1.setVisibility(View.VISIBLE);
        btnAddRecipient.setVisibility(View.GONE);
    }

    @OnClick(R.id.btnCancel)
    void onCancelButtonClicked() {
        dismiss();
    }

    private void setUpTitle(int statementType) {
        switch (statementType) {
            case AccMgtConstants.AccountStatementType.LAST2MONTH:
                setTitle(getString(R.string.account_statement_select_last_2_month)
                        , getString(R.string.account_statement_select_last_2_month_sub));
                break;
            case AccMgtConstants.AccountStatementType.LAST3MONTH:
                setTitle(getString(R.string.account_statement_select_last_3_month)
                        , getString(R.string.account_statement_select_last_3_month_sub));
                break;
            case AccMgtConstants.AccountStatementType.LAST6MONTH:
                setTitle(getString(R.string.account_statement_select_last_6_month)
                        , getString(R.string.account_statement_select_last_6_month_sub));
                break;
            default:
                setTitle(getString(R.string.account_statement_select_last_month)
                        , getString(R.string.account_statement_select_last_month_sub));
                break;
        }
    }

    private void setTitle(String title, String subTitle) {
        tvSelectedTitle.setText(title);
        tvSelectSubTitle.setText(subTitle);
    }
}
