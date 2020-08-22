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

def methodName = 'SortByMsgMethod'

def createBackgroundTaskResponse = WS.sendRequestAndVerify(findTestObject('Background Tasks/Create/Create Background Task', [('id') : backgroundTaskId
		  , ('methodName') : methodName]))

def triggerBackgroundTaskResponse = WS.sendRequestAndVerify(findTestObject('BackgroundTaskExecution/Trigger backgroundTask with full body in request', [('backgroundTaskId') : backgroundTaskId]))

def filterBTEbyBTid = "?Filters=backgroundTaskId==$backgroundTaskId"

def backgroundTaskExecutionResponse = WS.sendRequestAndVerify(findTestObject('BackgroundTaskExecution/Get by filters/Get Background Task Executions by filters', [('queryString') : filterBTEbyBTid]))

def executionId = WS.getElementPropertyValue(backgroundTaskExecutionResponse, '[0].id')

def messages = ['1msg/toSort','msg2to/Sort','msg/3toSort']

for (def i=0;i<messages.size();i++){
	def id = UUID.randomUUID().toString()
	WS.sendRequestAndVerify(findTestObject('Background Task Executions Logs/Create Background Task Executions Log', [('id') : "\"${id}\""
		, ('executionId') : "\"${executionId}\"",('message') : "\"${messages[i]}\""]))
}

def sortByMsg = "?Sorts=message"

// act
def sortByMsgResponse = WS.sendRequestAndVerify(findTestObject('Background Task Executions Logs/Get Background Task Executions Logs', [('queryString') : sortByMsg]))

//assert
def logsCount = WS.getElementsCount(sortByMsgResponse, '')

def boolean isNotSorted = false

for (def i=0;i<logsCount-1;i++){
	if(WS.getElementPropertyValue(sortByMsgResponse, "[${i}].message")>WS.getElementPropertyValue(sortByMsgResponse,"[${i+1}].message")){
		isNotSorted = true
		break
	}
}

assert !isNotSorted

//clean
WS.sendRequestAndVerify(findTestObject('Background Tasks/Delete/Delete Background Task By Id', [('id') : backgroundTaskId]))