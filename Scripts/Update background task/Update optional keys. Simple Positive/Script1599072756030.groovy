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
import java.lang.String as String
import java.lang.StringBuilder as StringBuilder
import java.util.UUID as UUID
import com.fasterxml.jackson.annotation.ObjectIdGenerators$UUIDGenerator as UUIDGenerator

def id = UUID.randomUUID().toString()

def name = 'new background task'

def description = 'first one'

def methodName = 'Method'

def declaringTypeFullName = 'Suvoda.SuperNamespace.SuperClass233'

def serviceName = 'SubjectService'
def NewName = 'NewName123'
def newNameToSend = "\"NewName123\""
def NewDescription = 'NewDescription'
def newDescriptionToSend = "\"NewDescription\""

def createBackgroundTaskResponse = WS.sendRequestAndVerify(findTestObject('Background Tasks/Create/Create Background Task', [('URL') : GlobalVariable.URL
	, ('id') : id, ('name') : name, ('description') : description, ('methodName') : methodName, ('declaringTypeFullName') : declaringTypeFullName
	, ('serviceName') : serviceName]))


def response = WS.sendRequest(findTestObject('Background Tasks/PUT/Update optional fields', [('URL') : GlobalVariable.URL, ('id') : id
	, ('name') : newNameToSend, ('description') : newDescriptionToSend]))


def createdTask = WS.sendRequestAndVerify(findTestObject('Background Tasks/GET/Get Background Task By Id', [('URL') : GlobalVariable.URL
	, ('id') : id]))

WS.verifyElementPropertyValue(createdTask, 'name', NewName)

WS.verifyElementPropertyValue(createdTask, 'description', NewDescription)

WS.sendRequestAndVerify(findTestObject('Background Tasks/Delete/Delete Background Task By Id', [('URL') : GlobalVariable.URL
	, ('id') : id]))