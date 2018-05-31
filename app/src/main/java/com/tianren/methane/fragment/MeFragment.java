package com.tianren.methane.fragment;

import android.Manifest;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoFragment;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.tianren.methane.App;
import com.tianren.methane.R;
import com.tianren.methane.activity.AboutUsActivity;
import com.tianren.methane.activity.CallPoliceActivity;
import com.tianren.methane.activity.FeedBackActivity;
import com.tianren.methane.activity.LinkServiceActivity;
import com.tianren.methane.activity.LoginActivity;
import com.tianren.methane.activity.ReportActivity;
import com.tianren.methane.activity.ResetPwdActivity;
import com.tianren.methane.utils.SharedPreferenceUtil;
import com.tianren.methane.view.ImgPickerPopupwindow;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2018/3/19.
 */

public class MeFragment extends TakePhotoFragment implements View.OnClickListener {

    private View view;
    private CircleImageView img_header;
    //    ImageView img_header;
    private ImgPickerPopupwindow imgpop;//头像选择器
    private LinearLayout ll_resetpwd, ll_feedback, ll_service, ll_aboutus, ll_baojing;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me, null);
        initView(view);
        return view;
    }


    private void initView(View view) {
        imgpop = new ImgPickerPopupwindow(getActivity(), view.findViewById(R.id.ll_parent), photoHandler, null);
//        img_header= (CircleImageView) view.findViewById(R.id.img_header);
        img_header = (CircleImageView) view.findViewById(R.id.img_header);
        img_header.setOnClickListener(this);

        view.findViewById(R.id.quit_button).setOnClickListener(this);
        view.findViewById(R.id.ll_resetpwd).setOnClickListener(this);
        view.findViewById(R.id.ll_feedback).setOnClickListener(this);
        view.findViewById(R.id.ll_aboutus).setOnClickListener(this);
        view.findViewById(R.id.ll_baojing).setOnClickListener(this);
        view.findViewById(R.id.ll_service).setOnClickListener(this);
        view.findViewById(R.id.ll_report).setOnClickListener(this);


//        appbar= (AppBarLayout) view.findViewById(R.id.appbar);

        TextView tv_nick = (TextView) view.findViewById(R.id.tv_nick);
//        tv_nick.setText("当前用户："+ SP.geentUername());
       /* try{
            Glide.with(this)
                    .load(UrlConstant.imgURL+MyApp.getInstance().getLoginData().getUserLogo())
                    .into(img_header);
        }catch (Exception e){
            toast(e.toString());
        }*/

    }

    Handler photoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            }
            File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            Uri imageUri = Uri.fromFile(file);
            configCompress(getTakePhoto());
            configTakePhotoOption(getTakePhoto());

            super.handleMessage(msg);
            switch (msg.arg1) {
                case 1://从相册选择照片进行裁剪
                    getTakePhoto().onPickFromCaptureWithCrop(imageUri, getCropOptions());
                    break;
                case 2://从相机拍取照片进行裁剪
                    getTakePhoto().onPickFromDocumentsWithCrop(imageUri, getCropOptions());
                    break;
            }
        }
    };

    //图片获取基础设置设置
    private void configTakePhotoOption(TakePhoto takePhoto) {

        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();

        builder.setWithOwnGallery(true);//使用TakePhoto自带相册

        builder.setCorrectImage(true);//纠正拍照的照片旋转角度

        takePhoto.setTakePhotoOptions(builder.create());


    }

    //takephoto 图片压缩配置
    private void configCompress(TakePhoto takePhoto) {
        if (false) {//是否压缩
            takePhoto.onEnableCompress(null, false);//不压缩时调用
            return;
        }
        int maxSize = 1024 * 100;//压缩最大质量   单位B
        int width = 800;//压缩宽度 px
        int height = 800;//压缩高度 px
        boolean showProgressBar = true;//是否现实进度条
        boolean enableRawFile = false;//拍照压缩后是否保存原图
        CompressConfig config;
        if (true) {//压缩工具
            config = new CompressConfig.Builder()
                    .setMaxSize(maxSize)
                    .setMaxPixel(width >= height ? width : height)
                    .enableReserveRaw(enableRawFile)
                    .create();
        } else {//压缩工具 Luban
            LubanOptions option = new LubanOptions.Builder()
                    .setMaxHeight(height)
                    .setMaxWidth(width)
                    .setMaxSize(maxSize)
                    .create();
            config = CompressConfig.ofLuban(option);
            config.enableReserveRaw(enableRawFile);
        }
        takePhoto.onEnableCompress(config, showProgressBar);
    }

    //图片剪切设置
    private CropOptions getCropOptions() {
        int height = 800;
        int width = 800;
        boolean withWonCrop = true;//是否使用takephoto自带

        CropOptions.Builder builder = new CropOptions.Builder();

        builder.setOutputX(width).setOutputY(height);//宽x高
//        builder.setAspectX(width).setAspectY(height);//宽/高
        builder.setWithOwnCrop(withWonCrop);
        return builder.create();
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        String photoPath = result.getImage().getCompressPath();
        String fileName = photoPath.substring(photoPath.lastIndexOf("/") + 1, photoPath.length());
        Glide.with(this).load(new File(photoPath)).into(img_header);
//        uploadHeader(ImageUtils.bitmapToString(photoPath),fileName);
    }

    //上传图片
    private void uploadHeader(String base64, String filename) {
//        NetManager.getInstance().uploadFile(getActivity(),Constants.UPLOADFILE,urlhandler,base64,filename);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quit_button:
                Application application = getActivity().getApplication();
                ((App) getActivity().getApplication()).closeAllActivity();
                SharedPreferenceUtil.clearSave();
                //开启登录界面
                Intent intent = new Intent(application, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                application.startActivity(intent);
                break;

            case R.id.ll_resetpwd://修改密码
                getActivity().startActivity(new Intent(getActivity(), ResetPwdActivity.class));
                break;

            case R.id.ll_feedback://意见反馈
                getActivity().startActivity(new Intent(getActivity(), FeedBackActivity.class));
                break;

            case R.id.ll_aboutus://关于我们
                getActivity().startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;

            case R.id.ll_service://关于我们
                getActivity().startActivity(new Intent(getActivity(), LinkServiceActivity.class));
                break;

            case R.id.img_header://头像设置
                imgpop.show();
                break;
            case R.id.ll_baojing://报警信息
                startActivity(new Intent(getActivity(), CallPoliceActivity.class));
                break;
            case R.id.ll_report://生产报表
                startActivity(new Intent(getActivity(), ReportActivity.class));
                break;
            default:
                break;
        }
    }
}
