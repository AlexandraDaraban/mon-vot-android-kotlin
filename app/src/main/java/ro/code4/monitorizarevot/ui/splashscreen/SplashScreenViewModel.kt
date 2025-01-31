package ro.code4.monitorizarevot.ui.splashscreen

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import org.koin.core.inject
import ro.code4.monitorizarevot.helper.SingleLiveEvent
import ro.code4.monitorizarevot.helper.getToken
import ro.code4.monitorizarevot.helper.hasCompletedOnboarding
import ro.code4.monitorizarevot.helper.isPollingStationConfigCompleted
import ro.code4.monitorizarevot.ui.base.BaseViewModel

class SplashScreenViewModel : BaseViewModel() {
    private val sharedPreferences: SharedPreferences by inject()
    private val loginLiveData = SingleLiveEvent<LoginStatus>()

    fun loginLiveData(): LiveData<LoginStatus> = loginLiveData

    init {
        checkLogin()
    }

    private fun checkLogin() {
        val isLoggedIn = sharedPreferences.getToken() != null

        loginLiveData.postValue(
            LoginStatus(
                isLoggedIn,
                sharedPreferences.isPollingStationConfigCompleted(),
                sharedPreferences.hasCompletedOnboarding()
            )
        )
    }

    data class LoginStatus(
        val isLoggedIn: Boolean,
        val isPollingStationConfigCompleted: Boolean,
        val onboardingCompleted: Boolean
    )
}