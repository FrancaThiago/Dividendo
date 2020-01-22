package br.com.alura.agenda.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.com.alura.agenda.R;
import br.com.alura.agenda.model.Compra;
import br.com.alura.agenda.ui.ListaComprasView;

import static br.com.alura.agenda.ui.activity.ConstantesActivities.CHAVE_COMPRA;

public class ListaComprasActivity extends AppCompatActivity {
    private static final String TITULO_APPBAR = "Lista de compras";
    private final ListaComprasView listaComprasView = new ListaComprasView(this);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_compras);
        setTitle(TITULO_APPBAR);
        configuraFabNovaCompra();
        configuraFabCompras();
        configuraLista();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater()
                .inflate(R.menu.activity_lista_compras_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.activity_lista_compras_menu_remover) {
            listaComprasView.confirmaRemocao(item);
        }

        return super.onContextItemSelected(item);
    }

    private void configuraFabNovaCompra() {
        FloatingActionButton botaoNovaCompra = findViewById(R.id.activity_lista_compras_fab_nova_compra);
        botaoNovaCompra.setOnClickListener(view -> abreFormularioModoInsereCompra());
    }

    private void configuraFabCompras() {
        FloatingActionButton botaoAlunos = findViewById(R.id.activity_lista_compras_fab_alunos);
        botaoAlunos.setOnClickListener(view -> abreListaAlunos());
    }

    private void abreListaAlunos() {
        startActivity(new Intent(this, ListaAlunosActivity.class));
    }

    private void abreFormularioModoInsereCompra() {
        startActivity(new Intent(this, FormularioCompraActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        listaComprasView.atualizaCompras();
    }

    private void configuraLista() {
        ListView listaDeCompras = findViewById(R.id.activity_lista_compras_listview);
        listaComprasView.configuraAdapter(listaDeCompras);
        configuraListenerDeCliquePorItem(listaDeCompras);
        registerForContextMenu(listaDeCompras);
    }

    private void configuraListenerDeCliquePorItem(ListView listaDeCompras) {
        listaDeCompras.setOnItemClickListener((adapterView, view, posicao, id) -> {
            Compra compraEscolhida = (Compra) adapterView.getItemAtPosition(posicao);
            abreFormularioModoEditaCompra(compraEscolhida);
        });
    }

    private void abreFormularioModoEditaCompra(Compra compra) {
        Intent vaiParaFormularioActivity = new Intent(ListaComprasActivity.this, FormularioCompraActivity.class);
        vaiParaFormularioActivity.putExtra(CHAVE_COMPRA, compra);
        startActivity(vaiParaFormularioActivity);
    }

}
