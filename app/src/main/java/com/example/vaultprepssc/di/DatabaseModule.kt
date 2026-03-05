package com.example.vaultprepssc.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import android.content.Context
import androidx.room.Room
import com.example.vaultprepssc.data.local.VaultDatabase
import com.example.vaultprepssc.data.local.dao.VaultDao
import com.example.vaultprepssc.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): VaultDatabase {
        // Initialize SQLCipher
        SQLiteDatabase.loadLibs(context)
        
        // Use a hardcoded passphrase for now, or fetch from secure storage in production
        val passphrase = SQLiteDatabase.getBytes("vault_prep_passphrase".toCharArray())
        val factory = SupportFactory(passphrase)
        
        return Room.databaseBuilder(
            context,
            VaultDatabase::class.java,
            Constants.DATABASE_NAME
        ).openHelperFactory(factory)
        .build()
    }

    @Provides
    @Singleton
    fun provideVaultDao(database: VaultDatabase): VaultDao {
        return database.vaultDao()
    }
}
