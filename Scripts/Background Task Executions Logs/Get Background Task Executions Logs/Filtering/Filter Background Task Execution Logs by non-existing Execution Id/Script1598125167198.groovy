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

def executionsResponse = WS.sendRequestAndVerify(findTestObject('BackgroundTaskExecution/Get by filters/Get Background Task Executions by filters'))

def executionsCount = WS.getElementsCount(executionsResponse, '')

def executionId

if (executionsCount!=0){
	def boolean isFound = true
	while (isFound){
		executionId = UUID.randomUUID().toString()
		isFound = false
		for (def i=0;i<executionsCount;i++){
			if (WS.getElementPropertyValue(executionsResponse, "[${i}].id")==executionId){
				isFound = true
				break
			}
		}
	}
}
else {
	executionId = UUID.randomUUID().toString()
}

def queryString = "?Filters=backgroundTaskExecutionId==$executionId"

def getLogsResponse = WS.sendRequest(findTestObject('Background Task Executions Logs/Get Background Task Executions Logs', [('queryString') : queryString]))

WS.verifyResponseStatusCode(getLogsResponse, 200)

assert WS.getElementsCount(getLogsResponse, '')== 0