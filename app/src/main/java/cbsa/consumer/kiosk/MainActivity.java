package cbsa.consumer.kiosk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cbsa.consumer.kiosk.accountmanagement.ui.account.statement.AccMgtEnterEmailDialogFragment;
import consumer.kiosk.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AccMgtEnterEmailDialogFragment.AccountStatementInfo info = new AccMgtEnterEmailDialogFragment.AccountStatementInfo();
        AccMgtEnterEmailDialogFragment fragment = AccMgtEnterEmailDialogFragment.newInstance(info);
        fragment.show(getSupportFragmentManager(), AccMgtEnterEmailDialogFragment.TAG);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.test)
    public void test(){
        AccMgtEnterEmailDialogFragment.AccountStatementInfo info = new AccMgtEnterEmailDialogFragment.AccountStatementInfo();
        AccMgtEnterEmailDialogFragment fragment = AccMgtEnterEmailDialogFragment.newInstance(info);
        fragment.show(getSupportFragmentManager(), AccMgtEnterEmailDialogFragment.TAG);
    }
}
