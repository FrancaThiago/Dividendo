package br.com.alura.agenda.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.R;
import br.com.alura.agenda.model.Compra;

public class ListaComprasAdapter extends BaseAdapter {
    private final List<Compra> compras = new ArrayList<>();
    private final Context context;

    public ListaComprasAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return compras.size();
    }

    @Override
    public Compra getItem(int posicao) {
        return compras.get(posicao);
    }

    @Override
    public long getItemId(int posicao) {
        return compras.get(posicao).getId();
    }

    @Override
    public View getView(int posicao, View view, ViewGroup viewGroup) {
        View viewCriada = criaView(viewGroup);
        Compra compraDevolvido = compras.get(posicao);
        vincula(viewCriada, compraDevolvido);
        return viewCriada;
    }

    private void vincula(View view, Compra compra) {
        TextView nome = view.findViewById(R.id.item_compra_nome);
        nome.setText(compra.getNome());
        TextView preco = view.findViewById(R.id.item_compra_preco);
        preco.setText(compra.getPreco());
    }

    private View criaView(ViewGroup viewGroup) {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.item_compra, viewGroup, false);
    }

    public void atualizaComp(List<Compra> compras){
        this.compras.clear();
        this.compras.addAll(compras);
        notifyDataSetChanged();
    }

    public void removeComp(Compra compra) {
        compras.remove(compra);
        notifyDataSetChanged();
    }
}
