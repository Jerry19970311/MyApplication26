package cn.edu.bistu.cs.se.myapplication26;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="mainActivityTag";
    MyService myService=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ServiceConnection serviceConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.v(TAG,"onServiceConnected");
                myService=((MyService.LocalBinder)iBinder).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.v(TAG,"onServiceDisconnected");
            }
        };
        Button buttonStart=(Button)findViewById(R.id.buttonStartService);
        Button buttonStop=(Button)findViewById(R.id.buttonStopService);
        Button buttonUse=(Button)findViewById(R.id.buttonUseService);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,MyService.class);
                bindService(intent,serviceConnection, Service.BIND_AUTO_CREATE);
            }
        });
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unbindService(serviceConnection);
            }
        });
        buttonUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myService!=null){
                    Log.v(TAG,"Using Service:"+myService.add(1,2));
                }
            }
        });
        final EditText textView=(EditText)findViewById(R.id.eText);
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                textView.setText(msg.arg1+"");
            }
        };
        final Runnable myWorker=new Runnable() {
            @Override
            public void run() {
                int progress=0;
                while (progress<=100){
                    Message msg=new Message();
                    msg.arg1=progress;
                    handler.sendMessage(msg);
                    progress+=10;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Message msg=handler.obtainMessage();
                msg.arg1=-1;
                handler.sendMessage(msg);
            }
        };
        Button button=(Button)findViewById(R.id.buttonTestHandle);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread workThread=new Thread(null,myWorker,"WorkThread");
                workThread.start();
            }
        });
    }
}
