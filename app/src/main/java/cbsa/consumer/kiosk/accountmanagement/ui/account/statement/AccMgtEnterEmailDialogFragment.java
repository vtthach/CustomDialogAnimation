package cbsa.consumer.kiosk.accountmanagement.ui.account.statement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
    TextView btnSubmit;
    @Bind(R.id.rowRecipientInProfile)
    RelativeLayout rowRecipientInProfile;
    @Bind(R.id.rowRecipientCustom1)
    RelativeLayout rowRecipientCustom1;
    @Bind(R.id.rowRecipientCustom2)
    RelativeLayout rowRecipientCustom2;
    @Bind(R.id.tvHeading)
    TextView tvHeading;

    AccountStatementInfo accountStatementInfo;
    IDialogListener dialogListener;

    public void setDialogListener(IDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

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
        generateData(fragment, accountStatementInfo);
        return fragment;
    }

    protected static void generateData(Fragment fragment, AccountStatementInfo accountStatementInfo) {
        Bundle data = new Bundle();
        data.putParcelable(STATEMENT_INFO, Parcels.wrap(accountStatementInfo));
        fragment.setArguments(data);
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

    protected void setUpView(AccountStatementInfo accountStatementInfo) {
        setUpHeading();
        setUpTitle(accountStatementInfo.statementType);
        setUpRowViews(accountStatementInfo);
    }

    protected void setUpHeading() {
        if (accountStatementInfo.isConfirmEmailInfo) {
            tvHeading.setText(R.string.account_statement_confirm_heading);
        } else {
            tvHeading.setText(R.string.account_statement_enter_email_heading);
        }
    }

    private void setUpRowViews(AccountStatementInfo accountStatementInfo) {
        // Setup email in profile
        if (!TextUtils.isEmpty(accountStatementInfo.emailInProfile)) {
            // Show profile email
            rowRecipientInProfile.setVisibility(View.VISIBLE);
            edEmailInProfile.setEnabled(false);
            edEmailInProfile.setText(accountStatementInfo.emailInProfile);
            edEmailInProfile.setHint(accountStatementInfo.emailInProfile);
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
        checkShowHideButtonAdd();
    }

    protected void checkShowHideButtonAdd() {
        boolean isNeedToShow = false;
        if (!isViewVisible(rowRecipientCustom1)) {
            isNeedToShow = true;
        }
        if (!isViewVisible(rowRecipientCustom2) && !isViewVisible(rowRecipientInProfile)) {
            isNeedToShow = true;
        }
        btnAddRecipient.setVisibility(isNeedToShow ? View.VISIBLE : View.GONE);
    }

    private void setUpRowCustom(String emailCustom, EditText edEmail, View rowRecipient) {
        if (!TextUtils.isEmpty(emailCustom)) {
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
            boolean isEmptyInput = isEmptyInputEmailInProfile();
            if (isValidEmail(edEmailInProfile.getText().toString()) || isEmptyInput) {
                btnChange.setText(getString(R.string.account_statement_enter_email_change));
                edEmailInProfile.setEnabled(false);
                if (isEmptyInput) {
                    edEmailInProfile.setText(accountStatementInfo.emailInProfile);
                }
            } else {
                showErrorMessage(edEmailInProfile);
            }
        } else {
            btnChange.setText(getString(R.string.account_statement_enter_email_ok_to_change));
            edEmailInProfile.setEnabled(true);
        }
    }

    private void showErrorMessage(EditText view) {
        // TODO 1 show error message with custom toast here
        view.requestFocus();
        view.setError(getString(R.string.account_statement_email_invalid));
    }

    private boolean isEmptyInputEmailInProfile() {
        return edEmailInProfile.getText().length() == 0;
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    @OnClick(R.id.btnRemove1)
    void onBtnRemoveClicked() {
        edEmailCustom1.setText("");
        rowRecipientCustom1.setVisibility(View.GONE);
        checkShowHideButtonAdd();
    }

    @OnClick(R.id.btnRemove2)
    void onBtnRemoveClicked2() {
        edEmailCustom2.setText("");
        rowRecipientCustom2.setVisibility(View.GONE);
        checkShowHideButtonAdd();
    }

    @OnClick(R.id.btnAddRecipient)
    void onBtnAddRecipientClicked() {
        if (!showCustomRow(edEmailCustom1, rowRecipientCustom1)) {
            showCustomRow(edEmailCustom2, rowRecipientCustom2);
        }
        checkShowHideButtonAdd();
    }

    private boolean showCustomRow(EditText edEmailCustom1, View customRow) {
        if (isViewVisible(customRow)) {
            return false;
        } else {
            customRow.setVisibility(View.VISIBLE);
            edEmailCustom1.setEnabled(true);
            return true;
        }
    }

    public boolean isViewVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    @OnClick(R.id.btnCancel)
    void onCancelButtonClicked() {
        dismiss();
    }

    @OnClick(R.id.btnSubmit)
    void onSubmitClicked() {
        if (validateInput()) {
            dismiss();
            if (dialogListener != null) {
                dialogListener.onSubmitButtonClicked(getNewAccountStatementInfo());
            }
        }
    }

    private boolean validateInput() {
        return validateEmailFormat(rowRecipientInProfile, edEmailInProfile)
                && validateEmailFormat(rowRecipientCustom1, edEmailCustom1)
                && validateEmailFormat(rowRecipientCustom2, edEmailCustom2)
                && isNotEmptyEmailList();
    }

    private boolean isNotEmptyEmailList() {
        return isViewVisible(rowRecipientInProfile)
                || isViewVisible(rowRecipientCustom1)
                || isViewVisible(rowRecipientCustom2);
    }

    private AccountStatementInfo getNewAccountStatementInfo() {
        AccountStatementInfo accountStatementInfo = new AccountStatementInfo();
        accountStatementInfo.statementType = this.accountStatementInfo.statementType;
        accountStatementInfo.isConfirmEmailInfo = this.accountStatementInfo.isConfirmEmailInfo;
        accountStatementInfo.emailInProfile = isViewVisible(edEmailInProfile) ? edEmailInProfile.getText().toString() : null;
        accountStatementInfo.emailSaved1 = isViewVisible(edEmailCustom1) ? edEmailCustom1.getText().toString() : null;
        accountStatementInfo.emailSaved2 = isViewVisible(edEmailCustom2) ? edEmailCustom2.getText().toString() : null;
        return accountStatementInfo;
    }

    /**
     * Validate email format for visible EditText view
     *
     * @param rowRecipientCustom1
     * @param edEmailInProfile
     */
    private boolean validateEmailFormat(View rowRecipientCustom1, EditText edEmailInProfile) {
        if (rowRecipientCustom1.getVisibility() == View.VISIBLE) {
            if (!isValidEmail(edEmailInProfile.getText().toString())) {
                showErrorMessage(edEmailInProfile);
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    protected void setUpTitle(int statementType) {
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

    public interface IDialogListener {
        void onSubmitButtonClicked(AccountStatementInfo accountStatementInfo);
    }
}
