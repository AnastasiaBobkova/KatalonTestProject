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

// arrange
def backgroundTaskId = UUID.randomUUID().toString()

def methodName = 'FilterByNotEqlExecIdMethod'

def createBackgroundTaskResponse = WS.sendRequestAndVerify(findTestObject('Background Tasks/Create/Create Background Task', [('id') : backgroundTaskId
		  , ('methodName') : methodName]))

def triggerBackgroundTaskResponse = WS.sendRequestAndVerify(findTestObject('BackgroundTaskExecution/Trigger backgroundTask with full body in request', [('backgroundTaskId') : backgroundTaskId]))

def filterBTEbyBTid = "?Filters=backgroundTaskId==$backgroundTaskId"

def backgroundTaskExecutionResponse = WS.sendRequestAndVerify(findTestObject('BackgroundTaskExecution/Get by filters/Get Background Task Executions by filters', [('queryString') : filterBTEbyBTid]))

def executionId = WS.getElementPropertyValue(backgroundTaskExecutionResponse, '[0].id')

def logId = UUID.randomUUID().toString()

WS.sendRequestAndVerify(findTestObject('Background Task Executions Logs/Create Background Task Executions Log', [('id') : "\"${logId}\""
	, ('executionId') : "\"${executionId}\""]))

def filterLogsByNotEqlExecId = "?Filters=backgroundTaskExecutionId!=$executionId"

// act
def filterByNotEqlExecIdResponse = WS.sendRequestAndVerify(findTestObject('Background Task Executions Logs/Get Background Task Executions Logs', [('queryString') : filterLogsByNotEqlExecId]))

//assert
def totalElementsCount = WS.getElementsCount(filterByNotEqlExecIdResponse, '')

def boolean isLogFound = false
for (def i=0;i<totalElementsCount;i++){
		if (WS.getElementPropertyValue(filterByNotEqlExecIdResponse, "[${i}].backgroundTaskExecutionId")==executionId){
		isLogFound=true
		break
	}
}
assert !isLogFound

//clean
WS.sendRequestAndVerify(findTestObject('Background Tasks/Delete/Delete Background Task By Id', [('id') : backgroundTaskId]))