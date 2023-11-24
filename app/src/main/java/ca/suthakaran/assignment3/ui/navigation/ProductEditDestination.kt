package ca.suthakaran.assignment3.ui.navigation

import ca.suthakaran.assignment3.R

object ProductEditDestination : NavigationDestination {
    override val route = "item_edit"
    override val titleRes = R.string.edit_item_title
    const val productIdArg = "itemId"
    val routeWithArgs = "$route/{$productIdArg}"
}