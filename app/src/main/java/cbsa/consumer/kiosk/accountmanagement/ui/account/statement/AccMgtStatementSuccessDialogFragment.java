package cbsa.consumer.kiosk.accountmanagement.ui.account.statement;

import android.view.View;

import butterknife.Bind;
import consumer.kiosk.R;

public class AccMgtStatementSuccessDialogFragment extends AccMgtEnterEmailDialogFragment {
    @Bind(R.id.imgSuccess)
    View imgSuccess;
    @Bind(R.id.btnRemove2)
    View btnRemove2;

    public static AccMgtStatementSuccessDialogFragment newInstance(AccountStatementInfo accountStatementInfo) {
        AccMgtStatementSuccessDialogFragment fragment = new AccMgtStatementSuccessDialogFragment();
        generateData(fragment, accountStatementInfo);
        return fragment;
    }

    @Override
    protected void setUpHeading() {
        tvHeading.setText(R.string.account_statement_success);
    }

    @Override
    protected void checkShowHideButtonAdd() {
        btnAddRecipient.setVisibility(View.GONE);
    }

    @Override
    protected void setUpView(AccountStatementInfo accountStatementInfo) {
        super.setUpView(accountStatementInfo);
        // TODO 3 update drawable icon success
        imgSuccess.setVisibility(View.VISIBLE);
        btnCancel.setVisibility(View.INVISIBLE);
        btnSubmit.setText(R.string.account_statement_done);
        btnChange.setVisibility(View.GONE);
        btnRemove1.setVisibility(View.GONE);
        btnRemove2.setVisibility(View.GONE);
    }

}
