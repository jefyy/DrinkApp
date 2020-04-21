package brown.jeff.cocktailapp.ui.drinkclicked

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import brown.jeff.cocktailapp.model.Drink
import brown.jeff.cocktailapp.repositories.DrinkRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class DrinkClickedViewModel(
    private val sharedDrinkViewModelData: SharedDrinkViewModelData,
    private val drinkRepository: DrinkRepository
) : ViewModel() {
    // TODO: Implement the ViewModel

    val drink: LiveData<Drink>
        get() = sharedDrinkViewModelData.drink

    fun setDrinkValue(drink: Drink) {
        sharedDrinkViewModelData.setDrink(drink)
    }

    fun addDrinkToFavorites(drink: Drink) {
        viewModelScope.launch {
            drinkRepository.insertDrinksLocalDB(drink)
        }
    }

    fun removeDrinkFromFavorites(drinkId: String) {
        viewModelScope.launch {
            drinkRepository.removeDrinkById(drinkId)
        }
    }


}
