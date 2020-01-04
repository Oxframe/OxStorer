package cn.oxframe.storer.sample;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

import cn.oxframe.storer.OxFiler;
import cn.oxframe.storer.OxStorer;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        OxStorer.instance(getApplicationContext(), "aaaa");
        initView();

    }

    Button add;
    Button query;
    Button queryall;

    private void initView() {
        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OxStorer.put("aaa", "aaaa");
                OxStorer.put("bbb", "bbbb");
                OxStorer.put("ccc", "cccc");
            }
        });

        query = findViewById(R.id.query);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object aaa = OxStorer.get("aaa", "dddd");
                assert aaa != null;
                Log.e("MainActivity", aaa.toString());
            }
        });

        queryall = findViewById(R.id.queryall);
        queryall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, ?> map = OxStorer.getAll();
                for (String key : map.keySet()) {
                    Object value = map.get(key);
                    assert value != null;
                    Log.e("MainActivity", value.toString());
                }
            }
        });

        findViewById(R.id.contains).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean aaa = OxStorer.contains("aaa");
                Boolean ddd = OxStorer.contains("ddd");
                Log.e("MainActivity", "aaa  =  " + aaa);
                Log.e("MainActivity", "ddd  =  " + ddd);
            }
        });

        findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OxStorer.remove("ccc");
            }
        });

        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OxStorer.clear();
            }
        });

        findViewById(R.id.getpath).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("MainActivity", mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath());
                Log.e("MainActiity", Environment.getExternalStorageDirectory() + "");
                Log.e("MainActiity", Environment.getDataDirectory() + "");
                Log.e("MainActivity", "是否有内存卡" + OxFiler.sdCardIsAvailable());
                Log.e("MainActivity", "根目录" + OxFiler.getRootPath());
            }
        });

    }

}
