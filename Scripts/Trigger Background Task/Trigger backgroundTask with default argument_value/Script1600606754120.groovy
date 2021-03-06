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
def methodName = 'ForecastForDays'
def declaringTypeFullName = 'Suvoda.TestDomainService.WebApi.WeatherForecast'
def serviceName = 'TestDomainService'
def argumentDescriptions_name = 'daysAmount'
def argumentDescriptions_type = 'System.Int32'
def argumentDescriptions_defaultValue = '3'
def argumentDescriptions_isDefaultValueProvided = true

def response = WS.sendRequest(findTestObject('Background Tasks/Create/Create Background Task', [
	        ('id') : backgroundTaskId
	      , ('methodName') : methodName
		  , ('declaringTypeFullName') : declaringTypeFullName
		  , ('serviceName') : serviceName
		  , ('canReportExecutionProgress'): false
		  , ('argumentDescriptions_name') : argumentDescriptions_name 
		  , ('argumentDescriptions_type') : argumentDescriptions_type  
		  , ('argumentDescriptions_defaultValue') : argumentDescriptions_defaultValue
		  , ('argumentDescriptions_isDefaultValueProvided') : argumentDescriptions_isDefaultValueProvided]))

// act
def requestTriggerBackgroundTask = findTestObject('Background Tasks/Trigger Background Task', [('taskId') : backgroundTaskId])
def responseTriggerBackgroundTask = WS.sendRequestAndVerify(requestTriggerBackgroundTask)

sleep (10000)

def requestExecutionSetting = WS.sendRequestAndVerify(findTestObject('Background Tasks/GET/Get Background Task By Id'
	, [('id') : backgroundTaskId]))

//assert
def executionSettingId = WS.getElementPropertyValue(requestExecutionSetting, 'schedules[0].id')
assert executionSettingId != null
WS.verifyElementPropertyValue(requestExecutionSetting, 'schedules[0].backgroundTaskId', backgroundTaskId)
WS.verifyElementPropertyValue(requestExecutionSetting, 'schedules[0].cronExpression', null)
WS.verifyElementPropertyValue(requestExecutionSetting, 'schedules[0].timeNextRun', null)
WS.verifyElementPropertyValue(requestExecutionSetting, 'schedules[0].status', 'Completed')
WS.verifyElementPropertyValue(requestExecutionSetting, 'schedules[0].isActive', true)
WS.verifyElementPropertyValue(requestExecutionSetting, 'schedules[0].argumentValues[0].name', argumentDescriptions_name)
WS.verifyElementPropertyValue(requestExecutionSetting, 'schedules[0].argumentValues[0].value', argumentDescriptions_defaultValue)

def queryString = "?Filters=backgroundTaskId==$backgroundTaskId"

def executionsResponse = WS.sendRequestAndVerify(findTestObject('Object Repository/Background Task Executions/Get by filters/Get Background Task Executions by filters'
	, [('queryString') : queryString]))

def executionId = WS.getElementPropertyValue(executionsResponse, '[0].id')
assert executionId != null
WS.verifyElementPropertyValue(executionsResponse, '[0].backgroundTaskId', backgroundTaskId)
WS.verifyElementPropertyValue(executionsResponse, '[0].executionSettingId', executionSettingId )

def getTriggeredAt = WS.getElementPropertyValue(executionsResponse,'[0].triggeredAt')
def getStartedAt = WS.getElementPropertyValue(executionsResponse,'[0].startedAt')
def getFinishedAt = WS.getElementPropertyValue(executionsResponse,'[0].finishedAt')
assert getTriggeredAt < getStartedAt && getStartedAt < getFinishedAt

WS.verifyElementPropertyValue(executionsResponse, '[0].status', 'Completed')
WS.verifyElementPropertyValue(executionsResponse, '[0].response', null)
WS.verifyElementPropertyValue(executionsResponse, '[0].executionProgress', null)
WS.verifyElementPropertyValue(executionsResponse, '[0].parentBackgroundTaskExecutionId', null)
WS.verifyElementPropertyValue(executionsResponse, '[0].triggeredBy', 'Client Id')
WS.verifyElementPropertyValue(executionsResponse, '[0].startReason', 'Manual')

//clean
WS.sendRequestAndVerify(findTestObject('Background Tasks/Delete/Delete Background Task By Id', [('id') : backgroundTaskId]))