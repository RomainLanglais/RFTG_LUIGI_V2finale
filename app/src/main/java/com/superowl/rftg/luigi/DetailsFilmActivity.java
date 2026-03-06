package com.superowl.rftg.luigi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.superowl.rftg.luigi.models.Film;
import com.superowl.rftg.luigi.utils.PanierManager;
import com.superowl.rftg.rftg_luigi_v2.R;

/**
 * Écran détails d'un film
 * FONCTION AJOUT AU PANIER
 */
public class DetailsFilmActivity extends AppCompatActivity {

    private TextView tvTitle;
    private TextView tvDescription;
    private Button btnAjouterPanier;
    private Film filmActuel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_film);

        // Initialisation
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        btnAjouterPanier = findViewById(R.id.btnAjouterPanier);

        // Récupérer les données
        recupererDonnees();
        afficher();

        // FONCTION AJOUT AU PANIER
        btnAjouterPanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouterAuPanier();
            }
        });
    }

    private void recupererDonnees() {
        Integer id = getIntent().getIntExtra("FILM_ID", 0);
        String title = getIntent().getStringExtra("FILM_TITLE");
        String description = getIntent().getStringExtra("FILM_DESCRIPTION");

        filmActuel = new Film(id, title, description);
    }

    private void afficher() {
        tvTitle.setText(filmActuel.getTitle());
        tvDescription.setText(filmActuel.getDescription());
    }

    /**
     * FONCTION AJOUT AU PANIER
     */
    private void ajouterAuPanier() {
        PanierManager.ajouterFilm(filmActuel);
        Toast.makeText(this,
            "✓ \"" + filmActuel.getTitle() + "\" ajouté au panier !",
            Toast.LENGTH_SHORT).show();
        finish();
    }
}
