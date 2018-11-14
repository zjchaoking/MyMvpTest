package com.kaicom.mymvptest.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kaicom.api.util.ApkUtil;
import com.kaicom.api.view.toast.ToastTools;
import com.kaicom.mymvptest.R;
import com.kaicom.mymvptest.utils.DialogUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.kaicom.mymvptest.files.AppConfigs.LOCATION_PERMISSION_CODE;
import static com.kaicom.mymvptest.files.AppConfigs.STORAGE_PERMISSION_CODE;
import static com.kaicom.mymvptest.files.AppConfigs.TRACE_RECORD_PATH;

public class MainsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button btnRequest;
    private TextView tvContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mains);
        initView();
        initListeners();
        checkPermission(LOCATION_PERMISSION_CODE);
    }

    private void initListeners() {
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            doRequest();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    private void doRequest() throws Exception {
        URL url = new URL("http://chaoking.51vip.biz/MyTestWebProject/");
        URLConnection urlConnection = url.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.connect();
        OutputStream outputStream = httpURLConnection.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        writer.write("name=金超");
        writer.flush();
        outputStream.flush();
        writer.close();
        outputStream.close();
        InputStream inputStream = httpURLConnection.getInputStream();
        final String content = readInputStream(inputStream);
        System.out.println("请求返回内容"+content);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvContent.setText(content);
            }
        });
    }

    /**
     * 从输入流中读取数据
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public static String readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();//网页的二进制数据
        outStream.close();
        inStream.close();
        return new String(data);
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainsActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        btnRequest = (Button) findViewById(R.id.btn_request);
        tvContent = (TextView) findViewById(R.id.tv_content);
    }

    protected void checkPermission(int requestCode) {
        switch (requestCode) {
            case LOCATION_PERMISSION_CODE:
                if (ContextCompat.checkSelfPermission(this,
                        ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    //没有权限，向系统申请该权限。
                    ActivityCompat.requestPermissions(this,
                            new String[]{ACCESS_FINE_LOCATION},
                            requestCode);
                } else {
                    //已经获得权限，则执行定位请求。
                    ToastTools.showLazzToast( "已获取定位权限");
                    //请求SD卡读写权限
                    checkPermission(STORAGE_PERMISSION_CODE);
                }
                break;

            case STORAGE_PERMISSION_CODE:
                if (ContextCompat.checkSelfPermission(this,
                        WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //没有权限，向系统申请该权限。
                    ActivityCompat.requestPermissions(this,
                            new String[]{WRITE_EXTERNAL_STORAGE},
                            requestCode);
                } else {
                    //已经获得权限，则执行定位请求。
                    ToastTools.showLazzToast( "已获取SD卡读写权限");
                    initLogFolds();
                }
                break;
            default:

        }
    }

    private void initLogFolds() {
        File recordFile = new File(TRACE_RECORD_PATH);
        if (!recordFile.exists()) {
            recordFile.mkdirs();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        DialogUtil.context = this;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ToastTools.showLazzToast( "定位权限已获取");
                    //请求SD卡读写权限
                    checkPermission(STORAGE_PERMISSION_CODE);
                } else {
                    ApkUtil.exit();
                }
                break;
            case STORAGE_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ToastTools.showLazzToast( "SD卡读写权限已获取");
                    initLogFolds();
                } else {
                    ApkUtil.exit();
                }
                break;
            default:

        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            DialogUtil.showWarnDialog("确认要退出登陆吗？",true, new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    finish();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.item_trace_records) {
            toNextActivity(TraceRecordsActivity.class);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 跳转Activity界面
     *
     * @param cls
     */
    protected void toNextActivity(Class cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}
