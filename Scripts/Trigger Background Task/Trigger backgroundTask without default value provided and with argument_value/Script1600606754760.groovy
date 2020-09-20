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
import groovy.json.JsonSlurper as JsonSlurper

// arrange
def backgroundTaskId = UUID.randomUUID().toString()

def argumentDescriptions_isDefaultValueProvided = false

def response = WS.sendRequest(findTestObject('Background Tasks/Create/Create Background Task', [
	  ('id') : backgroundTaskId
	, ('argumentDescriptions_isDefaultValueProvided') : argumentDescriptions_isDefaultValueProvided]))

def argumentValues_value = '5'

// act
def requestTriggerBackgroundTask = findTestObject('Background Tasks/Trigger backgroundTask with argument values'
  , [('backgroundTaskId') : backgroundTaskId, 
	 ('argumentValues_value') : argumentValues_value])
def responseTriggerBackgroundTask = WS.sendRequest(requestTriggerBackgroundTask)

//assert
WS.verifyResponseStatusCode(responseTriggerBackgroundTask, 200)

def requestBackgroundTaskWithScheduleById = findTestObject('Background Tasks/GET/Get Background Task By Id',
	[('id') : backgroundTaskId])

def responseById = WS.sendRequestAndVerify(requestBackgroundTaskWithScheduleById)

WS.verifyElementPropertyValue(responseById, 'schedules[0].argumentValues[0].value', argumentValues_value)

//clean
WS.sendRequestAndVerify(findTestObject('Background Tasks/Delete/Delete Background Task By Id', [('id') : backgroundTaskId]))