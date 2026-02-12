package com.superowl.rftg.luigi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.superowl.rftg.luigi.models.Film;
import com.superowl.rftg.luigi.utils.PanierManager;
import com.superowl.rftg.rftg_luigi_v2.R;

import java.util.ArrayList;

/**
 * Écran panier
 */
public class PanierActivity extends AppCompatActivity {

    private ListView listViewPanier;
    private TextView tvTotal;
    private TextView tvNombre;
    private Button btnVider;
    private Button btnValider;
    private ArrayAdapter<Film> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);

        // Initialisation
        listViewPanier = findViewById(R.id.listViewPanier);
        tvTotal = findViewById(R.id.tvTotal);
        tvNombre = findViewById(R.id.tvNombre);
        btnVider = findViewById(R.id.btnVider);
        btnValider = findViewById(R.id.btnValider);

        afficher();

        // Bouton vider
        btnVider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vider();
            }
        });

        // Bouton valider → Validation commande
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validerCommande();
            }
        });
    }

    private void afficher() {
        ArrayList<Film> films = PanierManager.getFilms();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, films);
        listViewPanier.setAdapter(adapter);

        int nombre = PanierManager.getNombre();
        double total = PanierManager.getTotal();

        tvNombre.setText(nombre + " film(s) dans le panier");
        tvTotal.setText(String.format("%.2f€", total));

        // Désactiver validation si panier vide
        btnValider.setEnabled(nombre > 0);

        if (nombre == 0) {
            Toast.makeText(this, "📭 Votre panier est vide", Toast.LENGTH_SHORT).show();
        }
    }

    private void vider() {
        if (PanierManager.getNombre() == 0) {
            Toast.makeText(this, "Le panier est déjà vide", Toast.LENGTH_SHORT).show();
            return;
        }

        PanierManager.vider();
        afficher();
        Toast.makeText(this, "🗑️ Panier vidé", Toast.LENGTH_SHORT).show();
    }

    private void validerCommande() {
        if (PanierManager.getNombre() == 0) {
            Toast.makeText(this, "Panier vide", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, ValidationCommandeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        afficher();
    }
}
