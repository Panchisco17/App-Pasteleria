package com.example.app_pasteleria.data.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.app_pasteleria.data.dao.CatalogoDao
import com.example.app_pasteleria.data.model.Catalogo

@Database(
    entities = [Catalogo::class],
    version = 1,
    exportSchema = false // evite warning
)

abstract class CatalogoDatabase: RoomDatabase(){
    abstract fun CatalogoDao(): CatalogoDao

    companion object{
        private var INSTANCE: CatalogoDatabase? = null

        fun getDatabase(context: Context): CatalogoDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CatalogoDatabase::class.java,
                    "producto_database"
                ).build()
                INSTANCE = instance
                instance
            } // fin return
        } // fin getDatabase
    } // fin companion
} // fin abstract