package org.d3if0020.assesment3mobpro.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.d3if0020.assesment3mobpro.data.User

class UserViewModel : ViewModel() {
    private val _user = MutableStateFlow(User())

    fun updateUser(newUser: User) {
        _user.value = newUser
    }
}
