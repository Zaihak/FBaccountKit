package net.myanmarlink.kittest;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    private final static int REQUEST_CODE=99;
    Button btn_ph,btn_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_ph=findViewById(R.id.login_ph_btn);
        btn_email=findViewById(R.id.login_email_btn);
        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginPage(LoginType.EMAIL);
            }
        });
        btn_ph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginPage(LoginType.PHONE);
            }
        });


}

    private void startLoginPage(LoginType loginType) {
        if (loginType==LoginType.EMAIL){
            Intent intent=new Intent(this, AccountKitActivity.class);
            AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder
                    =new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.EMAIL,AccountKitActivity.ResponseType.TOKEN);
            intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,configurationBuilder.build());
            startActivityForResult(intent,REQUEST_CODE);
        }else if (loginType==LoginType.PHONE)
        {
            Intent intent=new Intent(this, AccountKitActivity.class);
            AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder
                    =new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.PHONE,AccountKitActivity.ResponseType.TOKEN);
            intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,configurationBuilder.build());
            startActivityForResult(intent,REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE){
            AccountKitLoginResult result=data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if (result.getError() !=null){
                Toast.makeText(this,""+result.getError().getErrorType().getMessage(),Toast.LENGTH_SHORT).show();
                return;
            }else if (result.wasCancelled()){
                Toast.makeText(this,"Cancel",Toast.LENGTH_LONG).show();
                return;
            }else {

                        if (result.getAccessToken() !=null){
                            Toast.makeText(this,"Success !"+result.getAccessToken().getAccountId(),Toast.LENGTH_LONG);

                        }else {
                            Toast.makeText(this,"Success !"+result.getAuthorizationCode().substring(0,10),Toast.LENGTH_LONG);

                        }

                Intent intent=new Intent(MainActivity.this,Success.class);
                startActivity(intent);
            }


        }
    }
}
