package com.pg.whatsstatussaver.Utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.WindowManager;

import com.pg.whatsstatussaver.R;


/**
 * Custom progress dialog class.
 */
public class CustomProgressDialog extends Dialog {


    private CustomProgressDialog dialog = null;
    private Handler mHandler = null;
    private boolean isDialogDissmissed ;
    private boolean setTimeOut ;
    private ICustomProgressDialog mCallback = null;

    public interface ICustomProgressDialog{
       void onProgressDissmiss();
    }


    public CustomProgressDialog(Context context) {
        super(context);
        dialog = new CustomProgressDialog(context, R.style.CustomProgressStyle);
        mHandler = new Handler();
        isDialogDissmissed = false;
        this.setTimeOut = false;
        setTimeOut = true;
    }


    public CustomProgressDialog(Context context , boolean setTimeOut) {
        super(context);
        dialog = new CustomProgressDialog(context, R.style.CustomProgressStyle);
        mHandler = new Handler();
        isDialogDissmissed = false;
        this.setTimeOut = setTimeOut;
    }


    public CustomProgressDialog(Context context , boolean setTimeOut ,
                                ICustomProgressDialog callBack) {
        super(context);
        dialog = new CustomProgressDialog(context,R.style.CustomProgressStyle);
        mHandler = new Handler();
        isDialogDissmissed = false;
        this.setTimeOut = setTimeOut;
        this.mCallback = callBack;
    }





    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }


    public void onWindowFocusChanged(boolean hasFocus){
//        ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
//        AnimationDrawable spinner = (AnimationDrawable) imageView.getBackground();
//        spinner.start();
    }

    public void setMessage(CharSequence message) {
//        if(message != null && message.length() > 0) {
//            findViewById(R.id.message).setVisibility(View.VISIBLE);
//            TextView txt = (TextView)findViewById(R.id.message);
//            txt.setText(message);
//            txt.invalidate();
//        }
    }

    public  CustomProgressDialog show(CharSequence message, boolean indeterminate, boolean cancelable,
                                      OnCancelListener cancelListener) {
        dialog.setTitle("");
        dialog.setContentView(R.layout.progress_hud);
        dialog.setCanceledOnTouchOutside(false);
//        if(message == null || message.length() == 0) {
//            dialog.findViewById(R.id.message).setVisibility(View.GONE);
//        } else {
//            TextView txt = (TextView)dialog.findViewById(R.id.message);
//            txt.setText(message);
//        }
        dialog.setCancelable(cancelable);
        if(cancelListener != null){
            dialog.setOnCancelListener(cancelListener);
        }
        dialog.getWindow().getAttributes().gravity= Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount=0.2f;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        //dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        isDialogDissmissed = false;
        dialog.show();

        if(setTimeOut){
            if(mHandler != null){
                mHandler.removeCallbacks(mRunnable);
                mHandler.postDelayed(mRunnable, 60);
            }
        }

        return dialog;
    }

    /**
     * Runnable objetc to dismiss the dialog when the timer is expired.
     */
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            dissMissDialog();
        }
    };


    /**
     * Method to dismiss the dialog.
     */
    public void dissMissDialog(){
        if (dialog != null && dialog.isShowing()
                && dialog.getContext() != null) {
            try {
                isDialogDissmissed = true;
                dialog.dismiss();
                if(mCallback != null){
                    mCallback.onProgressDissmiss();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Method to check the dialog is dissmissed or not.
     * @return
     */
    public boolean isDialogDissmissed() {
        return isDialogDissmissed;
    }

    /**
     * Method to set the boolean value, used to enable the timer.
     * @param value
     */
    public void setTimeOut(boolean value){
        this.setTimeOut = value;
    }
}
