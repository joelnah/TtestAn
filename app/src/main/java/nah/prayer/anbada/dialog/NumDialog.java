package nah.prayer.anbada.dialog;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import nah.prayer.anbada.R;

public class NumDialog extends DialogFragment {
    private EditText et_phone, et_content;
    private LinearLayout linear_background;
    private TextView btn_cancel, btn_edit;

    private String phone, content, ok, cancel;
    private int phoneColor, contentColor, icon, LinearBackground;

    private int type;

    private static NumDialog numDialog;
    private NumDialogListener mNumDialogListener;
    private boolean hasTitle, hasContent, hasIcon, hasBackground, hasButtonOk, hasButtonCancel;
    private String tmp_phone, tmp_content;

    private boolean isViewChack = false;

    public static interface NumDialogListener {
        public void ok(int type, String phone, String content, int resultCode);

        public void cancel(int resultCode);
    }


    public static NumDialog getInstance(int type, NumDialogListener listener) {
        /*if (numDialog == null) {
            numDialog = new NumDialog();
        }*/
        numDialog = null;
        numDialog = new NumDialog();
        numDialog.type = type;
        numDialog.mNumDialogListener = listener;
        return numDialog;
    }
/*    public static NumDialog getInstance(int type, String db_type, NumDialogListener listener) {
        if (numDialog == null) {
            numDialog = new NumDialog();
        }
        numDialog.type = type;
        numDialog.db_type = db_type;
        numDialog.mNumDialogListener = listener;
        return numDialog;
    }*/

    public NumDialog() {}

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        setCancelable(true);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final View viewInterface = inflater.inflate(R.layout.dialog_num_edit, null);

        initView(viewInterface);
        setUpView();
        setLisenter(viewInterface);

        setEnterAnimation(viewInterface);
        return viewInterface;
    }

    private void setLisenter(final View viewInterface) {
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_phone.getText().toString().replace(" ","").equals("")){
                    return;
                }
                setExitAnimation(viewInterface);
                if (mNumDialogListener != null) {
                    tmp_phone = et_phone.getText().toString();
                    tmp_content = et_content.getText().toString();
                    mNumDialogListener.ok(type, tmp_phone, tmp_content, Activity.RESULT_OK);
                }
            }
        });
            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setExitAnimation(viewInterface);
                    if (mNumDialogListener != null) {
                        mNumDialogListener.cancel(Activity.RESULT_CANCELED);
                    }
                }
            });
    }

    private void initView(View viewInterface) {
        linear_background = (LinearLayout) viewInterface.findViewById(R.id.linear_background);
        et_phone = (EditText) viewInterface.findViewById(R.id.et_phone);
        et_content = (EditText) viewInterface.findViewById(R.id.et_content);
        btn_edit = (TextView) viewInterface.findViewById(R.id.btn_edit);
        btn_cancel = (TextView) viewInterface.findViewById(R.id.btn_cancel);
    }

    private void setUpView() {
        if (hasTitle) {
            et_phone.setText(phone);
            if(phoneColor != 0)
                et_phone.setTextColor(phoneColor);
        }
        if (hasContent) {
            et_content.setText(content);
            if(contentColor != 0)
                et_content.setTextColor(contentColor);
        }

        if (hasBackground) {
            GradientDrawable myGrad = (GradientDrawable)linear_background.getBackground();
            myGrad.setColor(LinearBackground);
        }
        if (hasButtonOk) {
            btn_edit.setText(ok);
        }
        if (hasButtonCancel) {
            btn_cancel.setText(cancel);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        isViewChack = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isViewChack = false;
    }
    public boolean isViewCheck(){
        return isViewChack;
    }

    private void setEnterAnimation(View view) {
        PropertyValuesHolder propertyValuesHolder1 = PropertyValuesHolder.ofFloat("translationY", -2000, 0);
        PropertyValuesHolder propertyValuesHolder2 = PropertyValuesHolder.ofFloat("rotation", -20, 0);
        ObjectAnimator animator1 = ObjectAnimator.
                ofPropertyValuesHolder(view, propertyValuesHolder1,
                        propertyValuesHolder2);
//        animator1.setInterpolator(new AccelerateInterpolator());
        animator1.setDuration(400);
//        animator1.start();

        PropertyValuesHolder propertyValuesHolder3 = PropertyValuesHolder.ofFloat("translationY", 0, -15);
        ObjectAnimator animator2 = ObjectAnimator.
                ofPropertyValuesHolder(view, propertyValuesHolder3);
        //   animator2.setInterpolator(new DecelerateInterpolator());
        animator2.setDuration(200);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator2).after(animator1);
        animatorSet.setInterpolator(new DecelerateInterpolator());
//        animatorSet.setDuration(800);
        animatorSet.start();
    }

    private void setExitAnimation(View view) {
        PropertyValuesHolder propertyValuesHolder1 = PropertyValuesHolder.ofFloat("translationY", 0, 2000);
        PropertyValuesHolder propertyValuesHolder2 = PropertyValuesHolder.ofFloat("rotation", 0, 10);
        ObjectAnimator animator1 = ObjectAnimator.
                ofPropertyValuesHolder(view, propertyValuesHolder1,
                        propertyValuesHolder2);
//        animator1.setInterpolator(new AccelerateInterpolator());
        animator1.setDuration(400);
//        animator1.start();

//		PropertyValuesHolder propertyValuesHolder3 = PropertyValuesHolder.ofFloat("translationY",0,-10);
//		ObjectAnimator animator2 = ObjectAnimator.
//				ofPropertyValuesHolder(view,propertyValuesHolder3);
//		//   animator2.setInterpolator(new DecelerateInterpolator());
//		animator2.setDuration(200);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator1);
//        animatorSet.setDuration(800);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        if (mNumDialogListener != null) {
            mNumDialogListener.cancel(Activity.RESULT_CANCELED);
        }
        super.onCancel(dialog);
    }

    public NumDialog bulidPhone(String phone, int textColor) {
        this.phone = phone;
        this.phoneColor = textColor;
        hasTitle = true;
        return numDialog;
    }


    public NumDialog bulidContent(String content, int textColor) {
        this.content = content;
        this.contentColor = textColor;
        hasContent = true;
        return numDialog;
    }

    public NumDialog bulidIcon(int imageRes) {
        icon = imageRes;
        hasIcon = true;
        return numDialog;
    }

    public NumDialog bulidBackground(int color) {
        LinearBackground = color;
        hasBackground = true;
        return numDialog;
    }

    public NumDialog bulidButtonOk(String text) {
        ok = text;
        hasButtonOk = true;
        return numDialog;
    }

    public NumDialog bulidButtonCancel(String text) {
        cancel = text;
        hasButtonCancel = true;
        return numDialog;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hasTitle = false;
        hasContent = false;
        hasIcon = false;
        hasBackground = false;
        hasButtonCancel = false;
        hasButtonOk = false;
    }
}
