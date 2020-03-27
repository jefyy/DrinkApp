package brown.jeff.cocktailapp.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import brown.jeff.cocktailapp.R
import brown.jeff.cocktailapp.model.Drink
import brown.jeff.cocktailapp.ui.adapter.DrinkAdapter
import kotlinx.android.synthetic.main.fragment_popular.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularDrinksFragment : Fragment() {

    private val popularDrinksViewModel: PopularDrinksViewModel by viewModel()
    private lateinit var popularDrinkAdapter: DrinkAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_popular, container, false)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        popularDrinkAdapter = DrinkAdapter(
            emptyList()
        ) { drink: Drink -> handleDrinkClickListener(drink) }

        recyclerview_popular.apply {
            adapter = popularDrinkAdapter
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)

        }



        popularDrinksViewModel.getAllPopularDrinks()
        popularDrinksViewModel.popularDrinks.observe(viewLifecycleOwner, Observer {
            popularDrinkAdapter.updateDrinkList(it)

        })


    }

    fun handleDrinkClickListener(drink: Drink) {
        Toast.makeText(context, "Drink Name: ${drink.drink}", Toast.LENGTH_SHORT).show();

    }
}
