package lap.hou.com.lesson5_thread_asyncask;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ThreadHandlerActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int MESSAGE_NUMBER =1001 ;
    private static final int MESSAGE_DONE = 1002;
    private TextView  mTextView;
    private Button mButton;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_handler);
        initView();
        initHandler();

    }

    private void initHandler() {
        mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case MESSAGE_NUMBER:
                        mTextView.setText(String.valueOf(msg.arg1));
                        break;
                    case MESSAGE_DONE:
                        Toast.makeText(ThreadHandlerActivity.this, "Đếm xong", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private  void  initView(){
        mTextView= (TextView) findViewById(R.id.textView);
        mButton=(Button)findViewById(R.id.button);
        mButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                demLui();
                break;
            default:
                break;
        }
    }

    private void demLui() {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
            int number =10;
                do {
                    number--;
                    Message message=new Message();
                    message.what = MESSAGE_NUMBER;
                    message.arg1=number;
                    mHandler.sendMessage(message);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }while (number>0);
                mHandler.sendEmptyMessage(MESSAGE_DONE);
            }
        });
        thread.start();
    }
}
