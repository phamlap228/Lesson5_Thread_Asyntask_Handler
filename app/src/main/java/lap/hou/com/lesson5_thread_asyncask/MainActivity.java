package lap.hou.com.lesson5_thread_asyncask;

import android.os.AsyncTask;
import android.support.v4.util.Preconditions;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new DownloadFileFromServer().execute();//gọi Asynctask và gọi class kết thừa ASynctask
    }
//để sử dụng Asynctask cần extends từ class AsyncTask
    private class DownloadFileFromServer extends AsyncTask{
        private ProgressBar mProgressBar;
        private TextView mTextView;
//Ocerride các phương thức cần thiết
        //Phương thức thực hiện đầu tiên khi khởi tạo
        @Override
        protected void onPreExecute() { //ánh sạ các thành phần cần sd
            mProgressBar= (ProgressBar) findViewById(R.id.progressBar);
            mTextView= (TextView) findViewById(R.id.tvDownload);
            super.onPreExecute();
        }


        //Phương thức thực hiện các hành động loading mà bình thường phải tạo 1 Thread riêng để tránh Ả/Hưởng đến main thread
    //ở đây không thực hiện hành động nào liên quan đến UI
        @Override
        protected Object doInBackground(Object[] params) {
            //tạo vòng lặp thay thế cho quá trình loading
            for (int i=0;i<100;i++){
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i+1);//Bắn ra các thông báo để xử lý trong khi ui Loading..
            }
            return null;
        }
    // phương thức nhận các thông báo bắn ra và sử lý UI
        @Override
        protected void onProgressUpdate(Object[] values) {
            //lấy dl được gọi đến
             int progress = (int) values[0];
            //hiển thị progress
            mProgressBar.setProgress(progress);
            mTextView.setText("Loading... "+String.valueOf(progress)+"%");
            super.onProgressUpdate(values);
        }
        //sau khi load xong phương thức này sẽ đc gọi
        //sau bước này asynctask sẽ dừng lại
        @Override
        protected void onPostExecute(Object o) {
            mTextView.setText("Download Complete, Goto next Activity");
            super.onPostExecute(o);

        }
    }
}
