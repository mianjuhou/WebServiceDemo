package cn.com.jtv.webservicedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_result = (TextView) findViewById(R.id.tv_result);
//        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        getRemoteInfo();
//                    }
//                }).start();
//            }
//        });

        findViewById(R.id.btn_util).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebServiceUtil.initNamespaceAndMethod("http://WebXml.com.cn/","getMobileCodeInfo");
                WebServiceUtil.setPropertyNameAndValue("mobileCode","18669303903");
                WebServiceUtil.setPropertyNameAndValue("userId","");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        WebServiceUtil.call("http://WebXml.com.cn/getMobileCodeInfo","http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx",true);
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_result.setText(WebServiceUtil.getStringResult());
                            }
                        });
                    }
                }).start();
            }
        });
    }

//    private String result;
    /**
     * 未封装时的步骤
     */
//    private void getRemoteInfo() {
//        String nameSpace = "http://WebXml.com.cn/";
//        String methodName = "getMobileCodeInfo";
//        String propertyName1="mobileCode";
//        String propertyValue1="18669303903";
//        String propertyName2="userId";
//        String propertyValue2="";
//
//        String url = "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx";
//        String soapAction = nameSpace + methodName;
//        ////配置SoapObject，域、方法、参数////
//        SoapObject soapOut = new SoapObject(nameSpace, methodName);
//        soapOut.addProperty(propertyName1, propertyValue1);
//        soapOut.addProperty(propertyName2, propertyValue2);
//        ////序列化要传送的信息SoapSerializationEnvelope、设置服务器平台////
//        SoapSerializationEnvelope evelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
//        evelope.bodyOut = soapOut;
//        evelope.dotNet = true;
//        evelope.setOutputSoapObject(soapOut);
//        ////设置发送信息的地址/////
//        HttpTransportSE transport = new HttpTransportSE(url);
//        try {
//            transport.call(soapAction, evelope);
//            ////从响应结果中提取信息/////
//            SoapObject soapIn = (SoapObject) evelope.bodyIn;
//            result = soapIn.getProperty(0).toString();
//            ////显示需要的信息//////
//            MainActivity.this.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    tv_result.setText(result);
//                }
//            });
//        } catch (IOException e) {
//            Log.e("WebServiceDemo","请求时发生网络异常");
//            throw new RuntimeException(e);
//        } catch (XmlPullParserException e) {
//            Log.e("WebServiceDemo","解析相应结果的XML时发生异常");
//            throw new RuntimeException(e);
//        }
//    }
}
