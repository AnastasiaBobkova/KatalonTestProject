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
def argumentValues_value = '4'

def argumentValues_name = 'x'

def requestTriggerBackgroundTask = findTestObject('Background Tasks/Trigger backgroundTask with argument values'
  , [('backgroundTaskId') : backgroundTaskId, ('argumentValues_name') : argumentValues_name, 
	  ('argumentValues_value') : argumentValues_value])
def responseTriggerBackgroundTask = WS.sendRequest(requestTriggerBackgroundTask)

//assert
WS.verifyResponseStatusCode(responseTriggerBackgroundTask, 200)

def requestBackgroundTaskExecutionById = WS.sendRequestAndVerify(findTestObject('Background Tasks/GET/Get Background Task By Id',
	[('id') : backgroundTaskId]))

def argumentValues = WS.getElementPropertyValue(requestBackgroundTaskExecutionById, 'schedules[0].argumentValues')

boolean isTaskFound = false;
for (def argumentValue : argumentValues) {
	if (argumentValue.value == argumentValues_value && argumentValue.name == argumentValues_name) {
		isTaskFound = true;
		break;
	}
}
assert isTaskFound

//clean
WS.sendRequestAndVerify(findTestObject('Background Tasks/Delete/Delete Background Task By Id'
	, [('id') : backgroundTaskId]))