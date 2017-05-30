package com.emeraldElves.alcohollabelproject.Data

/**
 * Created by Kyle on 5/30/2017.
 */
open class AlcoholInfo(var alcoholContent: Double, val fancifulName: String, val brandName: String,
                       val origin: ProductSource, val type: AlcoholType, val serialNumber: String,
                       val formula: String)

class WineInfo(alcoholContent: Double, fancifulName: String, brandName: String, origin: ProductSource, serialNumber: String, formula: String, var pH: Double, var vintageYear: Int, var grapeVarietal: String, var appellation: String) : AlcoholInfo(alcoholContent, fancifulName, brandName, origin, AlcoholType.WINE, serialNumber, formula)

class BeerInfo(alcoholContent: Double, fancifulName: String, brandName: String, origin: ProductSource, serialNumber: String, formula: String) : AlcoholInfo(alcoholContent, fancifulName, brandName, origin, AlcoholType.BEER, serialNumber, formula)

class SpiritInfo(alcoholContent: Double, fancifulName: String, brandName: String, origin: ProductSource, serialNumber: String, formula: String) : AlcoholInfo(alcoholContent, fancifulName, brandName, origin, AlcoholType.DISTILLEDSPIRITS, serialNumber, formula)