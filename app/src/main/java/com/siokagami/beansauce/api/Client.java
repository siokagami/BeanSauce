package com.siokagami.beansauce.api;

/**
 * Created by SiOKagami on 2016/5/4.
 */
import android.content.Context;
import android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.LogInterface;
import com.loopj.android.http.RequestParams;
import com.siokagami.beansauce.utils.DeviceUtil;
import cz.msebera.android.httpclient.entity.StringEntity;
import java.io.UnsupportedEncodingException;
public class Client {
    /**
     * 服务器类型:测试、发布
     * 本应用中测试环境与发布环境相同，该Client类可在不同APP中适当使用
     */
    private static final byte TYPE_TEST = 0x01;
    private static final byte TYPE_RELEASE = 0x02;
    private static final byte clientType = TYPE_RELEASE;
    /**
     * 不同的域名对应不同的Client
     */
    private static AsyncHttpClient authClient;
    private static AsyncHttpClient appClient;
    /**
     * 设置Client属性
     *
     * @param client AsyncHttpClient
     */
    private static void setClient(AsyncHttpClient client) {
        client.setLoggingEnabled(true);
        client.setLoggingLevel(LogInterface.INFO);
        client.setEnableRedirects(true);
    }
    /**
     * 获取Client,以供使用(大多数用不到)
     * @return AsyncHttpClient
     */
    public static AsyncHttpClient getAuthClient() {
        return authClient;
    }
    /**
     * 如果要添加登录功能则需要使用该Client，故作保留
     */
    public static void setAuthClient(AsyncHttpClient authClient) {
        Client.authClient = authClient;
        setClient(Client.authClient);
        authClient.setMaxRetriesAndTimeout(3, 5 * 1000);
        if (clientType == TYPE_RELEASE) {
            authClient.setBasicAuth("", "");
        } else if (clientType == TYPE_TEST) {
            authClient.setBasicAuth("", "");
        }
    }
    public static AsyncHttpClient getAppClient() {
        return appClient;
    }
    public static void setAppClient(AsyncHttpClient appClient) {
        Client.appClient = appClient;
        setClient(Client.appClient);
    }
    private static String getHostName(AsyncHttpClient client) {
        if (client == authClient) {
            switch (clientType) {
                case TYPE_TEST:
                    return "https://api.douban.com";
                case TYPE_RELEASE:
                    return "https://api.douban.com";
                default:
                    return "";
            }
        } else if (client == appClient) {
            switch (clientType) {
                case  TYPE_TEST:
                    return "https://api.douban.com";
                case TYPE_RELEASE:
                    return "https://api.douban.com";
                default:
                    return "";
            }
        }  else {
            return "";
        }
    }
    public static void get(AsyncHttpClient client, Context context, String url, RequestParams params,
                           AsyncHttpResponseHandler responseHandler) {
        client.setUserAgent("Sauce-Android-" + DeviceUtil.getVersionName(context));
        client.get(context, getHostName(client) + url, params, responseHandler);
        Log.wtf("API", "　get　" + getHostName(client) + url + "　" + params);
    }
    public static void get(AsyncHttpClient client, Context context, String url,
                           AsyncHttpResponseHandler responseHandler) {
        client.setUserAgent("Sauce-Android-" + DeviceUtil.getVersionName(context));
        client.get(context, getHostName(client) + url, responseHandler);
        Log.wtf("API", "　get　" + getHostName(client) + url);
    }
    public static void patch(AsyncHttpClient client, Context context, String url,
                             RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.setUserAgent("Sauce-Android-" + DeviceUtil.getVersionName(context));
        client.patch(context, getHostName(client) + url, params, responseHandler);
        Log.wtf("API", "　patch　" + getHostName(client) + url + "　" + params);
    }
    public static void post(AsyncHttpClient client, Context context, String url, RequestParams params,
                            AsyncHttpResponseHandler responseHandler) {
        client.setUserAgent("Sauce-Android-" + DeviceUtil.getVersionName(context));
        client.post(context, getHostName(client) + url, params, responseHandler);
        Log.wtf("API", "　post　" + getHostName(client) + url + "　" + params);
    }
    public static void postUpload(AsyncHttpClient client, Context context, RequestParams params,
                                  AsyncHttpResponseHandler responseHandler) {
        client.setUserAgent("Sauce-Android-" + DeviceUtil.getVersionName(context));
        client.post(context, getHostName(client), params, responseHandler);
        Log.wtf("API", "　post　" + getHostName(client) + "　" + params);
    }
    public static void post(AsyncHttpClient client, Context context, String url, String json,
                            AsyncHttpResponseHandler handler) throws UnsupportedEncodingException {
        client.setUserAgent("Sauce-Android-" + DeviceUtil.getVersionName(context));
        StringEntity entity = new StringEntity(json, "UTF-8");
        client.post(context, getHostName(client) + url, entity, "application/json;charset=UTF-8",
                handler);
        Log.wtf("API", "　post　" + getHostName(client) + url + "　" + json);
    }
    public static void put(AsyncHttpClient client, Context context, String url, RequestParams params,
                           AsyncHttpResponseHandler responseHandler) {
        client.setUserAgent("Sauce-Android-" + DeviceUtil.getVersionName(context));
        client.put(context, getHostName(client) + url, params, responseHandler);
        Log.wtf("API", "　put　" + getHostName(client) + url + "　" + params);
    }
    public static void delete(AsyncHttpClient client, Context context, String url,
                              RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.setUserAgent("Sauce-Android-" + DeviceUtil.getVersionName(context));
        client.delete(getHostName(client) + url, params, responseHandler);
        Log.wtf("API", "　delete　" + getHostName(client) + url + "　" + params);
    }
}
