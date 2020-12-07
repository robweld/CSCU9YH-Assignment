package uk.ac.stir.cs.UnitConversionApp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

class Page1Fragment : Fragment() {
    private var selectedCategory : String = ""
    private var selectedConvertFrom : String = ""
    private var categoryCache : Int = 0
    private var convertFromCache : Int = 0
    private var convertToCache : Int = 0

    /**
     * overrides onCreateView method with the page1_fragment.xml layout
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.page1_fragment, container, false)
    }

    /**
     * overrides onViewCreated sets up the page1fragment spinners and creates
     * listeners for each one to respond to the user changes the item selected
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = getActivity()!!.getApplicationContext()
        val db = DatabaseHandler(context)
        val model = ViewModelProviders.of(activity!!).get(ConversionViewModel::class.java)

        var categoryArray: ArrayAdapter<String> = ArrayAdapter(context, android.R.layout.simple_spinner_item)
        categoryArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var categorySpinner = view.findViewById(R.id.categorySpinner) as Spinner
        categorySpinner.adapter = categoryArray

        var convertFromArray: ArrayAdapter<String> = ArrayAdapter(context, android.R.layout.simple_spinner_item)
        convertFromArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var convertFromSpinner = view.findViewById(R.id.convertFromSpinner) as Spinner
        convertFromSpinner.adapter = convertFromArray

        var convertToArray: ArrayAdapter<String> = ArrayAdapter(context, android.R.layout.simple_spinner_item)
        convertToArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var convertToSpinner = view.findViewById(R.id.convertToSpinner) as Spinner
        convertToSpinner.adapter = convertToArray

        updateArrays(db,categoryArray,convertFromArray,convertToArray,model)

        categorySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            /**
             * overrides onItemSelected to get the position selected by the user and updates the array adapters of the
             * other spinners
             */
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                categoryCache = position
                updateArrays(db,categoryArray,convertFromArray,convertToArray,model)
                categorySpinner.setSelection(categoryCache)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        convertFromSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            /**
             * overrides onItemSelected to get the position selected by the user and updates the array adapters of the
             * other spinners
             */
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                convertFromCache = position
                updateConversionArrays(db,convertFromArray,convertToArray,model)
                convertFromSpinner.setSelection(convertFromCache)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        convertToSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            /**
             * overrides onItemSelected to get the position selected by the user and updates the array adapters of the
             * other spinners
             */
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                convertToCache = position
                updateConversionArrays(db,convertFromArray,convertToArray,model)
                convertToSpinner.setSelection(convertToCache)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

    /**
     * creates and sends a query to the database asking for all the different categories stored in the database
     * to be displayed in the spinner and sets up the the selected category query for the other two spinners to
     * be updated with what the current category selected is on
     * @param db the refrence to the database
     * @param categoryArray the list of categories in the database to be updated
     * @param convertFromArray the list of conversions in the database that are under the category selected or about to be
     *updated to the category selected
     * @param convertToArray the list of ConversionInfo's that correspond to the other two selected spinners or about to be
     * updated to correspond to the other selected value in the other two spinners
     * @param model the link to the page2 fragments that the selected ConversionInfo will be sent through
     */
    private fun updateArrays(db: DatabaseHandler, categoryArray: ArrayAdapter<String>,
                             convertFromArray: ArrayAdapter<String>, convertToArray: ArrayAdapter<String>,
                             model: ConversionViewModel) {
        categoryArray.clear()
        var list = db.readData(" GROUP BY category","category")
        if (list.isNotEmpty()){
            if (categoryCache > list.size){
                categoryCache = 0
            }
            selectedCategory = " WHERE category = \'" + list[categoryCache] + "\'"
            for(i in list){
                categoryArray.add(i)
            }
            updateConversionArrays(db,convertFromArray,convertToArray,model)
        }else{
            selectedCategory = ""
            categoryArray.add("Add conversions to the database")
            convertFromArray.add("Add conversions to the database")
            convertToArray.add("Add conversions to the database")
        }
    }

    /**
     * creates and sends a query to the database asking for all the different categories stored in the database
     * to be displayed in the spinner and sets up the the selected category query for the converTo spinner to
     * be updated with what the current category selected is on and the current converFrom is selected
     * @param db the refrence to the database
     * @param convertFromArray the list of conversions in the database that are under the category selected or about to be
     *updated to the category selected
     * @param convertToArray the list of ConversionInfo's that correspond to the other two selected spinners or about to be
     * updated to correspond to the other selected value in the other two spinners
     * @param model the link to the page2 fragments that the selected ConversionInfo will be sent through
     */
    private fun updateConversionArrays(db: DatabaseHandler, convertFromArray: ArrayAdapter<String>,
                                       convertToArray: ArrayAdapter<String>, model: ConversionViewModel) {
        convertFromArray.clear()
        var selection = selectedCategory + " GROUP BY convertFrom"
        var list = db.readData(selection,"convertFrom")
        if (list.isNotEmpty()){
            if (convertFromCache > list.size){
                convertFromCache = 0
            }
            selectedConvertFrom = " AND convertFrom = \'" + list[convertFromCache] + "\'"
            for(i in list){
                convertFromArray.add(i)
            }

            updateConvertTo(db,convertToArray,model)
        }else{
            convertToArray.clear()
            selectedConvertFrom = ""
            convertFromArray.add("Add conversions to the database")
            convertToArray.add("Add conversions to the database")
        }
    }

    /**
     * creates and sends a query to the database asking for the ConversionInfo's that match the queries created by the other two spinners
     * @param db the refrence to the database
     * @param convertToArray the list of ConversionInfo's that correspond to the other two selected spinners or about to be
     * updated to correspond to the other selected value in the other two spinners
     * @param model the link to the page2 fragments that the selected ConversionInfo will be sent through
     */
    private fun updateConvertTo(db: DatabaseHandler, convertToArray: ArrayAdapter<String>, model: ConversionViewModel){
        convertToArray.clear()
        var connversionInfoList = db.getConversionInfo(selectedCategory + selectedConvertFrom)
        if (convertToCache > connversionInfoList.size){
            convertToCache = 0
        }
        for(i in connversionInfoList){
            convertToArray.add(i.convertTo.toString())
        }

        model.selectedConversion.setValue(connversionInfoList[convertToCache])
    }
}