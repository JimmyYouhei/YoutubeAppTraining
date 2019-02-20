package vn.org.quantestyoutube2.prm391x_project_4_se00409x.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import vn.org.quantestyoutube2.prm391x_project_4_se00409x.entity.UserAccount;

// class that include and manage account database
public class DatabaseSource {

    private Context mContext;
    private SQLiteDatabase mAccountDatabase;
    SQLiteOpenHelper mOpenHelper;

    // contructor
    public DatabaseSource (Context context){
        mContext = context;
        mOpenHelper = new AccountDatabaseOpenHelper(mContext);
        mAccountDatabase = mOpenHelper.getWritableDatabase();
    }

    // initialize or acquire database
    public void open(){
        mAccountDatabase = mOpenHelper.getWritableDatabase();
    }

    // close to avoid leak
    public void close() {
        mOpenHelper.close();
    }

    // insert method for database
    public void insert(UserAccount account){
        ContentValues values = account.toValues();
        mAccountDatabase.insert(UserAccountTable.TABLE_NAME , null , values);
    }

    // count item method in database
    public long countAccount(){
        return DatabaseUtils.queryNumEntries(mAccountDatabase , UserAccountTable.TABLE_NAME);
    }

    // method to transfer all account to a List using Cursor
    public List<UserAccount> getAllAccount (){
        List<UserAccount> userAccountList = new ArrayList<>();

        Cursor cursor = mAccountDatabase.query(UserAccountTable.TABLE_NAME , UserAccountTable.ALL_COLUMN ,
                null,null,null,null, UserAccountTable.COLUMN_NAME);

        while (cursor.moveToNext()){
            UserAccount account = new UserAccount();
            account.setUsername(cursor.getString(cursor.getColumnIndex(UserAccountTable.COLUMN_NAME)));
            account.setPassword(cursor.getString(cursor.getColumnIndex(UserAccountTable.COLUMN_PASSWORD)));

            userAccountList.add(account);
        }

        cursor.close();
        return userAccountList;
    }

}