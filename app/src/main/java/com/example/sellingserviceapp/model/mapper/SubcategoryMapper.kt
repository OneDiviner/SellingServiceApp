package com.example.sellingserviceapp.model.mapper

import com.example.sellingserviceapp.model.domain.CategoryDomain
import com.example.sellingserviceapp.model.domain.SubcategoryDomain
import com.example.sellingserviceapp.model.dto.CategoryDto
import com.example.sellingserviceapp.model.dto.SubcategoryDto
import com.example.sellingserviceapp.model.entity.CategoryEntity
import com.example.sellingserviceapp.model.entity.SubcategoryEntity
import com.example.sellingserviceapp.model.mapper.SubcategoryMapper.toDomain
import com.example.sellingserviceapp.model.mapper.SubcategoryMapper.toEntity

private val subcategoryMap = mapOf(
    // Ремонт и отделка
    "SUBCATEGORY_INTERIOR_DESIGN" to "Дизайн интерьеров",
    "SUBCATEGORY_PLUMBING" to "Сантехника",
    "SUBCATEGORY_ELECTRICAL" to "Электрика",
    "SUBCATEGORY_FURNITURE" to "Сборка и ремонт мебели",
    "SUBCATEGORY_PAINTING" to "Малярные работы",
    "SUBCATEGORY_CEILINGS" to "Потолки",
    "SUBCATEGORY_FLOORING" to "Полы и напольные покрытия",
    "SUBCATEGORY_DOORS" to "Двери",
    "SUBCATEGORY_TURNKEY_REPAIR" to "Ремонт квартир и домов под ключ",
    "SUBCATEGORY_WALLPAPER" to "Поклейка обоев",

    // Красота
    "SUBCATEGORY_MANICURE_PEDICURE" to "Маникюр, педикюр",
    "SUBCATEGORY_HAIRDRESSING" to "Услуги парикмахера",
    "SUBCATEGORY_LASHES_BROWS" to "Ресницы, брови",
    "SUBCATEGORY_PERMANENT_MAKEUP" to "Перманентный макияж",
    "SUBCATEGORY_COSMETOLOGY" to "Косметология",
    "SUBCATEGORY_HAIR_REMOVAL" to "Эпиляция",
    "SUBCATEGORY_MAKEUP" to "Макияж",
    "SUBCATEGORY_SPA_MASSAGE" to "СПА-услуги, массаж",
    "SUBCATEGORY_TATTOO_PIERCING" to "Тату, пирсинг",
    "SUBCATEGORY_WORKSPACE_RENTAL" to "Аренда рабочего места",

    // Ремонт и обслуживание техники
    "SUBCATEGORY_TV" to "Телевизоры",
    "SUBCATEGORY_MOBILE_DEVICES" to "Мобильные устройства",
    "SUBCATEGORY_WASHING_MACHINES" to "Стиральные машины",
    "SUBCATEGORY_DISHWASHERS" to "Посудомоечные машины",
    "SUBCATEGORY_REFRIGERATORS" to "Холодильники",
    "SUBCATEGORY_AIR_CONDITIONERS" to "Кондиционеры",
    "SUBCATEGORY_COFFEE_MACHINES" to "Кофемашины",
    "SUBCATEGORY_PCS" to "Персональные компьютеры",
    "SUBCATEGORY_PHOTO_AUDIO_VIDEO" to "Фото, аудио, видеотехника",
    "SUBCATEGORY_APPLIANCE_INSTALLATION" to "Монтаж техники",

    // IT, дизайн, маркетинг
    "SUBCATEGORY_ADVERTISING_PR_MARKETING" to "Реклама, PR, маркетинг",
    "SUBCATEGORY_GRAPHIC_DESIGN" to "Графический дизайн",
    "SUBCATEGORY_PROGRAMMING_CRM" to "Программирование, настройка CRM",
    "SUBCATEGORY_WEBSITE_APP_DEVELOPMENT" to "Создание сайтов и приложений",
    "SUBCATEGORY_TEXT_TRANSLATION" to "Тексты, переводы",
    "SUBCATEGORY_MARKETPLACES_LISTINGS" to "Маркетплейсы, сервисы объявлений",
    "SUBCATEGORY_SMM_PROMOTION" to "Продвижение, соцсети",
    "SUBCATEGORY_SEO_CONTEXT_ADS" to "SEO, контекстная реклама",
    "SUBCATEGORY_WEB_MOBILE_DESIGN" to "Веб- и мобильный дизайн",
    "SUBCATEGORY_MARKETPLACE_INFOGRAPHICS" to "Инфографика для маркетплейсов",

    // Образование
    "SUBCATEGORY_TUTORING" to "Репетиторство",
    "SUBCATEGORY_COURSES" to "Курсы",
    "SUBCATEGORY_CHILD_DEVELOPMENT" to "Детское развитие",
    "SUBCATEGORY_PROFESSIONAL_TRAINING" to "Профессиональная подготовка",
    "SUBCATEGORY_MUSIC" to "Музыка",
    "SUBCATEGORY_DRIVING" to "Вождение",

    // Праздники, мероприятия
    "SUBCATEGORY_EVENT_ORGANIZATION" to "Организация и проведение мероприятий",
    "SUBCATEGORY_EVENT_DECOR" to "Оформление и декор",
    "SUBCATEGORY_EVENT_RENTAL" to "Прокат и аренда для мероприятий",
    "SUBCATEGORY_LEISURE_ORGANIZATION" to "Организация досуга и отдыха",

    // Уборка
    "SUBCATEGORY_DEEP_CLEANING" to "Генеральная уборка",
    "SUBCATEGORY_WINDOW_CLEANING" to "Мойка окон",
    "SUBCATEGORY_REGULAR_CLEANING" to "Простая уборка",
    "SUBCATEGORY_CARPET_CLEANING" to "Чистка ковров",
    "SUBCATEGORY_UPHOLSTERY_CLEANING" to "Чистка мягкой мебели",
    "SUBCATEGORY_DISINFECTION_PEST_CONTROL" to "Дезинфекция, дезинсекция",

    // Фото и видеосъёмка
    "SUBCATEGORY_PHOTOGRAPHY" to "Фотосъёмка",
    "SUBCATEGORY_VIDEOGRAPHY" to "Видеосъёмка",
    "SUBCATEGORY_PHOTO_STUDIO_RENTAL" to "Аренда фотостудии",
    "SUBCATEGORY_PHOTO_VIDEO_EDITING" to "Монтаж фото, видео",

    // Грузовые перевозки
    "SUBCATEGORY_IN_CITY" to "По городу",
    "SUBCATEGORY_INTERCITY" to "Между городами",
    "SUBCATEGORY_INTERNATIONAL" to "Международные",

    // Охрана, безопасность
    "SUBCATEGORY_CCTV_INSTALLATION" to "Установка видеонаблюдения",
    "SUBCATEGORY_INTERCOM_INSTALLATION" to "Установка домофона",
    "SUBCATEGORY_PERSONAL_SECURITY" to "Личная охрана",
    "SUBCATEGORY_SECURITY_ALARM_INSTALLATION" to "Установка охранной сигнализации",
    "SUBCATEGORY_CCTV_REPAIR" to "Ремонт видеонаблюдения",

    // Автосервис
    "SUBCATEGORY_SCHEDULED_MAINTENANCE" to "Плановое ТО",
    "SUBCATEGORY_BODY_REPAIR_PAINTING" to "Кузовной ремонт и покраска",
    "SUBCATEGORY_CAR_DIAGNOSTICS_REPAIR" to "Диагностика и ремонт авто",
    "SUBCATEGORY_TUNING_EQUIPMENT" to "Тюнинг и оборудование",
    "SUBCATEGORY_CAR_WASH_CARE" to "Мойка и уход за авто",
    "SUBCATEGORY_TIRE_SERVICE_RIM_REPAIR" to "Шиномонтаж и ремонт дисков",
    "SUBCATEGORY_ROADSIDE_ASSISTANCE" to "Помощь на дороге",
    "SUBCATEGORY_SELF_SERVICE_CAR_SERVICES" to "Автосервисы для самообслуживания",
    "SUBCATEGORY_CAR_PURCHASE_ASSISTANCE" to "Помощь при покупке авто",

    // Строительство
    "SUBCATEGORY_TURNKEY_HOUSE_CONSTRUCTION" to "Строительство домов под ключ",
    "SUBCATEGORY_GARAGE_SAUNA_VERANDA_BUILDING" to "Строительство гаражей, бань, веранд",
    "SUBCATEGORY_WOODEN_HOUSES_FINISHING" to "Отделка деревянных домов, бань, саун",
    "SUBCATEGORY_BRICKWORK" to "Кладочные работы",
    "SUBCATEGORY_ROOFING" to "Кровельные работы",
    "SUBCATEGORY_WELDING_FORGING" to "Сварка, ковка, металлоконструкции",
    "SUBCATEGORY_FOUNDATION_CONCRETE_WORK" to "Фундаментные и бетонные работы",
    "SUBCATEGORY_DIAMOND_DRILLING_CUTTING" to "Алмазное сверление и резка",
    "SUBCATEGORY_DEMOLITION" to "Снос и демонтаж",
    "SUBCATEGORY_FACADE_WORK" to "Фасадные работы",
    "SUBCATEGORY_DESIGN_ESTIMATES" to "Проектирование и сметы",
    "SUBCATEGORY_LABORERS" to "Разнорабочие"
)

object SubcategoryMapper {
    fun SubcategoryDto.toEntity(categoryId: Int): SubcategoryEntity {
        val name = subcategoryMap[code]?: code
        return SubcategoryEntity(
            id = id,
            name = name,
            code = code,
            categoryId = categoryId
        )
    }
    fun SubcategoryEntity.toDomain(): SubcategoryDomain {
        return SubcategoryDomain(
            id = id,
            name = name,
            code = code
        )
    }
    fun map(subcategoryCode: String) : String {
        return subcategoryMap[subcategoryCode]?: subcategoryCode
    }
}

fun subcategoriesDtoListToEntityList(subcategoriesDto: List<SubcategoryDto>, categoryId: Int): List<SubcategoryEntity> {
    return subcategoriesDto.map {
        it.toEntity(categoryId)
    }
}

fun subcategoriesEntityListToDomainList(subcategoriesEntity: List<SubcategoryEntity>): List<SubcategoryDomain> {
    return subcategoriesEntity.map {
        it.toDomain()
    }
}