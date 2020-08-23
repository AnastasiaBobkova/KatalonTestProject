import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

// arrange
def id = UUID.randomUUID().toString()

def name = 'new'

def description = 'first'

def methodName = 'someMethod'

def declaringTypeFullName = 'Suvoda.SuperNamespace.SuperClass3'

def serviceName = 'SubjectService'

// act
def response = WS.sendRequestAndVerify(findTestObject('Background Tasks/Create/Create Background Task with random order of keys', [('id') : id
            , ('name') : name, ('description') : description, ('methodName') : methodName, ('declaringTypeFullName') : declaringTypeFullName
            , ('serviceName') : serviceName]))

//assert
def createdTask = WS.sendRequestAndVerify(findTestObject('Background Tasks/GET/Get Background Task By Id', [('URL') : GlobalVariable.URL
            , ('id') : id]))

WS.verifyElementPropertyValue(createdTask, 'name', 'new')

WS.verifyElementPropertyValue(createdTask, 'description', 'first')

//clean
WS.sendRequestAndVerify(findTestObject('Background Tasks/Delete/Delete Background Task By Id', [('URL') : GlobalVariable.URL, ('id') : id]))