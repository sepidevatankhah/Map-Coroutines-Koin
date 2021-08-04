package ir.nwise.app.ui.map

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.whenever
import ir.nwise.domain.AppRepository
import ir.nwise.domain.models.Car
import ir.nwise.domain.usecase.GetCarsUseCase
import ir.nwise.app.ui.utils.CoroutineTestRule
import ir.nwise.app.ui.utils.captureEmittedData
import ir.nwise.app.ui.utils.captureLastEmittedValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MapViewModelTest {
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val coroutinesTestRule = CoroutineTestRule()

    private lateinit var testViewModel: MapViewModel

    private lateinit var getCarsUseCase: GetCarsUseCase

    @Mock
    private lateinit var appRepository: AppRepository

    @Mock
    private lateinit var networkManager: ir.nwise.data.common.utils.NetworkManager

    @Before
    fun setUp() {
        getCarsUseCase = GetCarsUseCase(
            appRepository = appRepository,
            dispatchers = coroutinesTestRule.testDispatcherProvider
        )
        testViewModel = MapViewModel(getCarsUseCase, networkManager)
    }

    @Test
    fun `#getCars() must emit #Loading and #Loaded ViewStates`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val carList = listOf(
                Car(
                    id = "1",
                    name = "title",
                    make = "BMW",
                    group = "group",
                    color = "color",
                    latitude = 12.0,
                    longitude = 12.0,
                    carImageUrl = "carImageUrl",
                    modelIdentifier = "modelIdentifier",
                    modelName = "modelName",
                    transmission = "transmission",
                    innerCleanliness = "innerCleanliness",
                    series = "series",
                    fuelType = "fuelType",
                    fuelLevel = 1.4f,
                    licensePlate = "licensePlate"
                )
            )
            //given
            val observer = testViewModel.liveData.captureEmittedData()
            whenever(networkManager.hasNetwork()).thenAnswer {
                true
            }
            whenever(appRepository.getCars()).thenAnswer {
                carList
            }

            //when
            testViewModel.getCars()

            //Then
            Assert.assertNotNull(testViewModel.liveData.value)
            Assert.assertEquals(
                listOf(MapViewState.Loading, MapViewState.Loaded(carList)),
                observer.invoke()
            )
        }

    @Test
    fun `#getCars() must return car list`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            val carList = listOf(
                Car(
                    id = "1",
                    name = "title",
                    make = "BMW",
                    group = "group",
                    color = "color",
                    latitude = 12.0,
                    longitude = 12.0,
                    carImageUrl = "carImageUrl",
                    modelIdentifier = "modelIdentifier",
                    modelName = "modelName",
                    transmission = "transmission",
                    innerCleanliness = "innerCleanliness",
                    series = "series",
                    fuelType = "fuelType",
                    fuelLevel = 1.4f,
                    licensePlate = "licensePlate"
                )
            )
            //given
            val observer = testViewModel.liveData.captureLastEmittedValue()
            whenever(networkManager.hasNetwork()).thenAnswer {
                true
            }
            whenever(appRepository.getCars()).thenAnswer {
                carList
            }

            //when
            testViewModel.getCars()

            //Then
            Assert.assertNotNull(testViewModel.liveData.value)
            Assert.assertEquals(
                testViewModel.liveData.value,
                observer.invoke()
            )
        }


    @Test
    fun `when fetching results fails then return an error and must emit #Loading and #Error ViewStates`() {
        val exception = Mockito.mock(HttpException::class.java)
        coroutinesTestRule.testDispatcher.runBlockingTest {
            //given
            val observer = testViewModel.liveData.captureLastEmittedValue()
            whenever(appRepository.getCars()).thenAnswer {
                throw (exception)
            }
            whenever(networkManager.hasNetwork()).thenAnswer {
                true
            }

            //when
            testViewModel.getCars()

            //Then
            Assert.assertNotNull(testViewModel.liveData.value)
            Assert.assertEquals(
                MapViewState.Error(exception),
                observer.invoke()
            )
        }
    }

    @Test
    fun `when there is no internet connection, it must emit #NoInternetConnectionException`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            //given
            val observer = testViewModel.liveData.captureLastEmittedValue()
            whenever(networkManager.hasNetwork()).thenAnswer {
                false
            }

            //when
            testViewModel.getCars()

            //Then
            Assert.assertNotNull(testViewModel.liveData.value)
            Assert.assertEquals(
                testViewModel.liveData.value,
                observer.invoke()
            )
        }
    }


}