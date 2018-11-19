package com.kaicom.mymvptest.files;

import android.content.Context;
import android.content.SharedPreferences;

import static com.kaicom.mymvptest.MyMvpApplication.app;
import static com.kaicom.mymvptest.files.AppContants.NICK_NAME;
import static com.kaicom.mymvptest.files.AppContants.USER_SYNOPSIS;

/**
 * Created by LeoJin on 2018/11/20.
 */

public class FilePreference {

    private static final String FILE_PREFERENCE_NAME = "sport_file_preference_name";
    private static FilePreference filePreference;
    private Context context;
    private SharedPreferences sp;

    private FilePreference() {
        this.context = context;
        sp = app.getSharedPreferences(FILE_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized FilePreference getInstance(){
        if(filePreference==null){
            filePreference = new FilePreference();
        }
        return filePreference;
    }

    /**
     * 设置用户昵称
     * @param nickname
     */
    public void setNickname(String nickname){
        sp.edit().putString(NICK_NAME,nickname).apply();
    }

    /**
     * 获取用户昵称
     * @return
     */
    public String getNickname(){
        return sp.getString(NICK_NAME,"");
    }
    /**
     * 设置用户简介
     * @param synopsis
     */
    public void setSynopsis(String synopsis){
        sp.edit().putString(USER_SYNOPSIS,synopsis).apply();
    }

    /**
     * 获取用户简介
     * @return
     */
    public String getSynopsis(){
        return sp.getString(USER_SYNOPSIS,"");
    }
}
