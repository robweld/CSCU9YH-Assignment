package uk.ac.stir.cs.UnitConversionApp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASE_NAME ="conversionDB"
val TABLE_NAME="conversionInfo"

val COL_ID = "id"
val COL_CATEGORY = "category"
val COL_CONVERT_FROM = "convertFrom"
val COL_MULTIPLIER = "multiplier"
val COL_CONVERT_TO = "convertTo"

/**
 * Creates the table with all the columns that correspond to the data points
 * stored within ConversionInfo
 */
class DatabaseHandler(var context: Context?) : SQLiteOpenHelper(context,DATABASE_NAME,null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE " + TABLE_NAME +" (" +
                COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_CATEGORY + " VARCHAR(256)," +
                COL_CONVERT_FROM + " VARCHAR(256)," +
                COL_MULTIPLIER +" FLOAT," +
                COL_CONVERT_TO +" VARCHAR(256))"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?,oldVersion: Int,newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * takes ConversionInfo and uses it to populate a new row
     * in the database
     */
    fun insertData(conversionInfo : ConversionInfo){
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_CATEGORY,conversionInfo.category)
        cv.put(COL_CONVERT_FROM,conversionInfo.convertFrom)
        cv.put(COL_MULTIPLIER,conversionInfo.multiplier)
        cv.put(COL_CONVERT_TO,conversionInfo.convertTo)
        var result = db.insert(TABLE_NAME,null,cv)
        if(result == -1.toLong())
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
    }

    /**
     * recieves a query and returns the ConversionInfo's that match that
     * request
     */
    fun getConversionInfo(selection: String) : MutableList<ConversionInfo>{
        var list : MutableList<ConversionInfo> = ArrayList()

        val db = this.readableDatabase
        var query = "Select * from " + TABLE_NAME + selection
        val result = db.rawQuery(query,null)
        if(result.moveToFirst()){
            do {
                var conversionInfo = ConversionInfo()
                conversionInfo.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                conversionInfo.category = result.getString(result.getColumnIndex(COL_CATEGORY))
                conversionInfo.convertFrom = result.getString(result.getColumnIndex(COL_CONVERT_FROM))
                conversionInfo.multiplier = result.getString(result.getColumnIndex(COL_MULTIPLIER)).toDouble()
                conversionInfo.convertTo = result.getString(result.getColumnIndex(COL_CONVERT_TO))
                list.add(conversionInfo)
            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }

    /**
     * recieves a query and returns a list of whatever column is being asked
     * for matches it
     */
    fun readData(selection: String, what: String) : MutableList<String>{
        var list : MutableList<String> = ArrayList()

        val db = this.readableDatabase
        var query = "Select * from " + TABLE_NAME + selection
        val result = db.rawQuery(query,null)
        if(result.moveToFirst()){
            do {
                list.add(result.getString(result.getColumnIndex(what)))
            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return list
    }

    /**
     * deletes all rows in the database and resets the autoincrement id
     */
    fun deleteAll(){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.execSQL("DELETE FROM sqlite_sequence WHERE NAME = \'" + TABLE_NAME + "\' ")
        db.close()
    }

    /**
     * deletes row with the specified id
     */
    fun deleteRow(id : String){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "id = ?", arrayOf(id))
    }

    /**
     * updates row with the specified id
     */
    fun updateData(conversionInfo : ConversionInfo) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_CATEGORY,conversionInfo.category)
        cv.put(COL_CONVERT_FROM,conversionInfo.convertFrom)
        cv.put(COL_MULTIPLIER,conversionInfo.multiplier)
        cv.put(COL_CONVERT_TO,conversionInfo.convertTo)
        var result = db.update(TABLE_NAME,cv,"id = ?", arrayOf(conversionInfo.id.toString()))
        if(result == 1)
            Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
    }
}