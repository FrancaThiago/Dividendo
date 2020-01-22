package br.com.alura.agenda.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import br.com.alura.agenda.dao.CompraDAO;
import br.com.alura.agenda.model.Compra;
import br.com.alura.agenda.ui.adapter.ListaSelecaoAdapter;

public class ListaSelecaoView {

    private final ListaSelecaoAdapter adapter;
    private final CompraDAO dao;
    private final Context context;

    public ListaSelecaoView(Context context) {
        this.context = context;
        this.adapter = new ListaSelecaoAdapter(this.context);
        this.dao = new CompraDAO();
    }

    public void atualizaSCompras() {
        adapter.atualizaSComp(dao.todos());
    }

    public void configuraSAdapter(ListView listaDeCompras) {
        listaDeCompras.setAdapter(adapter);
    }
}
