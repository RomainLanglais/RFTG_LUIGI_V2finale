package com.superowl.rftg.luigi;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.superowl.rftg.luigi.database.StaffDAO;
import com.superowl.rftg.luigi.models.Utilisateur;
import com.superowl.rftg.luigi.utils.SessionManager;
import com.superowl.rftg.rftg_luigi_v2.R;

/**
 * Écran de connexion - Point d'entrée de l'application
 * Authentification via backend REST Toad
 */
public class ConnexionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText etUsername;
    private EditText etPassword;
    private EditText etURL;
    private Button btnConnexion;
    private ProgressBar progressBar;
    private StaffDAO staffDAO;
    private String[] listeURLs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        // Initialiser le token JWT depuis strings.xml
        UrlManager.init(this);

        // Initialisation du DAO
        staffDAO = new StaffDAO();

        // Initialisation des vues
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etURL = findViewById(R.id.etURL);
        btnConnexion = findViewById(R.id.btnConnexion);
        progressBar = findViewById(R.id.progressBar);

        // Configuration du Spinner
        listeURLs = getResources().getStringArray(R.array.listeURLs);
        Spinner spinnerURLs = findViewById(R.id.spinnerURLs);
        spinnerURLs.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapterListeURLs = ArrayAdapter.createFromResource(
                this, R.array.listeURLs, android.R.layout.simple_spinner_item);
        adapterListeURLs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerURLs.setAdapter(adapterListeURLs);

        // Bouton de connexion
        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connexion();
            }
        });

        // Touche Entrée sur le champ mot de passe
        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    connexion();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        etURL.setText(listeURLs[position]);
        UrlManager.setURLConnexion(listeURLs[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    /**
     * FONCTION CONNEXION
     * Authentification via backend REST Toad
     */
    private void connexion() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validation des champs
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Sauvegarder l'URL saisie manuellement
        String urlSaisie = etURL.getText().toString().trim();
        if (!urlSaisie.isEmpty()) {
            UrlManager.setURLConnexion(urlSaisie);
        }

        // Désactiver le bouton pendant la connexion
        btnConnexion.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        // Appel réseau dans un thread séparé
        new Thread(() -> {
            Utilisateur user = staffDAO.authenticate(username, password);

            runOnUiThread(() -> {
                btnConnexion.setEnabled(true);
                progressBar.setVisibility(View.GONE);

                if (user != null) {
                    // Connexion réussie
                    SessionManager.setUtilisateur(user);
                    Toast.makeText(this, "Bienvenue " + user.getNomComplet() + " !", Toast.LENGTH_SHORT).show();

                    // Redirection vers la liste des films
                    Intent intent = new Intent(ConnexionActivity.this, ListeFilmsActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Échec de connexion
                    Toast.makeText(this, "Username ou mot de passe incorrect", Toast.LENGTH_LONG).show();
                }
            });
        }).start();
    }
}
