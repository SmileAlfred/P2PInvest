package com.example.p2pinvest.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.p2pinvest.R;
import com.example.p2pinvest.common.BaseActivity;
import com.example.p2pinvest.util.BitmapUtils;
import com.example.p2pinvest.util.UIUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class UserInfoActivity extends BaseActivity implements View.OnClickListener {

    private static final int PICTURE = 100;
    private static final int CAMERA = 200;
    private ImageView ivTitleBack;
    private TextView tvTitle;
    private ImageView ivTitleSetting;
    private ImageView ivUserIcon;
    private Button btnUserLogout;
    private TextView tvUserChange;

    @Override
    protected void findViews() {
        ivTitleBack = findViewById(R.id.iv_title_back);
        tvTitle = findViewById(R.id.tv_title);
        ivTitleSetting = findViewById(R.id.iv_title_setting);
        ivUserIcon = findViewById(R.id.iv_user_icon);
        btnUserLogout = findViewById(R.id.btn_user_logout);
        tvUserChange = findViewById(R.id.tv_user_change);

        ivTitleBack.setOnClickListener(this);
        tvUserChange.setOnClickListener(this);
        btnUserLogout.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initTitle() {
        ivTitleBack.setVisibility(View.VISIBLE);
        tvTitle.setText("用户信息");
        ivTitleSetting.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_back:
                //1.销毁当前的页面
                this.removeCurrentActivity(UserInfoActivity.this);
                break;
            case R.id.tv_user_change:
                String[] items = new String[]{"图库", "相机"};
                //提供一个AlertDialog
                new AlertDialog.Builder(this)
                        .setTitle("选择来源")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0://图库
                                        //启动其他应用的activity:使用隐式意图
                                        if (ContextCompat.checkSelfPermission(UserInfoActivity.this
                                                , Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                                != PackageManager.PERMISSION_GRANTED) {
                                            ActivityCompat.requestPermissions(UserInfoActivity.this
                                                    , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICTURE);
                                        } else {
                                            openAlbum();
                                        }
                                        break;
                                    case 1://相机
                                        Intent camera = new Intent("android.intent.action.GET_CONTENT");
                                        startActivityForResult(camera, CAMERA);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        })
                        .setCancelable(false)
                        .show();
                break;
            case R.id.btn_user_logout:
                //"退出登录"button的回调方法
                //1.将保存在sp中的数据清除
                SharedPreferences sp = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
                //清除数据操作必须提交；提交以后，文件仍存在，只是文件中的数据被清除了
                sp.edit().clear().commit();
                //2.将本地保存的图片的file删除
                File filesDir;
                //判断sd卡是否挂载
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    //路径1：storage/sdcard/Android/data/包名/files
                    filesDir = this.getExternalFilesDir("");
                } else {//手机内部存储
                    //路径：data/data/包名/files
                    filesDir = this.getFilesDir();

                }
                File file = new File(filesDir, "icon.png");
                if (file.exists()) {
                    file.delete();//删除存储中的文件
                }
                //3.销毁所有的activity
                this.removeAll();
                //4.重新进入首页面
                this.goToActivity(MainActivity.class, null);
                break;
            default:
                break;
        }
    }

    /**
     * 请求权限 方法
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PICTURE:
                /**
                 * 解决 打开相册 请求
                 */
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(UserInfoActivity.this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    /**
     * 打开相册
     */
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        /**
         * 设置意图类型：
         * intent.setType("image/*")        表示读取相册照片
         * intent.setType("text/plain");    表示调用手机默认分享
         */
        intent.setType("image/*");
        startActivityForResult(intent, PICTURE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    //重写启动新的activity以后的回调方法

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //相机
        if (requestCode == CAMERA && resultCode == RESULT_OK && data != null) {
            //获取intent中的图片对象
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            //对获取到的bitmap进行压缩、圆形处理
            bitmap = BitmapUtils.zoom(bitmap, ivUserIcon.getWidth(), ivUserIcon.getHeight());
            bitmap = BitmapUtils.circleBitmap(bitmap);

            //加载显示
            ivUserIcon.setImageBitmap(bitmap);
            //上传到服务器（省略）

            //保存到本地
            saveImage(bitmap);

            //图库
        } else if (requestCode == PICTURE && resultCode == RESULT_OK && data != null) {
            if (Build.VERSION.SDK_INT >= 19) {
                handleImageOnKitKat(data);
            } else {
                handleImageBeforeKitKat(data);
            }
        }
    }

    /**
     * Android4.4 版本之后，相册图片不再返回真实 URI；故对其进行以下解析
     *
     * @param data intent 所携带的 信息
     */
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contenUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contenUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
        Log.i("TAG", "handleImageOnKitKat > 1:  + imagePath" + imagePath);
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            //存储--->内存
            Bitmap decodeFile = BitmapFactory.decodeFile(imagePath);
            Bitmap zoomBitmap = BitmapUtils.zoom(decodeFile, ivUserIcon.getWidth(), ivUserIcon.getHeight());
            //bitmap圆形裁剪
            Bitmap circleImage = BitmapUtils.circleBitmap(zoomBitmap);
            //加载显示
            ivUserIcon.setImageBitmap(circleImage);
            //上传到服务器（省略）

            //保存到本地
            saveImage(circleImage);
        } else {
            Toast.makeText(this, "Failed to get image", Toast.LENGTH_SHORT).show();
        }
    }
    //将Bitmap保存到本地的操作

    /**
     * 数据的存储。（5种）
     * Bimap:内存层面的图片对象。
     * <p>
     * 存储--->内存：
     * BitmapFactory.decodeFile(String filePath);
     * BitmapFactory.decodeStream(InputStream is);
     * 内存--->存储：
     * bitmap.compress(Bitmap.CompressFormat.PNG,100,OutputStream os);
     */
    private void saveImage(Bitmap bitmap) {
        File filesDir;
        //判断sd卡是否挂载
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //路径1：storage/sdcard/Android/data/包名/files
            filesDir = this.getExternalFilesDir("");

        } else {//手机内部存储
            //路径：data/data/包名/files
            filesDir = this.getFilesDir();

        }
        FileOutputStream fos = null;
        try {
            File file = new File(filesDir, "icon.png");
            fos = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}