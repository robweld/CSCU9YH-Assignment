package uk.ac.stir.cs.UnitConversionApp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.page3_fragment.*
import kotlinx.android.synthetic.main.text_input_dialog.view.*


class Page3Fragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.page3_fragment, container, false)
    }

    /**
     * overrides onCreateView method with the page3_fragment.xml layout
     * and sets up the buttons with listeners
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        val context = getActivity()!!.getApplicationContext()
        var db = DatabaseHandler(context)
        displayData(db)

        btn_insert.setOnClickListener {
            var data = loadConversionData(db,"-1")
            if (data != null) {
                db.insertData(data)
                displayData(db)
            }
        }

        btn_update.setOnClickListener {
            showDialogBox("Update",db)
        }

        btn_delete.setOnClickListener {
            showDialogBox("Delete",db)
        }

        btn_deleteAll.setOnClickListener {
            db.deleteAll()
            displayData(db)
        }
    }

    /**
     * overrides onCreateView method with the page3_fragment.xml layout
     * and sets up the buttons with listeners
     */
    private fun loadConversionData(db: DatabaseHandler, id: String): ConversionInfo? {
        var data: ConversionInfo? = null
        if (etvCategory.text.toString().isNotEmpty() &&
            etvConvertFrom.text.toString().isNotEmpty() &&
            etvMultiplier.text.toString().isNotEmpty() &&
            etvConvertTo.text.toString().isNotEmpty()) {
            if(etvConvertFrom.text.toString() != etvConvertTo.text.toString()){
                if(db.readData(" WHERE id != \'" + id + "\' AND convertFrom = \'" + etvConvertFrom.text.toString() +
                            "\' AND convertTo = \'" + etvConvertTo.text.toString() + "\'", "id").isEmpty()){
                    data = ConversionInfo(etvCategory.text.toString(),etvConvertFrom.text.toString(),
                        etvMultiplier.text.toString().toDouble(),etvConvertTo.text.toString())
                }else{
                    Toast.makeText(context,"Conversion already exists", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(context,"Convert from and convert to need to be different"
                    , Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(context,"Please fill all data", Toast.LENGTH_SHORT).show()
        }
        return data
    }

    /**
     * displays a custom dialog box asking for the user to enter a number corresponding
     * to an id in the database and calls either updatehandler or deletehandler depending
     * on what button was clicked
     */
    private fun showDialogBox(text: String, db: DatabaseHandler){
        val dialogView = LayoutInflater.from(activity).inflate(R.layout.text_input_dialog, null)
        dialogView.txtField.setHint("Enter ID of conversion to be " + text + "d")
        val dialogBoxBuilder = activity?.let { AlertDialog.Builder(it).setView(dialogView) }
        val  dialogBoxInstance = dialogBoxBuilder?.show()

        dialogView.btn_okay.setOnClickListener {
            var response = dialogView.txtField.text.toString()
            dialogBoxInstance?.dismiss()
            if(text.equals("Update")){
                updateHandler(response,db)
            }
            if(text.equals("Delete")){
                deleteHandler(response,db)
            }
        }

        dialogView.btn_cancel.setOnClickListener {
            dialogBoxInstance?.dismiss()
        }
    }

    /**
     * if id is valid and all the checks performed when inserting new
     * data is passed in the loadConversion method then updates the
     * row in the database with that id with the data entered
     */
    private fun updateHandler(response: String, db: DatabaseHandler) {
        if(isValidID(response,db)){
            var data = loadConversionData(db,response)
            if (data != null) {
                data.id = response.toInt()
                db.updateData(data)
                displayData(db)
            }
        }
    }

    /**
     * if id is valid delete row in the database with the id sent in
     */
    private fun deleteHandler(response: String, db: DatabaseHandler) {
        if(isValidID(response,db)){
            db.deleteRow(response)
            displayData(db)
        }
    }

    /**
     * checks if the id entered exists in the database or if they clicked okay on
     * the input dialog without entering a number
     */
    private fun isValidID(response: String, db: DatabaseHandler): Boolean {
        var isValid = false
        if(response.isNotEmpty()){
            var list = db.readData(" WHERE id = " + response,"id")
            if (list.isNotEmpty()){
                isValid = true
            }else{
                Toast.makeText(context,"ID does not exist", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(context,"No ID entered", Toast.LENGTH_SHORT).show()
        }
        return isValid
    }

    /**
     * displays all the rows in the database to a scrollable text area
     */
    private fun displayData(db: DatabaseHandler) {
        var data = db.getConversionInfo("")
        tvResult.text = ""
        for (i in 0 until data.size) {
            tvResult.append(data[i].id.toString() + " " + data[i].category + " " + data[i].convertFrom +
                    " " + data[i].multiplier + " " + data[i].convertTo + "\n")
        }
    }
}