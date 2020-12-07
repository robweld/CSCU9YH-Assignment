package uk.ac.stir.cs.UnitConversionApp

class ConversionInfo{

    var id : Int = 0
    var category : String = ""
    var convertFrom : String = ""
    var multiplier : Double = 0.0
    var convertTo : String = ""

    /**
     * constructor taking in data that the ConversionInfo is designed to store
     */
    constructor(category:String, convertFrom:String, multiplier:Double, convertTo:String){
        this.category = category
        this.convertFrom = convertFrom
        this.multiplier = multiplier
        this.convertTo = convertTo
    }

    /**
     * empty constructor to declare variables with default data
     */
    constructor(){
    }

    /**
     * overrides toString so the convertTo is returned as this is what
     * is used within the convertTo spinner which is made using
     * ConversionInfo classes
     */
    override fun toString(): String {
        return convertTo
    }

}