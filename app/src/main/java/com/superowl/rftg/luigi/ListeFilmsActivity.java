package com.superowl.rftg.luigi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.superowl.rftg.luigi.database.FilmDAO;
import com.superowl.rftg.luigi.models.Film;
import com.superowl.rftg.luigi.utils.PanierManager;
import com.superowl.rftg.luigi.utils.SessionManager;
import com.superowl.rftg.rftg_luigi_v2.R;

import java.util.ArrayList;

/**
 * Écran liste des films disponibles
 * Films chargés depuis le backend REST Toad
 */
public class ListeFilmsActivity extends AppCompatActivity {

    private TextView tvBienvenue;
    private ListView listViewFilms;
    private Button btnPanier;
    private ProgressBar progressBar;
    private ArrayList<Film> listeFilms;
    private ArrayAdapter<Film> adapter;
    private FilmDAO filmDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_films);

        // Vérifier si l'utilisateur est connecté
        if (!SessionManager.estConnecte()) {
            retourConnexion();
            return;
        }

        // Initialisation du DAO
        filmDAO = new FilmDAO();

        // Initialisation des vues
        tvBienvenue = findViewById(R.id.tvBienvenue);
        listViewFilms = findViewById(R.id.listViewFilms);
        btnPanier = findViewById(R.id.btnPanier);
        progressBar = findViewById(R.id.progressBar);

        // Message de bienvenue
        tvBienvenue.setText("Bonjour " + SessionManager.getUtilisateur().getPrenom() + " ! 👋");

        // Initialiser la liste vide et l'adapter
        listeFilms = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listeFilms);
        listViewFilms.setAdapter(adapter);

        // Charger les films depuis le backend (thread séparé)
        chargerFilms();

        // Clic sur un film
        listViewFilms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Film film = listeFilms.get(position);
                ouvrirDetails(film);
            }
        });

        // Bouton panier
        btnPanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ouvrirPanier();
            }
        });

        mettreAJourBoutonPanier();
    }

    /**
     * Charger les films depuis le backend REST
     */
    private void chargerFilms() {
        progressBar.setVisibility(View.VISIBLE);
        new Thread(() -> {
            ArrayList<Film> films = filmDAO.getAllFilms();

            runOnUiThread(() -> {
                progressBar.setVisibility(View.GONE);
                listeFilms.clear();
                listeFilms.addAll(films);
                adapter.notifyDataSetChanged();
            });
        }).start();
    }

    private void ouvrirDetails(Film film) {
        Intent intent = new Intent(this, DetailsFilmActivity.class);
        intent.putExtra("FILM_ID", film.getFilmId());
        intent.putExtra("FILM_TITLE", film.getTitle());
        intent.putExtra("FILM_DESCRIPTION", film.getDescription());
        startActivity(intent);
    }

    private void ouvrirPanier() {
        Intent intent = new Intent(this, PanierActivity.class);
        startActivity(intent);
    }

    private void mettreAJourBoutonPanier() {
        int nombre = PanierManager.getNombre();
        btnPanier.setText("🛒 Panier (" + nombre + ")");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mettreAJourBoutonPanier();
    }

    private void retourConnexion() {
        Intent intent = new Intent(this, ConnexionActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "🚪 Déconnexion");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            SessionManager.deconnexion();
            PanierManager.vider();
            Toast.makeText(this, "Déconnexion réussie", Toast.LENGTH_SHORT).show();
            retourConnexion();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
