import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory as CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.testobject.HttpBodyContent as HttpBodyContent
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.testobject.TestObjectProperty as TestObjectProperty
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import com.kms.katalon.core.testobject.RequestObject as RequestObject
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent as HttpTextBodyContent
import java.util.List as List
import java.util.concurrent.locks.Condition as Condition
import java.util.ArrayList as ArrayList

// arrange
def backgroundTaskId = UUID.randomUUID().toString()

//POST object
def request = (RequestObject)findTestObject('Background Tasks/Create/POST for trigger (Create backgroundTask)')
String body = \
'{\
	"id": "'+backgroundTaskId+'",\
	"name": "BigCake",\
	"description": "Test",\
	"methodName": "ForecastForDaysTwoParameters",\
	"declaringTypeFullName": "Suvoda.TestDomainService.WebApi.WeatherForecast",\
	"serviceName": "TestDomainService",\
	"argumentDescriptions":[\
	{\
		"name": "daysAmount",\
		"type": "System.Int32",\
		"defaultValue": "2",\
		"isDefaultValueProvided": true\
	},{\
		"name": "x",\
		"type": "System.Int32",\
		"defaultValue": "",\
		"isDefaultValueProvided": false\
	}]\
}'

request.setBodyContent(new HttpTextBodyContent(body, 'UTF-8', 'application/json'))

//Make POST request
WS.sendRequest(request)

// act
def requestTriggerBackgroundTask = findTestObject('Background Tasks/Trigger backgroundTask with empty body'
	, [('backgroundTaskId') : backgroundTaskId])
def responseTriggerBackgroundTask = WS.sendRequest(requestTriggerBackgroundTask)

//assert
WS.verifyResponseStatusCode(responseTriggerBackgroundTask, 400)

//clean
WS.sendRequestAndVerify(findTestObject('Background Tasks/Delete/Delete Background Task By Id'
	, [('id') : backgroundTaskId]))