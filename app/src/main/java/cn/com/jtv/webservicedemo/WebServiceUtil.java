package cn.com.jtv.webservicedemo;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 *
 * Created by fangdean on 2016/3/1.
 * <p>
 * 使用步骤:每次使用都要按照如下顺序调用方法
 * <p>
 * 1.调用initNamespaceAndMethod()
 * <p>
 * 2.多次调用setPropertyNameAndValue()
 * <p>
 * 3.在子线程中调用call()方法
 * <p>
 * 4.调用getStringResult()方法获取字符串类型的结果
 */
public class WebServiceUtil {
    private static SoapObject soapOut;
    private static SoapObject soapIn;
    /**
     * 传入域名和方法名
     * @param nameSpace
     * @param methodName
     */
    public static void initNamespaceAndMethod(String nameSpace,String methodName){
        soapOut=new SoapObject(nameSpace,methodName);
    }

    /**
     *
     * @param propertyName
     * @param propertyValue
     */
    public static void setPropertyNameAndValue(String propertyName,String propertyValue){
        soapOut.addProperty(propertyName,propertyValue);
    }

    /**
     * 开始进行网络请求，比较耗时，要在子线程中实现
     * <p>
     * 如果服务端
     * @param soapAction  动作，通常是域和方法的组合
     * @param url    发送请求的地址
     * @param isDotNet 如果服务端开发平台是.Net的，此参数为true，否则为false
     */
    public static void call(String soapAction,String url,boolean isDotNet){
        SoapSerializationEnvelope evelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
        evelope.bodyOut = soapOut;
        evelope.dotNet = isDotNet;
        evelope.setOutputSoapObject(soapOut);

        HttpTransportSE transport = new HttpTransportSE(url);
        try {
            transport.call(soapAction, evelope);
            soapIn = (SoapObject) evelope.bodyIn;
        } catch (IOException e) {
            Log.e("WebServiceDemo","请求时发生网络异常");
            throw new RuntimeException(e);
        } catch (XmlPullParserException e) {
            Log.e("WebServiceDemo","解析相应结果的XML时发生异常");
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取结果返回结果中的第一个字符串数据
     * @return
     */
    public static String getStringResult(){
        String result = soapIn.getProperty(0).toString();
        return result;
    }
}
