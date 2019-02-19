package com.mdmobile.cyclops.util

/**
 * Data class that holds values for each property.
 * @uiLabel -> is the respective label to use in UI to show the property Name
 * @internalLabel -> used internally to point to this specific value
 * @statisticable -> whether or not this is a value that can be showed in the list of attributes while creating a custom chart
 */
data class Property(val internalLabel: String, val uiLabel: String = internalLabel,
                    val statisticable: Boolean = false)