package br.com.alura.agenda;

import android.app.Application;

import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.dao.CompraDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Compra;

@SuppressWarnings("WeakerAccess")
public class AgendaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        criaAlunosDeTeste();
        criaComprasDeTeste();
    }

    private void criaAlunosDeTeste() {
        AlunoDAO dao = new AlunoDAO();
        dao.salva(new Aluno("Alex", "1122223333", "alex@alura.com.br"));
        dao.salva(new Aluno("Fran", "1122223333", "fran@gmail.com"));
    }
    private void criaComprasDeTeste() {
        CompraDAO dao = new CompraDAO();
        dao.salva(new Compra("Cerveja", "10", "Dado", false));
        dao.salva(new Compra("Salsicha", "20", "Sadia", false));
    }

}
