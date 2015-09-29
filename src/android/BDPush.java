package com.jianzhi_inc.dandelion.bdpush;

import android.util.Log;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Baidu Push Message Cordova Plugin
 * Created by Sam on 9/19/15.
 */
public class BDPush extends CordovaPlugin {

    public final static String ACTION_BIND = "bind";
    public final static String ACTION_UNBIND = "unbind";


    //public final static String CALLBACK_BIND_SUCCESS = "bind_success";

    private static String apiKey;
    public static CallbackContext currentCallbackContext;

    /** TAG to Log */
    public static final String TAG = BDPush.class.getSimpleName();


    @Override
    protected void pluginInitialize() {
        super.pluginInitialize();
        if(null == apiKey) {
            apiKey = preferences.getString("BD_PUSH_API_KEY", "");
        }
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        Log.i(TAG, action + " is called, param [" + args.toString() + "]");

        if(ACTION_BIND.equals(action)) {
            return bindBDPush(args, callbackContext);
        }
        if(ACTION_UNBIND.equals(action)) {
            return unbindBDPush(args, callbackContext);
        }
        return super.execute(action, args, callbackContext);
    }


    private boolean bindBDPush(JSONArray args, CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                PushManager.startWork(webView.getContext(), PushConstants.LOGIN_TYPE_API_KEY, apiKey);
                currentCallbackContext = callbackContext;
            }
        });
        return true;
    }

    private boolean unbindBDPush(JSONArray args, CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                if(PushManager.isConnected(webView.getContext())) {
                    PushManager.stopWork(webView.getContext());
                    currentCallbackContext = callbackContext;
                }
            }
        });
        return true;
    }
}
