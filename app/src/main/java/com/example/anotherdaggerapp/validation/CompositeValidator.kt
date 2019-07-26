package com.example.anotherdaggerapp.validation

import java.util.*

class CompositeValidator<ERROR>(vararg validators: Pair<IValidator, ERROR>) : IValidator {
    private val mValidators = ArrayList<Pair<IValidator, ERROR>>()

    val errors = mutableSetOf<ERROR>()

    init {
        Collections.addAll(mValidators, *validators)
        for (v in mValidators) {
            if (!v.first.isValid) {
                errors.add(v.second)
            }
        }
    }

    override val isValid: Boolean = errors.isEmpty()
}


