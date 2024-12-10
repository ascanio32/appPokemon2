package com.example.pokemonapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.pokemonapp.R;
import com.example.pokemonapp.model.Pokemon;
import com.example.pokemonapp.presenter.FavoritesPresenter;

import java.util.List;

public class FavoritosAdapter extends RecyclerView.Adapter<FavoritosAdapter.ViewHolder> {
    private Context context;  //Contexto para inflar vistas y cargar imagenes
    private List<Pokemon> favoritePokemons;//Lista de pokemon favoritos
    private FavoritesPresenter presenter; //Presentador que maneja la logica de negocio relacionada con los favoritos

    public FavoritosAdapter(Context context, List<Pokemon> favoritePokemons, FavoritesPresenter presenter) {
        this.context = context;
        this.favoritePokemons = favoritePokemons;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_favorites_header, parent, false); // Infla el layout para cada elemento de la lista
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon pokemon = favoritePokemons.get(position); //Obtiene el pokemon en la posicion especificada
        holder.nombreTv.setText(pokemon.getName()); //Establece el nombre del pokemon en el textview

        String imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemon.getNumber() + ".png"; //Construye la url de la imagen del pokemon
        Glide.with(context)// Usa Glide para cargar la imagen del pokemon
                .load(imageUrl)
                .centerCrop()
                .into(holder.imageView);

        holder.ibfavoritos.setImageResource(R.drawable.corazon); // Establece la imagen del boton de favoritos

        holder.ibfavoritos.setOnClickListener(v -> {   // Configura un OnClickListener para el botón de favoritos
            presenter.removeFavorite(pokemon);  // Llama al método removeFavorite del presentador para eliminar el Pokémon de favoritos
            notifyItemRemoved(position);   // Notifica al adaptador que un elemento ha sido eliminado
            notifyItemRangeChanged(position,favoritePokemons.size());   // Notifica al adaptador que el rango de elementos ha cambiado
        });
    }

    @Override
    public int getItemCount() {
        return favoritePokemons.size();// Devuelve el número de elementos en la lista de favoritos
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {    // ViewHolder que contiene las vistas para mostrar los datos de cada Pokémon
        public TextView nombreTv;
        public ImageView imageView;
        private ImageButton ibfavoritos;

        public ViewHolder(View itemView) {
            super(itemView);
            // Inicializa las vistas del ViewHolder
            nombreTv = itemView.findViewById(R.id.nombreTV);
            imageView = itemView.findViewById(R.id.fotoIV);
            ibfavoritos = itemView.findViewById(R.id.ibfavoritos);
        }
    }
}
