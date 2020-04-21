package brown.jeff.cocktailapp.ui.random

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import brown.jeff.cocktailapp.R
import brown.jeff.cocktailapp.model.Drink
import brown.jeff.cocktailapp.ui.adapter.DrinkAdapter
import brown.jeff.cocktailapp.ui.favorite.FavoriteDrinksFragmentDirections
import brown.jeff.cocktailapp.util.loadImage
import kotlinx.android.synthetic.main.drink_random_fragment.*
import kotlinx.android.synthetic.main.fragment_popular.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RandomDrinkFragment : Fragment() {


    private val randomDrinkViewModel: RandomDrinkViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.drink_random_fragment, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.random_drink_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.random_menuitem -> {
                randomDrinkViewModel.getRandomDrink()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        randomDrinkViewModel.getRandomDrink()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeRandomDrink()
    }





    private fun observeRandomDrink() {
        randomDrinkViewModel.drink.observe(viewLifecycleOwner, Observer {
            setIngredients(it.strMeasure1, it.strIngredient1, ingredient1_tv, view_divider1)
            setIngredients(it.strMeasure2, it.strIngredient2, ingredient2_tv, view_divider2)
            setIngredients(it.strMeasure3, it.strIngredient3, ingredient3_tv, view_divider3)
            setIngredients(it.strMeasure4, it.strIngredient4, ingredient4_tv, view_divider4)
            setIngredients(it.strMeasure5, it.strIngredient5, ingredient5_tv, view_divider5)
            setIngredients(it.strMeasure6, it.strIngredient6, ingredient6_tv, view_divider6)
            setIngredients(it.strMeasure7, it.strIngredient7, ingredient7_tv, view_divider7)
            setIngredients(it.strMeasure8, it.strIngredient8, ingredient8_tv, view_divider8)
            setIngredients(it.strMeasure9, it.strIngredient9, ingredient9_tv, view_divider9)
            setIngredients(it.strMeasure10, it.strIngredient10, ingredient10_tv, view_divider10)
            setIngredients(it.strMeasure11, it.strIngredient11, ingredient11_tv, view_divider11)
            setIngredients(it.strMeasure12, it.strIngredient12, ingredient12_tv, view_divider12)
            setIngredients(it.strMeasure13, it.strIngredient13, ingredient13_tv, view_divider13)
            setIngredients(it.strMeasure14, it.strIngredient14, ingredient14_tv, view_divider14)
            setIngredients(it.strMeasure15, it.strIngredient15, ingredient15_tv, view_divider15)
            loadImage(randomdrink_imageview, it)
            instructions_textview.text = it.strInstructions

        })
    }

    private fun setIngredients(
        measure: String?,
        ingredientName: String?,
        textView: TextView,
        view: View
    ) {
        if (ingredientName != null && measure != null) {
            textView.visibility = View.VISIBLE
            view.visibility = View.VISIBLE
            textView.text =
                getString(R.string.set_ingredients_and_measurements, measure, ingredientName)
        } else {
            textView.visibility = View.GONE
            view.visibility = View.GONE
        }
    }


}
