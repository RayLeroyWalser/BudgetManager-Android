/*
    Android Asynchronous Http Client
    Copyright (c) 2011 James Smith <james@loopj.com>
    http://loopj.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.jpintado.budgetmanager.library.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public abstract class BaseResponseHandler {
    //region Constants
    protected static final int SUCCESS_MESSAGE  = 0;
    protected static final int FAILURE_MESSAGE  = 1;
    protected static final int START_MESSAGE    = 2;
    protected static final int FINISH_MESSAGE   = 3;
    protected static final int PROGRESS_MESSAGE = 4;
    //endregion

    //region Variables
    private Handler handler;
    //endregion

    //region Constructor
    public BaseResponseHandler() {
        // Set up a handler to post events back to the correct thread if possible
        if (Looper.myLooper() != null) {
            handler = new Handler()
            {
                @Override
                public void handleMessage(Message msg)
                {
                    BaseResponseHandler.this.handleMessage(msg);
                }
            };
        }
    }
    //endregion

    public void sendStartMessage() {
        sendMessage(obtainMessage(START_MESSAGE, null));
    }

    public void sendFinishMessage() {
        sendMessage(obtainMessage(FINISH_MESSAGE, null));
    }

    public void sendFailureMessage(Object message) {
        sendMessage(obtainMessage(FAILURE_MESSAGE, message));
    }

    public void sendProgressMessage(Object message) {
        sendMessage(obtainMessage(PROGRESS_MESSAGE, message));
    }

    public void sendSuccessMessage(Object response) {
        sendMessage(obtainMessage(SUCCESS_MESSAGE, response));
    }

    protected Message obtainMessage(int responseMessage, Object response) {
        Message msg;
        if(handler != null) {
            msg = this.handler.obtainMessage(responseMessage, response);
        }
        else {
            msg = new Message();
            msg.what = responseMessage;
            msg.obj = response;
        }
        return msg;
    }

    protected void handleMessage(Message msg) {
        switch(msg.what) {
            case SUCCESS_MESSAGE:
                handleSuccessMessage(msg.obj);
                break;
            case FAILURE_MESSAGE:
                if(msg.obj instanceof String)
                    onFailure((String) msg.obj);
                else
                    onFailure(msg.obj);
                break;
            case START_MESSAGE:
                onStart();
                break;
            case FINISH_MESSAGE:
                onFinish();
                break;
            case PROGRESS_MESSAGE:
                onProgressUpdate(msg.obj);
                break;
        }
    }

    private void sendMessage(Message msg) {
        if(handler != null)
            handler.sendMessage(msg);
    }

    public void onStart() {}
    public void onFinish() {}
    public void onProgressUpdate(Object statusCode) {}
    public void onFailure(String message) {}
    protected void onFailure(Object failureObj) {}

    protected abstract void handleSuccessMessage(Object obj);
}