package cn.zjx.myview;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author zjx on 2018/7/12.
 */
public class BookService extends Service {
    private static final  String TAG="BS";
    private AtomicBoolean mIsDestoryed=new AtomicBoolean();
    private CopyOnWriteArrayList<Book> mList=new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<INewAidlInterface> mListenerList=new CopyOnWriteArrayList<>();

    private Binder mBinder=new IMyAidlInterface.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mList.add(book);
        }

        @Override
        public void registerListener(INewAidlInterface face) throws RemoteException {
            if(!mListenerList.contains(face)){
                mListenerList.add(face);
            }else{
                Log.i(TAG,"EXIST");
            }
        }

        @Override
        public void unregisterListener(INewAidlInterface face) throws RemoteException {
            if(mListenerList.contains(face)){
                mListenerList.remove(face);
            }else{
                Log.i(TAG,"NOT FOUND");
            }
        }
    };

    @Override
    public void onCreate(){
        super.onCreate();
        mList.add(new Book(1,"语文"));
        mList.add(new Book(2,"数学"));
        new Thread(new ServiceWork()).start();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        mIsDestoryed.set(true);
        super.onDestroy();
    }

    private void onNewBook(Book book)throws RemoteException{
        mList.add(book);
        for (int i = 0; i < mListenerList.size(); i++) {
            INewAidlInterface aidlInterface=mListenerList.get(i);
            aidlInterface.onNewBook(book);
        }
    }

    private class ServiceWork implements  Runnable{

        @Override
        public void run() {
            while(!mIsDestoryed.get()){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId=mList.size()+1;
                Book newbook=new Book(bookId,"new book-"+bookId);
                try {
                    onNewBook(newbook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
