package com.jpintado.budgetmanager.library;

import android.app.Application;

import com.jpintado.budgetmanager.library.helper.UrlHelper;
import com.jpintado.budgetmanager.library.manager.CredentialManager;
import com.jpintado.budgetmanager.library.provider.InstitutionProvider;
import com.jpintado.budgetmanager.library.provider.UserInfoProvider;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BMLibrary {

    private static final String BM_HOST = "172.16.1.89";
    private static final String BM_PORT = "8001";
    private static final String BM_API_PREFIX = "/api";
    private static final String BM_OFX_PREFIX = "/ofx";

    private static final int THREAD_CORE_SIZE = 5;
    private static final int MAX_THREADS = 10;
    private static final int MAX_QUEUE_THREADS = 20;
    private static final long SECONDS_BEFORE_RESIZE_POOL = 60;

    public static CredentialManager credentialManager;
    public static InstitutionProvider institutionProvider;
    public static UserInfoProvider userInfoProvider;
    public static UrlHelper urlHelper;
    private static ExecutorService executorService;

    public BMLibrary(Application application) {
        credentialManager = new CredentialManager();
        userInfoProvider = new UserInfoProvider();
        institutionProvider = new InstitutionProvider();

        urlHelper = new UrlHelper.UrlHelperBuilder(BM_HOST)
                .setProtocol(UrlHelper.PROTOCOL_HTTP)
                .setPort(BM_PORT)
                .setApiPath(BM_API_PREFIX)
                .setOfxPath(BM_OFX_PREFIX)
                .build();

        executorService = createExecutorService();
    }

    public static void executeRunnable(Runnable runnable)
    {
        executorService.submit(runnable);
    }

    private static ExecutorService createExecutorService()
    {
        return new ThreadPoolExecutor
                (
                        THREAD_CORE_SIZE,                                   // core thread pool size
                        MAX_THREADS,                                        // maximum thread pool size
                        SECONDS_BEFORE_RESIZE_POOL,                         // time to wait before resizing pool
                        TimeUnit.SECONDS,
                        new ArrayBlockingQueue<Runnable>(MAX_QUEUE_THREADS)
                );
    }
}
