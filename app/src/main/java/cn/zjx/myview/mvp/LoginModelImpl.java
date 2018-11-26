package cn.zjx.myview.mvp;

/**
 * @author zjx on 2018/9/10.
 */
public class LoginModelImpl implements LoginContract.LoginModel {

    /**
     * 登录方法
     * @param name
     * @param password
     * @param callBack
     */
    @Override
    public void login(String name, String password, final LoginContract.LoginCallBack callBack) {
//        LoginNetUtils.getInstance().login(name, password, new BaseSubscriber<Login>() {
//            @Override
//            public void onSuccess(Login login) {
//                callBack.onSuccess(login);
//            }
//
//            @Override
//            public void onFail(String info) {
//                callBack.onFail(info);
//            }
//        });
    }
}