package com.zhongjiang.youxuan.speechlib

/**
 * @date on 2019/5/23 14:36
 * @packagename com.zhongjiang.youxuan.speechlib
 * @author dyn
 * @fileName ISpeechRecognizerCallback
 * @org com.zhongjiang.youxuan
 * @describe 添加描述
 * @email 583454199@qq.com
 **/
interface ISpeechRecognizerCallback {
    /**
     * 语音识别返回结果
     * */
    fun onListeningResult(resultStr:String)
    /**
     * 语音识别 开始
     * */
    fun onListeningStart()
    /**
     * 语音识别 结束
     * */
    fun onListeningFinish()
    /**
     * 语音识别  音量监听
     * */
    fun onListeningVolumeChanged(volume: Int)
    /**
     * 语音识别 错误返回
     * */
    fun onListeningError(error: MSpeechError, errorMsg:String)
}