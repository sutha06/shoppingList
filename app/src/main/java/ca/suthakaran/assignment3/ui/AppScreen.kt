package ca.suthakaran.assignment3.ui


import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ca.suthakaran.assignment3.ui.navigation.ShoppingNavHost

/**
 * Top level composable that represents screens for the application.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppScreen(navController: NavHostController = rememberNavController()) {
    ShoppingNavHost(navController = navController)
}
