package nah.prayer.anbada;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.nightonke.blurlockview.BlurLockView;
import com.nightonke.blurlockview.Password;

public class PasswordAct extends BaseAct implements BlurLockView.OnPasswordInputListener{

    private BlurLockView blurLockView;
    private ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        imageView1 = (ImageView)findViewById(R.id.image_1);

        blurLockView = (BlurLockView)findViewById(R.id.blurlockview);

        // Set the view that need to be blurred
        blurLockView.setBlurredView(imageView1);

        // Set the password
        blurLockView.setCorrectPassword(encryptedPreferences.getUtils().decryptStringValue(dbHelper.getResultPw()));

        blurLockView.setTitle(getString(R.string.click_to_show));
        blurLockView.setLeftButton("");
        blurLockView.setRightButton(getString(R.string.backspace));

        blurLockView.setType(Password.NUMBER, false);

        blurLockView.setOnPasswordInputListener(this);

    }


    @Override
    public void correct(String inputPassword) {
        //백그라운드에서 돌아온경우
        if(!getIntent().getBooleanExtra("isReturnedForground",false)){
            Intent i = new Intent(this, MainAct.class);
            startActivity(i);
        }
        finish();
        overridePendingTransition(0,0);
    }

    @Override
    public void incorrect(String inputPassword) {
        app.ToastMessage(getString(R.string.password_incorrect));
    }

    @Override
    public void input(String inputPassword) {

    }
    }
