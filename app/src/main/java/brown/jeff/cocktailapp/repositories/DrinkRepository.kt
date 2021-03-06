package brown.jeff.cocktailapp.repositories

import androidx.lifecycle.LiveData
import brown.jeff.cocktailapp.model.Drink
import brown.jeff.cocktailapp.model.Drinks
import brown.jeff.cocktailapp.network.DrinkApi
import brown.jeff.cocktailapp.network.Errors
import brown.jeff.cocktailapp.network.NetworkConnection
import brown.jeff.cocktailapp.network.Result
import brown.jeff.cocktailapp.network.Result.Failure
import brown.jeff.cocktailapp.network.Result.Success
import brown.jeff.cocktailapp.room.DrinkDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import timber.log.Timber

class DrinkRepository(
    private val drinkApi: DrinkApi,
    private val drinkDao: DrinkDao,
    private val networkConnection: NetworkConnection
) {


    //val data = drinkDao.getAllDrinks()
    //DRINK API CALLS
    //possible to use kotlin extensions to clean class up
    //separate into 2 classes; Api calls, Room calls

    //gets list of drinks by name
    suspend fun getDrinksByName(drinkName: String): Result<Drinks> {
        return withContext(Dispatchers.IO) {
            makeCall(drinkApi.searchDrinksByName(drinkName))
        }
    }


    //gets single drink by drinkId
    suspend fun getDrinkById(drinkId: String): Result<Drinks> {
        return withContext(Dispatchers.IO) {
            makeCall(drinkApi.getDrinkById(drinkId))
        }
    }

    //gets drinks by ingredients
    suspend fun getDrinksByIngredients(ingredients: String?): Result<Drinks> {
        return withContext(Dispatchers.IO) {
            makeCall(drinkApi.getDrinksByIngredient(ingredients))
        }
    }

    //gets popular drinks
    suspend fun getPopularDrinks(): Result<Drinks> {
        Timber.e("Drinks called")
        return withContext(Dispatchers.IO) {
            makeCall(drinkApi.getPopularDrinks())
        }
    }

    //gets random single drink
    suspend fun getRandomDrink(): Result<Drinks> {
        return withContext(Dispatchers.IO) {
            makeCall(drinkApi.getRandomDrink())
        }
    }

    //Call for api
    private fun <T> makeCall(call: Call<T>): Result<T> {
        return when (networkConnection.isInternetAvailable()) {
            true -> {
                try {
                    val response = call.execute()
                    when (response.isSuccessful && response.body() != null) {
                        true -> {
                            Success(response.body()!!)
                        }
                        false -> {
                            Failure(Errors.ResponseError(response.errorBody().toString()))
                        }
                    }
                } catch (e: java.lang.Exception) {
                    Failure(Errors.ExceptionError(e))
                }
            }
            false -> {
                Failure(Errors.NetworkError())
            }
        }
    }

    //DRINKDAO CALLS


    fun getDrinksLocalDB(): LiveData<List<Drink>> {
        Timber.e("Repository: Drinks Retrieved")
        return drinkDao.getAllDrinks()

    }

    suspend fun deleteAllDrinks() {
        withContext(Dispatchers.IO) {
            Timber.e("Repository: Delete Drinks")
            drinkDao.deleteAllDrinks()
        }
    }

    suspend fun insertDrinksLocalDB(drink: Drink): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                drinkDao.insertDrink(drink)
                Timber.e("Successfully inserted drink")
                true
            } catch (e: Exception) {
                Timber.e(e)
                false
            }
        }
    }

    suspend fun removeDrinkById(drinkId: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                if (drinkId.isNotEmpty()) {
                    drinkDao.deleteDrinkById(drinkId)
                    true
                } else {
                    Timber.e("Unknown Error")
                    false
                }
            } catch (e: Exception) {
                Timber.e(e)
                false
            }
        }
    }


}