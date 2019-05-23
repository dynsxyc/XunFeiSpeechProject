package com.zhongjiang.youxuan.speechlib

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.iflytek.cloud.*
import org.json.JSONException
import org.json.JSONObject

/**
 * @date on 2019/5/23 15:23
 * @packagename com.zhongjiang.youxuan.speechlib
 * @author dyn
 * @fileName SpeechRecognizerListener
 * @org com.zhongjiang.youxuan
 * @describe 添加描述
 * @email 583454199@qq.com
 **/
class SpeechRecognizerListener constructor(val callback: ISpeechRecognizerCallback?) : InitListener, RecognizerListener {
    companion object {
        var mResultBuffer = StringBuffer()
    }
    @SuppressLint("LongLogTag")
    override  fun onInit(code: Int) {
        if (code != ErrorCode.SUCCESS) {
            callback?.let {
                it.onListeningError(MSpeechError.ERROR_INIT, "语音识别功能初始化失败")
            }
            Log.i("SpeechRecognizerOperation","初始化失败 = $code")
        }
    }

    @SuppressLint("LongLogTag")
    override fun onBeginOfSpeech() {
        // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
        callback?.let {
            it.onListeningStart()
        }
        mResultBuffer.setLength(0)
        Log.i("SpeechRecognizerOperation","开始录音")
    }

    @SuppressLint("LongLogTag")
    override fun onError(error: SpeechError?) {
        // Tips：
        // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
//        if (mTranslateEnable && error.errorCode == 14002) {
//            showTip(error.getPlainDescription(true) + "\n请确认是否已开通翻译功能")
//        } else {
//            showTip(error.getPlainDescription(true))
//        }
        mResultBuffer.setLength(0)
        if (error != null) {
            callback?.let {
                if (error.errorCode == 10118) {

                    it.onListeningError(MSpeechError.ERROR_NO_SPEECH, "您可能没有说话")
                } else {
                    it.onListeningError(MSpeechError.ERROR_NO_SPEECH, error.getPlainDescription(true))
                }
            }
            Log.i("SpeechRecognizerOperation","onListeningError ${error.getPlainDescription(true)}")
        }

    }

    @SuppressLint("LongLogTag")
    override fun onEndOfSpeech() {
        // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
//        showTip("结束说话")
        Log.i("SpeechRecognizerOperation","结束说话")
        callback?.let {
            it.onListeningFinish()
        }
    }

    override fun onResult(results: RecognizerResult?, isLast: Boolean) {
        printResult(results,isLast)

    }

    @SuppressLint("LongLogTag")
    private fun printResult(results: RecognizerResult?,isLast: Boolean) {
        if (results == null){
            Log.i("SpeechRecognizerOperation","results == null")
            callback?.let {
                it.onListeningResult("")
            }
            return
        }
        val text = JsonParser.parseIatResult(results.resultString)

        var sn: String = ""
        // 读取json结果中的sn字段
        try {
            val resultJson = JSONObject(results.resultString)
            sn = resultJson.optString("sn")
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        var linkedHashMap = linkedMapOf<String,String>()
        linkedHashMap.put(sn, text)


        linkedHashMap.mapKeys {
            mResultBuffer.append(it.value)
        }
        if (isLast) {
            callback?.let {
                it.onListeningResult(mResultBuffer.toString())
            }
            Log.i("SpeechRecognizerOperation","onListeningResult ${mResultBuffer.toString()}")
        }
    }

    @SuppressLint("LongLogTag")
    override fun onVolumeChanged(volume: Int, data: ByteArray?) {
//        showTip("当前正在说话，音量大小：$volume")
//        Log.d(TAG, "返回音频数据：" + data.size)
        callback?.let {
            it.onListeningVolumeChanged(volume)
        }
        Log.i("SpeechRecognizerOperation","语音大小 $volume")
    }

    @SuppressLint("LongLogTag")
    override fun onEvent(eventType: Int, arg1: Int, arg2: Int, obj: Bundle?) {
        // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
        // 若使用本地能力，会话id为null
        //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
        //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
        //		Log.d(TAG, "session id =" + sid);
        //	}

//        Log.i("SpeechRecognizerOperation","onEvent")
    }
}