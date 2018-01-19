package com.xiaoyu.xycommon.helpers;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

/**
 * Created by chenliu on 2018/1/18.
 * 麦克风大小检测
 */

public final class MicphoneDetectHelper {

    private MicphoneDetectHelper(){
    }

    public static MicphoneDetectHelper newInstance(){
        return new MicphoneDetectHelper();
    }

    public interface MicphoneDetectListener {
        void onVolumeChanged(int volume);
    }

    private static final int SAMPLE_RATE_IN_HZ = 8000;
    private static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ,
            AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);
    private AudioRecord mAudioRecord;
    private boolean isGetVoiceRun;
    private MicphoneDetectListener micphoneDetectListener;

    public void setMicphoneDetectListener(MicphoneDetectListener micphoneDetectListener) {
        this.micphoneDetectListener = micphoneDetectListener;
    }

    public void stopDetest(){
        isGetVoiceRun = false;
        if (micphoneDetectListener != null) {
            micphoneDetectListener.onVolumeChanged(0);
        }
    }

    public void startDetest() {
        if (isGetVoiceRun) {
            return;
        }
        if(mAudioRecord == null){
            mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                    SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT,
                    AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE);
        }
        isGetVoiceRun = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                mAudioRecord.startRecording();
                short[] buffer = new short[BUFFER_SIZE];
                while (isGetVoiceRun) {
                    //r是实际读取的数据长度，一般而言r会小于buffersize
                    int r = mAudioRecord.read(buffer, 0, BUFFER_SIZE);
                    long v = 0;
                    // 将 buffer 内容取出，进行平方和运算
                    for (int i = 0; i < buffer.length; i++) {
                        v += buffer[i] * buffer[i];
                    }
                    // 平方和除以数据总长度，得到音量大小。
                    double mean = v / (double) r;
                    double volume = 10 * Math.log10(mean);
                    if (micphoneDetectListener != null) {
                        micphoneDetectListener.onVolumeChanged((int) volume);
                    }

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mAudioRecord.stop();
                mAudioRecord.release();
                mAudioRecord = null;
            }
        }).start();
    }
}
