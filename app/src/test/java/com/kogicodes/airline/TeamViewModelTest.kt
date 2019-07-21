package com.kogicodes.airline

import android.app.Application
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.kogicodes.airline.models.airports.AirportsModel
import com.kogicodes.airline.models.basic.Resource
import com.kogicodes.airline.models.schedules.ScheduleModel
import com.kogicodes.airline.repository.AirportsRepository
import com.kogicodes.airline.ui.MainViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class TeamViewModelTest {

    private lateinit var viewModel: MainViewModel

    @Spy
    private val scheduleObservable = MediatorLiveData<Resource<ScheduleModel>>()

    @Spy
    private val airPortsObservable = MediatorLiveData<Resource<AirportsModel>>()


    @Spy
    private val teamListLiveData: MutableLiveData<List<AirportsModel>> = MutableLiveData()

    @Mock
    private lateinit var repo: AirportsRepository

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()


    @Before
    fun setUp() {
        `when`(repo.airPortsObservable).thenReturn(airPortsObservable)
        viewModel = MainViewModel(application = Application())


    }
}