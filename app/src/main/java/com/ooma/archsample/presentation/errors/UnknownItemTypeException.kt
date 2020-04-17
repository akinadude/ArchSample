package com.ooma.archsample.presentation.errors

class UnknownItemTypeException(message: String) : RuntimeException(message) {

    constructor(itemType: Int) : this("Unknown item type: $itemType")
}