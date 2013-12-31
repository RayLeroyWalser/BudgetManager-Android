package com.jpintado.budgetmanager.library.handler;

public class StringResponseHandler extends BaseResponseHandler
{
    public void onSuccess(String response) {}
    public void onProgressMessage(Integer response) {}

    @Override
    public void onProgressUpdate(Object statusCode)
    {
        onProgressMessage((Integer) statusCode);
    }

    @Override
    protected void handleSuccessMessage(Object response)
    {
        onSuccess((String) response);
    }
}
