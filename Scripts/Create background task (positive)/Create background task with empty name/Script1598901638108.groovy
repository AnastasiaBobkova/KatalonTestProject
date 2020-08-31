import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

// arrange
def id = UUID.randomUUID().toString()

def name = ''

// act
def response = WS.sendRequestAndVerify(findTestObject('Background Tasks/Create/Create Background Task', [('id') : id, ('name') : name]))

//assert
def createdTask = WS.sendRequestAndVerify(findTestObject('Background Tasks/GET/Get Background Task By Id', [('URL') : GlobalVariable.URL
          , ('id') : id]))

WS.verifyResponseStatusCode(response, 200)

WS.verifyElementPropertyValue(createdTask, 'name', 'ForecastForDay')

//clean
WS.sendRequestAndVerify(findTestObject('Background Tasks/Delete/Delete Background Task By Id', [('URL') : GlobalVariable.URL, ('id') : id]))