package br.com.alura.agenda.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import br.com.alura.agenda.R;
import br.com.alura.agenda.model.Compra;
import br.com.alura.agenda.ui.ListaComprasView;
import br.com.alura.agenda.ui.ListaSelecaoView;

public class ListaSelecaoComprasActivity extends AppCompatActivity {
    private static final String TITULO_APPBAR = "Lista de compras";
    private final ListaComprasView listaComprasView = new ListaComprasView(this);
    public ArrayList<Compra> nSelecionados = new ArrayList<>();
    public Compra Comprita;
    public String stri;
    public boolean bool;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_selecao_compras);
        setTitle(TITULO_APPBAR);
        configuraLista();
        Intent intent = getIntent();
        if(intent.hasExtra("chaveDoObjeto")){
            nSelecionados = (ArrayList) intent.getSerializableExtra("chaveDoObjeto");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        listaComprasView.atualizaCompras();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater()
                .inflate(R.menu.activity_lista_selecao_compras_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.activity_lista_selecao_compra_menu_salvar){
            finalizaFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    private void finalizaFormulario() {
        Intent intent = new Intent();
        intent.putExtra("chaveDoObjeto", this.nSelecionados); //
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    private void configuraLista() {
        ListView listaDeCompras = findViewById(R.id.activity_lista_selecao_compras_listview);
        listaComprasView.configuraAdapter(listaDeCompras);
        configuraListenerDeCliquePorItem(listaDeCompras);
        registerForContextMenu(listaDeCompras);
    }

    private void configuraListenerDeCliquePorItem(ListView listaDeCompras) {
        listaDeCompras.setOnItemClickListener((adapterView, view, posicao, id) -> {
            Compra compraEscolhida = (Compra) adapterView.getItemAtPosition(posicao);
            Toast.makeText(this, compraEscolhida.getNome()+" Escolhido", Toast.LENGTH_SHORT).show();
            nSelecionados.add(compraEscolhida);
        });
    }
}
