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

def backgroundTaskId = UUID.randomUUID().toString()
def name = 'ForecastForDays'
def methodName = 'ForecastForDays'
def declaringTypeFullName = 'Suvoda.TestDomainService.WebApi.WeatherForecast'
def serviceName = 'TestDomainService'
def canReportExecutionProgress = false

WS.sendRequestAndVerify(findTestObject('Background Tasks/Create/Create Background Task', [('id') : backgroundTaskId
			, ('name') : name, ('methodName') : methodName, ('declaringTypeFullName') : declaringTypeFullName
			, ('serviceName') : serviceName, ('canReportExecutionProgress') : canReportExecutionProgress]))

WS.sendRequestAndVerify(findTestObject('Background Tasks/Trigger Background Task', [('taskId') : backgroundTaskId]))

def queryString = "?Filters=backgroundTaskId==$backgroundTaskId"

sleep (50000)

def executionInProgress = WS.sendRequestAndVerify(findTestObject('Object Repository/Background Task Executions/Get by filters/Get Background Task Executions by filters'
	, [('queryString') : queryString]))

WS.verifyElementPropertyValue(executionInProgress, '[0].status', 'Completed')
WS.verifyElementPropertyValue(executionInProgress, '[0].executionProgress', null)

WS.sendRequestAndVerify(findTestObject('Object Repository/Background Tasks/Delete/Delete Background Task By Id', [('id') : backgroundTaskId]))