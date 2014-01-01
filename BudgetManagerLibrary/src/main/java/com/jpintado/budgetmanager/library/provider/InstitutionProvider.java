package com.jpintado.budgetmanager.library.provider;

import com.jpintado.budgetmanager.library.BMLibrary;
import com.jpintado.budgetmanager.library.handler.BaseResponseHandler;
import com.jpintado.budgetmanager.library.handler.InstitutionCredentialsListResponseHandler;
import com.jpintado.budgetmanager.library.handler.InstitutionResponseHandler;
import com.jpintado.budgetmanager.library.handler.JSONArrayResponseHandler;
import com.jpintado.budgetmanager.library.handler.StringResponseHandler;
import com.jpintado.budgetmanager.library.model.Institution;
import com.jpintado.budgetmanager.library.runnable.AddInstitutionRunnable;
import com.jpintado.budgetmanager.library.runnable.GetAccountListRunnable;
import com.jpintado.budgetmanager.library.runnable.GetInstitutionInfoRunnable;
import com.jpintado.budgetmanager.library.runnable.GetInstitutionListRunnable;
import com.jpintado.budgetmanager.library.runnable.SearchInstitutionRunnable;

public class InstitutionProvider {

    public void searchInstitution(String query, JSONArrayResponseHandler responseHandler) {
        BMLibrary.executeRunnable(new SearchInstitutionRunnable(query, responseHandler));
    }

    public void getInstitutionInfo(String id, InstitutionResponseHandler responseHandler) {
        BMLibrary.executeRunnable(new GetInstitutionInfoRunnable(id, responseHandler));
    }

    public void getAccountList(BaseResponseHandler responseHandler) {
        BMLibrary.executeRunnable(new GetAccountListRunnable(responseHandler));
    }

    public void addInstitution(Institution institution, String username, String password, StringResponseHandler responseHandler) {
        BMLibrary.executeRunnable(new AddInstitutionRunnable(institution, username, password, responseHandler));
    }

    public void getInstitutionList(InstitutionCredentialsListResponseHandler responseHandler) {
        BMLibrary.executeRunnable(new GetInstitutionListRunnable(responseHandler));
    }
}
