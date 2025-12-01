package com.example.app_pasteleria.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.app_pasteleria.data.dao.CatalogoDao
import com.example.app_pasteleria.data.dao.UsuarioDao
import com.example.app_pasteleria.data.model.Catalogo
import com.example.app_pasteleria.data.model.Usuario

@Database(
    entities = [Catalogo::class, Usuario::class],
    version = 2,
    exportSchema = false
)
abstract class CatalogoDatabase: RoomDatabase(){
    abstract fun CatalogoDao(): CatalogoDao
    abstract fun UsuarioDao(): UsuarioDao

    companion object{
        private var INSTANCE: CatalogoDatabase? = null

        fun getDatabase(context: Context): CatalogoDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CatalogoDatabase::class.java,
                    "producto_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}