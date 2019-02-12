package shp.template.CustomView.Utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import shp.template.ActivityLogin;
import shp.template.ActivityPickAccount;

import com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral;
import com.oktaviani.dewi.mylibrary.authenticator.RecyclerGridPickAccountAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_ARRAY_ACCOUNT_AVAILABLE;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_ARRAY_ACCOUNT_NAME;
import static com.oktaviani.dewi.mylibrary.authenticator.AccountGeneral.ARG_ARRAY_DATA_TOKEN;

/**
 * Created by Dewi Oktaviani on 1/22/2018.
 */

public class AuthenticatorUtil{
    String[] accounts;
    private String IS_FROM_PICK_ACCOUNT = "is from pick account";
    /**
     * Add new account to the account manager
     * @param accountType
     * @param authTokenType
     */
    public void addNewAccount(final Activity context, final AccountManager mAccountManager, final String accountType, final String authTokenType, final boolean isFromPickAccount) {
        Bundle bundles = new Bundle();
        bundles.putString(ARG_ARRAY_DATA_TOKEN, "Add account inside apps");
        final AccountManagerFuture<Bundle> future = mAccountManager.addAccount(accountType, "app", null, bundles, context, new AccountManagerCallback<Bundle>() {
            @Override
            public void run(AccountManagerFuture<Bundle> future) {
                try {
                    Bundle bnd = future.getResult();
                    boolean newAccount = bnd.getBoolean(AccountGeneral.ARG_IS_ADDING_NEW_ACCOUNT, false);
                    final Account availableAccounts[] = countingAccount(mAccountManager);

                    Intent intent = new Intent(context, ActivityLogin.class);
                    intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                    intent.putExtra(AccountGeneral.ARG_AUTH_TYPE, authTokenType);
                    intent.putExtra(AccountGeneral.ARG_IS_ADDING_NEW_ACCOUNT, newAccount);
                    intent.putExtra(ARG_ARRAY_ACCOUNT_AVAILABLE, availableAccounts);
                    intent.putExtra(IS_FROM_PICK_ACCOUNT, isFromPickAccount);
                    context.finish();
                    context.startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, null);
    }

    /**
     * Show all the accounts registered on the account manager. Request an auth token upon user select.
     * @param authTokenType
     */
    public void showAccountPicker(final Activity context, final AccountManager mAccountManager, final String authTokenType) {
        final Account availableAccounts[] = countingAccount(mAccountManager);
        if (availableAccounts.length == 0) {
            addNewAccount(context, mAccountManager, AccountGeneral.ACCOUNT_TYPE, authTokenType, false);
        } else {
            String name[] = new String[availableAccounts.length];
            for (int i = 0; i < availableAccounts.length; i++) {
                name[i] = availableAccounts[i].name;
            }

            Intent myIntent = new Intent(context, ActivityPickAccount.class);
            myIntent.putExtra(ARG_ARRAY_ACCOUNT_AVAILABLE, availableAccounts);
            myIntent.putExtra(ARG_ARRAY_ACCOUNT_NAME, name);
            context.finish();
            context.startActivity(myIntent);
        }
    }

    public Account[] countingAccount(final AccountManager mAccountManager){
        final Account availableAccounts[] = mAccountManager.getAccountsByType(AccountGeneral.ACCOUNT_TYPE);
        return availableAccounts;
    }
    /**
     * Get the auth token for an existing account on the AccountManager
     * @param account
     * @param authTokenType
     */
    public void getExistingAccountAuthToken(final Activity activity, final AccountManager mAccountManager, final Account account, final String authTokenType) {
        String userName = account.name;
        String accountType = account.type;
        final String password = mAccountManager.getPassword(account);
        accounts = new String[]{userName, password, accountType, authTokenType};
        new ActivityPickAccount().getRole(accounts, activity, mAccountManager);
        Log.d("kalbe", "GetToken Bundle is ");
    }

    public void RenameAccount(final AccountManager mAccountManager, final Account account, Activity activity, final String accountPassword, final String authtokenType){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            mAccountManager.removeAccount(account, activity, new AccountManagerCallback<Bundle>() {
                @Override
                public void run(AccountManagerFuture<Bundle> future) {
//                    try {
//                        Bundle bundle = future.getResult();
//                        String authtoken = "dummy_access_token";
//                        mAccountManager.addAccountExplicitly(account, accountPassword, null);
//                        mAccountManager.setAuthToken(account, authtokenType, authtoken);
//                    } catch (OperationCanceledException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (AuthenticatorException e) {
//                        e.printStackTrace();
//                    }
                }
            }, null);
        }
    }

    public void RemoveAccount(final AccountManager mAccountManager, final Account account, final  Activity activity, final RecyclerGridPickAccountAdapter adapter, final List<String> listAccount, final Account availableAccounts[], final int position, final String accountType, final String authTokenType, final boolean isFromPickAccount){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            mAccountManager.removeAccount(account, activity, new AccountManagerCallback<Bundle>() {
                @Override
                public void run(AccountManagerFuture<Bundle> future) {
                    listAccount.remove(position);
                    remove(availableAccounts, account);
                    adapter.notifyDataSetChanged();
                    if (listAccount.size()==0){
                        addNewAccount(activity, mAccountManager, accountType, authTokenType , isFromPickAccount);
                    }
                }
            }, null);
        }else {
            mAccountManager.removeAccountExplicitly(account);
            listAccount.remove(position);
            remove(availableAccounts, account);
            adapter.notifyDataSetChanged();
            if (listAccount.size()==0){
                addNewAccount(activity, mAccountManager, accountType, authTokenType , isFromPickAccount);
            }
        }
    }

    private Account[] remove(Account[] accounts, Account item){
        Account[] account = accounts;
        List<Account> list = new ArrayList<Account>(Arrays.asList(account));
        list.remove(item);
        account = list.toArray(account);
        return  account;
    }

}
