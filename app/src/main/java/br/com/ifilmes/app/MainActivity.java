package br.com.ifilmes.app;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
    implements AdapterView.OnItemSelectedListener
{

    Spinner spinner_genero;
    ListView list_view_sugestoes;
    ImageView img_banner;

    List<String> lstGeneros;
    List<String> lstFilmesComedia;
    List<String> lstFilmesAcao;
    List<String> lstFilmesTerror;
    List<String> lstFilmesDrama;

    //Adaptadores
    ArrayAdapter<String> spinnerAdapter;
    ArrayAdapter<String> listAdapter;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;//definiindo o contexto

        preecherFilmes();

        spinner_genero = (Spinner) findViewById(R.id.spinner_genero);
        list_view_sugestoes =(ListView) findViewById(R.id.list_view_sugestoes);
        img_banner = (ImageView) findViewById(R.id.img_banner);

        //criacao do adapter
        spinnerAdapter = new ArrayAdapter<String>(
                this, //contextop
                R.layout.spinner_item, //layout dos itens
                lstGeneros // lista cm o conteudo
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_genero.setAdapter(spinnerAdapter);


        //setar o listnner da seleção do spinner
        spinner_genero.setOnItemSelectedListener(this);


        //Estou preenchendo o adapter inicialmente com uma lista vazia
        listAdapter = new ArrayAdapter<String>(
                this, //contexto
                R.layout.list_view_item, //layout dos itens
                new ArrayList<String>()
        );


        list_view_sugestoes.setAdapter(listAdapter);

        list_view_sugestoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, DetalhesActivity.class);
                startActivity(intent);
            }
        });
    }


    private void preecherFilmes(){
        lstGeneros = new ArrayList<>();
        lstFilmesComedia = new ArrayList<>();
        lstFilmesAcao = new ArrayList<>();
        lstFilmesTerror = new ArrayList<>();
        lstFilmesDrama = new ArrayList<>();

        lstGeneros.add("Comédia");
        lstGeneros.add("Ação");
        lstGeneros.add("Terror");
        lstGeneros.add("Drama");


        lstFilmesComedia.add("As Branquelas");
        lstFilmesComedia.add("Esqueceram de mim");
        lstFilmesComedia.add("50 tons de preto");
        lstFilmesComedia.add("Os 6 Ridiculos");

        lstFilmesAcao.add("Velozes e Furiosos");
        lstFilmesAcao.add("Duro de matar");
        lstFilmesAcao.add("Velocidade máxima");
        lstFilmesAcao.add("Rambo");

        lstFilmesTerror.add("O chamado");
        lstFilmesTerror.add("O exorcista");
        lstFilmesTerror.add("Annabelle");
        lstFilmesTerror.add("Invocação do mal");

        lstFilmesDrama.add("O gladiador");
        lstFilmesDrama.add("Um sonho de liberdade");
        lstFilmesDrama.add("Resgate do soldado Ryan");
        lstFilmesDrama.add("Diario de uma paixão");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String item = (String)adapterView.getSelectedItem();
        //Toast.makeText(context, "Voce selecionou " + item, Toast.LENGTH_LONG).show();

        //limpar os items do adapter
        listAdapter.clear();

        if(item.equals("Comédia")){
           // listAdapter.addAll(lstFilmesComedia);
            img_banner.setImageResource(R.drawable.ridiculous_large);
        }else if(item.equals("Ação")){
           // listAdapter.addAll(lstFilmesAcao);
            img_banner.setImageResource(R.drawable.narcos_large);
        }else if(item.equals("Terror")){
           // listAdapter.addAll(lstFilmesTerror);
            img_banner.setImageResource(R.drawable.invocacaomal2_large);
        }else if(item.equals("Drama")){
           // listAdapter.addAll(lstFilmesDrama);
            img_banner.setImageResource(R.drawable.privateryan_large);
        }

        buscarFilmes(item);
    }

    private void buscarFilmes(String genero){

        DatabaseHelper helper = new DatabaseHelper(this);

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor =
                db.rawQuery("select * from filmes where genero = ?;", new String[] {genero} );


        cursor.moveToFirst();
        List<String> lstFilmes = new ArrayList<>();
        for(int i =0 ; i < cursor.getCount() ; i++){

            String nomeFilme = cursor.getString(1);
            lstFilmes.add(nomeFilme);

            cursor.moveToNext();
        }

        cursor.close();

        listAdapter.addAll(lstFilmes);
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void inserir(View view) {
        Intent intent = new Intent(this, NovoFilmeActivity.class);
        startActivity(intent);
    }
}
