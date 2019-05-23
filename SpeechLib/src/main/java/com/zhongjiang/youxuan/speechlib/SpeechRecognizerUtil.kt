package com.zhongjiang.youxuan.speechlib

import android.os.Bundle
import com.iflytek.cloud.InitListener
import com.iflytek.cloud.RecognizerListener
import com.iflytek.cloud.RecognizerResult
import com.iflytek.cloud.SpeechError

/**
 * @date on 2019/5/23 11:35
 * @packagename com.zhongjiang.youxuan.speechlib
 * @author dyn
 * @fileName SpeechRecognizer
 * @org com.zhongjiang.youxuan
 * @describe 添加描述
 * @email 583454199@qq.com
 **/
class SpeechRecognizerUtil : InitListener, RecognizerListener {
    override fun onInit(p0: Int) {

    }

    override fun onVolumeChanged(p0: Int, p1: ByteArray?) {

    }

    override fun onResult(p0: RecognizerResult?, p1: Boolean) {

    }

    override fun onBeginOfSpeech() {

    }

    override fun onEvent(p0: Int, p1: Int, p2: Int, p3: Bundle?) {

    }

    override fun onEndOfSpeech() {

    }

    override fun onError(p0: SpeechError?) {

    }


}