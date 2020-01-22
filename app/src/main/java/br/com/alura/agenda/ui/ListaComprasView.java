package br.com.alura.agenda.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import br.com.alura.agenda.dao.CompraDAO;
import br.com.alura.agenda.model.Compra;
import br.com.alura.agenda.ui.adapter.ListaComprasAdapter;

public class ListaComprasView {

    private final ListaComprasAdapter adapter;
    private final CompraDAO dao;
    private final Context context;

    public ListaComprasView(Context context) {
        this.context = context;
        this.adapter = new ListaComprasAdapter(this.context);
        this.dao = new CompraDAO();
    }

    public void confirmaRemocao(final MenuItem item) {
        new AlertDialog
                .Builder(context)
                .setTitle("Removendo aluno")
                .setMessage("Tem certeza que quer remover o aluno?")
                .setPositiveButton("Sim", (dialogInterface, i) -> {
                    AdapterView.AdapterContextMenuInfo menuInfo =
                            (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                    Compra compraEscolhido = adapter.getItem(menuInfo.position);
                    remove(compraEscolhido);
                })
                .setNegativeButton("Não", null)
                .show();
    }

    public void atualizaCompras() {
        adapter.atualizaComp(dao.todos());
    }

    private void remove(Compra compra) {
        dao.removeCompra(compra);
        adapter.removeComp(compra);
    }

    public void configuraAdapter(ListView listaDeCompras) {
        listaDeCompras.setAdapter(adapter);
    }
}
