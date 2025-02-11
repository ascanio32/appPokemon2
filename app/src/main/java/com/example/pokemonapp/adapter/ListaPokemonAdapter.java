// ListaPokemonAdapter.java
package com.example.pokemonapp.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.pokemonapp.R;
import com.example.pokemonapp.model.Pokemon;
import com.example.pokemonapp.view.DetailActivity;
import java.util.ArrayList;
import java.util.List;

public class ListaPokemonAdapter extends RecyclerView.Adapter<ListaPokemonAdapter.ViewHolder> {
    private static final int PIKACHU_POSITION = 1;
    private ArrayList<Pokemon> dataset;
    private Context context;
    private boolean isHorizontal;
    private Pokemon specificPokemon;  //pokemon especifico

    public ListaPokemonAdapter(Context context, boolean isHorizontal, Pokemon specificPokemon) {  // Método para actualizar el dataset del adaptador
        this.context = context;
        this.dataset = new ArrayList<>();
        this.isHorizontal = isHorizontal;
        this.specificPokemon = specificPokemon;
    }
    // Constructor que no acepta un Pokémon específico
    public ListaPokemonAdapter(Context context, boolean isHorizontal) {
        this(context, isHorizontal, null);
    }
    public void setSpecificPokemon(Pokemon specificPokemon) {
        this.specificPokemon = specificPokemon;
        notifyDataSetChanged();
    }

    public void setDataset(List<Pokemon> newDataset) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {// Calcula las diferencias entre el dataset actual y el nuevo dataset
            @Override
            public int getOldListSize() {
                return dataset.size();// Devuelve el tamaño de la lista actual
            }

            @Override
            public int getNewListSize() {
                return newDataset.size(); // Devuelve el tamaño de la nueva lista
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                // Comprueba si los elementos en las posiciones especificadas son los mismos
                // Aquí se compara el número del Pokémon para determinar si son el mismo elemento
                return dataset.get(oldItemPosition).getNumber() == newDataset.get(newItemPosition).getNumber();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                // Comprueba si el contenido de los elementos en las posiciones especificadas es el mismo
                // Aquí se usa el método equals para comparar los objetos Pokémon
                return dataset.get(oldItemPosition).equals(newDataset.get(newItemPosition));
            }
        });

        dataset.clear();// Limpia el dataset actual y añade todos los elementos del nuevo dataset
        dataset.addAll(newDataset);
        diffResult.dispatchUpdatesTo(this);// Notifica al adaptador sobre los cambios calculados por DiffUtil
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false); // Infla el layout para cada elemento de la lista
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();  // Ajusta el ancho del elemento según la orientación del RecyclerView
        if (!isHorizontal) {
            layoutParams.width = parent.getWidth() / 3;
        } else {
            layoutParams.width = parent.getWidth() / 2;
        }
        view.setLayoutParams(layoutParams);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Pokemon p;
       /* if ((position % 3) == PIKACHU_POSITION) {
            p = specificPokemon != null ? specificPokemon : new Pokemon();
            p.setName("Pikachu");
            p.setNumber(25);
        } else {
            int actualPosition = position - (position / 3);
            p = dataset.get(actualPosition % dataset.size());
        }*/
       /* if (isHorizontal){
            if (position % 1 ==0){
                p = dataset.get(position);
            }else {
                p = dataset.get(position % dataset.size());
            }
        }else{
            if ((position % 3) == PIKACHU_POSITION){
                p = specificPokemon != null ? specificPokemon : new Pokemon();
                p.setName("Pikachu");
                p.setNumber(25);

            }else {
                int actualPosition = position - (position / 3);
                p = dataset.get(actualPosition % dataset.size());
            }
        }*/
       // Pokemon p = dataset.get(position);  // Obtiene el Pokémon en la posición especificada
       // Pokemon p = specificPokemon;  // Usa el Pokémon específico
       // Pokemon p = specificPokemon != null ? specificPokemon : dataset.get(position);

       Pokemon p;

        if (isHorizontal){
            if (position  % 300 == 0){
            p = specificPokemon != null ? specificPokemon : new Pokemon();
            p.setName("Pikachu");
            p.setNumber(25);
            }else {
                p = dataset.get(position % dataset.size());
            }
        }else {
            if (position  % 4444 ==0){
                p = specificPokemon != null ? specificPokemon : new Pokemon();
                p.setName("Pikachu");
                p.setNumber(25);
            }else{
                p = dataset.get(position % dataset.size());
            }
        }

        holder.nombreTextView.setText(p.getName()); // Establece el nombre del Pokémon en el TextView

        Glide.with(context)// Usa Glide para cargar la imagen del Pokémon
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + p.getNumber() + ".png")
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.fotoImageView);

        holder.itemView.setOnClickListener(v -> {// Configura un OnClickListener para el elemento de la lista
            LayoutInflater inflater = LayoutInflater.from(context);
            View layout = inflater.inflate(R.layout.custon_toas, null);

            ImageView toastIcon = layout.findViewById(R.id.toast_icon);
            TextView toastText = layout.findViewById(R.id.toast_text);

            toastIcon.setImageResource(R.mipmap.pikachu);
            toastText.setText(p.getName());

            Toast toas = new Toast(context);
            toas.setDuration(Toast.LENGTH_SHORT);
            toas.setView(layout);
            toas.show();

            MediaPlayer mediaPlayer = MediaPlayer.create(context,R.raw.voicpikachu);
            mediaPlayer.start();

            ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(holder.itemView, "rotation", 0f, 360f);
            rotateAnimator.setDuration(900);
            rotateAnimator.start();

            Intent intent = new Intent(context, DetailActivity.class); // Crea un Intent para abrir DetailActivity y pasa el Pokémon seleccionado
            intent.putExtra("pokemon", p);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
       return dataset.size()  ;// Devuelve el número de elementos en la lista
       // return  isHorizontal ? 10 : dataset.size() * 3;
       // return dataset.size() + (dataset.size() / 2);

    }

    public void adicionarListaPokemon(List<Pokemon> listaPokemon) {// Método para añadir una lista de Pokémon al dataset existente
        dataset.addAll(listaPokemon);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {  // ViewHolder que contiene las vistas para mostrar los datos de cada Pokémon
        private ImageView fotoImageView;
        private TextView nombreTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializa las vistas del ViewHolder
            fotoImageView = itemView.findViewById(R.id.fotoIV);
            nombreTextView = itemView.findViewById(R.id.nombreTV);
        }
    }
}
