package org.loofer.asynctaskfunctional;

import android.os.Bundle;
import android.widget.Button;

import org.loofer.asynctaskfunctional.base.BaseActivity;
import org.loofer.asynctaskfunctional.callback.IDataCallBack;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(view ->
                MainActivity.this.doAsync(new IDataCallBack<String>() {
            @Override
            public void onTaskBefore() {

            }

            @Override
            public String onTasking(Void... params) {
                return null;
            }

            @Override
            public void onTaskAfter(String result) {

            }
        }));
    }
}
