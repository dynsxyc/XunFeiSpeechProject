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
    fun onResult(resultStr:String)
    fun onStart()
    fun onFinish()
    fun onVolumeChanged(volume: Int)
    fun onError(error: MSpeechError,errorMsg:String)
}