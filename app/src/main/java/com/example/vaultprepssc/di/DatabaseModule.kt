package com.example.vaultprepssc.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import android.content.Context
import androidx.room.Room
import com.example.vaultprepssc.data.local.VaultDatabase
import com.example.vaultprepssc.data.local.dao.VaultDao
import com.example.vaultprepssc.util.Constants
import com.example.vaultprepssc.util.SecurityManager
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        securityManager: SecurityManager
    ): VaultDatabase {
        // Initialize SQLCipher
        SQLiteDatabase.loadLibs(context)
        
        val passphrase = SQLiteDatabase.getBytes(securityManager.getDatabasePassphrase().toCharArray())
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
