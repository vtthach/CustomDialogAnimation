package cbsa.consumer.kiosk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cbsa.consumer.kiosk.accountmanagement.ui.account.statement.AccMgtEnterEmailDialogFragment;
import cbsa.consumer.kiosk.accountmanagement.ui.account.statement.AccMgtStatementSuccessDialogFragment;
import consumer.kiosk.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AccMgtEnterEmailDialogFragment.AccountStatementInfo info = new AccMgtEnterEmailDialogFragment.AccountStatementInfo();
        AccMgtEnterEmailDialogFragment fragment = AccMgtEnterEmailDialogFragment.newInstance(info);
        fragment.show(getSupportFragmentManager(), AccMgtEnterEmailDialogFragment.TAG);
        fragment.setDialogListener(enterEmailCallback);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.test)
    public void test() {
        AccMgtEnterEmailDialogFragment.AccountStatementInfo info = new AccMgtEnterEmailDialogFragment.AccountStatementInfo();
        info.emailInProfile = "aloHa@gmail.com";
        AccMgtEnterEmailDialogFragment fragment = AccMgtEnterEmailDialogFragment.newInstance(info);
        fragment.show(getSupportFragmentManager(), AccMgtEnterEmailDialogFragment.TAG);
        fragment.setDialogListener(enterEmailCallback);
    }

    private AccMgtEnterEmailDialogFragment.IDialogListener enterEmailCallback = new AccMgtEnterEmailDialogFragment.IDialogListener() {
        @Override
        public void onSubmitButtonClicked(AccMgtEnterEmailDialogFragment.AccountStatementInfo accountStatementInfo) {
            if (accountStatementInfo.isConfirmEmailInfo) {
                // TODO 2 Call api here
                // OnSuccess
                AccMgtStatementSuccessDialogFragment fragment = AccMgtStatementSuccessDialogFragment.newInstance(accountStatementInfo);
                fragment.show(getSupportFragmentManager(), AccMgtStatementSuccessDialogFragment.TAG);
            } else {
                accountStatementInfo.isConfirmEmailInfo = true;
                AccMgtEnterEmailDialogFragment fragment = AccMgtEnterEmailDialogFragment.newInstance(accountStatementInfo);
                fragment.setDialogListener(enterEmailCallback);
                fragment.show(getSupportFragmentManager(), AccMgtEnterEmailDialogFragment.TAG);
            }
        }
    };
}
