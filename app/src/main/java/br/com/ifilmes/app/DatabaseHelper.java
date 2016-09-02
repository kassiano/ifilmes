package br.com.ifilmes.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sn1041520 on 01/09/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String NOME_BANCO = "ifilmesdb";
    private static int VERSAO = 1;


    public DatabaseHelper(Context context){
        super(context, NOME_BANCO, null, VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql =" CREATE TABLE filmes( _id INTEGER PRIMARY KEY," +
                " nome TEXT,"+
                " genero TEXT,"+
                " sinopse TEXT,"+
                " imagem TEXT);";


        sqLiteDatabase.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
