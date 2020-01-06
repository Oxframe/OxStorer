package cn.oxframe.storer.sample;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

import cn.oxframe.storer.OxDevicer;
import cn.oxframe.storer.OxFiler;
import cn.oxframe.storer.OxStorer;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE}, 100);
        }

        OxFiler.APP_NAME = "Test";
        OxStorer.instance(getApplicationContext(), "aaaa");
        initView();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("MainActivity", "requestCode" + requestCode);
    }

    Button add;
    Button query;
    Button queryall;

    private void initView() {
        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 1, 2);
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
                int i = checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 1, 2);
                OxStorer.remove("ccc");
            }
        });

        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 1, 2);
                OxStorer.clear();
            }
        });

        findViewById(R.id.getpath).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = checkPermission(Manifest.permission.READ_PHONE_STATE, 1, 2);

//                ffffffff-d701-4739-0000-00006e4d869a
//                ffffffff-d701-4739-0000-00006e4d869a
                Log.e("dsdasd1", OxDevicer.UUID());
                Log.e("dsdasd2", OxDevicer.UUID(mContext, "15021527225"));
            }
        });

    }

}
