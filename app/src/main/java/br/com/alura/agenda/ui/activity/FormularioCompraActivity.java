package br.com.alura.agenda.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.constraint.BuildConfig;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;

import br.com.alura.agenda.R;
import br.com.alura.agenda.dao.CompraDAO;
import br.com.alura.agenda.model.Compra;

import static br.com.alura.agenda.ui.activity.ConstantesActivities.CHAVE_COMPRA;

public class FormularioCompraActivity  extends AppCompatActivity {

    private static final String TITULO_APPBAR_NOVA_COMPRA = "Nova compra";
    private static final String TITULO_APPBAR_EDITA_COMPRA = "Edita compra";
    private EditText campoNome;
    private EditText campoPreco;
    private EditText campoMarca;
    private final CompraDAO dao = new CompraDAO();
    private Compra compra;
    private String caminhoFoto;
    public static final int CODIGO_CAMERA = 123;
    public String select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_compra);
        inicializacaoDosCampos();
        carregaCompra();

        Button botaoCamera = (Button) findViewById(R.id.activity_formulario_compra_botao_foto);

        botaoCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File arquivoFoto = new File(caminhoFoto);
                Uri fotoURI = FileProvider.getUriForFile(FormularioCompraActivity.this, BuildConfig.APPLICATION_ID + ".provider", arquivoFoto);
                vaiParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, fotoURI);
                startActivityForResult(vaiParaCamera, CODIGO_CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODIGO_CAMERA) {
            if (resultCode == RESULT_OK) {

                Bitmap bm = BitmapFactory.decodeFile(caminhoFoto);
                bm = Bitmap.createScaledBitmap(bm, 100, 100, true);
                ImageView foto = (ImageView) findViewById(R.id.activity_formulario_aluno_foto);
                foto.setImageBitmap(bm);
                foto.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater()
                .inflate(R.menu.activity_formulario_compra_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.activity_formulario_compra_menu_salvar){
            finalizaFormulario();
        }
        return super.onOptionsItemSelected(item);
    }

    private void carregaCompra() {
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_COMPRA)) {
            setTitle(TITULO_APPBAR_EDITA_COMPRA);
            compra = (Compra) dados.getSerializableExtra(CHAVE_COMPRA);
            preencheCampos();
        } else {
            setTitle(TITULO_APPBAR_NOVA_COMPRA);
            compra = new Compra();
        }
    }

    private void preencheCampos() {
        campoNome.setText(compra.getNome());
        campoPreco.setText(compra.getPreco());
        campoMarca.setText(compra.getMarca());
    }

    private void finalizaFormulario() {
        preencheCompra();
        if (compra.temIdValido()) {
            dao.edita(compra);
        } else {
            dao.salva(compra);
        }
        finish();
    }

    private void inicializacaoDosCampos() {
        campoNome = findViewById(R.id.activity_formulario_compra_nome);
        campoPreco = findViewById(R.id.activity_formulario_compra_preco);
        campoMarca = findViewById(R.id.activity_formulario_compra_marca);
    }

    private void preencheCompra() {
        String nome = campoNome.getText().toString();
        String preco = campoPreco.getText().toString();
        String marca = campoMarca.getText().toString();

        compra.setNome(nome);
        compra.setPreco(preco);
        compra.setMarca(marca);
    }
}