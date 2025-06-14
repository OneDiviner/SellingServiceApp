/*
import androidx.compose.animation.core.copy
import app.cash.turbine.test
import com.example.sellingserviceapp.data.DataManager
import com.example.sellingserviceapp.model.domain.CategoryDomain
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.model.domain.UserDomain
import com.example.sellingserviceapp.ui.screen.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private lateinit var testDispatcher: TestDispatcher
    private lateinit var dataManagerMock: DataManager
    private lateinit var viewModel: MainViewModel

    // Константы для читаемости и избежания магических чисел
    private val DEFAULT_PAGE = 0 // Предполагаем, что MainViewModel.PAGE = 0

    @Before
    fun setUp() {
        // Используем UnconfinedTestDispatcher для этого примера,
        // он выполняет корутины немедленно в текущем потоке до первой точки приостановки.
        testDispatcher = UnconfinedTestDispatcher()
        Dispatchers.setMain(testDispatcher)

        dataManagerMock = mock()
        // ViewModel будет создаваться в каждом тесте для лучшего контроля над init
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init - loads user, services and categories, updates states correctly`() = runTest(testDispatcher) {
        // Arrange (Подготовка)
        val mockUser = UserDomain.EMPTY.copy(firstName = "Name", secondName = "SecondName")
        val mockServices = listOf(
            ServiceDomain.EMPTY.copy(id = 1, tittle = "Title"),
            ServiceDomain.EMPTY.copy(
                id = 2,
                tittle = "Car Service",
                subcategoryCode = "SUBCATEGORY_REPAIR_CAR" // Убедитесь, что это значение используется в логике фильтрации ViewModel
            )
        )
        val mockCategories = listOf(CategoryDomain.EMPTY.copy(id = 1, name = "Category 1"))

        // Настройка моков для DataManager
        whenever(dataManagerMock.getUserFlow()).thenReturn(flowOf(mockUser))
        whenever(
            dataManagerMock.requestServices(
                DEFAULT_PAGE, // Используем константу
                MainViewModel.SIZE
            )
        ).thenReturn(mockServices) // Этот метод должен быть suspend
        whenever(dataManagerMock.getCategories()).thenReturn(mockCategories) // Этот метод должен быть suspend

        // Act (Действие)
        // Создаем ViewModel здесь, чтобы init-блок вызвался с нашими моками
        viewModel = MainViewModel(dataManagerMock)

        // Для UnconfinedTestDispatcher большинство корутин, запущенных в init,
        // уже должны были выполниться или, по крайней мере, начать выполняться.
        // testScheduler.advanceUntilIdle() гарантирует, что все запланированные задачи завершены.
        testScheduler.advanceUntilIdle() // Даем всем корутинам завершиться

        // Assert (Проверка)

        // 1. Проверяем состояние userFLow
        viewModel.userFLow.test {
            assertEquals("User data should be loaded", mockUser, awaitItem())
            cancelAndConsumeRemainingEvents()
        }

        // 2. Проверяем состояние servicesFlow
        viewModel.servicesFlow.test {
            assertEquals("Services data should be loaded", mockServices, awaitItem())
            cancelAndConsumeRemainingEvents()
        }

        // 3. Проверяем состояние isRefreshing после всех операций
        // (оно должно стать false в конце init-корутины ViewModel)
        assertEquals("isRefreshing should be false after loading", false, viewModel.isRefreshing)

        // 4. Проверяем загруженные категории
        assertEquals("Categories should be loaded", mockCategories, viewModel.categories)

        // 5. Проверяем отфильтрованный carRepairList
        // Убедитесь, что логика в MainViewModel.init() для carRepairList корректна:
        // она должна фильтровать из свежезагруженных `currentServices`, а не из `serviceList`
        // который мог быть еще не обновлен.


        // 6. Проверяем, что методы DataManager были вызваны
        verify(dataManagerMock).getUserFlow()
        verify(dataManagerMock).requestServices(DEFAULT_PAGE, MainViewModel.SIZE)
        verify(dataManagerMock).getCategories()
    }

    @Test
    fun `getUser - updates userFlow with data from DataManager`() = runTest(testDispatcher) {
        // Arrange
        val testUser = UserDomain.EMPTY.copy(firstName = "Test User", secondName = "Test User")
        whenever(dataManagerMock.getUserFlow()).thenReturn(flowOf(testUser))
        // Мокаем вызовы, которые могут произойти в init(), чтобы они не мешали этому тесту,
        // если бы getUser() не вызывался в init. В данном случае getUser() вызывается в init.
        whenever(dataManagerMock.requestServices(DEFAULT_PAGE, MainViewModel.SIZE)).thenReturn(emptyList())
        whenever(dataManagerMock.getCategories()).thenReturn(emptyList())


        viewModel = MainViewModel(dataManagerMock) // init будет вызван, включая getUser()

        testScheduler.advanceUntilIdle() // Даем корутинам из init завершиться

        // Act - getUser вызывается в init ViewModel. Если бы мы хотели протестировать
        // повторный вызов viewModel.getUser() (если бы такой метод был публичным и имел другую логику),
        // мы бы вызвали его здесь.

        // Assert
        viewModel.userFLow.test {
            // Ожидаем значение, которое было установлено в результате вызова getUser() в init
            assertEquals("UserFlow should be updated by getUser", testUser, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
        // getUserFlow() вызывается один раз в init -> getUser()
        verify(dataManagerMock).getUserFlow()
    }

    @Test
    fun `getServiceListByCategory - fetches services and updates state`() =
        runTest(testDispatcher) {
            // Arrange
            val categoryId = 1
            val initialServicesInInit = listOf(ServiceDomain.EMPTY.copy(id = 99, tittle = "Init Service"))
            val servicesForCategory = listOf(ServiceDomain.EMPTY.copy(id = 3, tittle = "Category Service"))

            // Моки для вызовов в init() ViewModel
            whenever(dataManagerMock.getUserFlow()).thenReturn(flowOf(UserDomain.EMPTY))
            whenever(dataManagerMock.requestServices(DEFAULT_PAGE, MainViewModel.SIZE))
                .thenReturn(initialServicesInInit) // Сервисы, которые загрузятся при init
            whenever(dataManagerMock.getCategories()).thenReturn(emptyList())

            // Мок для целевого вызова fetchServicesByCategory
            whenever(
                dataManagerMock.fetchServicesByCategory(
                    DEFAULT_PAGE,
                    MainViewModel.SIZE,
                    categoryId
                )
            ).thenReturn(servicesForCategory) // Этот метод должен быть suspend

            viewModel = MainViewModel(dataManagerMock) // ViewModel создается, init() отрабатывает

            testScheduler.advanceUntilIdle() // Даем корутинам из init завершиться

            // Act: вызываем тестируемый метод
            viewModel.getServiceListByCategory(categoryId)
            testScheduler.advanceUntilIdle() // Даем корутине getServiceListByCategory завершиться

            // Assert
            // isRefreshing должен стать true в начале getServiceListByCategory и false в конце
            assertEquals(
                "isRefreshing should be false after getServiceListByCategory",
                false,
                viewModel.isRefreshing
            )

            viewModel.servicesFlow.test {
                // servicesFlow - это StateFlow, он хранит последнее значение.
                // Первое значение будет от init() (initialServicesInInit).
                // Второе (последнее) значение будет от getServiceListByCategory (servicesForCategory).
                // Однако, если getServiceListByCategory перезаписывает то же StateFlow,
                // мы увидим только последнее значение после advanceUntilIdle.
                // Если бы это был SharedFlow или обычный Flow, потребовалось бы awaitItem() дважды.
                // Для StateFlow с Turbine, после advanceUntilIdle, awaitItem() должен вернуть последнее эмитированное значение.
                assertEquals(
                    "ServicesFlow should show services for the category",
                    servicesForCategory,
                    awaitItem()
                )
                cancelAndConsumeRemainingEvents()
            }

            verify(dataManagerMock).fetchServicesByCategory(
                DEFAULT_PAGE,
                MainViewModel.SIZE,
                categoryId
            )
            // Также проверяем, что вызовы из init были (они нужны для полной инициализации ViewModel)
            verify(dataManagerMock).getUserFlow()
            verify(dataManagerMock).requestServices(DEFAULT_PAGE, MainViewModel.SIZE)
            verify(dataManagerMock).getCategories()
        }
}*/
