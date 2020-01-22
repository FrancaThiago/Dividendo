package br.com.alura.agenda.dao;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.model.Compra;

public class CompraDAO {
    private final static List<Compra> compras = new ArrayList<>();
    private static int contadorDeIds = 1;

    public void salva(Compra compra) {
        compra.setId(contadorDeIds);
        compras.add(compra);
        atualizaIds();
    }

    private void atualizaIds() {
        contadorDeIds++;
    }

    public void edita(Compra compra) {
        Compra compraEncontrada = buscaCompraPeloId(compra);
        if (compraEncontrada != null) {
            int posicaoDaCompra = compras.indexOf(compraEncontrada);
            compras.set(posicaoDaCompra, compra);
        }
    }

    @Nullable
    private Compra buscaCompraPeloId(Compra compra) {
        for (Compra a :
                compras) {
            if (a.getId() == compra.getId()) {
                return a;
            }
        }
        return null;
    }

    public List<Compra> todos() {
        return new ArrayList<>(compras);
    }

    public void removeCompra(Compra compra) {
        Compra compraDevolvida = buscaCompraPeloId(compra);
        if(compraDevolvida != null){
            compras.remove(compraDevolvida);
        }
    }
}
