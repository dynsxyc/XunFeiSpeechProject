package com.zhongjiang.youxuan.speechlib

import android.content.Context
import android.os.Environment
import com.iflytek.cloud.ErrorCode
import com.iflytek.cloud.SpeechConstant
import com.iflytek.cloud.SpeechConstant.TYPE_CLOUD
import com.iflytek.cloud.SpeechRecognizer
import kotlin.collections.LinkedHashMap as LinkedHashMap1
/**
 * @date on 2019/5/23 11:35
 * @packagename com.zhongjiang.youxuan.speechlib
 * @author dyn
 * @fileName SpeechRecognizer
 * @org com.zhongjiang.youxuan
 * @describe 添加描述
 * @email 583454199@qq.com
 **/
class SpeechRecognizerOperation private constructor(builder: Builder){
    companion object {
       open class Builder constructor(var context: Context) {
            var callback: ISpeechRecognizerCallback? = null
           // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
           var bos:Long= 4000;
           // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
           var eos:Long= 1000;
           var punctuation:Boolean = false
           fun timeOutTime(timeOutTime: Long):Builder{
                if (timeOutTime<4000) {
                    bos = 4000
                }else{
                    bos = timeOutTime
                }
               return this
            }
           fun endOutTime(endOutTime :Long):Builder{
               if (endOutTime<1000){
                   eos = 1000
               }else{
                   eos = endOutTime
               }
               return this
           }
           fun hasPunctuation(punctuation:Boolean):Builder{
               this.punctuation = punctuation
               return this
           }

            fun callBack(callback: ISpeechRecognizerCallback): Builder {
                this.callback = callback
                return this
            }

            fun build(): SpeechRecognizerOperation {
                return SpeechRecognizerOperation(this)
            }

        }
    }
    private var mSpeechRecognizerListener = SpeechRecognizerListener(builder.callback)
    private var mSpeechRecognizer = SpeechRecognizer.createRecognizer(builder.context, mSpeechRecognizerListener)

    init {
        // 清空参数
        mSpeechRecognizer.setParameter(SpeechConstant.PARAMS, null)

        // 设置听写引擎
        mSpeechRecognizer.setParameter(SpeechConstant.ENGINE_TYPE, TYPE_CLOUD)
        // 设置返回结果格式
        mSpeechRecognizer.setParameter(SpeechConstant.RESULT_TYPE, "json")

        // 设置语言
        mSpeechRecognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        // 设置语言区域
        mSpeechRecognizer.setParameter(SpeechConstant.ACCENT, "mandarin");

        //此处用于设置dialog中不显示错误码信息
        //mSpeechRecognizer.setParameter("view_tips_plain","false");

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mSpeechRecognizer.setParameter(SpeechConstant.VAD_BOS, builder.bos.toString());

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mSpeechRecognizer.setParameter(SpeechConstant.VAD_EOS, builder.eos.toString());

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mSpeechRecognizer.setParameter(SpeechConstant.ASR_PTT, if (builder.punctuation) "1" else "0" )

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        mSpeechRecognizer.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mSpeechRecognizer.setParameter(SpeechConstant.ASR_AUDIO_PATH, "${Environment.getExternalStorageDirectory()}/msc/iat.wav");
    }

    fun stopListening(){
        mSpeechRecognizer.stopListening()
    }
    fun startListening(){
       var result =  mSpeechRecognizer.startListening(mSpeechRecognizerListener)
        if (result != ErrorCode.SUCCESS){
            mSpeechRecognizerListener.callback?.let {
                it.onError(MSpeechError.ERROR_UNKNOWN,"startListening 失败")
            }
        }
    }
    fun cancelListening(){
        mSpeechRecognizer.cancel()
    }


}

