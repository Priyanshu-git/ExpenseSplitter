package com.nexxlabs.expensesplitter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nexxlabs.expensesplitter.data.local.dao.ExpenseDao
import com.nexxlabs.expensesplitter.data.local.dao.ExpenseSplitDao
import com.nexxlabs.expensesplitter.data.local.dao.GroupDao
import com.nexxlabs.expensesplitter.data.local.dao.MemberDao
import com.nexxlabs.expensesplitter.data.local.entity.ExpenseEntity
import com.nexxlabs.expensesplitter.data.local.entity.ExpenseSplitEntity
import com.nexxlabs.expensesplitter.data.local.entity.GroupEntity
import com.nexxlabs.expensesplitter.data.local.entity.MemberEntity

@Database(
    entities = [
        GroupEntity::class,
        MemberEntity::class,
        ExpenseEntity::class,
        ExpenseSplitEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ExpenseSplitterDatabase : RoomDatabase() {

    abstract fun groupDao(): GroupDao
    abstract fun memberDao(): MemberDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun expenseSplitDao(): ExpenseSplitDao
}
