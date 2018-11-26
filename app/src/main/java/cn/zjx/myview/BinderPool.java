package cn.zjx.myview;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

/**
 * @author zjx on 2018/7/13.
 */
public class BinderPool {
    private static final String TAG = "BinderPool";
    public static final int BINDER_NONE = -1;
    public static final int BINDER_COMPUTE = 0;
    public static final int BINDER_SECRUITY = 1;

    private Context context;
    private IBinderPool mBinderPool;
    private static volatile BinderPool sInstance;
    private CountDownLatch countDownLatch;

    private BinderPool(Context context) {
        this.context = context.getApplicationContext();
        connetBinderPoolService();
    }

    public static BinderPool getsInstance(Context context) {
        if (sInstance == null) {
            synchronized (BinderPool.class) {
                if (sInstance == null) {
                    sInstance = new BinderPool(context);
                }
            }
        }
        return sInstance;
    }

    private synchronized void connetBinderPoolService() {
        countDownLatch = new CountDownLatch(1);
        Intent service = new Intent(context, BinderPoolService.class);
        context.bindService(service, connection, Context.BIND_AUTO_CREATE);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;

        try {
            if (mBinderPool != null) {
                binder = mBinderPool.queryBinder(binderCode);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return binder;

    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinderPool = IBinderPool.Stub.asInterface(service);
            try {
                mBinderPool.asBinder().linkToDeath(deathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.w(TAG, "binder died");
            mBinderPool.asBinder().unlinkToDeath(deathRecipient, 0);
            mBinderPool = null;
            connetBinderPoolService();
        }
    };

    public static class BinderPoolImpl extends IBinderPool.Stub {

        public BinderPoolImpl() {
            super();
        }

        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            IBinder binder = null;
            switch (binderCode) {
                case BINDER_SECRUITY: {
                    binder = new SecurityCenterImpl();
                    break;
                }
                case BINDER_COMPUTE: {
                    binder = new ComputeImpl();
                    break;
                }
                default:
                    break;
            }
            return binder;
        }
    }


    public static class SecurityCenterImpl extends ISecurityCenter.Stub {
        private static final char SECRET_CODE = '^';

        @Override
        public String encrypt(String content) throws RemoteException {
            char[] chars = content.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                chars[i] ^= SECRET_CODE;
            }
            return new String(chars);
        }

        @Override
        public String decrypt(String password) throws RemoteException {
            return encrypt(password);
        }
    }

    public static class ComputeImpl extends ICompute.Stub {
        @Override
        public int add(int a, int b) throws RemoteException {
            return a + b;
        }
    }
}
