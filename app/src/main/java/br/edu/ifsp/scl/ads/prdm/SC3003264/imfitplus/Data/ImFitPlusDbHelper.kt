package br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus.Data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.edu.ifsp.scl.ads.prdm.SC3003264.imfitplus.Model.DatabaseContract

class ImFitPlusDbHelper(context: Context) : SQLiteOpenHelper(
    context,
    DatabaseContract.DATABASE_NAME,
    null,
    DatabaseContract.DATABASE_VERSION
) {
    private val SQL_CREATE_USER_TABLE = """
        CREATE TABLE ${DatabaseContract.UserTable.TABLE_NAME} (
            ${DatabaseContract.UserTable.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${DatabaseContract.UserTable.COLUMN_NAME_NAME} TEXT NOT NULL,
            ${DatabaseContract.UserTable.COLUMN_NAME_AGE} INTEGER NOT NULL,
            ${DatabaseContract.UserTable.COLUMN_NAME_GENDER} TEXT NOT NULL,
            ${DatabaseContract.UserTable.COLUMN_NAME_HEIGHT} REAL NOT NULL,
            ${DatabaseContract.UserTable.COLUMN_NAME_WEIGHT} REAL NOT NULL,
            ${DatabaseContract.UserTable.COLUMN_NAME_ACTIVITY_LEVEL} TEXT NOT NULL
        )    
    """.trimIndent()

    private val SQL_CREATE_CALCULATION_HISTORY_TABLE = """
        CREATE TABLE ${DatabaseContract.CalculationHistoryTable.TABLE_NAME} (
            ${DatabaseContract.CalculationHistoryTable.COLUMN_NAME_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${DatabaseContract.CalculationHistoryTable.COLUMN_NAME_USER_ID} INTEGER NOT NULL,
            ${DatabaseContract.CalculationHistoryTable.COLUMN_NAME_IMC} REAL NOT NULL,
            ${DatabaseContract.CalculationHistoryTable.COLUMN_NAME_IMC_CATEGORY} TEXT NOT NULL,
            ${DatabaseContract.CalculationHistoryTable.COLUMN_NAME_TMB} REAL NOT NULL,
            ${DatabaseContract.CalculationHistoryTable.COLUMN_NAME_IDEAL_WEIGHT} INTEGER NOT NULL,
            ${DatabaseContract.CalculationHistoryTable.COLUMN_NAME_TIMESTAMP} INTEGER NOT NULL,
            FOREIGN KEY(${DatabaseContract.CalculationHistoryTable.COLUMN_NAME_USER_ID}) REFERENCES ${DatabaseContract.UserTable.TABLE_NAME}(${DatabaseContract.UserTable.COLUMN_NAME_ID})
        )
    """.trimIndent()

    // Usado para apagar as tabelas do banco de dados
    private val SQL_DELETE_USER_TABLE = "DROP TABLE IF EXISTS ${DatabaseContract.UserTable.TABLE_NAME}"
    private val SQL_DELETE_CALCULATION_HISTORY_TABLE = "DROP TABLE IF EXISTS ${DatabaseContract.CalculationHistoryTable.TABLE_NAME}"

    //  Metodo chamado quando o banco de dados é criado pela primeira vez
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_USER_TABLE)
        db.execSQL(SQL_CREATE_CALCULATION_HISTORY_TABLE)
    }

    // Metodo chamado quando a versao do banco de dados for atualizada | ao usar apaga todos os dados das tabelas para recriar as mesmas do zero sem dados de usuario.
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_CALCULATION_HISTORY_TABLE)
        db.execSQL(SQL_DELETE_USER_TABLE)
        onCreate(db)
    }

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        db.setForeignKeyConstraintsEnabled(true)
    }
}