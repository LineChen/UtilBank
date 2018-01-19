package com.xiaoyu.xycommon.helpers;

import android.media.MediaRecorder;
import android.os.Environment;

import com.xiaoyu.lib.utils.MyLog;
import com.xiaoyu.lib.utils.ToastUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by chenliu on 2018/1/18.
 * 录制音频
 */

public class AudioRecordHelper {

    private static final String TAG = "AudioRecordHelper";

    public interface AudioRecordListener{
        void onRecordComplete(File audioFile);
    }

    private MediaRecorder mMediaRecorder;
    private File audioFile;
    private AudioRecordListener audioRecordListener;
    private boolean isRecording;

    private AudioRecordHelper(){
        if(isSDCardExist()){
            audioFile = new File(getExternalPath() + "/" + System.currentTimeMillis() + ".m4a");
            mMediaRecorder = new MediaRecorder();
            //配置mMediaRecorder相应参数
            //从麦克风采集声音数据
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //设置保存文件格式为MP4
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            //设置采样频率,44100是所有安卓设备都支持的频率,频率越高，音质越好，当然文件越大
            mMediaRecorder.setAudioSamplingRate(44100);
            //设置声音数据编码格式,音频通用格式是AAC
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            //设置编码频率
            mMediaRecorder.setAudioEncodingBitRate(96000);
            //设置录音保存的文件
            mMediaRecorder.setOutputFile(audioFile.getAbsolutePath());
            try {
                mMediaRecorder.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //toast
            ToastUtil.show("未检测到SD卡");
        }
    }

    public static AudioRecordHelper newInstance(){
        return new AudioRecordHelper();
    }

    public void setAudioRecordListener(AudioRecordListener audioRecordListener) {
        this.audioRecordListener = audioRecordListener;
    }

    public void startRecord(){
        if(isSDCardExist()){
            //开始录音
            if(!isRecording){
                mMediaRecorder.start();
                isRecording = true;
                MyLog.d(TAG, "开始录音");
            }
        } else {
            //toast
            ToastUtil.show("未检测到SD卡");
        }
    }

    public void stopRecord(){
        if(isRecording){
            mMediaRecorder.stop();
            mMediaRecorder.release();
            MyLog.d(TAG, "结束录音");
            isRecording = false;
            if(audioFile.exists()){
                if (audioRecordListener != null) {
                    audioRecordListener.onRecordComplete(audioFile);
                }
            }
        }
    }

    public boolean isRecording() {
        return isRecording;
    }

    private boolean isSDCardExist() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    private String getExternalPath(){
        if(isSDCardExist()){
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }
}
