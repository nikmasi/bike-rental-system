package project.data


sealed class LoginResult {
    object Success : LoginResult()
    object UserNotFound : LoginResult()
    object WrongPassword : LoginResult()
    object UsernameEmpty : LoginResult()
    object PasswordEmpty : LoginResult()
}

sealed class NewBikeResult {
    object Success : NewBikeResult()
    object InvalidPrice : NewBikeResult()
    object MissingPhoto : NewBikeResult()
    object EmptyLocation : NewBikeResult()
}