import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable

def size = 2

for(def i = 0; i < size; i++){
	def id = UUID.randomUUID().toString();
	def methodName = "TestMethod${i}";
	WS.sendRequestAndVerify(findTestObject('Background Tasks/Create/Create Background Task', [
		('id') : id,
		('methodName') : methodName 
		]))
    }

WS.sendRequestAndVerify(findTestObject('Object Repository/Background Tasks/Delete/Delete Background Tasks all'))

def getTasksResponse = WS.sendRequestAndVerify(findTestObject('Object Repository/Background Tasks/GET/Get Background Tasks all'))
WS.verifyElementsCount(getTasksResponse, '', 0)