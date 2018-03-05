package com.emeraldElves.alcohollabelproject.Data

/**
 * Created by Kyle on 5/30/2017.
 */
//open class AlcoholInfo(var alcoholContent: Double, val fancifulName: String, val brandName: String,
//                       val origin: ProductSource, val type: AlcoholType, val serialNumber: String,
//                       val formula: String)

open class AlcoholInfo(val brandName: String, val type: AlcoholType, val serialNumber: String, val origin: ProductSource){
    var fancifulName: String = ""
    var formula: String = ""
    var alcoholContent: Double = 0.0
    var id: Long = -1

    companion object {
        val DB_TABLE = "alcohol"
        val DB_BRAND_NAME = "brand_name"
        val DB_ALCOHOL_TYPE = "alcohol_type"
        val DB_SERIAL_NUMBER = "serial_number"
        val DB_ORIGIN = "origin"
        val DB_ALCOHOL_CONTENT = "alcohol_content"
        val DB_FANCICUL_NAME = "fanciful_name"
        val DB_ID = "id"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AlcoholInfo

        if (brandName != other.brandName) return false
        if (type != other.type) return false
        if (serialNumber != other.serialNumber) return false
        if (origin != other.origin) return false
        if (fancifulName != other.fancifulName) return false
        if (formula != other.formula) return false
        if (alcoholContent != other.alcoholContent) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = brandName.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + serialNumber.hashCode()
        result = 31 * result + origin.hashCode()
        result = 31 * result + fancifulName.hashCode()
        result = 31 * result + formula.hashCode()
        result = 31 * result + alcoholContent.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }


}

//class WineInfo(alcoholContent: Double, fancifulName: String, brandName: String, origin: ProductSource, serialNumber: String, formula: String, var pH: Double, var vintageYear: Int, var grapeVarietal: String, var appellation: String) : AlcoholInfo(alcoholContent, fancifulName, brandName, origin, AlcoholType.WINE, serialNumber, formula)
//
//class BeerInfo(alcoholContent: Double, fancifulName: String, brandName: String, origin: ProductSource, serialNumber: String, formula: String) : AlcoholInfo(alcoholContent, fancifulName, brandName, origin, AlcoholType.BEER, serialNumber, formula)
//
//class SpiritInfo(alcoholContent: Double, fancifulName: String, brandName: String, origin: ProductSource, serialNumber: String, formula: String) : AlcoholInfo(alcoholContent, fancifulName, brandName, origin, AlcoholType.DISTILLEDSPIRITS, serialNumber, formula)