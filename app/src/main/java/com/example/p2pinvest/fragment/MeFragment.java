package com.example.p2pinvest.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.p2pinvest.R;
import com.example.p2pinvest.activity.BarChartActivity;
import com.example.p2pinvest.activity.ChongZhiActivity;
import com.example.p2pinvest.activity.GestureVerifyActivity;
import com.example.p2pinvest.activity.LineChartActivity;
import com.example.p2pinvest.activity.LoginActivity;
import com.example.p2pinvest.activity.PieChartActivity;
import com.example.p2pinvest.activity.TiXianActivity;
import com.example.p2pinvest.activity.UserInfoActivity;
import com.example.p2pinvest.bean.User;
import com.example.p2pinvest.common.BaseActivity;
import com.example.p2pinvest.common.BaseFragment;
import com.example.p2pinvest.util.BitmapUtils;
import com.example.p2pinvest.util.UIUtils;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;


/**
 * Created by shkstart on 2016/11/30 0030.
 * bitmap 和 ImageView 相互转化：
 * Bitmap bmMeIcon = ((BitmapDrawable) ivMeIcon.getDrawable()).getBitmap();
 * ivMeIcon.setImageBitmap(bitmap);
 */
public class MeFragment extends BaseFragment implements View.OnClickListener {

    private ImageView ivTitleBack;
    private TextView tvTitle;
    private ImageView ivTitleSetting;
    private ImageView ivMeIcon;
    private RelativeLayout rlMeIcon;
    private TextView tvMeName;
    private RelativeLayout rlMe;
    private ImageView recharge;
    private ImageView withdraw;
    private TextView llTouzi;
    private TextView llTouziZhiguan;
    private TextView llZichan;

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content) {
        /**
         * 将矩形头像改成圆形
         */
        //压缩处理：长方形变正方形
        Bitmap bmMeIcon = ((BitmapDrawable) ivMeIcon.getDrawable()).getBitmap();
        Bitmap bitmap = BitmapUtils.zoom(bmMeIcon, UIUtils.dp2px(62), UIUtils.dp2px(62));
        //圆形处理：正方形变圆形
        bitmap = BitmapUtils.circleBitmap(bitmap);
        ivMeIcon.setImageBitmap(bitmap);
        //回收bitmap资源
        //bmMeIcon.recycle();

        //判断用户是否已经登录
        isLogin();

        //判断一下，是否开启了手势密码。如果开启：先输入手势密码
        SharedPreferences sp = this.getActivity().getSharedPreferences("secret_protect", Context.MODE_PRIVATE);
        boolean isOpen = sp.getBoolean("isOpen", false);
        if(isOpen){
            ((BaseActivity)this.getActivity()).goToActivity(GestureVerifyActivity.class,null);
            return;
        }
    }

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void findViews(View view) {
        ivTitleBack = view.findViewById(R.id.iv_title_back);
        tvTitle = view.findViewById(R.id.tv_title);
        ivTitleSetting = view.findViewById(R.id.iv_title_setting);

        ivMeIcon = view.findViewById(R.id.iv_me_icon);
        rlMeIcon = view.findViewById(R.id.rl_me_icon);
        tvMeName = view.findViewById(R.id.tv_me_name);
        rlMe = view.findViewById(R.id.rl_me);
        recharge = view.findViewById(R.id.recharge);
        withdraw = view.findViewById(R.id.withdraw);
        llTouzi = view.findViewById(R.id.ll_touzi);
        llTouziZhiguan = view.findViewById(R.id.ll_touzi_zhiguan);
        llZichan = view.findViewById(R.id.ll_zichan);

        ivTitleSetting.setOnClickListener(this);
        recharge.setOnClickListener(this);
        withdraw.setOnClickListener(this);

        llTouzi.setOnClickListener(this);
        llTouziZhiguan.setOnClickListener(this);
        llZichan.setOnClickListener(this);
    }

    @Override
    protected void initTitle() {
        ivTitleBack.setVisibility(View.INVISIBLE);
        tvTitle.setText("我的资产");
        ivTitleSetting.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_title_setting:
                //启动用户信息界面的Activity
                ((BaseActivity) this.getActivity()).goToActivity(UserInfoActivity.class, null);
                break;
            case R.id.recharge:
                //设置“充值”操作
                ((BaseActivity) this.getActivity()).goToActivity(ChongZhiActivity.class, null);
                break;
            case R.id.withdraw:
                ((BaseActivity) this.getActivity()).goToActivity(TiXianActivity.class, null);
                break;
            case R.id.ll_touzi:
                ((BaseActivity) this.getActivity()).goToActivity(LineChartActivity.class, null);
                break;
            case R.id.ll_touzi_zhiguan:
                ((BaseActivity) this.getActivity()).goToActivity(BarChartActivity.class, null);
                break;
            case R.id.ll_zichan:
                ((BaseActivity) this.getActivity()).goToActivity(PieChartActivity.class, null);
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //读取本地保存的图片;注意这里的生命周期
        readImage();
    }

    private boolean readImage() {
        File filesDir;
        //判断sd卡是否挂载
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //路径1：storage/sdcard/Android/data/包名/files
            filesDir = this.getActivity().getExternalFilesDir("");
        } else {//手机内部存储
            //路径：data/data/包名/files
            filesDir = this.getActivity().getFilesDir();
        }
        File file = new File(filesDir, "icon.png");
        if (file.exists()) {
            //存储--->内存
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            ivMeIcon.setImageBitmap(bitmap);
            return true;
        }
        return false;
    }

    private void isLogin() {
        //查看本地是否有用户的登录信息
        SharedPreferences sp = this.getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String name = sp.getString("name", "");

        if (TextUtils.isEmpty(name)) {
            //本地没有保存过用户信息，给出提示：登录
            doLogin();
        } else {
            //已经登录过，则直接加载用户的信息并显示
            doUser();
        }
    }

    //加载用户信息并显示
    private void doUser() {
        //1.读取本地保存的用户信息
        User user = ((BaseActivity) this.getActivity()).readUser();
        //2.获取对象信息，并设置给相应的视图显示。
        tvMeName.setText(user.getName());

        //判断本地是否已经保存头像的图片，如果有，则不再执行联网操作
        boolean isExist = readImage();
        if (isExist) {
            return;
        }
        /**
         * Picasso.get().load() 之后加上transform()，将头像设置为圆形，并通过into加载到imageview
         */
        Picasso.get().load(user.getImageurl()).transform(new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {//下载以后的内存中的bitmap对象
                //压缩处理
                Bitmap bitmap = BitmapUtils.zoom(source, UIUtils.dp2px(62), UIUtils.dp2px(62));
                //圆形处理
                bitmap = BitmapUtils.circleBitmap(bitmap);
                //回收bitmap资源
                source.recycle();
                return bitmap;
            }

            @Override
            public String key() {
                return "";//需要保证返回值不能为null。否则报错
            }
        }).into(ivMeIcon);
    }

    //给出提示：登录
    private void doLogin() {
        new AlertDialog.Builder(this.getActivity())
                .setTitle("提示")
                .setMessage("您还没有登录哦！么么~")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((BaseActivity) MeFragment.this.getActivity()).goToActivity(LoginActivity.class, null);
                    }
                })
                .setCancelable(true)
                .show();
    }
}