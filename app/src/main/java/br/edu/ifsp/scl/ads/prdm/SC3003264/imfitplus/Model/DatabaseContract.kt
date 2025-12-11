package br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus.Model

import android.provider.BaseColumns

object DatabaseContract {
    const val DATABASE_NAME = "imfitplus.db"
    const val DATABASE_VERSION = 1

    object UserTable : BaseColumns {
        const val  TABLE_NAME = "user"
        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_AGE = "age"
        const val COLUMN_NAME_GENDER = "gender"
        const val COLUMN_NAME_HEIGHT = "height"
        const val COLUMN_NAME_WEIGHT = "weight"
        const val COLUMN_NAME_ACTIVITY_LEVEL = "activity_level"
    }

    object CalculationHistoryTable : BaseColumns{
        const val TABLE_NAME = "calculation_history"
        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_USER_ID = "user_id" // chave estrangeira para a tabela UserTable
        const val COLUMN_NAME_IMC = "imc"
        const val COLUMN_NAME_IMC_CATEGORY = "imc_category"
        const val COLUMN_NAME_TMB = "tmb"
        const val COLUMN_NAME_IDEAL_WEIGHT = "ideal_weight"
        const val COLUMN_NAME_TIMESTAMP = "timestamp"
    }
}