package cn.zjx.myview;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private VisitStepView sv_logistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView iv = (ImageView) findViewById(R.id.iv);
        iv.setImageResource(R.drawable.bg_update);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 计算中心点（这里是使用view的中心作为旋转的中心点）
                final float centerX = v.getWidth() / 2.0f;
                final float centerY = v.getHeight() / 2.0f;
                //括号内参数分别为（上下文，开始角度，结束角度，x轴中心点，y轴中心点，深度，是否扭曲）
                final Rotate3DAnimation rotation = new Rotate3DAnimation(MainActivity.this, 0, 360, centerX, centerY, 0f, true);
                rotation.setDuration(3000);
                rotation.setFillAfter(true);
                rotation.setInterpolator(new LinearInterpolator());
                v.startAnimation(rotation);
            }
        });

        sv_logistics = (VisitStepView) findViewById(R.id.sv_logistics);
        sv_logistics.setBindViewListener(new VisitStepView.BindViewListener() {
            @Override
            public void onBindView(TextView itemMsg, TextView itemDate, VisitStepModel data) {
                itemMsg.setText(data.msg);
                itemDate.setText(data.date);
            }
        });
        List<VisitStepModel> list = new ArrayList<>();
        VisitStepModel model;
        model = new VisitStepModel();
        model.type = 1;
        model.msg = "dsdada";
        model.date = "1123";
        list.add(model);
        model = new VisitStepModel();
        model.type = 2;
        model.msg = "fddfd";
        model.date = "12121";
        list.add(model);
        model = new VisitStepModel();
        model.type = 3;
        model.msg = "gfgf";
        model.date = "232";
        list.add(model);
        model = new VisitStepModel();
        model.type = 1;
        model.msg = "gfd";
        model.date = "22";
        list.add(model);
        sv_logistics.setDatas(list);
        Toast.makeText(MainActivity.this, Runtime.getRuntime().availableProcessors() + "", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, BookService.class);
        bindService(intent, mConnection2, BIND_AUTO_CREATE);
    }

    private static final String TAG = "MainActivity";

    private Messenger replyMessenger = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Log.i(TAG, "receive from server:" + msg.getData().getString("msg"));
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }

        }
    }

    private Messenger mService;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mService = new Messenger(iBinder);
            Message msg = Message.obtain(null, 1);
            Bundle data = new Bundle();
            data.putString("msg", "hello you as");
            msg.setData(data);
            msg.replyTo = replyMessenger;
            try {
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    private ServiceConnection mConnection2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IMyAidlInterface myAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
            try {
                myFace = myAidlInterface;
                List<Book> list = myAidlInterface.getBookList();
                for (int i = 0; i < list.size(); i++) {
                    Log.i(TAG, "query=======>" + list.get(i).name);
                }
                myAidlInterface.addBook(new Book(3, "英语"));
                List<Book> list2 = myAidlInterface.getBookList();
                for (int i = 0; i < list2.size(); i++) {
                    Log.i(TAG, "query=======>" + list2.get(i).name);
                }
                myAidlInterface.registerListener(aidlInterface);
                Log.i(TAG, "query=======>" + list.getClass().getCanonicalName());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private IMyAidlInterface myFace;

    private INewAidlInterface aidlInterface = new INewAidlInterface.Stub() {
        @Override
        public void onNewBook(Book book) throws RemoteException {
            handler.obtainMessage(0, book).sendToTarget();
        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Book book = (Book) msg.obj;
                    Log.i(TAG, "receive from server:" + book.name);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }

        }
    };


    @Override
    protected void onDestroy() {
        if (myFace != null && myFace.asBinder().isBinderAlive()) {
            try {
                myFace.unregisterListener(aidlInterface);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection2);
        super.onDestroy();
    }


}
