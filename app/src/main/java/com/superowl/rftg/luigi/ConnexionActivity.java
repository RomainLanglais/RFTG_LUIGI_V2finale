package com.superowl.rftg.luigi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
public class ConnexionActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnConnexion;
    private StaffDAO staffDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        // Initialisation du DAO
        staffDAO = new StaffDAO();

        // Initialisation des vues
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnConnexion = findViewById(R.id.btnConnexion);

        // Bouton de connexion
        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connexion();
            }
        });
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
            Toast.makeText(this, "⚠️ Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Désactiver le bouton pendant la connexion
        btnConnexion.setEnabled(false);

        // Appel réseau dans un thread séparé
        new Thread(() -> {
            Utilisateur user = staffDAO.authenticate(username, password);

            runOnUiThread(() -> {
                btnConnexion.setEnabled(true);

                if (user != null) {
                    // Connexion réussie
                    SessionManager.setUtilisateur(user);
                    Toast.makeText(this, "✓ Bienvenue " + user.getNomComplet() + " !", Toast.LENGTH_SHORT).show();

                    // Redirection vers la liste des films
                    Intent intent = new Intent(ConnexionActivity.this, ListeFilmsActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Échec de connexion
                    Toast.makeText(this, "❌ Username ou mot de passe incorrect", Toast.LENGTH_LONG).show();
                }
            });
        }).start();
    }
}
