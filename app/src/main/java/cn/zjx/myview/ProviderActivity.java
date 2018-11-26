package cn.zjx.myview;

import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.security.KeyStore;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import cn.zjx.myview.finger.FingerprintDilogFragment;
import cn.zjx.myview.jz.MySingletone;

public class ProviderActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        findViewById(R.id.timeview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("cn.zjx.myview.intent.action.MyAty"));
            }
        });
//        Uri uri=Uri.parse("content://cn.zjx.myview.bookp/book");
//        ContentValues values=new ContentValues();
//        values.put("_id",6);
//        values.put("name","s算法");
//        getContentResolver().insert(uri,values);
//        Cursor cursor=getContentResolver().query(uri,new String[]{"_id","name"},null,null,null);
//       while(cursor.moveToNext()){
//           Book book=new Book();
//           book.id=cursor.getInt(0);
//           book.name=cursor.getString(1);
//           Log.i("Provider",book.toString());
//       }
//       cursor.close();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                dowork();
//            }
//        }).start();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            getContentResolver().query(uri,null,null,null);
//            getContentResolver().query(uri,null,null,null);
//            getContentResolver().query(uri,null,null,null);
//        }
//        widget=new NewAppWidget();
//        IntentFilter intent=new IntentFilter();
//        intent.addAction("cn.zjx.myview.action.CLICK");
//        registerReceiver(widget,intent);

//        AppDataBase db = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "rooms").build();
//        final UserrDao userDao = (UserrDao) db.roomDao(); // 访问数据库的操作要在子线程中执行
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                userDao.deleteAll();
//                System.out.println("----------- INSERT U1 U2 -------------");
//                Userr u1 = new Userr(1, "a", "b");
//                Userr u2 = new Userr(2, "c", "d");
//                userDao.insertAll(u1, u2);
//                List<Userr> users = userDao.getAll();
//                System.out.println(users.toString());
//                System.out.println("------------- DELETE U2 --------------");
//                userDao.delete(u2);
//                users = userDao.getAll();
//                System.out.println(users.toString());
//                u1.setFirstName("aa");
//                u1.setLastName("bb");
//                System.out.println("------------- UPDATE U1 --------------");
//                userDao.update(u1);
//                users = userDao.getAll();
//                System.out.println(users.toString());
//            }
//        });
//        thread.start();
//        runThread();
//        if (supportFingerprint()) {
//            initKey();
//            initCipher();
//        }
        MySingletone.getInstance();
    }

    CountDownLatch countDownLatch = new CountDownLatch(1);

    private void runThread() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                        System.out.println("---------------Thread:" + Thread.currentThread().getName() + ",time: " + System.currentTimeMillis());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        countDownLatch.countDown();
    }


    NewAppWidget widget;

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(widget);
    }

    private static class MyHandler extends Handler{
        private final WeakReference<ProviderActivity> activity;

        private MyHandler(WeakReference<ProviderActivity> activity) {
            this.activity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(activity==null){

            }
        }
    };

    private void dowork() {
        BinderPool binderPool = BinderPool.getsInstance(ProviderActivity.this);
        IBinder security = binderPool.queryBinder(BinderPool.BINDER_SECRUITY);
        BinderPool.SecurityCenterImpl securityCenter = (BinderPool.SecurityCenterImpl) BinderPool.SecurityCenterImpl.asInterface(security);
        Log.d("Provider", "visit security");
        String msg = "hello world";
        try {
            String pwd = securityCenter.encrypt(msg);
            Log.d("Provider", "pwd:" + pwd);
            Log.d("Provider", "content:" + securityCenter.decrypt(msg));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.d("Provider", "visit compute");
        IBinder compute = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
        BinderPool.ComputeImpl compute1 = (BinderPool.ComputeImpl) BinderPool.ComputeImpl.asInterface(compute);
        try {
            Log.d("Provider", "add:" + compute1.add(3, 6));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static final String DEFAULT_KEY_NAME = "default_key";

    KeyStore keyStore;


    public boolean supportFingerprint() {
        if (Build.VERSION.SDK_INT < 23) {
            Toast.makeText(this, "您的系统版本过低，不支持指纹功能", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            KeyguardManager keyguardManager = getSystemService(KeyguardManager.class);
            FingerprintManager fingerprintManager = getSystemService(FingerprintManager.class);
            if (!fingerprintManager.isHardwareDetected()) {
                Toast.makeText(this, "您的手机不支持指纹功能", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!keyguardManager.isKeyguardSecure()) {
                Toast.makeText(this, "您还未设置锁屏，请先设置锁屏并添加一个指纹", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                Toast.makeText(this, "您至少需要在系统设置中添加一个指纹", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    @TargetApi(23)
    private void initKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(DEFAULT_KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
            keyGenerator.init(builder.build());
            keyGenerator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @TargetApi(23)
    private void initCipher() {
        try {
            SecretKey key = (SecretKey) keyStore.getKey(DEFAULT_KEY_NAME, null);
            Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            showFingerPrintDialog(cipher);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void showFingerPrintDialog(Cipher cipher) {
        FingerprintDilogFragment fragment = new FingerprintDilogFragment();
        fragment.setClipher(cipher);
        fragment.show(getFragmentManager(), "fingerprint");
    }

    public void onAuthenticated() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
