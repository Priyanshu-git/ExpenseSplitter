package com.nexxlabs.expensesplitter.di

import android.content.Context
import androidx.room.Room
import com.nexxlabs.expensesplitter.data.local.ExpenseSplitterDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ExpenseSplitterDatabase =
        Room.databaseBuilder(
            context,
            ExpenseSplitterDatabase::class.java,
            "expense_splitter.db"
        ).build()

    @Provides fun provideGroupDao(db: ExpenseSplitterDatabase) = db.groupDao()
    @Provides fun provideMemberDao(db: ExpenseSplitterDatabase) = db.memberDao()
    @Provides fun provideExpenseDao(db: ExpenseSplitterDatabase) = db.expenseDao()
    @Provides fun provideExpenseSplitDao(db: ExpenseSplitterDatabase) = db.expenseSplitDao()
}
