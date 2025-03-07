package com.example.sellingserviceapp.util.extension

import com.example.sellingserviceapp.ui.screen.authentication.login.LoginViewModel
import com.example.sellingserviceapp.ui.screen.authentication.registration.RegistrationViewModel
import com.example.sellingserviceapp.ui.screen.authentication.state.ButtonState
import com.example.sellingserviceapp.ui.screen.authentication.state.TextFieldState
import com.example.sellingserviceapp.ui.screen.authentication.userinfo.UserInfoViewModel
import org.w3c.dom.Text

fun validatePasswords(password: String, confirmPassword: String): TextFieldState {
    return when {
        password.isEmpty() -> TextFieldState.Error("Пароль не может быть пустым.")
        password != confirmPassword -> TextFieldState.Error("Пароли не совпадают.")
        //password.length < 8 -> "Пароль должен содержать не менее 8 символов."
        else -> TextFieldState.Default
    }
}

fun validateUserInfo(info: String): TextFieldState {
    return when {

        info.isEmpty() -> TextFieldState.Error("Поле не может быть пустым")

        info.any { it.isDigit() } -> TextFieldState.Error("Поле не должно содержать цифр")

        else -> TextFieldState.Default
    }
}

fun validateEmail(email: String): TextFieldState {
    return when {
        email.isEmpty() -> TextFieldState.Error("Почта не может быть пустой")

        " " in email -> TextFieldState.Error("Почтовый адресс не дожен содержать пробелов")

        !email.contains("@") -> TextFieldState.Error("Почтовый адресс должен содержать '@'")

        email.count { it == '@' } > 1 -> TextFieldState.Error("Почтовый адресс не должен содержать несколько '@'")

        !email.contains(".") -> TextFieldState.Error("Почтовый адресс должен содержать домен (например, .com, .ru)")

        email.matches("^[а-яА-я(){}<>,;:'/|*&^%$#!?]".toRegex()) -> TextFieldState.Error("Почтовый адрес не должен содержать кириллические буквы или спецсимволы (!,#,$ и др.)")

        !email.matches("^[-a-zA-z0-9+=_~]+(?:\\.[-a-zA-z0-9+=_~]+)*@(?:[a-z0-9]([-a-zA-z0-9]{0,61}[a-z0-9])?\\.)*(amazon|abarth|abbott|aero|arpa|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|nameState|net|org|pro|tel|travel|[a-z][a-z])".toRegex()) ->
            TextFieldState.Error("Неверный формат почтового адресса. Проверьте правильность ввода")

        else -> TextFieldState.Default
    }
}

fun LoginViewModel.validateFields(): ButtonState {
    return when {
        email.state is TextFieldState.Default && email.value.isNotBlank() &&
                password.state is TextFieldState.Default && password.value.isNotBlank() -> ButtonState.Ready

        else -> ButtonState.Default
    }
}

fun RegistrationViewModel.nextButtonValidateFields(): ButtonState {
    return when {

        email.state is TextFieldState.Default && email.value.isNotBlank() &&
                password.state is TextFieldState.Default && password.value.isNotBlank() &&
                confirmPassword.state is TextFieldState.Default && confirmPassword.value.isNotBlank() -> ButtonState.Ready

        else -> ButtonState.Default
    }
}

fun RegistrationViewModel.requestNewCodeButtonValidateButton(): ButtonState {
    when {
        //TODO: Сделать валидацию по таймеру
                emailConfirmCode.state is TextFieldState.Default
                        && emailConfirmCode.value.isNotBlank() -> return ButtonState.Ready

        else -> return ButtonState.Default
    }
}

fun UserInfoViewModel.validateFields(): ButtonState {
    return when {

        secondName.state is TextFieldState.Default && secondName.value.isNotBlank() &&
        name.state is TextFieldState.Default && name.value.isNotBlank() &&
        lastName.state is TextFieldState.Default && lastName.value.isNotBlank() &&
        phoneNumber.state is TextFieldState.Default && phoneNumber.value.isNotBlank() -> ButtonState.Ready

        else -> ButtonState.Default
    }
}