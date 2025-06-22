package com.example.sellingserviceapp.model.domain

data class ServiceDomain(
    val id: Int,
    val userId: Int,
    val tittle: String,
    val description: String?,
    val duration: Int?,
    val photoPath: String?,
    val photo: String?,
    val price: Int?,
    val createdAt: String?,
    val updatedAt: String?,
    val formats: List<FormatsDomain>?,
    val priceTypeName: String?,
    val priceTypeCode: String?,
    val statusCode: String,
    val statusName: String,
    val categoryCode: String,
    val categoryName: String,
    val subcategoryCode: String,
    val subcategoryName: String
) {
    companion object {
        val EMPTY = ServiceDomain(
            id = 0,
            userId = 0,
            tittle = "",
            description = "",
            duration = 0,
            photoPath = "",
            photo = "",
            price = 0,
            createdAt = "",
            updatedAt = "",
            formats = emptyList(),
            priceTypeName = "",
            priceTypeCode = "",
            statusCode = "",
            statusName = "",
            categoryCode = "",
            categoryName = "",
            subcategoryCode = "",
            subcategoryName = "",
        )
        val PLUMBING_FIXED_ACTIVE = ServiceDomain(
            id = 201, userId = 11, tittle = "Установка смесителя на кухне",
            description = "Быстрая и качественная установка вашего нового смесителя. Все необходимые инструменты привожу с собой.",
            duration = 90, photoPath = "http://example.com/photos/plumbing.jpg", photo = null,
            price = 3500, // 35.00
            createdAt = "2023-02-15T11:00:00Z", updatedAt = "2023-02-16T09:20:00Z",
            formats = listOf(FormatsDomain.TEST),
            priceTypeName = "За услугу", priceTypeCode = "FIXED",
            statusCode = "ACTIVE", statusName = "Активна",
            categoryCode = "HOME_REPAIR", categoryName = "Ремонт на дому",
            subcategoryCode = "PLUMBING", subcategoryName = "Сантехника"
        )

        val ENGLISH_TUTOR_PER_HOUR_ACTIVE = ServiceDomain(
            id = 202, userId = 22, tittle = "Репетитор английского языка (B2-C1)",
            description = "Разговорная практика, грамматика, подготовка к международным экзаменам. Онлайн или очно.",
            duration = 60, photoPath = "http://example.com/photos/eng_tutor.jpg", photo = null,
            price = 2000, // 20.00
            createdAt = "2023-01-20T14:30:00Z", updatedAt = "2023-05-01T10:00:00Z",
            formats = listOf(FormatsDomain.TEST),
            priceTypeName = "За час", priceTypeCode = "PER_HOUR",
            statusCode = "ACTIVE", statusName = "Активна",
            categoryCode = "EDUCATION", categoryName = "Образование",
            subcategoryCode = "LANGUAGES", subcategoryName = "Иностранные языки"
        )

        val PHOTO_SESSION_INACTIVE = ServiceDomain(
            id = 203, userId = 33, tittle = "Фотосессия на природе",
            description = "Профессиональная фотосессия в парках или за городом. Обработка 20 лучших фото.",
            duration = 120, photoPath = "http://example.com/photos/nature_photo.jpg", photo = null,
            price = 7000, // 70.00
            createdAt = "2022-11-10T08:00:00Z", updatedAt = "2023-03-01T17:45:00Z",
            formats = listOf(FormatsDomain.TEST),
            priceTypeName = "За фотосессию", priceTypeCode = "FIXED_PACKAGE",
            statusCode = "INACTIVE", statusName = "Неактивна",
            categoryCode = "PHOTO_VIDEO", categoryName = "Фото и Видео",
            subcategoryCode = "PHOTO_SESSION", subcategoryName = "Фотосессии"
        )

        val WEB_DEV_PROJECT_ACTIVE = ServiceDomain(
            id = 204, userId = 44, tittle = "Разработка Landing Page",
            description = "Создание продающего Landing Page под ключ: дизайн, верстка, базовая SEO-оптимизация.",
            duration = null, // Проектная работа
            photoPath = null, photo = null,
            price = 25000, // 250.00
            createdAt = "2023-04-05T12:00:00Z", updatedAt = "2023-06-01T10:10:00Z",
            formats = listOf(FormatsDomain.TEST),
            priceTypeName = "За проект", priceTypeCode = "PROJECT_BASED",
            statusCode = "ACTIVE", statusName = "Активна",
            categoryCode = "IT_WEB", categoryName = "IT и Веб разработка",
            subcategoryCode = "WEB_DEVELOPMENT", subcategoryName = "Веб-разработка"
        )
        val MASSAGE_THERAPY_ACTIVE = ServiceDomain(
            id = 205, userId = 55, tittle = "Классический массаж спины",
            description = "Профессиональный лечебный и расслабляющий массаж спины. Выезд на дом или в кабинете.",
            duration = 60, photoPath = "http://example.com/photos/massage.jpg", photo = null,
            price = 3000, // 30.00
            createdAt = "2023-03-22T10:00:00Z", updatedAt = "2023-05-15T11:00:00Z",
            formats = listOf(FormatsDomain.TEST),
            priceTypeName = "За сеанс", priceTypeCode = "PER_SESSION",
            statusCode = "ACTIVE", statusName = "Активна",
            categoryCode = "HEALTH_BEAUTY", categoryName = "Здоровье и красота",
            subcategoryCode = "MASSAGE", subcategoryName = "Массаж"
        )
    }
}