package ca.suthakaran.assignment3.ui.navigation

import ca.suthakaran.assignment3.R

object ProductEditDestination : NavigationDestination {
    override val route = "product_edit"
    override val titleRes = R.string.edit_item_title
    const val productIdArg = "productId"
    val routeWithArgs = "$route/{$productIdArg}"
}