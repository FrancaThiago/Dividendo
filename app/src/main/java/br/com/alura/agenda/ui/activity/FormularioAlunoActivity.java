package br.com.alura.agenda.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.constraint.BuildConfig;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.ArrayList;

import br.com.alura.agenda.R;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.model.Aluno;
import br.com.alura.agenda.model.Compra;
import br.com.alura.agenda.ui.ListaComprasView;

import static br.com.alura.agenda.ui.activity.ConstantesActivities.CHAVE_ALUNO;

public class FormularioAlunoActivity extends AppCompatActivity {

    private static final String TITULO_APPBAR_NOVO_ALUNO = "Novo aluno";
    private static final String TITULO_APPBAR_EDITA_ALUNO = "Edita aluno";
    private EditText campoNome;
    private EditText campoTelefone;
    private EditText campoEmail;
    private CheckBox campoCheck1;
    private CheckBox campoCheck2;
    private CheckBox campoCheck3;
    private final AlunoDAO dao = new AlunoDAO();
    private Aluno aluno;
    private String caminhoFoto;
    public static final int CODIGO_CAMERA = 123;
    public final ListaComprasView listaComprasView = new ListaComprasView(this);
    public ArrayList<Compra> nSelecionados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);
        inicializacaoDosCampos();
        carregaAluno();
        configuraBotaoSelecao();

        Button botaoCamera = (Button) findViewById(R.id.activity_formulario_aluno_botao_foto);
        botaoCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File arquivoFoto = new File(caminhoFoto);
                Uri fotoURI = FileProvider.getUriForFile(FormularioAlunoActivity.this, BuildConfig.APPLICATION_ID + ".provider", arquivoFoto);
                vaiParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, fotoURI);
                startActivityForResult(vaiParaCamera, CODIGO_CAMERA);
            }
        });
    }

    private void configuraBotaoSelecao() {
        Button botaoSelecao = findViewById(R.id.activity_formulario_aluno_seleciona_compras);
        botaoSelecao.setOnClickListener(view -> abreListaComprasSelecao());
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == CODIGO_CAMERA) {
//            if (resultCode == RESULT_OK) {
//
//                Bitmap bm = BitmapFactory.decodeFile(caminhoFoto);
//                bm = Bitmap.createScaledBitmap(bm, 100, 100, true);
//                ImageView foto = (ImageView) findViewById(R.id.activity_formulario_aluno_foto);
//                foto.setImageBitmap(bm);
//                foto.setScaleType(ImageView.ScaleType.FIT_XY);
//
//            }
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater()
                .inflate(R.menu.activity_formulario_aluno_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.activity_formulario_aluno_menu_salvar){
            finalizaFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    private void carregaAluno() {
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_ALUNO)) {
            setTitle(TITULO_APPBAR_EDITA_ALUNO);
            aluno = (Aluno) dados.getSerializableExtra(CHAVE_ALUNO);
            preencheCampos();
        } else {
            setTitle(TITULO_APPBAR_NOVO_ALUNO);
            aluno = new Aluno();
        }
    }

    private void preencheCampos() {
        campoNome.setText(aluno.getNome());
        campoTelefone.setText(aluno.getTelefone());
        campoEmail.setText(aluno.getEmail());
    }

    private void finalizaFormulario() {
        preencheAluno();
        if (aluno.temIdValido()) {
            dao.edita(aluno);
        } else {
            dao.salva(aluno);
        }
        finish();
    }

    private void inicializacaoDosCampos() {
        campoNome = findViewById(R.id.activity_formulario_aluno_nome);
        campoTelefone = findViewById(R.id.activity_formulario_aluno_telefone);
        campoEmail = findViewById(R.id.activity_formulario_aluno_email);
        //campoCheck1 = findViewById(R.id.activity_lista_compras_listview);
    }

    private void preencheAluno() {
        String nome = campoNome.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String email = campoEmail.getText().toString();

        aluno.setNome(nome);
        aluno.setTelefone(telefone);
        aluno.setEmail(email);
    }

    private void abreListaComprasSelecao() {
        Intent intent = new Intent(this, ListaSelecaoComprasActivity.class);
        intent.putExtra("chaveDoObjeto", new ArrayList<Compra>()); // Obj Pessoa precisa implementar Serializable
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // fazer um check para verificar o código de requisição e o de resposta
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
    /*objeto enviado pela activity B através do método setResult(),
    fiz um cast pro tipo (Pessoa) porque sei que vai ser esse tipo,
    mas pode  ser qualquer obj que seja serializable.
    Intent tem métodos para vários tipos para setar e recupera     vários tipos diferentes. */
                ArrayList<Compra> nSeleciona= (ArrayList<Compra>) data.getSerializableExtra("chaveDoObjeto");
                ListView listView = findViewById(R.id.activity_formulario_aluno_listview);
                ;
            }
        }
    }
}
