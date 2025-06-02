package com.example.sellingserviceapp.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE user ADD COLUMN avatar_path TEXT DEFAULT NULL")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `categories` (
                `id` INTEGER PRIMARY KEY,
                `code` TEXT NOT NULL
            )
        """
        )
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // 1. Создаем временную таблицу с правильной схемой
        db.execSQL("""
            CREATE TABLE categories_new (
                id INTEGER PRIMARY KEY NOT NULL,
                code TEXT NOT NULL
            )
        """)

        // 2. Копируем данные из старой таблицы
        db.execSQL("""
            INSERT INTO categories_new (id, code)
            SELECT 
                CASE WHEN id IS NULL THEN 0 ELSE id END, 
                code 
            FROM categories
        """)

        // 3. Удаляем старую таблицу
        db.execSQL("DROP TABLE categories")

        // 4. Переименовываем новую таблицу
        db.execSQL("ALTER TABLE categories_new RENAME TO categories")
    }
}

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Добавляем code в таблицу categories
        db.execSQL("ALTER TABLE categories ADD COLUMN code TEXT NOT NULL DEFAULT ''")

    }
}

val MIGRATION_5_6 = object : Migration(5, 6) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Создаем новую таблицу subcategories
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS `subcategories` (
                `id` INTEGER PRIMARY KEY,
                `code` TEXT NOT NULL,
                `code` TEXT NOT NULL DEFAULT '',
                `category_id` INTEGER NOT NULL,
                FOREIGN KEY (`category_id`) 
                REFERENCES `categories` (`id`) 
                ON DELETE CASCADE
            )
        """)

        // Создаем индекс для ускорения запросов по category_id
        database.execSQL("""
            CREATE INDEX IF NOT EXISTS `index_subcategories_category_id` 
            ON `subcategories` (`category_id`)
        """)
    }
}