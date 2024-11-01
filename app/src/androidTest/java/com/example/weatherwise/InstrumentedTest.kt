package com.example.weatherwise

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.example.weatherwise.data.dao.AppDatabase
import com.example.weatherwise.data.dao.WeatherDao
import com.example.weatherwise.data.dao.WeatherEntity
import com.example.weatherwise.data.datasource.CityWeather
import com.example.weatherwise.data.datasource.Clouds
import com.example.weatherwise.data.datasource.Weather
import com.example.weatherwise.data.datasource.WeatherMain
import com.example.weatherwise.data.datasource.Wind
import com.example.weatherwise.feature.about.About
import com.example.weatherwise.utils.DateConverter
import com.example.weatherwise.utils.TempConverter
import com.example.weatherwise.utils.WeatherCityId
import com.example.weatherwise.utils.WeatherConverter
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.Calendar
import java.util.Date


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(MockitoJUnitRunner::class)
class InstrumentedTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var db: AppDatabase
    private lateinit var dao: WeatherDao

    private val mockData = MockData()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>().applicationContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.cityWeatherDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun packageName_isCorrect() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.weatherwise", appContext.packageName)
    }

    @Test
    fun dateConvert_isCorrect() {
        val date = DateConverter.toDateString(1716637585589)
        assertEquals("2024.05.25", date)
    }


    @Test
    fun dbDate_isCorrect() {
        val calendar = Calendar.getInstance()
        calendar.set(2024, Calendar.MAY, 25)
        val testDate = calendar.time

        val date = DateConverter.fromDate(testDate)
        assertEquals(testDate.time, date)
    }

    @Test
    fun tempConvert_isCorrect() {
        val celsius = TempConverter.celsiusFromKelvin(295.91)
        assertEquals(23, celsius)
    }

    @Test
    fun dbWrite_isCorrect() = runBlocking {

        dao.save(mockData.mockWeatherEntity)
        assertTrue("", dao.getWeathers().contains(mockData.mockWeatherEntity))
    }

    @Test
    fun dbClear_isCorrect() = runBlocking {
        val weatherEntity = WeatherEntity(
            uuid = mockData.mockUUID,
            city = "NAME",
            desc = "asd",
            icon = "01",
            temp = 1.0,
            humidity = 3,
            wind = 0.0,
            clouds = 100,
            date = DateConverter.fromDate(Date())
        )
        dao.save(weatherEntity)
        dao.save(weatherEntity)
        dao.clear()
        assertEquals(dao.getWeathers().size, 0)
    }

    @Test
    fun dbDelete_isCorrect() = runBlocking {
        val weatherEntity = WeatherEntity(
            uuid = mockData.mockUUID,
            city = "NAME",
            desc = "asd",
            icon = "01",
            temp = 1.0,
            humidity = 3,
            wind = 0.0,
            clouds = 100,
            date = DateConverter.fromDate(Date())
        )
        dao.save(weatherEntity)
        dao.deleteWeather(mockData.mockUUID)
        assertFalse(dao.getWeathers().contains(weatherEntity))
    }

    @Test
    fun dataType_isCorrect() {

        val cityWeather = CityWeather(
            weather = listOf(Weather("asd", "01")),
            weatherMain = WeatherMain(1.0, 2, 3),
            wind = Wind(0.0),
            clouds = Clouds(100),
            name = "NAME"
        )

        val weatherEntity = WeatherEntity(
            uuid = mockData.mockUUID,
            city = "NAME",
            desc = "asd",
            icon = "01",
            temp = 1.0,
            humidity = 3,
            wind = 0.0,
            clouds = 100,
            date = DateConverter.fromDate(mockData.mockDate)
        )
        assertEquals(
            weatherEntity,
            WeatherConverter.convertToRoomWeather(cityWeather).copy(
                uuid = mockData.mockUUID, date = DateConverter.fromDate(mockData.mockDate)
            )
        )
    }

    @Test
    fun cityId_isCorrect() {
        val cityId = WeatherCityId.getCityId("Szeged")
        assertEquals("715429", cityId)
    }

    @Test
    fun testBackOption_isCorrect() {
        composeTestRule.setContent {
            About {}
        }
        composeTestRule.onNodeWithContentDescription("back").assertIsDisplayed()
    }
}