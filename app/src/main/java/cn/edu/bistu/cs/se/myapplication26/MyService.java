package cn.edu.bistu.cs.se.myapplication26;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private static final String TAG="myServiceTag";
    private LocalBinder myBinder=new LocalBinder();
    public class LocalBinder extends Binder{
        MyService getService(){
            return MyService.this;
        }
    }
    public int add(int x,int y){
        return x+y;
    }
    public MyService() {
    }
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG,"onBind()");
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(TAG,"onUnBind()");
        return super.onUnbind(intent);
    }
}
