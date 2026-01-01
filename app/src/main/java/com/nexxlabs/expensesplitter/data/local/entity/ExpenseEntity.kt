package com.nexxlabs.expensesplitter.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "expenses",
    foreignKeys = [
        ForeignKey(
            entity = GroupEntity::class,
            parentColumns = ["id"],
            childColumns = ["groupId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MemberEntity::class,
            parentColumns = ["id"],
            childColumns = ["paidByMemberId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [
        Index("groupId"),
        Index("paidByMemberId")
    ]
)
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val groupId: Long,
    val title: String,
    val amount: Double,
    val paidByMemberId: Long,
    val createdAt: Long = System.currentTimeMillis()
)
