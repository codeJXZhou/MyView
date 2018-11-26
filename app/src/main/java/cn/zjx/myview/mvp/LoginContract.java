package cn.zjx.myview.mvp;

/**
 * @author zjx on 2018/9/10.
 */
public class LoginContract {

    public interface LoginView{

        void onCheckFormatSuccess();

        void onCheckFormatFail(String info);

        void onLoginSuccess(Login login);

        void onLoginFail(String errorInfo);
    }

    public interface LoginModel{
        void login(String name,String password,LoginCallBack callBack);
    }

    public interface LoginCallBack{
        void onSuccess(Login login);

        void onFail(String errorInfo);
    }

}
