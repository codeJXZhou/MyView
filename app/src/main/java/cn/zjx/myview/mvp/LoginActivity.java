package cn.zjx.myview.mvp;

import android.os.Bundle;
import android.view.View;

/**
 * @author zjx on 2018/9/10.
 */
public class LoginActivity extends BaseActivity<LoginContract.LoginView, LoginPresenter>
        implements LoginContract.LoginView, View.OnClickListener {


    @Override
    protected void initActivityView(Bundle savedInstanceState) {
//        setContentView(R.layout.activity_login);

    }

    @Override
    protected void findViewById() {

    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void getData() {

    }

    @Override
    public void onCheckFormatSuccess() {
//        loading.show();
    }

    @Override
    public void onCheckFormatFail(String info) {
//        RxToast.error(mContext, info).show();
    }

    @Override
    public void onLoginSuccess(Login login) {

    }

    @Override
    public void onLoginFail(String errorInfo) {

    }

    @Override
    public void onClick(View view) {

    }

}
