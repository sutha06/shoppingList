package ca.suthakaran.assignment3.ui.navigation

import ca.suthakaran.assignment3.R

object ProductDetailsDestination : NavigationDestination {
    override val route = "item_details"
    override val titleRes = R.string.item_detail_title
    const val productIdArg = "itemId"
    val routeWithArgs = "$route/{$productIdArg}"
}