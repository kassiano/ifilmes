package br.com.ifilmes.app;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NovoFilmeActivity extends AppCompatActivity {

    EditText txt_nome_filme, txt_genero_filme, txt_sinopse, txt_imagem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_filme);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        txt_nome_filme= (EditText) findViewById(R.id.txt_nome_filme);
        txt_genero_filme= (EditText) findViewById(R.id.txt_genero_filme);
        txt_sinopse= (EditText) findViewById(R.id.txt_sinopse_filme);
        txt_imagem= (EditText) findViewById(R.id.txt_imagem);

    }

    private boolean validacao(){

        boolean retorno = true;

        txt_nome_filme.setError(null);
        txt_genero_filme.setError(null);

        if(txt_nome_filme.getText().toString().isEmpty()){
            txt_nome_filme.setError("Preencha o nome");
            retorno = false;
        }

        if(txt_genero_filme.getText().toString().isEmpty()){
            txt_genero_filme.setError("Preecnha o genero");
            retorno = false;
        }


        return retorno;
    }

    public void salvarFilme(View view) {


        if(!validacao()) return;

        DatabaseHelper helper = new DatabaseHelper(this);

        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", txt_nome_filme.getText().toString());
        valores.put("genero", txt_genero_filme.getText().toString());
        valores.put("sinopse", txt_sinopse.getText().toString());
        valores.put("imagem", txt_imagem.getText().toString());

        long sucesso= db.insert("filmes", null, valores);

        if(sucesso != -1){
            Toast.makeText(this, "inserido cm sucesso", Toast.LENGTH_LONG)
                    .show();
        }else{
            Toast.makeText(this, "erro", Toast.LENGTH_LONG)
                    .show();
        }

    }


}
