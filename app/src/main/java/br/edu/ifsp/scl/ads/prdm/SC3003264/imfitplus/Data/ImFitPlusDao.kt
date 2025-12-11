package br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus.Data

import android.content.ContentValues
import android.content.Context
import br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus.Model.CalculationHistory
import br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus.Model.DatabaseContract
import br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus.Model.User

class ImFitPlusDao(context: Context) {
    private val dbHelper = ImFitPlusDbHelper(context)

    fun saveUser(user: User): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.UserTable.COLUMN_NAME_NAME, user.name)
            put(DatabaseContract.UserTable.COLUMN_NAME_AGE, user.age)
            put(DatabaseContract.UserTable.COLUMN_NAME_GENDER, user.gender)
            put(DatabaseContract.UserTable.COLUMN_NAME_HEIGHT, user.height)
            put(DatabaseContract.UserTable.COLUMN_NAME_WEIGHT, user.weight)
            put(DatabaseContract.UserTable.COLUMN_NAME_ACTIVITY_LEVEL, user.activityLevel)
        }
        // Devolve o Id do usuario adicionado
        return db.insert(DatabaseContract.UserTable.TABLE_NAME, null, values)
    }

    fun saveCalculation(calc: CalculationHistory): Long{
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.CalculationHistoryTable.COLUMN_NAME_USER_ID, calc.userId)
            put(DatabaseContract.CalculationHistoryTable.COLUMN_NAME_IMC, calc.imc)
            put(DatabaseContract.CalculationHistoryTable.COLUMN_NAME_IMC_CATEGORY, calc.imcCategory)
            put(DatabaseContract.CalculationHistoryTable.COLUMN_NAME_TMB, calc.tmb)
            put(DatabaseContract.CalculationHistoryTable.COLUMN_NAME_IDEAL_WEIGHT, calc.idealWeight)
            put(DatabaseContract.CalculationHistoryTable.COLUMN_NAME_TIMESTAMP, calc.timestamp)
        }
        return db.insert(DatabaseContract.CalculationHistoryTable.TABLE_NAME, null, values)
    }

    fun getCalculationHistoryForUser(userId: Long): List<CalculationHistory> {
        val db = dbHelper.readableDatabase
        val historyList = mutableListOf<CalculationHistory>()

        val projection = arrayOf(
            DatabaseContract.CalculationHistoryTable.COLUMN_NAME_ID,
            DatabaseContract.CalculationHistoryTable.COLUMN_NAME_USER_ID,
            DatabaseContract.CalculationHistoryTable.COLUMN_NAME_IMC,
            DatabaseContract.CalculationHistoryTable.COLUMN_NAME_IMC_CATEGORY,
            DatabaseContract.CalculationHistoryTable.COLUMN_NAME_TMB,
            DatabaseContract.CalculationHistoryTable.COLUMN_NAME_IDEAL_WEIGHT,
            DatabaseContract.CalculationHistoryTable.COLUMN_NAME_TIMESTAMP
        )
        val selection = "${DatabaseContract.CalculationHistoryTable.COLUMN_NAME_USER_ID} = ?"
        val selectionArgs = arrayOf(userId.toString())
        val sortOrder = "${DatabaseContract.CalculationHistoryTable.COLUMN_NAME_TIMESTAMP} DESC"

        val cursor = db.query(
            DatabaseContract.CalculationHistoryTable.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )

        with(cursor) {
            while (moveToNext()) {
                val item = CalculationHistory(
                    id = getLong(getColumnIndexOrThrow(DatabaseContract.CalculationHistoryTable.COLUMN_NAME_ID)),
                    userId = getLong(getColumnIndexOrThrow(DatabaseContract.CalculationHistoryTable.COLUMN_NAME_USER_ID)),
                    imc = getDouble(getColumnIndexOrThrow(DatabaseContract.CalculationHistoryTable.COLUMN_NAME_IMC)),
                    imcCategory =  getString(getColumnIndexOrThrow(DatabaseContract.CalculationHistoryTable.COLUMN_NAME_IMC_CATEGORY)),
                    tmb = getDouble(getColumnIndexOrThrow(DatabaseContract.CalculationHistoryTable.COLUMN_NAME_TMB)),
                    idealWeight = getDouble(getColumnIndexOrThrow(DatabaseContract.CalculationHistoryTable.COLUMN_NAME_IDEAL_WEIGHT)),
                    timestamp = getLong(getColumnIndexOrThrow(DatabaseContract.CalculationHistoryTable.COLUMN_NAME_TIMESTAMP))
                )
                historyList.add(item)
            }
        }
        cursor.close()
        return historyList

    }
}