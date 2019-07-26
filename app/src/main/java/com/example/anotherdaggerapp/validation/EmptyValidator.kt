package com.example.anotherdaggerapp.validation

/**
 * Created by vitaly on 2019-07-26.
 */
class EmptyValidator(string: String) : IValidator {
    override val isValid: Boolean = string.isNotEmpty()
}