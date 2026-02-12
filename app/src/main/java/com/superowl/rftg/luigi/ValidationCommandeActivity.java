package com.superowl.rftg.luigi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.superowl.rftg.luigi.models.Film;
import com.superowl.rftg.luigi.utils.PanierManager;
import com.superowl.rftg.luigi.utils.SessionManager;
import com.superowl.rftg.rftg_luigi_v2.R;

import java.util.ArrayList;

/**
 * Écran de validation de commande
 * FONCTION VALIDATION PANIER
 */
public class ValidationCommandeActivity extends AppCompatActivity {

    private TextView tvRecap;
    private TextView tvTotal;
    private Button btnConfirmer;
    private Button btnAnnuler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation_commande);

        // Initialisation
        tvRecap = findViewById(R.id.tvRecap);
        tvTotal = findViewById(R.id.tvTotal);
        btnConfirmer = findViewById(R.id.btnConfirmer);
        btnAnnuler = findViewById(R.id.btnAnnuler);

        afficherRecapitulatif();

        // Bouton confirmer
        btnConfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmerCommande();
            }
        });

        // Bouton annuler
        btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void afficherRecapitulatif() {
        ArrayList<Film> films = PanierManager.getFilms();
        StringBuilder recap = new StringBuilder();

        recap.append("👤 Client : ").append(SessionManager.getUtilisateur().getNomComplet()).append("\n\n");
        recap.append("🎬 Films commandés :\n\n");

        for (int i = 0; i < films.size(); i++) {
            Film film = films.get(i);
            recap.append((i + 1)).append(". ").append(film.getTitle())
                 .append("\n   ").append(film.getPrixFormate()).append("\n\n");
        }

        tvRecap.setText(recap.toString());
        tvTotal.setText(String.format("%.2f€", PanierManager.getTotal()));
    }

    /**
     * FONCTION VALIDATION PANIER
     * Confirmer la commande
     * VERSION BOUCHON : Message de confirmation
     * TODO Phase 2 : POST /commandes
     */
    private void confirmerCommande() {
        // BOUCHON : Simuler l'enregistrement
        String message = "✅ Commande validée avec succès !\n\n" +
                        "📦 Nombre de films : " + PanierManager.getNombre() + "\n" +
                        "💰 Total : " + String.format("%.2f€", PanierManager.getTotal()) + "\n\n" +
                        "Merci " + SessionManager.getUtilisateur().getPrenom() + " !";

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        // Vider le panier
        PanierManager.vider();

        // Retour à la liste des films
        Intent intent = new Intent(this, ListeFilmsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
