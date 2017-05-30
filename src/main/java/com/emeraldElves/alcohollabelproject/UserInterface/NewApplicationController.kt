package com.emeraldElves.alcohollabelproject.UserInterface

import com.emeraldElves.alcohollabelproject.Authenticator
import com.emeraldElves.alcohollabelproject.Data.AlcoholInfo
import com.emeraldElves.alcohollabelproject.Data.AlcoholType
import com.emeraldElves.alcohollabelproject.Data.ProductSource
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.TextField

/**
 * Created by Kyle on 5/30/2017.
 */
class NewApplicationController: IController {

    @FXML
    private lateinit var welcomeApplicantLabel: Label

    @FXML
    private lateinit var alcoholContentField: TextField

    @FXML
    private lateinit var brandNameField: TextField

    @FXML
    private lateinit var alcoholName: TextField

    override fun init(data: Bundle?) {
        welcomeApplicantLabel.text = "Welcome ${Authenticator.getInstance().username}"
    }

    fun exemptionChecked(){

    }

    fun useFormLayout(){

    }

    fun submitImage(){

    }

    fun cancelApp(){}

    fun isAlcoholContentValid(): Boolean {
        val alcoholContent = alcoholContentField.text.toDoubleOrNull()
        if (alcoholContent == null || alcoholContent !in 0..100){
            alcoholContentField.promptText = "Enter a valid percentage from 0 to 100%"
            alcoholContentField.styleClass.add("error")
            return false
        } else {
            alcoholContentField.styleClass.remove("error")
            return true
        }
    }

    fun getAlcoholFields(): AlcoholInfo {

        val alcoholContent: Double

        if (!isAlcoholContentValid()){
            alcoholContent = 0.0
        } else {
            alcoholContent = alcoholContentField.text.toDouble()
        }
        val alcoholInfo = AlcoholInfo(alcoholContent, alcoholName.text, brandNameField.text, ProductSource.DOMESTIC, AlcoholType.BEER, "", "")
        return alcoholInfo
    }

    fun saveApplication(){

    }

    fun submitApp(){
        val alcoholInfo = getAlcoholFields()
        println("${alcoholInfo.fancifulName} - ${alcoholInfo.brandName} ${alcoholInfo.alcoholContent}% alcohol")
    }

    fun distinctiveChecked(){

    }

}