package com.steeplesoft.giftbook.database

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(connection: SQLiteConnection) {
        connection.execSQL(
            """
            CREATE TABLE IF NOT EXISTS GiftIdea_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                title TEXT NOT NULL,
                notes TEXT,
                recipientId INTEGER NOT NULL,
                occasionId INTEGER,
                estimatedCost INTEGER NOT NULL,
                actualCost INTEGER,
                FOREIGN KEY(recipientId) REFERENCES Recipient(id) ON UPDATE NO ACTION ON DELETE CASCADE,
                FOREIGN KEY(occasionId) REFERENCES Occasion(id) ON UPDATE NO ACTION ON DELETE SET NULL
            )
            """.trimIndent()
        )
        connection.execSQL(
            """
            INSERT INTO GiftIdea_new(id, title, notes, recipientId, occasionId, estimatedCost, actualCost)
            SELECT id, title, notes, recipientId, occasionId, estimatedCost, actualCost FROM GiftIdea
            """.trimIndent()
        )
        connection.execSQL("DROP TABLE GiftIdea")
        connection.execSQL("ALTER TABLE GiftIdea_new RENAME TO GiftIdea")
        connection.execSQL("CREATE INDEX IF NOT EXISTS index_GiftIdea_recipientId ON GiftIdea(recipientId)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS index_GiftIdea_occasionId ON GiftIdea(occasionId)")
    }
}
