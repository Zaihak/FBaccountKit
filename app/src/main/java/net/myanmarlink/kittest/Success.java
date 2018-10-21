package net.myanmarlink.kittest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;

public class Success extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        Button btn_SignOut=findViewById(R.id.btn_logout);
        btn_SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountKit.logOut();
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(Account account) {
                EditText edtUserID,edPhone,edEmail;
                edtUserID= findViewById(R.id.edt_id);
                edtUserID.setText(String.format(account.getId()));
               edEmail= findViewById(R.id.edt_email);
               if (account.getEmail()!=null){
                   edEmail.setText(String.format(account.getEmail().toString()));

               }else {
                   edEmail.setText("null");
               }

                edPhone= findViewById(R.id.edt_ph);
               if (account.getPhoneNumber()!= null)
               {edPhone.setText(String.format(account.getPhoneNumber().toString()));
               }else {
                   edPhone.setText("null");
               }

            }

            @Override
            public void onError(AccountKitError accountKitError) {

            }
        });
    }
}
